import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
public class MenuFrame {

    private JFrame frame;
    private JTextField hostInput, portInput;
    public JLabel label, wait;
    private JButton connect;
    private int port;
    private String host;
    public Socket socket;
    private int playerID;

    public MenuFrame(){
        frame = new JFrame();
        hostInput = new JTextField(10);
        portInput = new JTextField(10);
        connect = new JButton("Connect");
        label = new JLabel("hi bb, put localhost in the first text field and 9999 in the second!");
        wait = new JLabel(" ");
    }

    public void setUpGUI(){
        Container cp = frame.getContentPane();
        FlowLayout flow = new FlowLayout();
        cp.setLayout(flow);
        frame.setPreferredSize(new Dimension(500, 140));
        frame.setTitle("Maze Game");
        cp.add(hostInput);
        cp.add(portInput);
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
        LevelManager lm = new LevelManager(host, port, 1);
        System.out.println("yes");
        lm.start();
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
