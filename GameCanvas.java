import java.awt.*;
import javax.swing.*;

public class GameCanvas extends JComponent {
    public final int tileSize = 32;
    public final int columns = 32;
    public final int rows = 24;
    public final int canvasWidth = 1024;
    public final int canvasHeight = 768;

    public Player player;
    private Map map;

    public GameCanvas(){
        player = new Player();
        map = new Map();
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        map.draw(g2d);
        player.draw(g2d);
    }

}