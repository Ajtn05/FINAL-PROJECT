import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Lock implements InteractableObjects{
    private int x, y;
    private BufferedImage goldPadlock, silverPadlock, bronzePadlock;
    private boolean locked = true;
    private ArrayList<int[]> tileCoordinates;
    private ArrayList<Integer> newTileNums;
    private GameCanvas gc;
    private KeyObject keyObject;
    private String lockType;

    public Lock(int x, int y, ArrayList<int[]> tileCoordinates, ArrayList<Integer> newTileNums, GameCanvas gc, KeyObject keyObject, String lockType){
        this.x = x;
        this.y = y;
        this.tileCoordinates = tileCoordinates;
        this.newTileNums = newTileNums;
        this.gc = gc;
        this.keyObject = keyObject;
        this.lockType = lockType;
        getImage();
    }

    public void getImage(){
        try {
            goldPadlock = ImageIO.read(getClass().getResourceAsStream("goldPadlock.png"));  
            silverPadlock = ImageIO.read(getClass().getResourceAsStream("silverPadlock.png"));  
            bronzePadlock = ImageIO.read(getClass().getResourceAsStream("bronzePadlock.png"));  
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getLockType(){
        switch (lockType) {
            case "gold":
                return goldPadlock;
            case "silver":
                return silverPadlock;
            case "bronze":
                return bronzePadlock;
        }
        return null;
    }

    public void checkCollision(Player player){
        int pX = player.getX();
        int pY = player.getY();
       
        // if ((x-pX <= 40 && y-pY <= 60 && !(pX - x > 40) && !(pY - y > 60))) {
        //     if (player.hasKey() && locked){  
        //         player.interact("lock");
        //         unlock();
        //     } 
        // }

        if ((x-pX <= 40 && y-pY <= 60 && !(pX - x > 40) && !(pY - y > 60))) {
            if (player.hasKey() && locked){
                if (player.hasKeyType().equals(lockType)){
                    player.interact("lock", lockType);
                    unlock();
                    System.out.println("it shouldddd work");
                }

            //    if (keyObject.hasKeyType().equals("gold")){
            //         player.interact("goldLock");
            //         unlock();
            //   }  
            } 
        }
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

    public boolean isInteracted() {
        return !locked;
    }

    @Override
    public void draw(Graphics2D g){
        if (locked){
            g.drawImage(getLockType(), x, y, 24, 24, null);
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

    public boolean getLocked() {
        return locked;
    }
}