public class LevelManager {
    private String host;
    private int port;
    private GameCanvas gc;
    private int level;

    public LevelManager(String host, int port, int level) {
        this.host = host;
        this.port = port;
        this.level = level;
    }

    public void start() {
        gc = new GameCanvas(level);
        GameFrame gf = new GameFrame(gc);
        gf.connectToServer(host, port);
        gf.setUpGUI();
        gf.startGameTimer();
        gf.addKeyBindings();
    }

    public void addLevel() {
        level++;
    }

}