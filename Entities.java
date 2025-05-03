import java.awt.image.*;

public class Entities {
    public int x, y, speed;
    public String character;
    public BufferedImage boyLeft1, boyRight1, boyUp1, boyDown1, 
                         boyLeft2, boyRight2, boyUp2, boyDown2, 
                         girlUp1, girlDown1, girlLeft1, girlRight1, 
                         girlUp2, girlDown2, girlLeft2, girlRight2;
    public BufferedImage boyUp, boyDown, boyLeft, boyRight, girlUp, girlDown, girlLeft, girlRight;
    public BufferedImage key;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

}