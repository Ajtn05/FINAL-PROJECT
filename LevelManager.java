import java.util.*;

public class LevelManager {
    private String host, playerType;
    private int port;
    private GameCanvas gc;
    private GameFrame gf;
    // private PopUps popUps;
    private KeyObject keyObject;
    private int level;
    private MenuFrame mf;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<InteractableObjects> interactables;

    public LevelManager(String host, int port, String playerType, int level, MenuFrame mf) {
        this.host = host;
        this.port = port;
        this.playerType = playerType;
        this.level = level;
        this.mf = mf;
        obstacles = new ArrayList<>();
        interactables = new ArrayList<>();
    }

    public ArrayList<Obstacle> getObstacles(){
        return obstacles;
    }

    public void setObstacles(ArrayList<Obstacle> newObstacles) {
        obstacles = newObstacles;
    }

    public void start() {
        gc = new GameCanvas(level, obstacles, interactables);
        gf = new GameFrame(gc, this, playerType);
        if (gf.connectToServer(host, port, playerType, mf)) {
            gf.setUpGUI();
            gf.startGameTimer();
            gf.addKeyBindings();
            setUpObstacles();
        }
        else {
            mf.frame.dispose();
            // mf.setUpGUI("Player type already exists, please choose another");
            mf.setUpGUI();
        }
    }

    //DELETE AFTER
    public void testStart() {
        gc = new GameCanvas(level, obstacles, interactables);
        gf = new GameFrame(gc, this, playerType);
        if (gf.testConnectToServer(host, port, playerType, mf)) {
            gf.setUpGUI();
            gf.startGameTimer();
            gf.addKeyBindings();
            setUpObstacles();
        }
    }
    
    public void addLevel() {
        level++;
        gc.addLevel(level);
        setUpObstacles();
    }

    public void resetLevel() {
        System.out.println("reset level");
        gc.addLevel(level);
        gf.gameReset();
        setUpObstacles();
    }

    public void setUpObstacles(){
        obstacles.clear();
        interactables.clear();
        switch(level){
            case 5:

                obstacles.add(new Traps("spike", 352, 382, 32, 32, 10));
                obstacles.add(new Traps("fire", 97, 314, 32, 32, 6));
                obstacles.add(new Traps("fire", 672, 314, 32, 32, 7));
                obstacles.add(new Traps("spike", 833, 606, 32, 32, 9));

                MapItem PressurePlate = new MapItem(new int[][]{{27,9}, {28,9}, {1,17}}, new Integer[]{24, 24, 23});
                obstacles.add(new PressurePlate(32,530,24,24, PressurePlate));
                
                KeyObject key = new KeyObject(228,354, "gold");
                interactables.add(key);

                MapItem lock1 = new MapItem(new int[][]{{1,13}}, new Integer[]{24});
                interactables.add(new Lock(36,423, lock1, gc, key, "gold"));
              
                KeyObject key2 = new KeyObject(485,642, "silver");
                interactables.add(key2);

                MapItem lock2 = new MapItem(new int[][]{{30, 18}}, new Integer[]{24});
                interactables.add(new Lock(965,582, lock2, gc, key2, "silver"));

                MapItem PressurePlate2 = new MapItem(new int[][]{{15,8}, {11, 3}}, new Integer[]{23, 26});
                obstacles.add(new PressurePlate(481,242,24,24, PressurePlate2));

                KeyObject key20 = new KeyObject(356, 164, "bronze");
                interactables.add(key20);

                MapItem lock20 = new MapItem(new int[][]{{15, 16}}, new Integer[]{24});
                interactables.add(new Lock(484,518, lock20, gc, key20, "bronze"));

                MapItem PressurePlate15 = new MapItem(new int[][]{{26, 5}, {15, 4}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(840, 158,24,24, PressurePlate15));

                gc.getPopUps().showPopUp("GamePlay");
                
                break;
            case 2:

                obstacles.add(new Traps("spike", 97, 414, 32, 32, 10));
                obstacles.add(new Traps("fire", 288, 634, 32, 32, 8));
                obstacles.add(new Traps("spike", 225, 410, 32, 32, 7));
                obstacles.add(new Traps("spike", 225, 374, 32, 32, 7));
                obstacles.add(new Traps("fire", 480, 506, 32, 32, 9));
                obstacles.add(new Traps("fire", 960, 278, 32, 32, 9));
                obstacles.add(new Traps("fire", 960, 374, 32, 32, 9));
                obstacles.add(new Traps("spike", 610, 122, 32, 32, 9));

                MapItem PressurePlate3 = new MapItem(new int[][]{{3, 16}, {3, 21}, {23, 4}}, new Integer[]{23, 24, 26});
                obstacles.add(new PressurePlate(101,506,24,24, PressurePlate3));

                KeyObject key3 = new KeyObject(387, 708, "bronze");
                interactables.add(key3);

                MapItem lock3 = new MapItem(new int[][]{{7, 8}}, new Integer[]{25});
                interactables.add(new Lock(228, 265, lock3, gc, key3, "bronze"));

                KeyObject key4 = new KeyObject(549, 68, "silver");
                interactables.add(key4);

                MapItem lock4 = new MapItem(new int[][]{{11, 16}}, new Integer[]{26});
                interactables.add(new Lock(357, 520, lock4, gc, key4, "silver"));

                KeyObject key5 = new KeyObject(420, 515, "gold");
                interactables.add(key5);

                MapItem lock5 = new MapItem(new int[][]{{21, 6}}, new Integer[]{26});
                interactables.add(new Lock(677, 200, lock5, gc, key5, "gold"));

                MapItem PressurePlate4 = new MapItem(new int[][]{{23, 13}, {21, 18}}, new Integer[]{23, 26});
                obstacles.add(new PressurePlate(740,414,24,24, PressurePlate4));

                KeyObject key6 = new KeyObject(580, 612, "bronze");
                interactables.add(key6);

                MapItem PressurePlate5 = new MapItem(new int[][]{{28, 10}, {7, 21}}, new Integer[]{23, 26});
                obstacles.add(new PressurePlate(900,315,24,24, PressurePlate5));

                KeyObject key7 = new KeyObject(932, 94, "silver");
                interactables.add(key7);

                MapItem lock6 = new MapItem(new int[][]{{25, 15}}, new Integer[]{25});
                interactables.add(new Lock(805, 486, lock6, gc, key7, "silver"));

                MapItem lock7 = new MapItem(new int[][]{{30, 21}}, new Integer[]{25});
                interactables.add(new Lock(965, 680, lock7, gc, key6, "bronze"));
            
                MapItem PressurePlate16 = new MapItem(new int[][]{{30, 3}, {13, 4}}, new Integer[]{23, 26});
                obstacles.add(new PressurePlate(964,94,24,24, PressurePlate16));

                KeyObject key21 = new KeyObject(740, 514, "gold");
                interactables.add(key21);

                MapItem lock21 = new MapItem(new int[][]{{16, 21}}, new Integer[]{25});
                interactables.add(new Lock(516, 677, lock21, gc, key21, "gold"));

                MapItem PressurePlate17 = new MapItem(new int[][]{{17, 7}, {14, 21}}, new Integer[]{23, 26});
                obstacles.add(new PressurePlate(553,222,24,24, PressurePlate17));

                break;
            case 3:

                obstacles.add(new Traps("spike", 34, 254, 32, 32, 10));
                obstacles.add(new Traps("fire", 256, 58, 32, 32, 8));
                obstacles.add(new Traps("fire", 704, 155, 32, 32, 6));
                obstacles.add(new Traps("spike", 642, 701, 32, 32, 8));
                obstacles.add(new Traps("spike", 676, 701, 32, 32, 8));
                obstacles.add(new Traps("spike", 962, 250, 32, 32, 6));
                obstacles.add(new Traps("fire", 417, 442, 32, 32, 7));
                obstacles.add(new Traps("spike", 740, 510, 32, 32, 7));
                obstacles.add(new Traps("fire", 832, 508, 32, 32, 6));

                MapItem PressurePlate18 = new MapItem(new int[][]{{3, 14}, {14, 4}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(105,442,24,24, PressurePlate18));

                MapItem PressurePlate19 = new MapItem(new int[][]{{19, 10}, {5, 19}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(617,318,24,24, PressurePlate19));

                MapItem PressurePlate20 = new MapItem(new int[][]{{21, 8}, {3, 10}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(673,254,24,24, PressurePlate20));

                MapItem PressurePlate6 = new MapItem(new int[][]{{9, 20}, {18, 7}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(288,634,24,24, PressurePlate6));

                MapItem PressurePlate21 = new MapItem(new int[][]{{3, 8}, {25, 9}, {26, 9}}, new Integer[]{23, 24, 26});
                obstacles.add(new PressurePlate(97,242,24,24, PressurePlate21));

                KeyObject key8 = new KeyObject(517, 67, "bronze");
                interactables.add(key8);

                KeyObject key9 = new KeyObject(100, 642, "silver");
                interactables.add(key9);

                MapItem lock8 = new MapItem(new int[][]{{10, 4}}, new Integer[]{25});
                interactables.add(new Lock(324, 133, lock8, gc, key8, "bronze"));

                KeyObject key11 = new KeyObject(804, 67, "silver");
                interactables.add(key11);

                MapItem lock9 = new MapItem(new int[][]{{15, 9}}, new Integer[]{26});
                interactables.add(new Lock(484, 294, lock9, gc, key11, "silver"));

                MapItem PressurePlate7 = new MapItem(new int[][]{{15, 13}, {11, 21}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(488,414,24,24, PressurePlate7));

                KeyObject key10 = new KeyObject(675, 611, "bronze");
                interactables.add(key10);

                KeyObject key13 = new KeyObject(355, 451, "bronze");
                interactables.add(key13);

                MapItem lock10 = new MapItem(new int[][]{{28, 5}}, new Integer[]{25});
                interactables.add(new Lock(901, 165, lock10, gc, key13, "bronze"));

                MapItem PressurePlate8 = new MapItem(new int[][]{{30, 2}, {5, 3}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(964,58,24,24, PressurePlate8));

                MapItem lock11 = new MapItem(new int[][]{{19, 18}}, new Integer[]{25});
                interactables.add(new Lock(612, 582, lock11, gc, key9, "silver"));

                KeyObject key12 = new KeyObject(963, 164, "gold");
                interactables.add(key12);

                MapItem lock12 = new MapItem(new int[][]{{17, 15}}, new Integer[]{25});
                interactables.add(new Lock(549, 485, lock12, gc, key12, "gold"));

                MapItem lock13 = new MapItem(new int[][]{{29, 22}}, new Integer[]{25});
                interactables.add(new Lock(932, 710, lock13, gc, key10, "bronze"));
                
                break;
            case 4:

                obstacles.add(new Traps("spike", 99, 127, 32, 32, 6));
                obstacles.add(new Traps("spike", 99, 348, 32, 32, 6));
                obstacles.add(new Traps("fire", 32, 506, 32, 32, 7));

                obstacles.add(new Traps("fire", 192, 700, 32, 32, 8));
                obstacles.add(new Traps("fire", 224, 700, 32, 32, 8));
                obstacles.add(new Traps("fire", 256, 700, 32, 32, 8));

                obstacles.add(new Traps("spike", 226, 480, 32, 32, 8));
                obstacles.add(new Traps("spike", 226, 448, 32, 32, 8));

                obstacles.add(new Traps("fire", 352, 284, 32, 32, 5));
                obstacles.add(new Traps("spike", 482, 602, 32, 32, 8));

                obstacles.add(new Traps("spike", 642, 350, 32, 32, 6));
                obstacles.add(new Traps("spike", 738, 350, 32, 32, 6));

                obstacles.add(new Traps("spike", 612, 702, 32, 32, 7));

                obstacles.add(new Traps("fire", 961, 412, 32, 32, 6));
                obstacles.add(new Traps("fire", 961, 316, 32, 32, 9));
                obstacles.add(new Traps("fire", 961, 220, 32, 32, 6));

                obstacles.add(new Traps("spike", 610, 62, 32, 32, 7));
                obstacles.add(new Traps("spike", 482, 190, 32, 32, 8));
                obstacles.add(new Traps("spike", 482, 222, 32, 32, 8));

                MapItem PressurePlate9 = new MapItem(new int[][]{{3, 14}, {28, 4}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(104,446,24,24, PressurePlate9));

                KeyObject key14 = new KeyObject(37,642, "silver");
                interactables.add(key14);

                KeyObject key15 = new KeyObject(358, 706, "gold");
                interactables.add(key15);

                MapItem lock14 = new MapItem(new int[][]{{7, 10}}, new Integer[]{25});
                interactables.add(new Lock(228, 326, lock14, gc, key14, "silver"));

                MapItem PressurePlate10 = new MapItem(new int[][]{{9, 10}, {30, 14}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(291,324,24,24, PressurePlate10));

                KeyObject key16 = new KeyObject(835, 450, "bronze");
                interactables.add(key16);

                MapItem PressurePlate22 = new MapItem(new int[][]{{11, 2}, {11, 12}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(353,62,24,24, PressurePlate22));

                MapItem PressurePlate23 = new MapItem(new int[][]{{28, 13}, {22, 4}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(896,414,24,24, PressurePlate23));

                MapItem lock15 = new MapItem(new int[][]{{9, 16}}, new Integer[]{25});
                interactables.add(new Lock(293, 517, lock15, gc, key15, "gold"));

                MapItem PressurePlate11 = new MapItem(new int[][]{{11, 16}, {17, 12}, {18, 12}}, new Integer[]{23, 24, 25});
                obstacles.add(new PressurePlate(352,510,24,24, PressurePlate11));

                MapItem lock16 = new MapItem(new int[][]{{24, 19}}, new Integer[]{25});
                interactables.add(new Lock(773, 614, lock16, gc, key16, "bronze"));

                MapItem PressurePlate12 = new MapItem(new int[][]{{22, 14}, {8, 4}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(712,446,24,24, PressurePlate12));

                KeyObject key17 = new KeyObject(710, 706, "silver");
                interactables.add(key17);

                MapItem lock17 = new MapItem(new int[][]{{26, 10}}, new Integer[]{25});
                interactables.add(new Lock(836, 327, lock17, gc, key17, "silver"));                

                KeyObject key18 = new KeyObject(901, 258, "bronze");
                interactables.add(key18);

                MapItem lock18 = new MapItem(new int[][]{{17, 6}}, new Integer[]{25});
                interactables.add(new Lock(548, 198, lock18, gc, key18, "bronze"));

                MapItem PressurePlate13 = new MapItem(new int[][]{{30, 2}, {3, 20}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(965,58,24,24, PressurePlate13));

                MapItem PressurePlate14 = new MapItem(new int[][]{{19, 5}, {13, 7}}, new Integer[]{23, 24});
                obstacles.add(new PressurePlate(613,154,24,24, PressurePlate14));

                KeyObject key19 = new KeyObject(421, 66, "gold");
                interactables.add(key19);

                MapItem lock19 = new MapItem(new int[][]{{30, 21}}, new Integer[]{25});
                interactables.add(new Lock(965, 677, lock19, gc, key19, "gold"));

                break;
            case 1:

                // for (int i = 0; i < 20; i++){ obstacles.add(new Traps("fire", 192 + (i*32), 187, 32, 32, 6));}
                // for (int i = 0; i < 14; i++){obstacles.add(new Traps("fire", 160, 187 + (i*32), 32, 32, 6));}
                // for (int i = 0; i < 21; i++){obstacles.add(new Traps("fire", 192 + (i*32), 603, 32, 32, 6));}
                // for (int i = 0; i < 14; i++){obstacles.add(new Traps("fire", 832, 187 + (i*32), 32, 32, 6));}

                King king = new King(564, 130, 32, 48);
                interactables.add(king);

                break;
        }   
    }

    //GETTERS
    public InteractableObjects getInteractableObjects(String name) {
        for (InteractableObjects interactable : interactables) {
            if (interactable.getID().equals(name)) {
                return interactable;
            }
        }
        return null;
    }

    public GameCanvas getGC() {return gc;}

    public GameFrame getGF() {return gf;}

    public int getLevel() {return level;}
}