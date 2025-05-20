/** 
    This is the King class, which draws the King character on the game's last level and handles
    logic for when the player interacts with it. It has methods for loading the key sprites, 
    checking collisions, and other setter and getter methods. It extends the Entities class 
    and implements the InteractableObjects interface. 

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

import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

public class King extends Entities implements InteractableObjects {
    private String ID;
    private boolean dead = false;

    /**
        Constructs a King with the specified position and size
        @param x               The x-coordinates of the king.
        @param y               The y-coordinates of the king.
        @param w               The width of the king.
        @param h               The height of the king.
    **/

    public King(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        getImage();
    }

    /**
        Loads the king sprite from the assets folder. 
    **/

     public void getImage(){
        try {
           kingSprite = ImageIO.read(getClass().getResourceAsStream("assets/images/king_walk_down.png"));
           king = kingSprite.getSubimage(354,16,16,32);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
        Checks for collision between the player and the king. 
        If the collision is detected, the player can use its 
        interact method to kill the king.
    **/

    @Override
    public void checkCollision(Player player) {
        int pX = player.getX();
        int pY = player.getY();

        if (pX + 24 > x && pX < x + width && 
            pY + 40 > y && pY < y + height) {
                player.interact(this);
        }
    }

    /**
        Only draws the king on the screen if it's not marked as dead.
    **/

    public void draw(Graphics2D g){
        if (dead == false){
            g.drawImage(king, 564, 130, 32, 48, null);
        }
    }

    /**
        Sets the dead boolean to be true.
    **/

    public void setDead() {
        dead = true;
    }

    /**
        Updates the dead when king has been killed.
        @return dead = true.
    **/

    @Override
    public boolean isInteracted() {
       return !dead;
    }

    /**
        Returns the x-coordinate of the key.
    **/


    @Override
    public int getX() {
        return x;
    }

    /**
        Returns the y-coordinate of the key.
    **/

    @Override
    public int getY() {
        return y;
    }
}