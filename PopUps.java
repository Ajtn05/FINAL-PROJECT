import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PopUps {
    private BufferedImage GamePlay, GameOver, YouDied;
    public boolean showPopUp = false;
    private int counter = 0;
    private int duration = 600;
    private GameCanvas gc;

    public PopUps(GameCanvas gc){
        this.gc = gc;
        getImages();
    }

    public void getImages(){
        try {
           GamePlay = ImageIO.read(getClass().getResourceAsStream("assets/images/GamePlay_PopUp.png"));
           GameOver = ImageIO.read(getClass().getResourceAsStream("assets/images/GameOver_PopUp.png"));
           YouDied = ImageIO.read(getClass().getResourceAsStream("assets/images/YouDied_PopUp.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getPopUpImage(){
        if (gc.getLevel() == 1){
           return GamePlay;
        }

        if (gc.getLevel() == 6){
            return GameOver;
        }        
        return YouDied;
    }

    public void showPopUp(){
        showPopUp = true;
        counter = 0;
    }

    public Boolean setFalse(){
        return showPopUp = false;
    }

    public void update(){
        if (showPopUp){
            counter++;
            if (counter >= duration){
                showPopUp = false;
            }
        }
    }

    public void draw(Graphics2D g){
        if (showPopUp){
            g.drawImage(getPopUpImage(), 80, 130, 875, 477, null);
        }
    } 

}