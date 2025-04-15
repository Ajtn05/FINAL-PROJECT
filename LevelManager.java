public class LevelManager {
    private String host, playerType;
    private int port;
    private GameCanvas gc;
    private int level;
    private MenuFrame mf;

    public LevelManager(String host, int port, String playerType, int level, MenuFrame mf) {
        this.host = host;
        this.port = port;
        this.playerType = playerType;
        this.level = level;
        this.mf = mf;
    }

    public void start() {
        gc = new GameCanvas(level);
        GameFrame gf = new GameFrame(gc, this, playerType);
        if (gf.connectToServer(host, port, playerType, mf)) {
            gf.setUpGUI();
            gf.startGameTimer();
            gf.addKeyBindings();
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
    }

}