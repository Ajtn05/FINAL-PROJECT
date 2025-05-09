import java.util.*;

public class LevelManager {
    private String host, playerType;
    private int port;
    private GameCanvas gc;
    private GameFrame gf;
    private KeyObject keyObject;
    private int level;
    private MenuFrame mf;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<InteractableObjects> interactables;

    public LevelManager(String host, int port, String playerType, int level, MenuFrame mf) {
        this.host = host;
        this.port = port;
        this.playerType = playerType;
        this.level = level;
        this.mf = mf;
        obstacles = new ArrayList<>();
        interactables = new ArrayList<>();
    }

    public ArrayList<Obstacle> getObstacles(){
        return obstacles;
    }

    public void start() {
        gc = new GameCanvas(level, interactables);
        gf = new GameFrame(gc, this, playerType);
        if (gf.connectToServer(host, port, playerType, mf)) {
            gf.setUpGUI();
            gf.startGameTimer();
            gf.addKeyBindings();
            setUpObstacles();
        }
        else {
            mf.frame.dispose();
            mf.setUpGUI("Player type already exists, please choose another");
        }
    }

    //DELETE AFTER
    public void testStart() {
        gc = new GameCanvas(level, interactables);
        gf = new GameFrame(gc, this, playerType);
        if (gf.testConnectToServer(host, port, playerType, mf)) {
            gf.setUpGUI();
            gf.startGameTimer();
            gf.addKeyBindings();
            setUpObstacles();
        }
    }
    
    public void addLevel() {
        level++;
        gc.addLevel(level);
        setUpObstacles();
    }

    public void setUpObstacles(){
        obstacles.clear();
        interactables.clear();
        switch(level){
            case 1:
                //pressureplate
                MapItem PressurePlate = new MapItem(new int[][]{{27,9}, {28,9}, {1,17}}, new Integer[]{24, 24, 23});
                obstacles.add(new PressurePlate(32,530,24,24, PressurePlate));
                
                // key 1
                // KeyObject key = new KeyObject(228,354, "gold");
                KeyObject key = new KeyObject(228,354, "gold");
                interactables.add(key);

                KeyObject key10 = new KeyObject(100,62, "silver");
                interactables.add(key10);

                //lock 1
                MapItem lock1 = new MapItem(new int[][]{{1,13}}, new Integer[]{24});
                interactables.add(new Lock(36,423, lock1, gc, key, "gold"));

                //key 2
                KeyObject key2 = new KeyObject(485,642, "silver");
                interactables.add(key2);

                // //lock 2
                MapItem lock2 = new MapItem(new int[][]{{30, 18}}, new Integer[]{24});
                interactables.add(new Lock(965,582, lock2, gc, key2, "silver"));

                MapItem PressurePlate2 = new MapItem(new int[][]{{15,8}, {15,16}}, new Integer[]{23, 26});
                obstacles.add(new PressurePlate(481,242,24,24, PressurePlate2));
                
                break;
            case 2:
                MapItem PressurePlate3 = new MapItem(new int[][]{{3, 16}, {3, 21}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(101,506,24,24, PressurePlate3));

                KeyObject key3 = new KeyObject(387, 708, "bronze");
                interactables.add(key3);

                MapItem lock3 = new MapItem(new int[][]{{7, 8}}, new Integer[]{25});
                interactables.add(new Lock(228, 265, lock3, gc, key3, "bronze"));

                KeyObject key4 = new KeyObject(549, 68, "silver");
                interactables.add(key4);

                MapItem lock4 = new MapItem(new int[][]{{11, 16}}, new Integer[]{26});
                interactables.add(new Lock(357, 520, lock4, gc, key4, "silver"));

                KeyObject key5 = new KeyObject(420, 515, "gold");
                interactables.add(key5);

                MapItem lock5 = new MapItem(new int[][]{{21, 6}}, new Integer[]{26});
                interactables.add(new Lock(677, 200, lock5, gc, key5, "gold"));

                MapItem PressurePlate4 = new MapItem(new int[][]{{23, 13}, {21, 18}}, new Integer[]{23, 26});
                obstacles.add(new PressurePlate(740,414,24,24, PressurePlate4));

                KeyObject key6 = new KeyObject(580, 612, "silver");
                interactables.add(key6);

                MapItem lock6 = new MapItem(new int[][]{{27, 16}}, new Integer[]{25});
                interactables.add(new Lock(869, 520, lock6, gc, key6, "silver"));

                MapItem PressurePlate5 = new MapItem(new int[][]{{28, 10}, {26, 2}}, new Integer[]{23, 26});
                obstacles.add(new PressurePlate(900,315,24,24, PressurePlate5));

                KeyObject key7 = new KeyObject(949, 81, "gold");
                interactables.add(key7);

                MapItem lock7 = new MapItem(new int[][]{{30, 21}}, new Integer[]{25});
                interactables.add(new Lock(965, 680, lock7, gc, key7, "gold"));

                break;
        }
    }

    //GETTERS

    // public void checkLocks() {
    //     for (InteractableObjects interactable : interactables){
    //         if (interactable instanceof Lock lock){
    //             lock.checkCollision(player1);
    //             lock.checkCollision(player2);
    //             repaint();
    //         }
    //     }  
    // }

    public InteractableObjects getInteractableObjects(String name) {
        for (InteractableObjects interactable : interactables) {
            if (interactable.getID().equals(name)) {
                return interactable;
            }
        }
        return null;
    }

    public GameCanvas getGC() {return gc;}

    public GameFrame getGF() {return gf;}



}