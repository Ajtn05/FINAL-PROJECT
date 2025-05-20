import java.util.*;

public class PressurePlate extends Entities implements Obstacle {
    private String ID;
    private ArrayList<int[]> tileCoordinates;
    private ArrayList<Integer> newTileNums;

    public PressurePlate(int x, int y, int w, int h, MapItem object){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.tileCoordinates = new ArrayList<>(Arrays.asList(object.getTileCoordinates()));
        this.newTileNums = new ArrayList<>(Arrays.asList(object.getNewTileNum()));
    }

    @Override
    public void checkCollision(Player player, Map map, GameCanvas gc){
        checkCollisionForPlayer(player, map);
    }

    public void checkCollisionForPlayer(Player player, Map map){
        int pX = player.getX();
        int pY = player.getY();

        if (pX + 24 > x && pX < x + width && 
            pY + 40 > y && pY < y + height) {

            for (int i = 0; i < tileCoordinates.size(); i++){
                int[] cord = tileCoordinates.get(i);
                int newtileNum = newTileNums.get(i);
                map.getTileNum()[cord[0]][cord[1]] = newtileNum;
            }
        }
    }

    @Override
    public String getID() {
        return ID;
    }
}