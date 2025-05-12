import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Traps implements Obstacle {
    public BufferedImage spikes, spike1, spike2, spike3, spike4, spike5, spike6,
                         fire, fire1, fire2, fire3, fire4, fire5, fire6, fire7, fire8, fire9;
    private int x, y, width, height;
    private String ID;
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private int speed;
    private String trap;

    public Traps(String trap, int x, int y, int w, int h, int speed){
        this.trap = trap;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.speed = speed;
        getImages();
    }

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


    @Override
    public void checkCollision(Player player, Map map, GameCanvas gc){
        checkCollisionForPlayer(player, map);
    }

    public void checkCollisionForPlayer(Player player, Map map){
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

    @Override
    public String getID() {
        return ID;
    }

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