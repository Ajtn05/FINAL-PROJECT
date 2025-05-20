/** 
    This is the GameCanvas class, which handles drawing the map, obstacles, 
    interactable objects, and the players. It also handles checking for
    player interaction when they press E.

    @author Janelle Angela C. Lopez (242682)
    @author Aldrin Joseph T. Nellas (243215)
	@version April 1, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

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
    private PopUps popUps;
    private int level;
    private ArrayList<InteractableObjects> interactables;
    private ArrayList<Obstacle> obstacles;
    private Lives player1Lives, player2Lives;
    private King king;
    private GameFrame gf;

    /**
        Constructs a GameCanvas instance with the level and arraylists of obstacles and 
        interactable objects.
        @param level                 the level number to load
        @param obstacles             arraylist of obstacles
        @param interactables         arraylist of interactable objects
    **/

    public GameCanvas(int level, ArrayList<Obstacle> obstacles, ArrayList<InteractableObjects> interactables){
        addLevel(level);
        this.level = level;
        this.obstacles = obstacles;
        this.interactables = interactables;
        popUps = new PopUps();
        addKeyListener(this);
        setFocusable(true);
    }

    /**
        Sets the two player instances used in the game.
        @param player1 the first player
        @param player2 the second player
    **/

    public void setPlayer(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
        Iterates through the interactables arraylist and checks if
        any key object has been interacted with.
    **/

    public boolean scanKeys() {
        for (InteractableObjects interactable : interactables){
            if (interactable instanceof KeyObject){
                return interactable.isInteracted();
            }
        }
        return false;
    }

    /**
        Checks for collisions between the player and key object.
    **/

    public void checkKeys(Player player) {
        for (InteractableObjects interactable : interactables){
            if (interactable instanceof KeyObject keyObject){
                keyObject.checkCollision(player);
                repaint();
            }
        }  
    }

    /**
        Checks for collisions between the player and lock object.
    **/

    public void checkLocks(Player player) {
        for (InteractableObjects interactable : interactables){
            if (interactable instanceof Lock lock){
                lock.checkCollision(player);
                repaint();
            }
        }  
    }

    /**
        Checks for collisions between the player and the king.
    **/

    public void checkKing(Player player){
        for (InteractableObjects interactable : interactables){
            if (interactable instanceof King king){
                king.checkCollision(player);
                repaint();
            }
        }
    }

    /**
        Loads the tileMap depending on the level number.
    **/

    public void addLevel(int level) {
        switch(level){
            case 1 -> tileMap = "assets/maps/tileMap1.txt";
            case 2 -> tileMap = "assets/maps/tileMap2.txt";
            case 3 -> tileMap = "assets/maps/tileMap3.txt";
            case 4 -> tileMap = "assets/maps/tileMap4.txt";
            case 5 -> tileMap = "assets/maps/tileMap5.txt";
        }
        map = new Map(tileMap);
    }

    /**
        Draws the entire game screen, including the map, players, obstaclesl
        interactables, and popUps.
    **/
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        map.draw(g2d);

        ArrayList<InteractableObjects> interactablesCopy = new ArrayList<>(interactables);
        for (InteractableObjects interactable : interactablesCopy) {
            if (interactable instanceof KeyObject keyObject) {
                if (keyObject.getOwner() != null && keyObject.getOwner().equals(player1)) {
                    keyObject.checkDraw(g2d, player1);
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
    
    /**
        Responds to key release actions. Triggers the checkKeys, checkLocks, and checkKings
        methods when a player pressed E. It also closes the popUps. If the players
        reach the final level, they can also kill another player using E.
    **/
   
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E){
            checkKeys(player1);   
            checkLocks(player1);
            checkKing(player1);
            popUps.setFalse();
            if (level == 5){
                player1.getGF().setLoss();
            }
        }
    }

    //GETTER METHODS
    public Map getMap(){
        return this.map;
    }

    public PopUps getPopUps(){
        return popUps;
    }

    /**
        Checks if both players have completed the level.
    **/

    public boolean getComplete() {
        if (player1.getLevelCompleted() == true && player2.getLevelCompleted() == true) {
            return true;
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
