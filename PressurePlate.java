import java.util.*;

public class PressurePlate implements Obstacle {
    private int plateX, plateY, plateW, plateH;
    private ArrayList<int[]> tileCoordinates;
    private ArrayList<Integer> newTileNums;

    // private Player player1;
    // private Player player2;
    // private Map map;
    // private GameCanvas gc;

    public PressurePlate(int plateX, int plateY, int plateW, int plateH, ArrayList<int[]> tileCoordinates, ArrayList<Integer> newTileNums){
        this.plateX = plateX;
        this.plateY = plateY;
        this.plateW = plateW;
        this.plateH = plateH;
        this.tileCoordinates = tileCoordinates;
        this.newTileNums = newTileNums;

        // this.player1 = player1;
        // this.player2 = player2;
        // this.map = map;
        // this.gc = gc;
    }

    @Override
    public void checkCollision(Player player1, Player player2, Map map, GameCanvas gc){
        checkCollisionForPlayer(player1, map);
        checkCollisionForPlayer(player2, map);
    }

    public void checkCollisionForPlayer(Player player, Map map){
        int pX = player.getX();
        int pY = player.getY();

        // int plateX = 32;
        // int plateY = 530;
        // int plateW = 24;
        // int plateH = 24;

        if (pX + 24 > plateX && pX < plateX + plateW && 
            pY + 40 > plateY && pY < plateY + plateH) {

            for (int i = 0; i < tileCoordinates.size(); i++){
                int[] cord = tileCoordinates.get(i);
                int newtileNum = newTileNums.get(i);

                    // map.getTileNum()[27][9] = 24;
                    // map.getTileNum()[28][9] = 24;
                    // map.getTileNum()[1][17] = 23;

                    map.getTileNum()[cord[0]][cord[1]] = newtileNum;
                }
        }
    }
}