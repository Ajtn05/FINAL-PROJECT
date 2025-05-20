/** 
    This is the KeyObject class which handles the logic for the keys the player uses to
    unlock doors. Each key has a type (gold, silver, bronze) and has methods for loading
    the key sprites, checking collisions, and other setter and getter methods.
    It extends the Entities class and implements the InteractableObjects interface. 

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
import javax.imageio.ImageIO;

public class KeyObject extends Entities implements InteractableObjects {
    private String ID;
    private boolean unclaimed = true;
    private boolean used = false;
    private String keyType;
    private int keyOrder = 0;
    private Player owner = null;

    /**
        Constructs a KeyObject with the specified position and type.
        @param x               The x-coordinates of the key.
        @param y               The y-coordinates of the key.
        @param keyType         The type of the key (gold, silver, bronze).
    **/

    public KeyObject(int x, int y, String keyType){
        this.x = x;
        this.y = y;
        this.keyType = keyType;
        getImage();
    }

    /**
        Loads the key sprites from the assets folder. 
    **/
    
    public void getImage(){
        try {
            goldKey = ImageIO.read(getClass().getResourceAsStream("assets/images/goldKey.png"));  
            silverKey = ImageIO.read(getClass().getResourceAsStream("assets/images/silverKey.png"));  
            bronzeKey = ImageIO.read(getClass().getResourceAsStream("assets/images/bronzeKey.png"));  
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
        Returns the image to be drawn based on the key's type.
        @return Buffered images for gold, silver, and bronze keys.
    **/

    public BufferedImage getKeyTypeImage(){
        switch (keyType) {
            case "gold": return goldKey;
            case "silver": return silverKey;
            case "bronze": return bronzeKey;
        }
        return null;
    }

    /**
        Returns the keyType the player currently has.
        @return String for the keyType.
    **/

    public String hasKeyType(){
        return keyType;
    }

    /**
        Checks for collision between the player and the keyObject. 
        If the collision is detected and the key is unclaimed, the player
        can collect the key and the boolean unclaimed gets set 
        to false;
    **/

    public void checkCollision(Player player){
        int pX = player.getX();
        int pY = player.getY();

        if (pX + 24 > x && pX < x + 24 &&
            pY + 40 > y && pY < y + 24) {
                if (unclaimed){
                    player.interact(this);
                    unclaimed = false;
                }
            }
    }

    /**
        Draws the key on the screen if it is unclaimed and not used.
    **/

    @Override
    public void draw(Graphics2D g){
        if (used){
            return;
        }
        if (unclaimed){
            g.drawImage(getKeyTypeImage(), x, y, 24, 24, null);
        }
    }

    /**
        Draws the key in the player's inventory after it has been collected.
        @param g                The graphics object used for rendering.
        @param player           The player who owns they key.
        @param location         The location for drawing the key in the inventory.
    **/

    public void checkDraw(Graphics2D g, Player player) {
        for(KeyObject key : player.getKeys()) {
            if (this.equals(key) && !unclaimed) {
                g.drawImage(getKeyTypeImage(), 10*keyOrder, 3, 24, 24, null);
            }
        }
    }

    /**
        Calculates the x position of the key to be drawn on the screen based
        on the key order.
        @return the x-coordinate for drawing the key.
    **/

    // public int getLocation() {
    //     int location;
    //     if (keyOrder == 1) {location = 10;}
    //     else {location = 10*keyOrder;}
    //     return location;
    // }

    /**
        Sets the key's order in the player's inventory.
    **/
    
    public void setKeyOrder(int keyOrder) {
        this.keyOrder = keyOrder;
    }

    /**
        Decrements the key's order, usually after another key is used.
    **/
 
    public void decrementKeyOrder() {
        keyOrder--;
    }
 
    /**
        Updates the unclaimed boolean when key has been interacted with.
        @return unclaimed = false. 
    **/

    @Override
    public boolean isInteracted(){
        return !unclaimed;
    }

    /**
        Sets the key as used, so it no longer draws in the game.
    **/

    public void setUsed(){
        used = true;
    }
    
    /**
        Sets the key as claimed (collected by the player).
    **/

     public void claim(){
        unclaimed = false;
    }

    /**
        Assigns the player who owns the key.
        @param owner    The player who owns the key.
    **/
 
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
        Returns the player who owns the key.
        @return         The player who owns the key.
    **/

    public Player getOwner() {
        return owner;
    }

    /**
        Returns the x-coordinate of the key.
    **/
 
    @Override
    public int getX(){
        return x;
    }

    /**
        Returns the y-coordinate of the key.
    **/

    @Override
    public int getY(){
        return y;
    }

    /**
        Returns ID of the key.
    **/

    @Override
    public String getID() {
        return ID;
    }
}