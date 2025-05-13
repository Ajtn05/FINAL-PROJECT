import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;

public class GameFrame extends JComponent {
    private JFrame frame;
    private GameCanvas gc;
    private Player player1, player2;
    private Timer timer;
    private Map map;
    private Socket socket;
    private int playerID;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;
    private LevelManager lm;
    private String playerType;
    private Boolean startTraps = false;

    private boolean left, right, up, down, hasKey, opensDoor = false, dead;
    private int x, y, keys, lives;


    public GameFrame(GameCanvas gc, LevelManager lm, String playerType){
        this.playerType = playerType;
        frame = new JFrame();
        this.gc = gc;
        map = new Map("assets/maps/tileMap1.txt");
        this.lm = lm;
    }

    public void setUpGUI(){
        gc.setPreferredSize(new Dimension(1024, 768));
        createPlayers();
        frame.add(gc);
        frame.setTitle("Maze Game - Player " + playerID + " " + playerType);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setFocusable(true);
        frame.setVisible(true);
        gc.requestFocusInWindow();
    }

    public void createPlayers() {
        String p1playerType = null, p2playerType = null;
        if (playerID == 1) {
            p1playerType = playerType;
            if (p1playerType.equals("boy")) {
                p1playerType = "boy";
                p2playerType = "girl";
            }
            else if (p1playerType.equals("girl")) {
                p1playerType = "girl";
                p2playerType = "boy";
            }
            player1 = new Player(1, 62, this, gc, p1playerType, lm);
            player2 = new Player(4, 62, this, gc, p2playerType, lm);
            gc.setPlayer(player1, player2);
        }

        else if (playerID == 2) {
            p2playerType = playerType;
            if (p2playerType.equals("boy")) {
                p2playerType = "boy";
                p1playerType = "girl";
            }
            else if (p2playerType.equals("girl")) {
                p2playerType = "girl";
                p1playerType = "boy";
            }
            player1 = new Player(4, 62, this, gc, p2playerType, lm);
            player2 = new Player(1, 62, this, gc, p1playerType, lm);
            gc.setPlayer(player1, player2);
        }
    }

    public void startGameTimer(){
        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                ArrayList<Obstacle> obstacleCopy = new ArrayList<>(lm.getObstacles());
                for (Obstacle obstacle : obstacleCopy) {
                    obstacle.checkCollision(player1, gc.getMap(), gc);
                    obstacle.checkCollision(player2, gc.getMap(), gc);
                    if (obstacle instanceof Traps traps && startTraps){
                        traps.updateSpriteAnimation();
                    }
                } 
                if (keys > 0) {
                    gc.checkKeys(player2);
                }
                if (opensDoor) {
                    gc.checkLocks(player2); // here
                }

                player1.update(gc.getMap());
                player2.update(gc.getMap());
                gc.getPopUps().update();
                gc.repaint();
            }
        });
        timer.start();
    }

    public void levelComplete() {
        if (player1.levelCompleted() && player2.levelCompleted()) {
            lm.addLevel();
            createPlayers();
        }
    }

    public void gameReset() {
        createPlayers();
        player2.setLives(5);
    }
  
    //SERVER COMMUNICATION
    private class ReadFromServer implements Runnable {
        private DataInputStream dataIn;
        
        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable created");
        }

        public void run(){
            try {
                while (true) { 
                    if (player2 != null) {
                        String message = dataIn.readUTF();
                        String[] packets = message.split(",");

                        byte booleans = Byte.parseByte(packets[0]);
                        x = Integer.parseInt(packets[1]);
                        y = Integer.parseInt(packets[2]);
                        keys = Integer.parseInt(packets[3]);
                        lives = Integer.parseInt(packets[4]);

                        left      = (booleans & (1 << 0)) != 0;
                        right     = (booleans & (1 << 1)) != 0;
                        up        = (booleans & (1 << 2)) != 0;
                        down      = (booleans & (1 << 3)) != 0;
                        opensDoor = (booleans & (1 << 4)) != 0;
                        startTraps = (booleans & (1 << 5)) != 0;

                        player2.moveLeft(left);
                        player2.moveRight(right);
                        player2.moveUp(up);
                        player2.moveDown(down);
                        player2.setLives(lives);
                        player2.setPosition(x, y);

                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted Exception from WTS run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFS run()");
            }
        }
        
        public void waitForStartMsg(){
            try {
                String startMsg = dataIn.readUTF();
                System.out.println("Message from server: " + startMsg);
                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();
            } catch (IOException e) {
                System.out.println("IOException from waitForStartMsg()");
            }
        }
    }

    private class WriteToServer implements Runnable {
        private DataOutputStream dataOut;
        
        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable created");
        }

        public void run(){
            try {
                while (true) { 
                    if(player1 != null) {
                        byte booleans = 0;
                        //i love bitwise OR
                        if (player1.getLeft()) booleans  |= 1 << 0;
                        if (player1.getRight()) booleans |= 1 << 1;
                        if (player1.getUp()) booleans    |= 1 << 2;
                        if (player1.getDown()) booleans  |= 1 << 3;
                        if (player1.opensDoor()) booleans|= 1 << 4;
                        if (startTraps) booleans         |= 1 << 5;

                        x = player1.getX();
                        y = player1.getY();
                        keys = player1.keys.size();
                        lives = player1.getLives();

                        String message = booleans + "," + x + "," + y + "," + keys + "," + lives;
                        dataOut.writeUTF(message);
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted Exception from WTS run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTS run()");
            }
        }
    }

    public boolean connectToServer(String host, int port, String playerType, MenuFrame mf) {
        try {
            socket = new Socket(host, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("You are player#" + playerID);
            out.writeUTF(playerType);
            String check = in.readUTF();
            if (check.equals("continue")) {
                mf.end();
                if (playerID == 1) {
                    System.out.println("Waiting for Player #2 to connect");
                }
                rfsRunnable = new ReadFromServer(in);
                wtsRunnable = new WriteToServer(out);
                rfsRunnable.waitForStartMsg();
                return true;
            }
            else {
                frame.dispose();
                return false;
            }
        } catch (IOException ex) {
            System.out.println("IOExeption from connectToServer");
            return false;
        }
    }  

    public boolean testConnectToServer(String host, int port, String playerType, MenuFrame mf) {
        try {
            socket = new Socket(host, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("You are player#" + playerID);
            out.writeUTF(playerType);
            String check = in.readUTF();
            if (check.equals("continue")) {
                mf.end();
                if (playerID == 1) {
                    System.out.println("Waiting for Player #2 to connect");
                }
                rfsRunnable = new ReadFromServer(in);
                wtsRunnable = new WriteToServer(out);
                rfsRunnable.waitForStartMsg();
                return true;
            }
            else {
                return false;
            }
        } catch (IOException ex) {
            System.out.println("IOExeption from connectToServer");
            return false;
        }
    }  



    //KEYPRESS
    private void createBinding(ActionMap am, InputMap im, String action, int key){
        am.put(action + "Action", new AbstractAction(){
            public void actionPerformed(ActionEvent ae){
                switch (action){
                    case "up": player1.moveUp(true);
                    break;
                    case "down": player1.moveDown(true);
                    break;
                    case "left": player1.moveLeft(true);
                    break;
                    case "right": player1.moveRight(true); 
                    break;
                }
            }
        });

        am.put(action + "ReleaseAction", new AbstractAction() {
            public void actionPerformed(ActionEvent ae){
                switch (action){
                    case "up": player1.moveUp(false);
                    break;
                    case "down": player1.moveDown(false);
                    break;
                    case "left": player1.moveLeft(false);
                    break;
                    case "right": player1.moveRight(false); startTraps = true;
                    break;
                }
            }
        });

        im.put(KeyStroke.getKeyStroke(key, 0, false), action + "Action" );
        im.put(KeyStroke.getKeyStroke(key, 0, true), action + "ReleaseAction");    
    }

    public void addKeyBindings(){
        ActionMap am = gc.getActionMap();
        InputMap im = gc.getInputMap();

        createBinding(am, im, "up", KeyEvent.VK_W);
        createBinding(am, im, "down", KeyEvent.VK_S);
        createBinding(am, im, "left", KeyEvent.VK_A);
        createBinding(am, im, "right", KeyEvent.VK_D);
    }

    public int getOtherLives() {
        return player2.getLives();
    }

}
