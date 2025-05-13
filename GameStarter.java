public class GameStarter {
    public static void main(String[] args){
        MenuFrame mf = new MenuFrame();
        mf.setUpGUI();
        mf.setUpInputListeners();
        // mf.setUpButtonListeners();
        // mf.setUpGUI("hi bb, put localhost in the first text field and 9999 in the second!");
    }
}