import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class KeyObject extends Entities implements InteractableObjects {
    private int x, y;
    private boolean unclaimed = true;

    public KeyObject(int x, int y){
        this.x = x;
        this.y = y;
        getImage();
    }
    public void getImage(){
        try {
            key = ImageIO.read(getClass().getResourceAsStream("key.png"));            
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void checkCollision(Player player){
        int pX = player.getX();
        int pY = player.getY();

        if (pX + 24 > x && pX < x + 24 &&
            pY + 40 > y && pY < y + 24) {
                if (unclaimed){
                    player.collect("key");
                    unclaimed = false;
                }
            }
    }

    public boolean unClaimed(){
        return true;
    }

    @Override
    public boolean isInteracted(){
        return !unclaimed;
    }

    @Override
    public void draw(Graphics2D g){
        if (unclaimed){
            g.drawImage(key, x, y, 24, 24, null);
        }
    }

    @Override
    public int getX(){
        return x;
    }

    @Override
    public int getY(){
        return y;
    }
}