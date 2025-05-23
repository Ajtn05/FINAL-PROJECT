/** 
    This is the Lock class, which handles collision logic for lock objects.

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
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Lock implements InteractableObjects{
    private int x, y;
    private String ID;
    private BufferedImage goldPadlock, silverPadlock, bronzePadlock;
    private boolean locked = true;
    private ArrayList<int[]> tileCoordinates;
    private ArrayList<Integer> newTileNums;
    private GameCanvas gc;
    private KeyObject keyObject;
    private String lockType;

    public Lock(int x, int y, MapItem object, GameCanvas gc, String lockType){
        this.x = x;
        this.y = y;
        this.tileCoordinates = new ArrayList<>(Arrays.asList(object.getTileCoordinates()));
        this.newTileNums = new ArrayList<>(Arrays.asList(object.getNewTileNum()));
        this.gc = gc;
        this.lockType = lockType;
        getImage();
    }

    public void getImage(){
        try {
            goldPadlock = ImageIO.read(getClass().getResourceAsStream("assets/images/goldPadlock.png"));  
            silverPadlock = ImageIO.read(getClass().getResourceAsStream("assets/images/silverPadlock.png"));  
            bronzePadlock = ImageIO.read(getClass().getResourceAsStream("assets/images/bronzePadlock.png"));  
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void checkCollision(Player player){
        int pX = player.getX();
        int pY = player.getY();

        if ((x-pX <= 40 && y-pY <= 60 && !(pX - x > 40) && !(pY - y > 60))) {
            if (player.hasKey() && locked){
                if (player.hasKeyType(lockType)){
                    player.interact(this);
                    unlock();
                }
            } 
        }
    }

    public void setKey(KeyObject key) {
        this.keyObject = key;
    }

    public void unlock(){
        locked = false;
        changeTile(gc.getMap());
        keyObject.setUsed();
    }

    public void changeTile(Map map){
        for (int i = 0; i < tileCoordinates.size(); i++){
            int[] cord = tileCoordinates.get(i);
            int newtileNum = newTileNums.get(i);
            map.getTileNum()[cord[0]][cord[1]] = newtileNum;
        }
    }

    @Override
    public void draw(Graphics2D g){
        if (locked){
            g.drawImage(getLockTypeImage(), x, y, 24, 24, null);
        }
    }

    //GETTERS
    public BufferedImage getLockTypeImage(){
        switch (lockType) {
            case "gold": return goldPadlock;
            case "silver": return silverPadlock;
            case "bronze": return bronzePadlock;
        }
        return null;
    }

    public String getLockType() {
        return lockType;
    }

    public boolean isInteracted() {
        return !locked;
    }

    @Override
    public int getX(){
        return x;
    }

    @Override
    public int getY(){
        return y;
    }

    public boolean getLocked() {
        return locked;
    }
}