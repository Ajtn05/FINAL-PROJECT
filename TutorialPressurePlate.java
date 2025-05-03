
public class TutorialPressurePlate {
    private Player player1;
    private Player player2;
    private Map map;
    private GameCanvas gc;

    public TutorialPressurePlate(Player player1, Player player2, Map map, GameCanvas gc){
        this.player1 = player1;
        this.player2 = player2;
        this.map = map;
        this.gc = gc;
    }

    public void checkPlateCollision(){
        checkCollisionForPlayer(player1);
        checkCollisionForPlayer(player2);
    }

    public void checkCollisionForPlayer(Player player){
        int pX = player.getX();
        int pY = player.getY();

        int plateX = 32;
        int plateY = 530;
        int plateW = 24;
        int plateH = 24;

        if (pX + 24 > plateX && pX < plateX + plateW && 
            pY + 40 > plateY && pY < plateY + plateH) {
                map.getTileNum()[27][9] = 24;
                map.getTileNum()[28][9] = 24;
                map.getTileNum()[1][17] = 23;
            }
    }
}