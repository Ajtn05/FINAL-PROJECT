/** 
    This is the Traps class, which handles where the traps are drawn on the Map. 
    It has methods for loading different trap sprites, updating the sprite animation,
    checking collisions with the traps, and drawing them. When a player comes in 
    contact with a trap, they die and go back to the starting point.

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
import javax.imageio.ImageIO;

public class Traps extends Entities implements Obstacle {
    private String trap;

    /**
        Constucts the trap object's type depending on type passed and its bounding 
        box at the given x and y coordinates and size. It also passes an integer 
        to determine how fast the sprites will change.
        @param type         string trap type
        @param x            x coordinate of the bounding box
        @param y            y coordinate of the bounding box
        @param w            width of the bounding box
        @param h            height of the bounding box
        @param speed        int to determine speed of traps
    **/

    public Traps(String trap, int x, int y, int w, int h, int speed){
        this.trap = trap;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.speed = speed;
        getImages();
    }

    /**
        Loads the trap sprites from the assets folder. 
    **/

    public void getImages(){
        int imageW = 32;
        int imageH = 32;
        int fireH = 41;
        
        try {
            spikes = ImageIO.read(getClass().getResourceAsStream("assets/images/Spike_Trap.png"));
            spike1 = spikes.getSubimage(2,0, imageW, imageH);
            spike2 = spikes.getSubimage(98,0, imageW, imageH);
            spike3 = spikes.getSubimage(130,0, imageW, imageH);
            spike4 = spikes.getSubimage(226,0, imageW, imageH);
            spike5 = spikes.getSubimage(257,0, imageW, imageH);
            spike6 = spikes.getSubimage(2,0, imageW, imageH);

            fire = ImageIO.read(getClass().getResourceAsStream("assets/images/Fire_Trap.png"));
            fire1 = fire.getSubimage(0,0, imageW, fireH);
            fire2 = fire.getSubimage(32,0, imageW, fireH);
            fire3 = fire.getSubimage(64,0, imageW, fireH);
            fire4 = fire.getSubimage(96,0, imageW, fireH);
            fire5 = fire.getSubimage(224,0, imageW, fireH);
            fire6 = fire.getSubimage(256,0, imageW, fireH);
            fire7 = fire.getSubimage(288,0, imageW, fireH);
            fire8 = fire.getSubimage(320,0, imageW, fireH);
            fire9 = fire.getSubimage(352,0, imageW, fireH);

        } catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
        Returns the type of trap to be drawn depending on the what
        type of trapped is passed when its constructed. It returns 
        different sprites depending on the sprite counter.
        @return Buffered Image of the trap sprites.
    **/

    public BufferedImage getSpikeTypeImage(){
        if (trap.equals("spike")){
            switch (spriteNum){
                case 1: return spike1; 
                case 2: return spike1; 
                case 3: return spike2; 
                case 4: return spike3; 
                case 5: return spike3; 
                case 6: return spike3;
                case 7: return spike4;
                case 8: return spike5; 
                case 9: return spike5; 
                case 10: return spike5; 
                case 11: return spike4; 
                case 12: return spike3;
                default: return spike1;
            }
        }

        if (trap.equals("fire")){
            switch (spriteNum){
                case 1: return fire1; 
                case 2: return fire2; 
                case 3: return fire3; 
                case 4: return fire4; 
                case 5: return fire3; 
                case 6: return fire5;
                case 7: return fire6;
                case 8: return fire6; 
                case 9: return fire7; 
                case 10: return fire7; 
                case 11: return fire3; 
                case 12: return fire2;
                default: return fire1;
            }  
        }
        return null;
    }


    /**
        Checks for collisions between the player and the trap. When the player
        collides with a specific sprite image, it kills them.
    **/

    @Override
    public void checkCollision(Player player, Map map){
        int pX = player.getX();
        int pY = player.getY();

        if (pX + 24 > x && pX < x + width && 
            pY + 40 > y && pY < y + height) {

            if (trap.equals("spike")){
                if (spriteNum == 7 || spriteNum == 8 || spriteNum == 9 || 
                spriteNum == 10 || spriteNum == 11){
                    player.kill();
                }
            }
           
            if (trap.equals("fire")){
                if (spriteNum == 7 || spriteNum == 8 || 
                    spriteNum == 9 || spriteNum == 10){
                    player.kill();
                }
            }
        }
    }

    /**
        This method gets called in the game loop and each time its called, 
        the spritecounter increases. Once the sprite counter reaches the speed
        it, a different sprite image gets loaded and resets the counter to zero.
        The speed determines how many frames to wait before the next trap sprite gets
        drawn.
    **/

    public void updateSpriteAnimation(){
        spriteCounter++;
        if (spriteCounter >= speed){
            spriteNum = (spriteNum % 12) + 1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g){
        g.drawImage(getSpikeTypeImage(), x, y, width, height, null);
    } 
}