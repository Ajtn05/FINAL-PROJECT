import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entities {
    public boolean up = false, 
                   down = false, 
                   left = false, 
                   right = false;

    public Player (int x, int y){
        this.x = x; //1
        this.y = y; //64
        speed = 4;
        direction = "down";
        getImage();
    }

    public void getImage(){
        try {
            boyUp1 = ImageIO.read(getClass().getResourceAsStream("boy_up_1.png"));
            boyUp2 = ImageIO.read(getClass().getResourceAsStream("boy_up_2.png"));
            boyDown1 = ImageIO.read(getClass().getResourceAsStream("boy_down_1.png"));
            boyDown2 = ImageIO.read(getClass().getResourceAsStream("boy_down_2.png"));
            boyLeft1 = ImageIO.read(getClass().getResourceAsStream("boy_left_1.png"));
            boyLeft2 = ImageIO.read(getClass().getResourceAsStream("boy_left_2.png"));
            boyRight1 = ImageIO.read(getClass().getResourceAsStream("boy_right_1.png"));
            boyRight2 = ImageIO.read(getClass().getResourceAsStream("boy_right_2.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean checkCollision(int x, int y, int[][] mapNum){
        boolean collision = false;
        int leftEdge = (x - 2) / 32;
        int rightEdge = (x + 24) / 32;
        int topEdge = (y + 2) / 32;
        int bottomEdge = (y + 24) / 32;

        for (int i = 0; i < 22; i++){
            if (collision = mapNum[leftEdge][topEdge] == i || 
                            mapNum[rightEdge][topEdge] == i ||
                            mapNum[leftEdge][bottomEdge] == i || 
                            mapNum[rightEdge][bottomEdge] == i ) {
                collision = true;
                break;
            }   
        }

        if (x == 0 || x == 1024 || y == 0 || y == 768) {
            //basically level complete state i think
            collision = true;
        }
    
        // System.out.println("x: " + x);
        // System.out.println("y: " + y);
        // System.out.println("Left Edge: " + leftEdge);
        // System.out.println("Right Edge: " + rightEdge);
        // System.out.println("Top Edge: " + topEdge);
        // System.out.println("Bottom Edge: " + bottomEdge);
        return collision;
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
    
        //walking animation?
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

    public String getDirection() {
        return direction;
    }
    
    public void setX(double x) {
        System.out.println("player x: " + x);
        this.x = (int) x;
    }

    public void setY(double y) {
        System.out.println("player y: " + y);
        this.y = (int) y;
    }

    public void draw(Graphics2D g){
        BufferedImage image = null;
 
        
        if (spriteNum == 0){
            switch (direction){
                case "up":
                    image = boyUp1;
                    break;
                case "down":
                    image = boyDown1; 
                    break;
                case "left":
                    image = boyLeft1; 
                    break;
                case "right":
                    image = boyRight1; 
                    break;
            } 
        } else {
            switch (direction){
                case "up": 
                    if (spriteNum == 1){
                        image = boyUp1; 
                }
                    if (spriteNum == 2) {
                        image = boyUp2; 
                    }
                    break;
                case "down": 
                    if (spriteNum == 1){
                        image = boyDown1; 
                    }
                    if (spriteNum == 2) {
                        image = boyDown2; 
                    }
                    break;
                case "left":
                    if (spriteNum == 1){
                        image = boyLeft1; 
                    }
                    if (spriteNum == 2) {
                        image = boyLeft2; 
                    }
                    break;
                case "right":
                    if (spriteNum == 1){
                        image = boyRight1; 
                    }
                    if (spriteNum == 2) {
                        image = boyRight2; 
                    }
                    break;
            }
        }
        g.drawImage(image, x, y, 26, 26, null);
    }
}