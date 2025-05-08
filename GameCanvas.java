import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GameCanvas extends JComponent implements KeyListener{
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



    public GameCanvas(int level, ArrayList<InteractableObjects> interactables){
        switch(level){
            case 1:
                tileMap = "tileMap1.txt";
                break;
            case 2:
                tileMap = "tileMap2.txt";
                break;
        }
        this.level = level;
        map = new Map(tileMap);
        this.interactables = interactables;
        addKeyListener(this);
        setFocusable(true);
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
        
        for (InteractableObjects interactable : interactables){
            interactable.draw(g2d);
        }

        player1.draw(g2d);
        player2.draw(g2d);
    }

    public boolean scanKeys() {
        for (InteractableObjects interactable : interactables){
            if (interactable instanceof KeyObject keyObject){
                return keyObject.isInteracted();
            }
        }
        return false;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E){
            checkKeys();   
            checkLocks();       
        }
    }

    public void checkKeys() {
        for (InteractableObjects interactable : interactables){
            if (interactable instanceof KeyObject keyObject){
                keyObject.checkCollision(player1);
                keyObject.checkCollision(player2);    
                repaint();
                break;
            }
        }  
    }

    public void checkLocks() {
        for (InteractableObjects interactable : interactables){
            if (interactable instanceof Lock lock){
                lock.checkCollision(player1);
                lock.checkCollision(player2);
                repaint();
                break;
            }
        }  
    }

    public void addLevel() {
        level++;
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

    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}