public class MapItem {
    private int[][] tileCoordinates;
    private Integer [] newTilenum;

    public MapItem(int[][] tileCoordinates, Integer [] newTileNum) {
        this.tileCoordinates = tileCoordinates;
        this.newTilenum = newTileNum;
    }

    public int[][] getTileCoordinates() {
        return tileCoordinates;
    }

    public Integer [] getNewTileNum() {
        return newTilenum;
    }
}