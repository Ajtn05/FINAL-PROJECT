import java.util.*;

public class LevelManager {
    private String host, playerType;
    private int port;
    private GameCanvas gc;
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

    // public ArrayList<InteractableObjects> getInteractables(){
    //     return interactables;
    // }

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
                
                KeyObject key = new KeyObject(228,354);
                interactables.add(key);
                break;
            case 2:
                break;
        }
    }

}