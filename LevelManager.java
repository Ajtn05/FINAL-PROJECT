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
                KeyObject key = new KeyObject(228,64, "bronze");
                interactables.add(key);

                //lock 1
                MapItem lock1 = new MapItem(new int[][]{{1,13}}, new Integer[]{24});
                interactables.add(new Lock(36,423, lock1, gc, key, "bronze"));

                //key 2
                KeyObject key2 = new KeyObject(300,64, "bronze");
                interactables.add(key2);

                // //lock 2
                MapItem lock2 = new MapItem(new int[][]{{15,2}}, new Integer[]{24});
                interactables.add(new Lock(356,150, lock2, gc, key2, "bronze"));
                
                break;
            case 2:
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