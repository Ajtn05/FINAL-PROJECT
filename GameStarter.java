

public class GameStarter {
    public static void main(String[] args){
        // GameFrame gf = new GameFrame();
        // gf.connectToServer();
        // gf.setUpGUI();
        // gf.startGameTimer();
        // gf.addKeyBindings();

        MenuFrame mf = new MenuFrame();
        mf.setUpButtonListeners();
        mf.setUpGUI("hi bb, put localhost in the first text field and 9999 in the second!");
    }
}