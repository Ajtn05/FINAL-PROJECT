/** 
    This is the MapItem class, which handles the tile changes to apply to the map. It stores 
    the coordinates of specific tiles and the corresponding new tile values that should replace 
    them when triggered by either a pressure plate or a lock has been interacted with.

    @author Janelle Angela C. Lopez (242682)
    @author Aldrin Joseph T. Nellas (243215)
	@version April 1, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

public class MapItem {
    private int[][] tileCoordinates;
    private Integer [] newTilenum;

    /**
        Constructs a MapItem with the given tile coordinates and new tile numbers.
        @param tileCoordinates        Specific row and column of tile to be changed.
        @param newTileNum             New tile number to change it to.
    **/

    public MapItem(int[][] tileCoordinates, Integer [] newTileNum) {
        this.tileCoordinates = tileCoordinates;
        this.newTilenum = newTileNum;
    }

    /**
        Returns the coordinates of the tiles that will be changed.
        @return A 2D array of [coloumn][row] tile positions.
    **/

    public int[][] getTileCoordinates() {
        return tileCoordinates;
    }

    /**
        Returns the new tile values that will replace the existing tiles.
        @return Array of integers representing new tile numbers.
    **/

    public Integer [] getNewTileNum() {
        return newTilenum;
    }
}