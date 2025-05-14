import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

public class King extends Entities implements InteractableObjects {
    private String ID;
    private boolean dead = false;

    public King(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        getImage();
    }

     public void getImage(){
        try {
           kingSprite = ImageIO.read(getClass().getResourceAsStream("assets/images/King_Sprite.png"));
           king = kingSprite.getSubimage(6,9,367,467);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void checkCollision(Player player) {
        int pX = player.getX();
        int pY = player.getY();

        if (pX + 24 > x && pX < x + width && 
            pY + 40 > y && pY < y + height) {
                player.interact(this);
        }
    }

    public void draw(Graphics2D g){
        if (dead == false){
            g.drawImage(king, 564, 130, 32, 48, null);
        }
    }

    public void setDead() {
        dead = true;
     }

    @Override
    public String getID() {
       return ID;
    }

    @Override
    public boolean isInteracted() {
       return !dead;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}