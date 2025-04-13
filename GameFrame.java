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


    public GameFrame(GameCanvas gc){
        frame = new JFrame();
        this.gc = gc;
        map = new Map("tileMap1.txt");
    }

    public void createPlayers() {
    
        if (playerID == 1) {
            player1 = new Player(1, 64);
            player2 = new Player(4, 64);
        }
        else {
            player1 = new Player(4, 64);
            player2 = new Player(1, 64);
        }
    }

    public void setUpGUI(){
        gc.setPreferredSize(new Dimension(1024, 768));
        createPlayers();
        gc.setPlayer(player1, player2);
        frame.add(gc);
        frame.setTitle("Maze Game - Player " + playerID);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setFocusable(true);
        frame.setVisible(true);
        gc.requestFocusInWindow();

    }

    public void startGameTimer(){
        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                player1.update(map);
                player2.update(map);
                gc.repaint();
            }
        });
        timer.start();
    }

    public void addKeyBindings(){
        ActionMap am = gc.getActionMap();
        InputMap im = gc.getInputMap();

        createBinding(am, im, "up", KeyEvent.VK_W);
        createBinding(am, im, "down", KeyEvent.VK_S);
        createBinding(am, im, "left", KeyEvent.VK_A);
        createBinding(am, im, "right", KeyEvent.VK_D);
    }

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
                        boolean left, right, up, down;
                        int x, y;
                        left = dataIn.readBoolean();
                        right = dataIn.readBoolean();
                        up = dataIn.readBoolean();
                        down = dataIn.readBoolean();
                        x = dataIn.readInt();
                        y = dataIn.readInt();


                        player2.moveLeft(left);
                        player2.moveRight(right);
                        player2.moveUp(up);
                        player2.moveDown(down);
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
                        dataOut.writeBoolean(player1.getLeft());
                        dataOut.writeBoolean(player1.getRight());
                        dataOut.writeBoolean(player1.getUp());
                        dataOut.writeBoolean(player1.getDown());
                        dataOut.writeInt(player1.getX());
                        dataOut.writeInt(player1.getY());
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

    public void connectToServer(String host, int port) {
        try {
            socket = new Socket(host, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("You are player#" + playerID);
            if (playerID == 1) {
                System.out.println("Waiting for Player #2 to connect");
            }
            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);
            rfsRunnable.waitForStartMsg();

        } catch (IOException ex) {
            System.out.println("IOExeption from connectToServer");
        }
    }  
}
