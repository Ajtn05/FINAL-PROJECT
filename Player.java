/** 
    This is the Player class, which handles collision logic, movement update logic, and 
    the logic for interacting with different objects. It also has methods for loading
    the different sprite images and kill methods.

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
import java.awt.image.*;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class Player extends Entities {
    public boolean up = false, down = false, left = false, right = false;
    private GameFrame gf;
    private GameCanvas gc;
    private LevelManager lm;
    private int keysCollected = 0;
    private boolean hasKey = false, opensDoor = false, hasSword = false;
    private boolean levelCompleted = false;
    private ArrayList<Integer> PASSABLE_TILES;
    public ArrayList<KeyObject> keys;
    public Lives lives;
    private boolean dead = false;
    private int initialX, initialY;
    private EndingFrame ef;


    public Player (int x, int y, GameFrame gf, GameCanvas gc, String character, LevelManager lm){
        this.x = x;
        this.y = y; 
        this.initialX = x;
        this.initialY = y;
        this.character = character;
        this.gc = gc;
        this.gf = gf;
        this.lm = lm;
        lives = new Lives(this, 5);
        speed = 3;
        direction = "down";
        PASSABLE_TILES = new ArrayList<>(Arrays.asList(22, 23, 24, 25, 26, 34));
        keys = new ArrayList<>();
        getImages();
    }

    public void getImages(){
        try {
            int width = 16;
            int height = 32;

            boyUp = ImageIO.read(getClass().getResourceAsStream("assets/images/walk_up.png"));
            boyUp1 = boyUp.getSubimage(17, 19, width, height);
            boyUp2 = boyUp.getSubimage(257, 19, width, height);
            boyDown = ImageIO.read(getClass().getResourceAsStream("assets/images/walk_down.png"));
            boyDown1 = boyDown.getSubimage(66, 17, width, height);
            boyDown2 = boyDown.getSubimage(259, 17, width, height);
            boyLeft = ImageIO.read(getClass().getResourceAsStream("assets/images/walk_left_down.png"));
            boyLeft1 = boyLeft.getSubimage(18, 19, width, height);
            boyLeft2 = boyLeft.getSubimage(66, 19, width, height);
            boyRight = ImageIO.read(getClass().getResourceAsStream("assets/images/walk_right_down.png"));
            boyRight1 = boyRight.getSubimage(18, 19, width, height);
            boyRight2 = boyRight.getSubimage(65, 19, width, height);            

            girlUp = ImageIO.read(getClass().getResourceAsStream("assets/images/girl_walk_up.png"));
            girlUp1 = girlUp.getSubimage(18, 21, width, height);
            girlUp2 = girlUp.getSubimage(162, 21, width, height);
            girlDown = ImageIO.read(getClass().getResourceAsStream("assets/images/girl_walk_down.png"));
            girlDown1 = girlDown.getSubimage(66, 20, width, height);
            girlDown2 = girlDown.getSubimage(258, 21, width, height);
            girlLeft = ImageIO.read(getClass().getResourceAsStream("assets/images/girl_walk_left_down.png"));
            girlLeft1 = girlLeft.getSubimage(18, 18, width, height);
            girlLeft2 = girlLeft.getSubimage(66, 18, width, height);
            girlRight = ImageIO.read(getClass().getResourceAsStream("assets/images/girl_walk_right_down.png"));
            girlRight1 = girlRight.getSubimage(16, 18, width, height);
            girlRight2 = girlRight.getSubimage(64, 19, width, height);            

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean checkCollision(int x, int y, int[][] mapNum){
        boolean collision = false;
        int leftEdge = (x + 2) / 32;
        int rightEdge = (x + 18) / 32;
        int topEdge = (y + 15) / 32;
        int bottomEdge = (y + 32) / 32;

        int[] checkTiles = {
            mapNum[leftEdge][topEdge],
            mapNum[rightEdge][topEdge], 
            mapNum[leftEdge][bottomEdge], 
            mapNum[rightEdge][bottomEdge],
        };

        for (int tile : checkTiles){
            if (tile == 35) {
                levelCompleted = true;
                gf.levelComplete();
            }
            if (!PASSABLE_TILES.contains(tile)){
                collision = true;
                break;
            }
        }
        
        if (x < 0 || x >= 1020 || y < 0 || y >= 764) {
                collision = true;
        }

        return collision;
    }   


    public void interact(InteractableObjects object){
        
        if (object instanceof KeyObject keyObject) {
            KeyObject key = ((KeyObject) object);
            keysCollected++;
            key.claim();
            key.setKeyOrder(keysCollected);
            keys.add((KeyObject) object);
            key.setOwner(this);
        }

        if (object instanceof Lock lock) {
            int i = 0;
            for (KeyObject key : keys) {
                if (i == 1) {
                    key.decrementKeyOrder();
                    i = 0;
                }
                if (lock.getLockType().equals(key.hasKeyType())) {
                    i = 1;
                    lock.setKey(key);
                    keysCollected--;
                    opensDoor = true;
                    keys.remove(key);
                    break;
                }
            }
        }

        if (object instanceof King king){
            king.setDead();
            gf.setWin();
        }
    }


    public GameFrame getGF() {
        return gf;
    }

    public void respawn(){
        this.x = initialX;
        this.y = initialY;
    }

    public void kill(){
        if (lives.getLives() == 1) {
            setDead();
            checkKill();
        }
        else {
            respawn();
            lives.takeLife();
        }
    }

    public void checkKill() {
        if (this.equals(gf.getPlayer2())) {
            if (lm.getGF().getP1Dead()) {
                lm.resetLevel();
                System.out.println("bug here1");
            }
        }  
        else if (this.equals(gf.getPlayer1())) {
            if (lm.getGF().getP2Dead()) {
                lm.resetLevel();
                System.out.println("bug here2");
            }
        }  
        else {
            dead = false;
            lives.addLife();
        }
    }

    public void setDead() {dead = true;}

    public boolean getDead() {return dead;}

    public boolean hasKeyType(String type){
        for (KeyObject key : keys) {
            if (key.hasKeyType().equals(type)) {
                return true;
            }
        }
        return false;
     }

    public boolean hasKey(){
        if (keys.size() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public boolean opensDoor(){
        return opensDoor;
    }

    public void stopDoor() {
        opensDoor = false;
    }

    public void update(Map map){
        int potentialX = x;
        int potentialY = y;
        boolean moving = false;
        
        if (up){
            direction = "up";
            potentialY -= speed;
            moving = true;
        }
        if (down){
            direction = "down";
            potentialY += speed;
            moving = true;
        }
        if (left){
            direction = "left";
            potentialX -= speed;
            moving = true;
        }
        if (right){
            direction = "right";
            potentialX += speed;
            moving = true;

        }

        if (!checkCollision(potentialX, potentialY, map.getTileNum())){
            x = potentialX;
            y = potentialY;
        }

        updateSpriteAnimation(moving);
    }
    
    private void updateSpriteAnimation(boolean moving){
        spriteCounter++;
        if (moving){
             if (spriteCounter > 10){
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
             }
        } else {
            spriteNum = 0;
        }
    }

    public void draw(Graphics2D g){
        BufferedImage image = null;
 
        if (character.equals("boy")){
            if (spriteNum == 0){
                switch (direction){
                    case "up": image = boyUp1; break;
                    case "down": image = boyDown1; break;
                    case "left": image = boyLeft1; break;
                    case "right": image = boyRight1; break;
                } 
            } else {
                switch (direction){
                    case "up": 
                        if (spriteNum == 1){image = boyUp1;}
                        if (spriteNum == 2) {image = boyUp2;} break;
                    case "down": 
                        if (spriteNum == 1){image = boyDown1;}
                        if (spriteNum == 2) {image = boyDown2;} break;
                    case "left":
                        if (spriteNum == 1){image = boyLeft1;}
                        if (spriteNum == 2) {image = boyLeft2;} break;
                    case "right": 
                        if (spriteNum == 1){image = boyRight1;}
                        if (spriteNum == 2) {image = boyRight2;} break;
                }
            }
        }

        if (character.equals("girl")){
            if (spriteNum == 0){
                switch (direction){
                    case "up" -> image = girlUp1;
                    case "down" -> image = girlDown1;
                    case "left" -> image = girlLeft1;
                    case "right" -> image = girlRight1;
                } 
            } else {
                switch (direction){
                    case "up" -> { 
                        if (spriteNum == 1) {image = girlUp1;}
                        if (spriteNum == 2) {image = girlUp2;}
                    }
                    case "down" -> { 
                        if (spriteNum == 1){
                            image = girlDown1; 
                        }
                        if (spriteNum == 2) {
                            image = girlDown2; 
                        }
                    }
                    case "left" -> {
                        if (spriteNum == 1){
                            image = girlLeft1; 
                        }
                        if (spriteNum == 2) {
                            image = girlLeft2; 
                        }
                    }
                    case "right" -> {
                        if (spriteNum == 1){
                            image = girlRight1; 
                        }
                        if (spriteNum == 2) {
                            image = girlRight2; 
                        }
                    }
                }
            }
        }
        
        g.drawImage(image, x, y, 24, 40, null);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp(boolean up){
        this.up = up;
    }

    public void moveDown(boolean down){
        this.down = down;
    }

    public void moveLeft(boolean left){
        this.left = left;
    }

    public void moveRight(boolean right){
        this.right = right;
    }

    public ArrayList<KeyObject> getKeys() {
        return keys;
    }
    public boolean getLeft() {
        return left;
    }

    public boolean getRight() {
        return right;
    }

    public boolean getUp() {
        return up;
    }

    public boolean getDown() {
        return down;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
    
    public int getKeysCollected() {
        return keysCollected;
    }

    public boolean getLevelCompleted() {
        return levelCompleted;
    }

    public String getDirection() {
        return direction;
    }
    
    public void setX(double x) {
        this.x = (int) x;
    }

    public void setY(double y) {
        this.y = (int) y;
    }

    public void setLives(int num) {
        lives.setLives(num);
    }

    public void setLevelNotComplete() {
        levelCompleted = false;
    }

    public int getLives() {
        return lives.getLives();
    }
}