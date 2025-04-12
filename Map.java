import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Map {
    private Tiles[] tiles;
    private String level;
    int tileNum[][];
    int columns = 32;
    int rows = 24;
    //private BufferedImage mapAssets;

    public Map(String level){
        tiles = new Tiles[27];
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
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("topwall_TLedge.png"));
            
            tiles[1] = new Tiles();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("topwall_TRedge.png"));

            tiles[2] = new Tiles();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("topwall_MH.png"));

            tiles[3] = new Tiles();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("topwall_MV.png"));

            tiles[4] = new Tiles();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("topwall_BLedge.png"));
        
            tiles[5] = new Tiles();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("topwall_BRedge.png"));

            tiles[6] = new Tiles();
            tiles[6].image = ImageIO.read(getClass().getResourceAsStream("topwall_Ledge.png"));

            tiles[7] = new Tiles();
            tiles[7].image = ImageIO.read(getClass().getResourceAsStream("topwall_Redge.png"));

            tiles[8] = new Tiles();
            tiles[8].image = ImageIO.read(getClass().getResourceAsStream("topwall_Iedge.png"));

            tiles[9] = new Tiles();
            tiles[9].image = ImageIO.read(getClass().getResourceAsStream("topwall_Iedge2.png"));

            tiles[10] = new Tiles();
            tiles[10].image = ImageIO.read(getClass().getResourceAsStream("topwall_Iedge3.png"));

            tiles[11] = new Tiles();
            tiles[11].image = ImageIO.read(getClass().getResourceAsStream("wall.png"));

            tiles[12] = new Tiles();
            tiles[12].image = ImageIO.read(getClass().getResourceAsStream("wall_torch.png"));

            tiles[13] = new Tiles();
            tiles[13].image = ImageIO.read(getClass().getResourceAsStream("wall_Ltorch.png"));

            tiles[14] = new Tiles();
            tiles[14].image = ImageIO.read(getClass().getResourceAsStream("wall_Rtorch.png"));

            tiles[15] = new Tiles();
            tiles[15].image = ImageIO.read(getClass().getResourceAsStream("wall_window.png"));

            tiles[16] = new Tiles();
            tiles[16].image = ImageIO.read(getClass().getResourceAsStream("topwall_Iedge4.png"));

            tiles[17] = new Tiles();
            tiles[17].image = ImageIO.read(getClass().getResourceAsStream("topwall_Uedge.png"));

            tiles[18] = new Tiles();
            tiles[18].image = ImageIO.read(getClass().getResourceAsStream("topwall_Dedge.png"));

            tiles[19] = new Tiles();
            tiles[19].image = ImageIO.read(getClass().getResourceAsStream("topwall_MV2.png"));

            tiles[20] = new Tiles();
            tiles[20].image = ImageIO.read(getClass().getResourceAsStream("door_left.png"));

            tiles[21] = new Tiles();
            tiles[21].image = ImageIO.read(getClass().getResourceAsStream("door_right.png"));

            tiles[22] = new Tiles();
            tiles[22].image = ImageIO.read(getClass().getResourceAsStream("door_tl.png"));

            tiles[23] = new Tiles();
            tiles[23].image = ImageIO.read(getClass().getResourceAsStream("door_tr.png"));

            tiles[24] = new Tiles();
            tiles[24].image = ImageIO.read(getClass().getResourceAsStream("floor1.png"));

            tiles[25] = new Tiles();
            tiles[25].image = ImageIO.read(getClass().getResourceAsStream("floor2.png"));

            tiles[26] = new Tiles();
            tiles[26].image = ImageIO.read(getClass().getResourceAsStream("floor3.png"));


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
            } else {
                System.out.println("Invalid tile number: " + num + " at (" + col + ", " + row + ")");
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