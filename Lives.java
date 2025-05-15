import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class Lives {
    private boolean used = false;
    private Player player = null;
    private int fullNumber = 0, takenNumber = 0;
    private BufferedImage full, taken;
    private ArrayList<BufferedImage> hearts;

    public Lives(Player player, int lives) {
        this.fullNumber = lives;
        this.player = player;
        getImage();
    }

    public void getImage() {
        try {
            full = ImageIO.read(getClass().getResourceAsStream("assets/images/heart.png"));
            taken = ImageIO.read(getClass().getResourceAsStream("assets/images/empty_heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getTypeImage() {
        if (used) {return taken;}
        else {return full;}
    }

    public void setUsed() {
        used = true;
    }


    public void draw(Graphics2D g) {
        for (int i= 0; i < fullNumber; i++) {
            g.drawImage(full, 20*i, 37, 24, 24, null);
        }
        for (int i = 5; i > fullNumber; i--) {
            g.drawImage(taken, 20*(i-1), 37, 24, 24, null);
        }
    }

    public void takeLife() {
        fullNumber--;
        takenNumber++;
    }

    public void addLife() {
        fullNumber++;
        takenNumber--;
    }

    public Player player() {
        return player;
    }

    public int getLives() {
        return fullNumber;
    }

    public void setLives(int newNum) {
        fullNumber = newNum;
    }

}