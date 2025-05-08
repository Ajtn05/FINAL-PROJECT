import java.util.*;

public class LevelManager {
    private String host, playerType;
    private int port;
    private GameCanvas gc;
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
        GameFrame gf = new GameFrame(gc, this, playerType);
        if (gf.connectToServer(host, port, playerType, mf)) {
            gf.setUpGUI();
            gf.startGameTimer();
            gf.addKeyBindings();
            setUpObstacles();
        }
        else {
            // mf.reset(this);
            mf.frame.dispose();
            mf.setUpGUI("Player type already exists, please choose another");
        }
    }

    public void setUp() {
    }

    public void addLevel() {
        level++;
        setUpObstacles();
    }

    public void setUpObstacles(){
        obstacles.clear();
        interactables.clear();
        switch(level){
            case 1:
                ArrayList<int[]> tileCoordinates = new ArrayList<>(Arrays.asList(new int[][]{{27,9}, {28,9}, {1,17}}));
                ArrayList<Integer> newTilenums = new ArrayList<>(Arrays.asList(24,24,23));
                obstacles.add(new PressurePlate(32,530,24,24, tileCoordinates,newTilenums));
                
                KeyObject key = new KeyObject(228,354, "gold");
                interactables.add(key);

                ArrayList<int[]> tileCoordinates1 = new ArrayList<>(Arrays.asList(new int[][]{{1,13}}));
                ArrayList<Integer> newTilenums1 = new ArrayList<>(Arrays.asList(24));
                
                Lock lock = new Lock(36,423, tileCoordinates1, newTilenums1, gc, keyObject, "gold");
                interactables.add(lock);
                break;
            case 2:
                break;
        }
    }

}