import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GameFrame extends JComponent {
    private JFrame frame;
    private GameCanvas gc;
    private Player player;
    private Timer timer;
    private Map map;

    public GameFrame(){
        frame = new JFrame();
        gc = new GameCanvas();
        player = new Player();
        map = new Map();
        gc.setPlayer(player);
    }

    public void setUpGUI(){
        gc.setPreferredSize(new Dimension(1024, 768));
        frame.add(gc);
        frame.setTitle("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setFocusable(true);
        frame.setVisible(true);
        gc.requestFocusInWindow();

    }

    public void startGameTimer(){
        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                player.update(map);
                gc.repaint();
            }
        });
        timer.start();
    }

    public void addKeyBindings(){
        ActionMap am = gc.getActionMap();
        InputMap im = gc.getInputMap();

        createBinding(am, im, "up", KeyEvent.VK_W);
        createBinding(am, im, "down", KeyEvent.VK_S);
        createBinding(am, im, "left", KeyEvent.VK_A);
        createBinding(am, im, "right", KeyEvent.VK_D);
    }

    private void createBinding(ActionMap am, InputMap im, String action, int key){
        am.put(action + "Action", new AbstractAction(){
            public void actionPerformed(ActionEvent ae){
                switch (action){
                    case "up": player.moveUp(true);
                    break;
                    case "down": player.moveDown(true);
                    break;
                    case "left": player.moveLeft(true);
                    break;
                    case "right": player.moveRight(true);
                    break;
                }
            }
        });

        am.put(action + "ReleaseAction", new AbstractAction() {
            public void actionPerformed(ActionEvent ae){
                switch (action){
                    case "up": player.moveUp(false);
                    break;
                    case "down": player.moveDown(false);
                    break;
                    case "left": player.moveLeft(false);
                    break;
                    case "right": player.moveRight(false);
                    break;
                }
            }
        });

        im.put(KeyStroke.getKeyStroke(key, 0, false), action + "Action" );
        im.put(KeyStroke.getKeyStroke(key, 0, true), action + "ReleaseAction");    
    }
}
