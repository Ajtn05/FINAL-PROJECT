import java.util.*;

public class PressurePlate implements Obstacle {
    private int x, y, width, height;
    private String ID;
    private ArrayList<int[]> tileCoordinates;
    private ArrayList<Integer> newTileNums;

    // public PressurePlate(int plateX, int plateY, int plateW, int plateH, ArrayList<int[]> tileCoordinates, ArrayList<Integer> newTileNums){
    //     this.plateX = plateX;
    //     this.plateY = plateY;
    //     this.plateW = plateW;
    //     this.plateH = plateH;
    //     this.tileCoordinates = tileCoordinates;
    //     this.newTileNums = newTileNums;

    public PressurePlate(int x, int y, int w, int h, MapItem object){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.tileCoordinates = new ArrayList<>(Arrays.asList(object.getTileCoordinates()));
        this.newTileNums = new ArrayList<>(Arrays.asList(object.getNewTileNum()));
    }

    @Override
    public void checkCollision(Player player1, Player player2, Map map, GameCanvas gc){
        checkCollisionForPlayer(player1, map);
        checkCollisionForPlayer(player2, map);
    }

    public void checkCollisionForPlayer(Player player, Map map){
        int pX = player.getX();
        int pY = player.getY();

        if (pX + 24 > x && pX < x + width && 
            pY + 40 > y && pY < y + height) {

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

    @Override
    public String getID() {
        return ID;
    }
}