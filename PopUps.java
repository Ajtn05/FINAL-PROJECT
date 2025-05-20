/** 
    This is the PopUps class, which handles drawing pop images during the game. 
    It loads the different popUp image depending on the type thats passed in
    the showPopUp method when its called.

    @author Janelle Angela C. Lopez (242682)
    @author Aldrin Joseph T. Nellas (243215)
	@version April 1, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PopUps {
    private BufferedImage GamePlay, KingMessage;
    public boolean showPopUp = false;
    private String type;

    /**
        Constucts a new PopUps and loads the different pop up images.
    **/

    public PopUps(){   
        getImages();
    }

    /**
        Loads the pop up images from the assets folder. 
    **/

    public void getImages(){
        try {
            GamePlay = ImageIO.read(getClass().getResourceAsStream("assets/images/GamePlay_PopUp.png"));
            KingMessage = ImageIO.read(getClass().getResourceAsStream("assets/images/King_PopUp.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
        Returns the image to be drawn based on the pop up type.
        @return Buffered Image of the pop up.
    **/

    public BufferedImage getPopUpImage(){
        switch (type){
            case "GamePlay": return GamePlay;
            case "KingMessage": return KingMessage;
        }
        return null;
    }

    /**
        Sets pop up to true and sets what type to draw.
    **/

    public void showPopUp(String type){
        this.type = type;
        showPopUp = true;
    }

    /**
        Sets showPopUp to false after the player presses E.
    **/

    public Boolean setFalse(){
        return showPopUp = false;
    }


    /**
        Draws the pop up on the screen depending if showPopUp is true.
    **/

    public void draw(Graphics2D g){
        if (showPopUp && type == "KingMessage"){
            g.drawImage(getPopUpImage(), 610, 70, 200, 100, null);
        } else if (showPopUp){
            g.drawImage(getPopUpImage(), 80, 132,  875, 477, null);
        }
    } 
}