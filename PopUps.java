import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PopUps {
    private BufferedImage GamePlay, KingMessage;
    public boolean showPopUp = false;
    private String type;

    public PopUps(){   
        getImages();
    }

    public void getImages(){
        try {
            GamePlay = ImageIO.read(getClass().getResourceAsStream("assets/images/GamePlay_PopUp.png"));
            KingMessage = ImageIO.read(getClass().getResourceAsStream("assets/images/King_PopUp.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getPopUpImage(){
        switch (type){
            case "GamePlay": return GamePlay;
            case "KingMessage": return KingMessage;
        }
        return null;
    }

    public void showPopUp(String type){
        this.type = type;
        showPopUp = true;
    }

    public Boolean setFalse(){
        return showPopUp = false;
    }

    public void draw(Graphics2D g){
        if (showPopUp && type == "KingMessage"){
            g.drawImage(getPopUpImage(), 610, 70, 200, 100, null);
        } else if (showPopUp){
            g.drawImage(getPopUpImage(), 80, 132,  875, 477, null);
        }
    } 
}