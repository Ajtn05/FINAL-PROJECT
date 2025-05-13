import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GameCanvas extends JComponent implements KeyListener {
    public final int tileSize = 32;
    public final int columns = 32;
    public final int rows = 24;
    public final int canvasWidth = 1024;
    public final int canvasHeight = 768;

    public Player player1, player2;
    public String tileMap;
    private Map map;
    private int level;
    private ArrayList<InteractableObjects> interactables;
    private ArrayList<Obstacle> obstacles;
    private Lives player1Lives, player2Lives;
    private PopUps popUps;


    public GameCanvas(int level, ArrayList<Obstacle> obstacles, ArrayList<InteractableObjects> interactables){
        switch(level){
            case 5:
                tileMap = "assets/maps/tileMap1.txt";
                break;
            case 2:
                tileMap = "assets/maps/tileMap2.txt";
                break;
            case 3:
                tileMap = "assets/maps/tileMap3.txt";
                break;
            case 4:
                tileMap = "assets/maps/tileMap4.txt";
                break;
            case 1:
                tileMap = "assets/maps/tileMap5.txt";
                break;

        }
        this.level = level;
        map = new Map(tileMap);
        popUps = new PopUps(this);
        this.obstacles = obstacles;
        this.interactables = interactables;
        addKeyListener(this);
        setFocusable(true);
    }

    public void setPlayer(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public boolean scanKeys() {
        for (InteractableObjects interactable : interactables){
            if (interactable instanceof KeyObject){
                return interactable.isInteracted();
            }
        }
        return false;
    }

    public void checkKeys(Player player) {
        for (InteractableObjects interactable : interactables){
            if (interactable instanceof KeyObject keyObject){
                keyObject.checkCollision(player);
                repaint();
            }
        }  
    }

    public void checkLocks(Player player) {
        for (InteractableObjects interactable : interactables){
            if (interactable instanceof Lock lock){
                lock.checkCollision(player);
                repaint();
            }
        }  
    }

    public void addLevel(int level) {
        System.out.println("player killed, adding level: " + level);
        switch(level){
            case 5 -> tileMap = "assets/maps/tileMap1.txt";
            case 2 -> tileMap = "assets/maps/tileMap2.txt";
            case 3 -> tileMap = "assets/maps/tileMap3.txt";
            case 4 -> tileMap = "assets/maps/tileMap4.txt";
            case 1 -> tileMap = "assets/maps/tileMap5.txt";

        }
        map = new Map(tileMap);
    }

    //DRAW METHODS
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        map.draw(g2d);

        ArrayList<InteractableObjects> interactablesCopy = new ArrayList<>(interactables);
        for (InteractableObjects interactable : interactablesCopy) {
            if (interactable instanceof KeyObject keyObject) {
                if (keyObject.getOwner() != null && keyObject.getOwner().equals(player1)) {
                    keyObject.checkDraw(g2d, player1, keyObject.getLocation());
                }
            }
            interactable.draw(g2d);
        }


        player1.lives.draw(g2d);

        ArrayList<Obstacle> obstaclesCopy = new ArrayList<>(obstacles);
        for (Obstacle obstacle : obstaclesCopy){
            if (obstacle instanceof Traps traps){
                traps.draw(g2d);
            }
        }
        
        player1.draw(g2d);
        player2.draw(g2d);
        popUps.draw(g2d);

    }
    
    //KEYPRESS METHODS
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E){
            checkKeys(player1);   
            checkLocks(player1);       
        }
    }

    //GETTER METHODS
    public Map getMap(){
        return this.map;
    }

    public int getLevel(){
        return level;
    }

    public PopUps getPopUps(){
        return popUps;
    }
}
