import java.util.*;

public class LevelManager {
    private String host, playerType;
    private int port;
    private GameCanvas gc;
    private int level;
    private MenuFrame mf;
    private ArrayList<Obstacle> obstacles;

    public LevelManager(String host, int port, String playerType, int level, MenuFrame mf) {
        this.host = host;
        this.port = port;
        this.playerType = playerType;
        this.level = level;
        this.mf = mf;
        obstacles = new ArrayList<>();
    }

    public ArrayList<Obstacle> getObstacles(){
        return obstacles;
    }

    public void start() {
        gc = new GameCanvas(level);
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
        switch(level){
            case 1:
                ArrayList<int[]> tileCoordinates = new ArrayList<>(Arrays.asList(new int[][]{{27,9}, {28,9}, {1,17}}));
                ArrayList<Integer> newTilenums = new ArrayList<>(Arrays.asList(24,24,23));

                obstacles.add(new PressurePlate(32,530,24,24, tileCoordinates,newTilenums));
                break;
            case 2:
                break;
        }
    }

}