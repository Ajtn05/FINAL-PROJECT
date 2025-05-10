import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
public class MenuFrame {

    public JFrame frame;
    private JTextField hostInput, portInput, playerInput;
    public JLabel label, wait;
    private JButton connect;
    private int port;
    private String host, playerType, message;
    public Socket socket;
    private Container cp;

    public MenuFrame(){
        cp = null;
        frame = null;
        hostInput = new JTextField(8);
        portInput = new JTextField(8);
        playerInput = new JTextField(8);
        connect = new JButton("Connect");
        label = new JLabel(" ");
        wait = new JLabel(" ");
    }

    public void setUpGUI(String message){
        frame = new JFrame();
        label = null;
        this.message = message;
        cp = null;
        label = new JLabel(message);
        this.cp = frame.getContentPane();
        FlowLayout flow = new FlowLayout();
        cp.setLayout(flow);
        frame.setPreferredSize(new Dimension(500, 140));
        frame.setTitle("Maze Game");
        cp.add(hostInput);
        cp.add(portInput);
        cp.add(playerInput);
        cp.add(label);
        cp.add(connect);
        cp.add(wait);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }

    public void connect() {
        host = hostInput.getText();
        port = Integer.parseInt(portInput.getText());
        playerType = playerInput.getText();
        LevelManager lm = new LevelManager(host, port, playerType, 1, this);
        System.out.println("yes");
        lm.start();
    }

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


    public void end() {
        frame.dispose();
    }

    public void setUpButtonListeners() {
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Object o = ae.getSource();
                if (o == connect) {
                    connect();
                }
            }  
        };

        connect.addActionListener(buttonListener);
    }
}
