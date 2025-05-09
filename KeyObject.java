import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class KeyObject extends Entities implements InteractableObjects {
    private int x, y;
    private String ID;
    private boolean unclaimed = true;
    private boolean used = false;
    private String keyType;

    public KeyObject(int x, int y, String keyType){
        this.x = x;
        this.y = y;
        this.keyType = keyType;
        getImage();
    }
    
    public void getImage(){
        try {
            goldKey = ImageIO.read(getClass().getResourceAsStream("goldKey.png"));  
            silverKey = ImageIO.read(getClass().getResourceAsStream("silverKey.png"));  
            bronzeKey = ImageIO.read(getClass().getResourceAsStream("bronzeKey.png"));  
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getKeyType(){
        switch (keyType) {
            case "gold":
                return goldKey;
            case "silver":
                return silverKey;
            case "bronze":
                return bronzeKey;
        }
        return null;
    }

    public String hasKeyType(){
        return keyType;
     }

    public void checkCollision(Player player){
        int pX = player.getX();
        int pY = player.getY();

        if (pX + 24 > x && pX < x + 24 &&
            pY + 40 > y && pY < y + 24) {
                if (unclaimed){
                    player.interact("key", keyType);
                    unclaimed = false;
                }
            }
    }

    public void setUsed(){
       used = true;
    }

    @Override
    public boolean isInteracted(){
        return !unclaimed;
    }

    @Override
    public void draw(Graphics2D g){
        if (used){
            return;
        }
        if (unclaimed){
            g.drawImage(getKeyType(), x, y, 24, 24, null);
        } else if (!unclaimed){
            g.drawImage(getKeyType(), 3, 3, 24, 24, null);
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

    @Override
    public String getID() {
        return ID;
    }
}