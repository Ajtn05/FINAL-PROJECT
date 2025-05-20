/** 
    This is the PressurePlate class. When the player steps on a tile with a pressure plate,
    it changes that tile into a tile image of where it has been pressed. It extends
    the Entities class and implements the Obstacle interface. 

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

import java.util.*;

public class PressurePlate extends Entities implements Obstacle {
    private String ID;
    private ArrayList<int[]> tileCoordinates;
    private ArrayList<Integer> newTileNums;

    /**
        Constucts a pressure plate object's bounding box at the given x and y coordinates
        and size. It passes a MapItem Object for handling the tile changes
        after a player has stepped on the bounding box.
        @param x            x coordinate of the bounding box
        @param y            y coordinate of the bounding box
        @param w            width of the bounding box
        @param h            height of the bounding box
        @param object       MapItem object that handles tile changes
    **/

    public PressurePlate(int x, int y, int w, int h, MapItem object){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.tileCoordinates = new ArrayList<>(Arrays.asList(object.getTileCoordinates()));
        this.newTileNums = new ArrayList<>(Arrays.asList(object.getNewTileNum()));
    }

    /**
        Checks if the player is colliding with the pressure plate's bounding box
        and changes the tile image.
    **/

    @Override
    public void checkCollision(Player player, Map map){
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
}