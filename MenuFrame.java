import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.net.*;

public class MenuFrame extends JComponent implements MouseMotionListener, MouseListener {
    public JFrame frame;
    private String host = null, playerType = null;
    private int port = 0;
    public Socket socket;

    private BufferedImage staticMenu, hovered_boy, hovered_girl, hovered_id, hovered_port;
    private Rectangle hoverBoxHost, hoverBoxPort, hoverBoxG, hoverBoxB, hoverBoxPlay;
    private JTextField hostInput, portInput;
    private boolean isHoveringHost = false, isHoveringPort = false, isHoveringGirl = false, 
                    isHoveringBoy = false, isHoveringPlay = false, isBoySelected = false, isGirlSelected = false;

    public MenuFrame(){
        hoverBoxHost = new Rectangle(449,280, 277,51);
        hoverBoxPort = new Rectangle(447,357, 279,52);
        hoverBoxB = new Rectangle(307,460, 167,160);
        hoverBoxG = new Rectangle(548,460, 167,160);
        hoverBoxPlay = new Rectangle(450, 662, 125, 65);

        hostInput = new JTextField(8);
        portInput = new JTextField(8);
        
        Font font = new Font("SubVarioOTW03-CondFat", Font.PLAIN, 45);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/fonts/SubVarioOTW03-CondFat.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }

        hostInput.setFont(font);
        portInput.setFont(font);
        hostInput.setBackground(new Color(255, 186,81));
        portInput.setBackground(new Color(255, 186,81));

        getImages();
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void setUpGUI(){
        frame = new JFrame("Maze Game");
        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setContentPane(this);
        frame.add(hostInput);
        frame.add(portInput);
        frame.pack();
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

     public void getImages(){
        try {
            staticMenu = ImageIO.read(getClass().getResourceAsStream("assets/images/staticMenu.png"));
            hovered_girl = ImageIO.read(getClass().getResourceAsStream("assets/images/hovered_girl.png"));
            hovered_boy = ImageIO.read(getClass().getResourceAsStream("assets/images/hovered_boy.png"));
            hovered_id = ImageIO.read(getClass().getResourceAsStream("assets/images/hovered_host.png"));
            hovered_port = ImageIO.read(getClass().getResourceAsStream("assets/images/hovered_port.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g.drawImage(staticMenu, 0, 0, null);

        if (isHoveringHost){
            g.drawImage(hovered_id, 0, 0, null);
        }

        if (isHoveringPort){
            g.drawImage(hovered_port, 0, 0, null);
        }

        if (isHoveringGirl || isGirlSelected){
            g.drawImage(hovered_girl, 0, 0, null);
        }

        if (isHoveringBoy || isBoySelected){
            g.drawImage(hovered_boy, 0, 0, null);
        }
    }

    public void end() {
        frame.dispose();
    }

    public void connect() {
        LevelManager lm = new LevelManager(host, port, playerType, 1, this);
        lm.start();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        isHoveringHost = hoverBoxHost.contains(e.getPoint());
        isHoveringPort = hoverBoxPort.contains(e.getPoint());
        isHoveringBoy = hoverBoxB.contains(e.getPoint());
        isHoveringGirl = hoverBoxG.contains(e.getPoint());
        isHoveringPlay = hoverBoxPlay.contains(e.getPoint());
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (isHoveringHost){
            hostInput.setBounds(447,280, 282,52);
            hostInput.setVisible(true);
            hostInput.requestFocusInWindow();
        }

        if (isHoveringPort){
            portInput.setBounds(447,357, 282,52);
            portInput.setVisible(true);
            portInput.requestFocusInWindow();
        }

        if (isHoveringBoy){
            playerType = "boy";
            isBoySelected = true;
        }

        if (isHoveringGirl){
            playerType = "girl";
            isGirlSelected = true;
        } 

        if (isHoveringPlay){
            host = hostInput.getText();
            port = Integer.parseInt(portInput.getText());

            if (host != null && port > 0 && playerType != null){
                connect();
            } else {
                System.out.println("fill out fields");
            }
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

    public void test(String sex) {
        frame = new JFrame();
        host = "localhost";
        port = 9999;
        switch(sex) {
            case "1": playerType = "boy"; break;
            case "2": playerType = "girl"; break;
        }
        LevelManager lm = new LevelManager(host, port, playerType, 1, this);
        lm.testStart();  
    }

}
