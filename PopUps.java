import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PopUps {
    private BufferedImage GamePlay, GameOver, YouDied, KingMessage, message;
    public boolean showPopUp = false;
    private int counter = 0;
    private int duration = 600;
    private String type;
    private GameCanvas gc;

    public PopUps(){   
        getImages();
    }

    public void getImages(){
        try {
           GamePlay = ImageIO.read(getClass().getResourceAsStream("assets/images/GamePlay_PopUp.png"));
           GameOver = ImageIO.read(getClass().getResourceAsStream("assets/images/GameOver_PopUp.png"));
           YouDied = ImageIO.read(getClass().getResourceAsStream("assets/images/YouDied_PopUp.png"));
           KingMessage = ImageIO.read(getClass().getResourceAsStream("assets/images/King_PopUp.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getPopUpImage(){
        switch (type){
            case "GamePlay": return GamePlay;
            case "GameOver": return GameOver;
            case "KingMessage": return KingMessage;
            case "Died": return YouDied;
        }
        return null;
    }

    public void showPopUp(String type){
        this.type = type;
        showPopUp = true;
        counter = 0;
    }

    public Boolean setFalse(){
        return showPopUp = false;
    }

    public void update(GameCanvas gc, Player player1, Player player2){
        this.gc = gc;

        if (showPopUp){
            counter++;
            if (counter >= duration){
                showPopUp = false;
            }
        }

        if (gc.player1.getLives() == 0 || gc.player2.getLives() == 0){
            showPopUp("Died");
        }
    }

    public void draw(Graphics2D g){
        if (showPopUp && type == "KingMessage"){
            g.drawImage(getPopUpImage(), 610, 70, 200, 100, null);
        } else if (showPopUp){
            g.drawImage(getPopUpImage(), 80, 132,  875, 477, null);
        }
    } 
}