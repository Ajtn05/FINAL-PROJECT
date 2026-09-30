/** 
    This is the GameServer class which handles accepting new players and creating sockets 
    for them. Once the players have been connected, this handles the communication between players.

    @author Janelle Angela C. Lopez (242682)
    @author Aldrin Joseph T. Nellas (243215)
	@version April 1, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/
import java.io.*;
import java.net.*;

public class GameServer {
    private ServerSocket serverSocket;
    private int numPlayers, maxPlayers;
    private Socket p1Socket, p2Socket;
    private ReadFromClient p1ReadRunnable, p2ReadRunnable;
    private WriteToClient p1WriteRunnable, p2WriteRunnable;

    private volatile boolean p1left, p1right, p1up , p1down, p1hasKey,
                    p2left, p2right, p2up, p2down, p2hasKey, 
                    p1opensDoor = false, p2opensDoor = false,
                    p1dead = false, p2dead = false,
                    p1startTraps = false, p2startTraps = false, startTraps = false,
                    p1levelComplete = false, p2levelComplete = false, levelComplete = false,
                    p1loss = false, p2loss = false, p1win = false, p2win = false;
    private volatile int p1x, p1y, p2x, p2y, p1keys, p2keys, p1lives, p2lives;
    private volatile int p1level, p2level;
    private volatile int p1generation, p2generation;
    private volatile long p1claimedKeys, p2claimedKeys, p1unlockedLocks, p2unlockedLocks;
    private volatile int p1resetAck, p2resetAck, resetEpoch, resetLevel = 1, resetGeneration;
    private volatile boolean p1stateReceived, p2stateReceived;

    // Wait for both clients to apply a reset before accepting another death event.
    private synchronized void scheduleResetIfNeeded() {
        if (!p1stateReceived || !p2stateReceived ||
                p1resetAck != resetEpoch || p2resetAck != resetEpoch ||
                (p1lives > 0 && p2lives > 0)) {
            return;
        }
        resetLevel = p1lives <= 0 ? p1level : p2level;
        resetGeneration = Math.max(p1generation, p2generation) + 1;
        p1startTraps = false;
        p2startTraps = false;
        startTraps = false;
        resetEpoch++;
    }

    public GameServer() {
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
            throw new IllegalStateException("Could not start game server on port 9999", ex);
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
                if (!"boy".equals(test) && !"girl".equals(test)) {
                    out.writeUTF("end");
                    numPlayers--;
                    s.close();
                    continue;
                }
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
                    startTraps = p1startTraps || p2startTraps;
                    if(playerID == 1) {
                        String message = dataIn.readUTF();
                        String[] packets = message.split(",");

                        byte booleans = Byte.parseByte(packets[0]);
                        byte booleans2 = Byte.parseByte(packets[1]);
                        p1x = Integer.parseInt(packets[2]);
                        p1y = Integer.parseInt(packets[3]);
                        p1keys = Integer.parseInt(packets[4]);
                        p1lives = Integer.parseInt(packets[5]);
                        p1level = Integer.parseInt(packets[6]);
                        p1claimedKeys = Long.parseLong(packets[7]);
                        p1unlockedLocks = Long.parseLong(packets[8]);
                        p1generation = Integer.parseInt(packets[9]);
                        p1resetAck = Integer.parseInt(packets[10]);

                        p1left      = (booleans & (1)) != 0;
                        p1right     = (booleans & (1 << 1)) != 0;
                        p1up        = (booleans & (1 << 2)) != 0;
                        p1down      = (booleans & (1 << 3)) != 0;
                        p2opensDoor = (booleans & (1 << 4)) != 0;
                        p1startTraps = (booleans & (1 << 5)) != 0 && p1resetAck == resetEpoch;
                        p1dead = (booleans & (1 << 6)) != 0;


                        p1levelComplete = (booleans2 & (1 << 0)) != 0;
                        p1loss = (booleans2 & (1 << 1)) != 0;
                        p1win = (booleans2 & (1 << 2)) != 0;
                        p1stateReceived = true;
                    }
                    else {
                        String message = dataIn.readUTF();
                        String[] packets = message.split(",");

                        byte booleans = Byte.parseByte(packets[0]);
                        byte booleans2 = Byte.parseByte(packets[1]);
                        p2x = Integer.parseInt(packets[2]);
                        p2y = Integer.parseInt(packets[3]);
                        p2keys = Integer.parseInt(packets[4]);
                        p2lives = Integer.parseInt(packets[5]);
                        p2level = Integer.parseInt(packets[6]);
                        p2claimedKeys = Long.parseLong(packets[7]);
                        p2unlockedLocks = Long.parseLong(packets[8]);
                        p2generation = Integer.parseInt(packets[9]);
                        p2resetAck = Integer.parseInt(packets[10]);

                        p2left      = (booleans & (1)) != 0;
                        p2right     = (booleans & (1 << 1)) != 0;
                        p2up        = (booleans & (1 << 2)) != 0;
                        p2down      = (booleans & (1 << 3)) != 0;
                        p1opensDoor = (booleans & (1 << 4)) != 0;
                        p2startTraps = (booleans & (1 << 5)) != 0 && p2resetAck == resetEpoch;
                        p2dead = (booleans & (1 << 6)) != 0;

                        p2levelComplete = (booleans2 & (1 << 0)) != 0;
                        p2loss = (booleans2 & (1 << 1)) != 0;
                        p2win = (booleans2 & (1 << 2)) != 0;
                        p2stateReceived = true;
                    }
                    scheduleResetIfNeeded();
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
                    startTraps = p1startTraps || p2startTraps;
                    if (p1levelComplete && p2levelComplete) {levelComplete = true;}
                    if(playerID == 1) {

                        byte booleans = 0;
                        byte booleans2 = 0;
                        if (p2left) booleans  |= 1;
                        if (p2right) booleans |= 1 << 1;
                        if (p2up) booleans    |= 1 << 2;
                        if (p2down) booleans  |= 1 << 3;
                        if (p1opensDoor) booleans |= 1 << 4;
                        if (startTraps) booleans |= 1 << 5;
                        if (p1dead) booleans |= 1 << 6;
                        if (p2dead) booleans |= 1 << 7;

                        if (p2levelComplete) booleans2 |= 1 << 0;
                        if (p2loss) booleans2 |= 1 << 1;
                        if (p2win) booleans2 |= 1 << 2;

                        int x = p2x;
                        int y = p2y;
                        int keys = p2keys;
                        int lives = p2lives;

                        int epoch = resetEpoch;
                        String message = booleans + "," + booleans2 + "," + x + "," + y + "," + keys + "," + lives
                                + "," + p2level + "," + p2claimedKeys + "," + p2unlockedLocks + "," + p2generation
                                + "," + epoch + "," + resetLevel + "," + resetGeneration;
                        dataOut.writeUTF(message);
                        dataOut.flush();
                    }
                    else {
                        byte booleans = 0;
                        byte booleans2 = 0;
                        if (p1left) booleans  |= 1;
                        if (p1right) booleans |= 1 << 1;
                        if (p1up) booleans    |= 1 << 2;
                        if (p1down) booleans  |= 1 << 3;
                        if (p2opensDoor) booleans |= 1 << 4;
                        if (startTraps) booleans |= 1 << 5;
                        if (p2dead) booleans |= 1 << 6;
                        if (p1dead) booleans |= 1 << 7;

                        if (p1levelComplete) booleans2 |= 1 << 0;
                        if (p1loss) booleans2 |= 1 << 1;
                        if (p1win) booleans2 |= 1 << 2;

                        int x = p1x;
                        int y = p1y;
                        int keys = p1keys;
                        int lives = p1lives;

                        int epoch = resetEpoch;
                        String message = booleans + "," + booleans2 + "," + x + "," + y + "," + keys + "," + lives
                                + "," + p1level + "," + p1claimedKeys + "," + p1unlockedLocks + "," + p1generation
                                + "," + epoch + "," + resetLevel + "," + resetGeneration;
                        dataOut.writeUTF(message);
                        dataOut.flush();
                    }
        
                    try {
                        Thread.sleep(5);
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
