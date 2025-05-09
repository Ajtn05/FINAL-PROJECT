import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
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


    public GameFrame(GameCanvas gc, LevelManager lm, String playerType){
        this.playerType = playerType;
        frame = new JFrame();
        this.gc = gc;
        map = new Map("tileMap1.txt");
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
            player1 = new Player(1, 62, this, gc, p1playerType);
            player2 = new Player(4, 62, this, gc, p2playerType);
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
            player1 = new Player(4, 62, this, gc, p2playerType);
            player2 = new Player(1, 62, this, gc, p1playerType);
            gc.setPlayer(player1, player2);
        }
    }

    public void startGameTimer(){
        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                for (Obstacle obstacle : lm.getObstacles()){
                    obstacle.checkCollision(player1, player2, gc.getMap(), gc);
                }

                // for (Door door :lm.getDoors()) {
                //     door.checkCollision(player1);
                //     door.checkCollision(player2);
                // }

                player1.update(gc.getMap());
                player2.update(gc.getMap());
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
                        boolean left, right, up, down, hasKey, opensDoor = false;
                        int x, y;
                        left = dataIn.readBoolean();
                        right = dataIn.readBoolean();
                        up = dataIn.readBoolean();
                        down = dataIn.readBoolean();
                        x = dataIn.readInt();
                        y = dataIn.readInt();
                        hasKey = dataIn.readBoolean();
                        opensDoor = dataIn.readBoolean();

                        player2.moveLeft(left);
                        player2.moveRight(right);
                        player2.moveUp(up);
                        player2.moveDown(down);
                        player2.setPosition(x, y);
                        if (hasKey) {
                            gc.checkKeys();
                        }
                        if (opensDoor) {
                            gc.checkLocks();
                        }
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
                        dataOut.writeBoolean(player1.getLeft());
                        dataOut.writeBoolean(player1.getRight());
                        dataOut.writeBoolean(player1.getUp());
                        dataOut.writeBoolean(player1.getDown());
                        dataOut.writeInt(player1.getX());
                        dataOut.writeInt(player1.getY());
                        dataOut.writeBoolean(player1.hasKey());
                        dataOut.writeBoolean(player1.opensDoor());
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
                    case "right": player1.moveRight(false);
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
}
