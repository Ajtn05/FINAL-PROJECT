import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entities {
    public boolean up = false, 
                   down = false, 
                   left = false, 
                   right = false;

    public Player (){
        x = 1;
        y = 64;
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
        int leftEdge = x / 32;
        int rightEdge = (x + 26) / 32;
        int topEdge = y / 32;
        int bottomEdge = (y + 26) / 32;
        
        boolean collision = (mapNum[leftEdge][topEdge] == 30 || 
                             mapNum[rightEdge][topEdge] == 30 ||
                             mapNum[leftEdge][bottomEdge] == 30 || 
                             mapNum[rightEdge][bottomEdge] == 30); 


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
        
        if (up){
            direction = "up";
            potentialY -= speed;
        }
        if (down){
            direction = "down";
            potentialY += speed;
        }
        if (left){
            direction = "left";
            potentialX -= speed;
        }
        if (right){
            direction = "right";
            potentialX += speed;
        }

        if (!checkCollision(potentialX, potentialY, map.getTileNum())){
            x = potentialX;
            y = potentialY;
        }
    
        spriteCounter++;
        if (spriteCounter > 10){
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
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

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void draw(Graphics2D g){
        BufferedImage image = null;
 
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
        g.drawImage(image, x, y, 26, 26, null);
    }
}