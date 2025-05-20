/** 
    This is the Lives class, which manages the player's lives and 
    draws them on the screen. It keeps track of how many lives the 
    player has left and draws the full and empty hearts.

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
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class Lives {
    private boolean used = false;
    private Player player = null;
    private int fullNumber = 0, takenNumber = 0;
    private BufferedImage full, taken;
    private ArrayList<BufferedImage> hearts;

    /**
        Constructs a the Lives object for a given player with a specified
        number of starting lives, which is 5. 
        @param player        The player associated with this lives instance.
        @param lives         The initial number of the player's total lives.
    **/

    public Lives(Player player, int lives) {
        this.fullNumber = lives;
        this.player = player;
        getImage();
    }

    /**
        Loads the lives sprites from the assets folder. 
    **/

    public void getImage() {
        try {
            full = ImageIO.read(getClass().getResourceAsStream("assets/images/heart.png"));
            taken = ImageIO.read(getClass().getResourceAsStream("assets/images/empty_heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
        Returns the image to be drawn based on the how many lives
        the player has left. If they have full lives, the fullheart is drawn.
        When they lose a life, the empty gets drawin in its place.
        @return The full heart image if not used, otherwise the empty heart image.
    **/

    public BufferedImage getTypeImage() {
        if (used) {return taken;}
        else {return full;}
    }

    /**
        Draws the heart sprites on the screen, representing the player's current lives.
        The first loop draws the full hearts from left to right.
        The second loop draws empty hearts from right to left.
    **/

    public void draw(Graphics2D g) {
        for (int i= 0; i < fullNumber; i++) {
            g.drawImage(full, 20*i, 37, 24, 24, null);
        }
        for (int i = 5; i > fullNumber; i--) {
            g.drawImage(taken, 20*(i-1), 37, 24, 24, null);
        }
    }

    /**
        Reduces the number of lives by one and increases the count of lives taken.
    **/

    public void takeLife() {
        fullNumber--;
        takenNumber++;
    }

    /**
        Increases the number of lives by one and reduces the count of lives taken.
    **/

    public void addLife() {
        fullNumber++;
        takenNumber--;
    }

    /**
        Returns the current number of lives.
        @return number of lives remaining.
    **/

    public int getLives() {
        return fullNumber;
    }

    /**
        Updates number of remaining lives the player has.
        @param newNum   The new number of lives to set.
    **/

    public void setLives(int newNum) {
        fullNumber = newNum;
    }

}