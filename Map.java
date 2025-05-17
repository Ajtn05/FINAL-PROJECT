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
        tiles = new Tiles[36];
        tileNum = new int[columns][rows];
        this.level = level;
        getImages();
        loadMap();
    }

    public int[][] getTileNum(){
        return tileNum;
    }

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


        } catch (IOException e){
            e.printStackTrace();
        }
    }

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

    class Tiles {
        public BufferedImage image;
    }
}