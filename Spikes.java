import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Spikes implements Obstacle {
    public BufferedImage spikes, spike1, spike2, spike3, spike4, spike5, spike6;
    private int x, y, width, height;
    private String ID;
    private int spriteCounter = 0;
    private int spriteNum = 1;

    public Spikes(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        getImages();
    }

    public void getImages(){
        try {
            int imageW = 32;
            int imageH = 32;

            spikes = ImageIO.read(getClass().getResourceAsStream("assets/images/Spike_Trap.png"));
            spike1 = spikes.getSubimage(2,0, imageW, imageH);
            spike2 = spikes.getSubimage(98,0, imageW, imageH);
            spike3 = spikes.getSubimage(130,0, imageW, imageH);
            spike4 = spikes.getSubimage(226,0, imageW, imageH);
            spike5 = spikes.getSubimage(257,0, imageW, imageH);
            spike6 = spikes.getSubimage(2,0, imageW, imageH);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getSpikeTypeImage(){
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

    @Override
    public void checkCollision(Player player1, Player player2, Map map, GameCanvas gc){
        checkCollisionForPlayer(player1, map);
        checkCollisionForPlayer(player2, map);
    }

    public void checkCollisionForPlayer(Player player, Map map){
        int pX = player.getX();
        int pY = player.getY();

        if (pX + 24 > x && pX < x + width && 
            pY + 40 > y && pY < y + height) {

            if (spriteNum == 7 || spriteNum == 8 || spriteNum == 9 || 
                spriteNum == 10 || spriteNum == 11){
                player.kill();
            }
        }
    }

    @Override
    public String getID() {
        return ID;
    }

    public void updateSpriteAnimation(){
        spriteCounter++;
        if (spriteCounter >= 10){
            spriteNum = (spriteNum % 12) + 1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g){
        g.drawImage(getSpikeTypeImage(), x, y, width, height, null);
    } 
}