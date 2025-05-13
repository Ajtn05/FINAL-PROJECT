import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

public class King extends Entities {
    
    public King(int x, int y){
        this.x = x;
        this.y = y;
        getImages();
    }

     public void getImages(){
        try {
           king = ImageIO.read(getClass().getResourceAsStream("assets/images/GamePlay_PopUp.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g){
            g.drawImage(king, 80, 130, 875, 477, null);
    } 
}