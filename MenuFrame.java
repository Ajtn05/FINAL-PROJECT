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
    private Rectangle hoverBoxHost, hoverBoxPort, hoverBoxG, hoverBoxB;
    private JTextField hostInput, portInput;
    private boolean isHoveringHost = false, isHoveringPort = false, 
                    isHoveringGirl = false, isHoveringBoy = false;

    public MenuFrame(){
        hoverBoxHost = new Rectangle(442,316, 311,58);
        hoverBoxPort = new Rectangle(442,401, 311,58);
        hoverBoxB = new Rectangle(308,533, 167,160);
        hoverBoxG = new Rectangle(548,533, 167,160);

        hostInput = new JTextField(8);
        portInput = new JTextField(8);
        hostInput.setVisible(false);
        portInput.setVisible(false);
        
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

        if (isHoveringGirl){
            g.drawImage(hovered_girl, 0, 0, null);
        }

        if (isHoveringBoy){
            g.drawImage(hovered_boy, 0, 0, null);
        }
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

    public void end() {
        frame.dispose();
    }

    public void setUpInputListeners() {
        hostInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                host = hostInput.getText();
                System.out.println("host: " + host);
                hostInput.setVisible(false);
            }
        });

        portInput.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                port = Integer.parseInt(portInput.getText());
                System.out.println("port: " + port);
                portInput.setVisible(false); 
            }
        });

        // ActionListener buttonListener = new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent ae) {
        //         Object o = ae.getSource();
        //         if (o == connect) {
        //             connect();
        //         }
        //     }  
        // };

        // connect.addActionListener(buttonListener);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        isHoveringHost = hoverBoxHost.contains(e.getPoint());
        isHoveringPort = hoverBoxPort.contains(e.getPoint());
        isHoveringBoy = hoverBoxB.contains(e.getPoint());
        isHoveringGirl = hoverBoxG.contains(e.getPoint());
        repaint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        hostInput.setVisible(false);
        portInput.setVisible(false);

        if (hoverBoxHost.contains(e.getPoint())){
            hostInput.setBounds(439,313, 318,64);
            hostInput.setVisible(true);
            hostInput.requestFocusInWindow();
            checkInputs();
        }

        if (hoverBoxPort.contains(e.getPoint())){
            portInput.setBounds(439,401, 318,64);
            portInput.setVisible(true);
            portInput.requestFocusInWindow();
            checkInputs();
        }

        if (hoverBoxB.contains(e.getPoint())){
            playerType = "boy";
            checkInputs();
            // repaint();
            System.out.println("playerType: " + playerType);
        }

        if (hoverBoxG.contains(e.getPoint())){
            playerType = "girl";
            checkInputs();
            // repaint();
            System.out.println("playerType: " + playerType);
        } 
        
        // if (host == "localhost" && port == 9999 && playerType != null){
        //     connect();
        // }
    }

    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    private void checkInputs(){
        if (host != null && port > 0 && playerType != null){
            connect();
        }
    }

    public void connect() {
        // host = hostInput.getText();
        // port = Integer.parseInt(portInput.getText());
        // playerType = playerInput.getText();
        LevelManager lm = new LevelManager(host, port, playerType, 1, this);
        System.out.println("yes");
        lm.start();
    }

    // public void test(String sex) {
    //     frame = new JFrame();
    //     host = "localhost";
    //     port = 9999;
    //     switch(sex) {
    //         case "1": playerType = "boy"; break;
    //         case "2": playerType = "girl"; break;
    //     }
    //     LevelManager lm = new LevelManager(host, port, playerType, 1, this);
    //     lm.testStart();  
    // }
}
