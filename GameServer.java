import java.io.*;
import java.net.*;

public class GameServer {
    private ServerSocket serverSocket;
    private int numPlayers, maxPlayers;
    private Socket p1Socket, p2Socket;
    private ReadFromClient p1ReadRunnable, p2ReadRunnable;
    private WriteToClient p1WriteRunnable, p2WriteRunnable;

    private Boolean p1left, p1right, p1up , p1down, p1hasKey,
                    p2left, p2right, p2up, p2down, p2hasKey, 
                    p1opensDoor = false, p2opensDoor = false;
    private int p1x, p1y, p2x, p2y;

    public GameServer() {
        //values are hardcoded for now
        p1left = false;
        p1right = false;
        p1up = false;
        p1down = false;
        p1hasKey = false;
        p2left = false;
        p2right = false;
        p2up = false;
        p2down = false;
        p2hasKey = false;

        numPlayers = 0;
        maxPlayers = 2;
        try {
            this.serverSocket = new ServerSocket(9999);
        } catch (IOException ex) {
            System.out.println("game server container");
        }
    }

    public void acceptConnections() {
        try {
            int boy = 0;
            int girl = 0;
            while (numPlayers < maxPlayers) {
                Socket s = serverSocket.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers);

                String test = in.readUTF();
                if (test.equals("boy")) {
                    boy++;
                }
                else if (test.equals("girl")) {
                    girl++;
                }

                if (boy > 1 || girl > 1) {
                    if (test.equals("boy")) {
                        boy--;
                    }
                    else if (test.equals("girl")) {
                        girl--;
                    }
                    out.writeUTF("end");
                    numPlayers--;
                    s.close();
                }
                else {
                    out.writeUTF("continue");
                    System.out.println("Player #" + numPlayers + " has connected");

                    ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                    WriteToClient wtc = new WriteToClient(numPlayers, out);
    
                    if (numPlayers == 1) {
                        p1Socket = s;
                        p1ReadRunnable = rfc;
                        p1WriteRunnable = wtc;
                    } 
                    else {
                        p2Socket = s;
                        p2ReadRunnable = rfc;
                        p2WriteRunnable = wtc;
                        p1WriteRunnable.sendStartMsg();
                        p2WriteRunnable.sendStartMsg();
                        Thread readThread1 = new Thread(p1ReadRunnable);
                        Thread readThread2 = new Thread(p2ReadRunnable);
                        readThread1.start();
                        readThread2.start();
                        Thread writeThread1 = new Thread(p1WriteRunnable);
                        Thread writeThread2 = new Thread(p2WriteRunnable);
                        writeThread1.start();
                        writeThread2.start();
                    }
                }
            }
            System.out.println("No longer accepting connections");
        } catch (IOException e) {
            System.out.println("accept connections");
        }
    }

    private class ReadFromClient implements Runnable {
        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int pid, DataInputStream in) {
            playerID = pid;
            dataIn = in;
            System.out.println("RFC" + playerID + "Runnable created"); 
        }

        public void run() {
            try {
                while (true) { 
                    if(playerID == 1) {
                        p1left = dataIn.readBoolean();
                        p1right = dataIn.readBoolean();
                        p1up = dataIn.readBoolean();
                        p1down = dataIn.readBoolean();
                        p1x = dataIn.readInt();
                        p1y = dataIn.readInt();
                        p1hasKey = dataIn.readBoolean();
                        p1opensDoor = dataIn.readBoolean();
                    }
                    else {
                        p2left = dataIn.readBoolean();
                        p2right = dataIn.readBoolean();
                        p2up = dataIn.readBoolean();
                        p2down = dataIn.readBoolean();
                        p2x = dataIn.readInt();
                        p2y = dataIn.readInt();
                        p2hasKey = dataIn.readBoolean();
                        p2opensDoor = dataIn.readBoolean();
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFC run()");
            }
        }

    }

    private class WriteToClient implements Runnable {
        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int pid, DataOutputStream out) {
            playerID = pid;
            dataOut = out;
            System.out.println("RFC" + playerID + "Runnable created"); 
        }

        public void run() {
            try {
                while (true) { 
                    if(playerID == 1) {
                        dataOut.writeBoolean(p2left);
                        dataOut.writeBoolean(p2right);
                        dataOut.writeBoolean(p2up);
                        dataOut.writeBoolean(p2down);
                        dataOut.writeInt(p2x);
                        dataOut.writeInt(p2y);
                        dataOut.writeBoolean(p2hasKey);
                        dataOut.writeBoolean(p2opensDoor);
                        dataOut.flush();
                    }
                    else {
                        dataOut.writeBoolean(p1left);
                        dataOut.writeBoolean(p1right); 
                        dataOut.writeBoolean(p1up);
                        dataOut.writeBoolean(p1down);
                        dataOut.writeInt(p1x);
                        dataOut.writeInt(p1y);
                        dataOut.writeBoolean(p1hasKey);
                        dataOut.writeBoolean(p1opensDoor);
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException at WTC run");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTC run()");
            }   
        }

        public void sendStartMsg() {
            try {
                dataOut.writeUTF("we now have 2 players, go");
            } catch (IOException e) {
                System.out.println("IOException from sendStartMsg()");
            }
        } 

    }
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections(); 
    }
}
 