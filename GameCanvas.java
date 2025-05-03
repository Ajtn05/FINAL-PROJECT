import java.awt.*;
import javax.swing.*;

public class GameCanvas extends JComponent {
    public final int tileSize = 32;
    public final int columns = 32;
    public final int rows = 24;
    public final int canvasWidth = 1024;
    public final int canvasHeight = 768;

    public Player player1, player2;
    public String tileMap;
    private Map map;

    public GameCanvas(int level){
        switch(level){
            case 1:
                tileMap = "tileMap1.txt";
                break;
            case 2:
                tileMap = "tileMap2.txt";
                break;
        }
        map = new Map(tileMap);
    }

    public void setPlayer(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public Map getMap(){
        return this.map;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        map.draw(g2d);
        player1.draw(g2d);
        player2.draw(g2d);
    }

}