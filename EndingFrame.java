
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;

public class EndingFrame extends JComponent implements MouseMotionListener, MouseListener{
    public JFrame frame;

    private BufferedImage betrayed, win;
    private Rectangle hoverBoxReturn;
    private boolean isHoveringReturn = false;
    private String ending;


    public EndingFrame(String ending){
        this.ending = ending;
        hoverBoxReturn = new Rectangle(304, 578, 407, 63);
        getImages();
        addMouseMotionListener(this);
        addMouseListener(this);
    }

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

    public void getImages(){
        try {
            betrayed = ImageIO.read(getClass().getResourceAsStream("assets/images/EndingFrame_Betrayed.png"));
            win = ImageIO.read(getClass().getResourceAsStream("assets/images/EndingFrame_Win.png"));
        } catch (IOException ex){
            System.out.println("Error from EndingFrame");
        }
    }

    public BufferedImage getImageType(){
        switch(ending){
            case "betrayed": return betrayed;
            case "win": return win;
        }
        return null;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g.drawImage(getImageType(), 0, 0, null);
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        isHoveringReturn = hoverBoxReturn.contains(e.getPoint());
    }
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