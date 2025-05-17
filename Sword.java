import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Sword extends Entities implements InteractableObjects {
    private String ID;
    private boolean unclaimed = true;
    private boolean used = false;
    private String keyType;
    private int keyOrder = 0;
    private Player owner = null;

    public Sword(int x, int y, String keyType){
        this.x = x;
        this.y = y;
        this.keyType = keyType;
        getImage();
    }
    
    public void getImage(){
        try {
            goldKey = ImageIO.read(getClass().getResourceAsStream("assets/images/goldKey.png"));  
            silverKey = ImageIO.read(getClass().getResourceAsStream("assets/images/silverKey.png"));  
            bronzeKey = ImageIO.read(getClass().getResourceAsStream("assets/images/bronzeKey.png"));  
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getKeyTypeImage(){
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

    public void claim(){
        unclaimed = false;
    }


    public void setOwner(Player owner) {
        this.owner = owner;
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
            g.drawImage(getKeyTypeImage(), x, y, 24, 24, null);
        }
    }

    public void checkDraw(Graphics2D g, Player player, int location) {
        for(KeyObject key : player.getKeys()) {
            if (this.equals(key) && !unclaimed) {
                g.drawImage(getKeyTypeImage(), getLocation(), 3, 24, 24, null);
            }
        }
    }

    public int getLocation() {
        int location;
        if (keyOrder == 1) {location = 3;}
        else {location = 10*keyOrder;}
        return location;
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

    public Player getOwner() {
        return owner;
    }
}