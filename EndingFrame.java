/** 
    This is the EndingFrame class GUI thats shown at the end of the game. It extends 
    JComponentand implements MouseMotionListener and MouseListener. It lets the 
    players return to the main menu by clicking on the play button. Different 
    images will be shown depending on whether the players win or get betrayed.

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
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;

public class EndingFrame extends JComponent implements MouseMotionListener, MouseListener {
    public JFrame frame;
    private BufferedImage betrayed, win;
    private Rectangle hoverBoxReturn;
    private boolean isHoveringReturn = false;
    private String ending;

    /**
        Constructs an EndingFrame based on the type of game ending.
        @param ending   String indicating the type of ending to display ("win" or betrayed).
    **/

    public EndingFrame(String ending){
        this.ending = ending;
        hoverBoxReturn = new Rectangle(304, 578, 407, 63);
        getImages();
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
        Sets up the GUI for the ending screen. Initializes the game's properties, 
        sets its size, and makes it visible.
    **/

    public void setUpEndGUI(){
        frame = new JFrame("Maze Game");
        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setContentPane(this);
        frame.pack();
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
        Loads the ending screen images from the assets folder. 
    **/

    public void getImages(){
        try {
            betrayed = ImageIO.read(getClass().getResourceAsStream("assets/images/EndingFrame_Betrayed.png"));
            win = ImageIO.read(getClass().getResourceAsStream("assets/images/EndingFrame_Win.png"));
        } catch (IOException ex){
            System.out.println("Error from EndingFrame");
        }
    }

    /**
        Returns the image to be drawn based on the game ending type.
        @return Buffered Image of the ending screen.
    **/

    public BufferedImage getImageType(){
        switch(ending){
            case "betrayed": return betrayed;
            case "win": return win;
        }
        return null;
    }

    /**
       Draws the image returned by the getImageType method using the
       Graphics2D object.
    **/

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(getImageType(), 0, 0, null);
        
    }

    /**
        Called when the mouse is moved over the hoverBoxReturn bounding box (a rectangle).
        Updates the isHoveringReturn boolean based on whether the cursor
        is detected within the rectangle's area.
    **/

    @Override
    public void mouseMoved(MouseEvent e) {
        isHoveringReturn = hoverBoxReturn.contains(e.getPoint());
    }

    /**
       Called when the player presses the area with the bounding box.
       The current frame will be disposed and the main menu will be set up.
    **/

    @Override
    public void mousePressed(MouseEvent e) {
        if (isHoveringReturn){
            MenuFrame mf = new MenuFrame();
            mf.setUpGUI();
            frame.dispose();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}

}