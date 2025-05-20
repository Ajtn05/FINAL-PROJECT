import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;

public class GameFrame extends JComponent {
    public JFrame frame;
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
    public Boolean p1startTraps = false, startTraps = false;

    private boolean left, right, up, down, hasKey, opensDoor = false, p2opensDoor = false, p1dead = false, p2dead = false, p1levelComplete = false, p2levelComplete = false,
                    p1loss = false, p2loss = false, p1win = false, p2win = false;
    private int x, y, keys, lives, level, x2, y2;

    public GameFrame(GameCanvas gc, LevelManager lm, String playerType){
        this.playerType = playerType;
        frame = new JFrame();
        this.gc = gc;
        map = gc.getMap();
        this.lm = lm;
    }

    public void setUpGUI(){
        gc.setPreferredSize(new Dimension(1024, 768));

        level = lm.getLevel();

        setCoordinates();
        createPlayers();
        frame.add(gc);
        frame.setTitle("Maze Game - Player " + playerID + " " + playerType);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setFocusable(true);
        frame.setVisible(true);
        gc.requestFocusInWindow();
    }

    public void setCoordinates() {
        switch (level) {
            case 1: 
                x = 41;
                y = 698;
                x2 = 968;
                y2 = 58;
                break; 
            case 2:
                x = 1;
                y = 62;
                x2 = 740;
                y2 = 58;
                break;
            case 3:
                x = 41;
                y = 698;
                x2 = 741;
                y2 = 62;
                break;
            case 4:
                x = 1;
                y = 62;
                x2 = 776;
                y2 = 62;
                break;
            case 5:
                x = 450;
                y = 380;
                x2 = 550;
                y2 = 380;
                break;
        }
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
            player1 = new Player(x, y, this, gc, p1playerType, lm);
            player2 = new Player(x2, y2, this,  gc, p2playerType, lm);
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
            player1 = new Player(x2, y2, this, gc, p2playerType, lm);
            player2 = new Player(x, y, this, gc, p1playerType, lm);
            gc.setPlayer(player1, player2);
        }

    }

    public void startGameTimer(){
        timer = new Timer(12, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                ArrayList<Obstacle> obstacleCopy = new ArrayList<>(lm.getObstacles());
                for (Obstacle obstacle : obstacleCopy) {
                    obstacle.checkCollision(player1, gc.getMap(), gc);
                    obstacle.checkCollision(player2, gc.getMap(), gc);
                    if (obstacle instanceof Traps traps && (p1startTraps)){
                        traps.updateSpriteAnimation();
                    }
                } 
                if (keys > 0) {
                    gc.checkKeys(player2);
                }
                if (p2opensDoor) {
                    gc.checkLocks(player2);
                    p2opensDoor = false;
                    opensDoor = false;
                    player2.stopDoor();
                    player1.stopDoor();
                }
                if (p2loss) {  
                    timer.stop();
                    end("betrayed");
                }
                if (p2win) {  
                    timer.stop();
                    end("win");
                }
                if (startTraps) {
                    p1startTraps = true;
                }

                player1.update(gc.getMap());
                player2.update(gc.getMap());
                gc.repaint();
            }
        });
        timer.start();
    }

    public void levelComplete() {
        if (gc.getComplete() == true) {
            lm.addLevel(1);
            level = lm.getLevel();
            setCoordinates();
            createPlayers();
            p1startTraps = false;
            startTraps = false;
        }
    }

    public void gameReset() {
        p1startTraps = false;
        startTraps = false;
        setCoordinates();
        createPlayers();
        player2.setLives(5);
    }

    public void setLoss() {
        int p1X = player1.getX();
        int p1X2 = p1X + 24;
        int p1Y = player1.getY();
        int p1Y2 = p1Y + 40;

        int p2X = player2.getX();
        int p2X2 = p2X + 24;
        int p2Y = player2.getY();
        int p2Y2 = p2X + 40;

        if (p1X <= p2X2 && p1X2 >= p2X &&
            p1Y <= p2Y2 && p1Y2 >= p2Y){
                p1loss = true;
                end("betrayed");
        }
    }

    public void setWin() {
        p1win = true;
        end("win");
    }
    

    public void end(String type) {
        EndingFrame ef;
        if (type.equals("betrayed")) {ef = new EndingFrame("betrayed");}
        else {ef = new EndingFrame("win");}
        ef.setUpEndGUI();
        frame.dispose();
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
                        byte booleans2 = Byte.parseByte(packets[1]);
                        x = Integer.parseInt(packets[2]);
                        y = Integer.parseInt(packets[3]);
                        keys = Integer.parseInt(packets[4]);
                        lives = Integer.parseInt(packets[5]);

                        left      = (booleans & (1 << 0)) != 0;
                        right     = (booleans & (1 << 1)) != 0;
                        up        = (booleans & (1 << 2)) != 0;
                        down      = (booleans & (1 << 3)) != 0;
                        p2opensDoor = (booleans & (1 << 4)) != 0;
                        startTraps = (booleans & (1 << 5)) != 0;
                        p1dead = (booleans & (1 << 6)) != 0;
                        p2dead = (booleans & (1 << 7)) != 0;
                        
                        p2levelComplete =  (booleans2 & (1 << 0)) != 0;
                        p2loss =  (booleans2 & (1 << 1)) != 0;
                        p2win =  (booleans2 & (1 << 2)) != 0;
    
                    

                        player2.moveLeft(left);
                        player2.moveRight(right);
                        player2.moveUp(up);
                        player2.moveDown(down);
                        player2.setLives(lives);
                        player2.setPosition(x, y);
                        // if (startTraps) {p1startTraps = true;}

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
                        p1levelComplete = (player1.getLevelCompleted() && player1.getLevelCompleted());
                        opensDoor = player1.opensDoor();
                        if (player1.getLeft()) booleans  |= 1 << 0;
                        if (player1.getRight()) booleans |= 1 << 1;
                        if (player1.getUp()) booleans    |= 1 << 2;
                        if (player1.getDown()) booleans  |= 1 << 3;
                        if (opensDoor) booleans|= 1 << 4;
                        if (p1startTraps) booleans       |= 1 << 5;

                        if (player1.getDead()) booleans  |= 1 << 6;
                        if (p2dead) booleans             |= 1 << 7;

                        byte booleans2 = 0;
                        if (p1levelComplete) booleans2    |= 1 << 0;
                        if (p1loss) booleans2             |= 1 << 1;
                        if (p1win) booleans2             |= 1 << 2;
                        

                        x = player1.getX();
                        y = player1.getY();
                        keys = player1.keys.size();
                        lives = player1.getLives();


                        String message = booleans + "," + booleans2 + "," + x + "," + y + "," + keys + "," + lives;
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

    private void createBinding(ActionMap am, InputMap im, String action, int key){
        am.put(action + "Action", new AbstractAction(){
            public void actionPerformed(ActionEvent ae){
                startTraps();
                switch (action){
                    case "up": player1.moveUp(true); break;
                    case "down": player1.moveDown(true); break;
                    case "left": player1.moveLeft(true); break;
                    case "right": player1.moveRight(true); break;
                }
            }
        });

        am.put(action + "ReleaseAction", new AbstractAction() {
            public void actionPerformed(ActionEvent ae){
                switch (action){
                    case "up": player1.moveUp(false); break;
                    case "down": player1.moveDown(false); break;
                    case "left": player1.moveLeft(false); break;
                    case "right": player1.moveRight(false); break;
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

    public void startTraps(){
        this.p1startTraps = true;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean getP1Dead() {
        return p1dead;
    }

    public boolean getP2Dead() {
        return p2dead;
    }
}

