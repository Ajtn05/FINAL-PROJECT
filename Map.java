/** 
    This is the Map class, which loads the images and reads the tile map to 
    construct the layout of the map. Each tile image is assigned to a number and 
    the class has a method to reads the tile types from a text file 
    and associated them with the corresponding images.

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

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Map {
    private Tiles[] tiles;
    private String level;
    private int tileNum[][];
    int columns = 32;
    int rows = 24;

    public Map(String level){
        tiles = new Tiles[46];
        tileNum = new int[columns][rows];
        this.level = level;
        getImages();
        loadMap();
    }

    public int[][] getTileNum(){
        return tileNum;
    }

    /**
        Loads the tile sprites from the assets folder. 
        Each index in the tiles array corresponds to a specific tile image.
    **/

    public void getImages(){
        try {
            tiles[0] = new Tiles();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_TLedge.png"));
            
            tiles[1] = new Tiles();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_TRedge.png"));

            tiles[2] = new Tiles();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_MH.png"));

            tiles[3] = new Tiles();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_MV.png"));

            tiles[4] = new Tiles();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_BLedge.png"));
        
            tiles[5] = new Tiles();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_BRedge.png"));

            tiles[6] = new Tiles();
            tiles[6].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_Ledge.png"));

            tiles[7] = new Tiles();
            tiles[7].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_Redge.png"));

            tiles[8] = new Tiles();
            tiles[8].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_LI.png"));

            tiles[9] = new Tiles();
            tiles[9].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_DI.png"));

            tiles[10] = new Tiles();
            tiles[10].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_UI.png"));

            tiles[11] = new Tiles();
            tiles[11].image = ImageIO.read(getClass().getResourceAsStream("assets/images/wall.png"));

            tiles[12] = new Tiles();
            tiles[12].image = ImageIO.read(getClass().getResourceAsStream("assets/images/wall_torch.png"));

            tiles[13] = new Tiles();
            tiles[13].image = ImageIO.read(getClass().getResourceAsStream("assets/images/wall_Ltorch.png"));

            tiles[14] = new Tiles();
            tiles[14].image = ImageIO.read(getClass().getResourceAsStream("assets/images/wall_Rtorch.png"));

            tiles[15] = new Tiles();
            tiles[15].image = ImageIO.read(getClass().getResourceAsStream("assets/images/wall_window.png"));

            tiles[16] = new Tiles();
            tiles[16].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_RI.png"));

            tiles[17] = new Tiles();
            tiles[17].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_Uedge.png"));

            tiles[18] = new Tiles();
            tiles[18].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_Dedge.png"));

            tiles[19] = new Tiles();
            tiles[19].image = ImageIO.read(getClass().getResourceAsStream("assets/images/topwall_MH2.png"));

            tiles[20] = new Tiles();
            tiles[20].image = ImageIO.read(getClass().getResourceAsStream("assets/images/door1.png"));

            tiles[21] = new Tiles();
            tiles[21].image = ImageIO.read(getClass().getResourceAsStream("assets/images/door2.png"));

            tiles[22] = new Tiles();
            tiles[22].image = ImageIO.read(getClass().getResourceAsStream("assets/images/plate1.png"));

            tiles[23] = new Tiles();
            tiles[23].image = ImageIO.read(getClass().getResourceAsStream("assets/images/plate2.png"));

            tiles[24] = new Tiles();
            tiles[24].image = ImageIO.read(getClass().getResourceAsStream("assets/images/floor1.png"));

            tiles[25] = new Tiles();
            tiles[25].image = ImageIO.read(getClass().getResourceAsStream("assets/images/floor2.png"));

            tiles[26] = new Tiles();
            tiles[26].image = ImageIO.read(getClass().getResourceAsStream("assets/images/floor3.png"));

            tiles[27] = new Tiles();
            tiles[27].image = ImageIO.read(getClass().getResourceAsStream("assets/images/newdoor.png"));   
            
            tiles[28] = new Tiles();
            tiles[28].image = ImageIO.read(getClass().getResourceAsStream("assets/images/statue_UL.png"));    

            tiles[29] = new Tiles();
            tiles[29].image = ImageIO.read(getClass().getResourceAsStream("assets/images/statue_UR.png"));    

            tiles[30] = new Tiles();
            tiles[30].image = ImageIO.read(getClass().getResourceAsStream("assets/images/statue_ML.png"));    

            tiles[31] = new Tiles();
            tiles[31].image = ImageIO.read(getClass().getResourceAsStream("assets/images/statue_MR.png"));    

            tiles[32] = new Tiles();
            tiles[32].image = ImageIO.read(getClass().getResourceAsStream("assets/images/statue_LL.png"));   

            tiles[33] = new Tiles();
            tiles[33].image = ImageIO.read(getClass().getResourceAsStream("assets/images/statue_LR.png"));   
            
            tiles[34] = new Tiles();
            tiles[34].image = ImageIO.read(getClass().getResourceAsStream("assets/images/arena_floor.png"));    

            tiles[35] = new Tiles();
            tiles[35].image = ImageIO.read(getClass().getResourceAsStream("assets/images/portal.png"));    

            tiles[36] = new Tiles();
            tiles[36].image = ImageIO.read(getClass().getResourceAsStream("assets/images/arena_wall_TL.png"));    

            tiles[37] = new Tiles();
            tiles[37].image = ImageIO.read(getClass().getResourceAsStream("assets/images/arena_wall_TR.png"));    

            tiles[38] = new Tiles();
            tiles[38].image = ImageIO.read(getClass().getResourceAsStream("assets/images/arena_wall_BL.png"));    

            tiles[39] = new Tiles();
            tiles[39].image = ImageIO.read(getClass().getResourceAsStream("assets/images/arena_wall_BR.png"));    

            tiles[40] = new Tiles();
            tiles[40].image = ImageIO.read(getClass().getResourceAsStream("assets/images/arena_wall_L.png"));    

            tiles[41] = new Tiles();
            tiles[41].image = ImageIO.read(getClass().getResourceAsStream("assets/images/arena_wall_R.png"));    

            tiles[42] = new Tiles();
            tiles[42].image = ImageIO.read(getClass().getResourceAsStream("assets/images/arena_wall_U.png"));    

            tiles[43] = new Tiles();
            tiles[43].image = ImageIO.read(getClass().getResourceAsStream("assets/images/arena_wall_D.png"));    

            tiles[44] = new Tiles();
            tiles[44].image = ImageIO.read(getClass().getResourceAsStream("assets/images/arena_wall_9.png"));    

            tiles[45] = new Tiles();
            tiles[45].image = ImageIO.read(getClass().getResourceAsStream("assets/images/arena_wall_10.png"));    


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
        Reads the map layout from a text file and fills tileNum 2D array with the
        numbers from the file that correspond to the images.
    **/

    public void loadMap(){
        try {
            InputStream is = getClass().getResourceAsStream(level);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while(col < columns && row < rows){
                String line = br.readLine();
                String numbers[] = line.trim().split("\\s+");
                for (col = 0; col < columns; col++){
                    if (col < numbers.length){
                        int num = Integer.parseInt(numbers[col]);
                        tileNum[col][row] = num;
                    }
                    else {
                        tileNum[col][row] = -1;
                    }
                }
                col = 0;
                row++;
            }
            br.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
        Loops through the grid and draws the corresponding tile image at the correct
        column and row position based on where they are 
    **/

    public void draw(Graphics2D g){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < columns && row < rows){
            int num = tileNum[col][row];

            if (num >= 0 && num < tiles.length){
                g.drawImage(tiles[num].image, x, y, 32,32, null);
            }
            col++;
            x += 32;

            if (col == columns){
                col = 0;
                x = 0;
                row++;
                y += 32;
            }
        }
    }

    /**
        Initializes a buffered image for the tiles class.
    **/

    class Tiles {
        public BufferedImage image;
    }
}