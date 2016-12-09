/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiles;

import Map2.DirtTile;
import Map2.DirtTile2;
import Map2.DirtTile3;
import Map2.FTTile;
import Map2.FTTile2;
import Map2.FTTile3;
import Map2.GrassTile;
import Map2.GrassTile2;
import Map2.GrassTile3;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Brijesh
 */
public class Tile {
    
    //Static variables
        //stores one instance of every single tile
        public static Tile[] tiles =  new Tile[256];
        public static Tile emptyTile = new EmptyTile(0);
        public static Tile wallTile = new WallTile(1);
        public static Tile grassTile1 = new FirstGrassTile(2);
        public static Tile grassTile2 = new SecondGrassTile(3);
        public static Tile grassTile3 = new ThirdGrassTile(4);
        public static Tile floatTile1 = new FirstFloatTile(5);
        public static Tile floatTile2 = new SecondFloatTile(6);
        public static Tile floatTile3 = new ThirdFloatTile(7); 
        public static Tile dirt = new Dirt(10);
        public static Tile dirt2 = new Dirt2(11);
        
        //Water Tiles
        public static Tile water1 = new WaterTile(8);  
        public static Tile water2 = new FullWaterTile(9);  
        
        
        // Object Tiles
        public static Tile stone = new Stone(12);
        public static Tile box = new BoxTile(13);
        public static Tile bush = new Bush(14);
        public static Tile sign = new SignTile(15);
        public static Tile tree = new TreeTile(16);
        public static Tile crystal = new CrystalTile(17);
        public static Tile ice = new IceTile(18);
        
        // Map 2 Tile
        
        public static Tile mdirt = new DirtTile(20);
        public static Tile mdirt2 = new DirtTile2(21);
        public static Tile mdirt3 = new DirtTile3(22);
        public static Tile mgrass = new GrassTile(23);
        public static Tile mgrass2 = new GrassTile2(24);
        public static Tile mgrass3 = new GrassTile3(25);
        public static Tile fl = new FTTile(26);
        public static Tile fl2 = new FTTile2(27);
        public static Tile fl3 = new FTTile3(28);

        
        //Class
    
    public static final int TILEWIDTH = 130, TILEHEIGHT = 130;
    
    protected BufferedImage texture;
    // id for each tile
    protected final int id;
    
    //Constructor
    public Tile(BufferedImage texture, int id){
        
        this.texture = texture;
        this.id = id;
        
        //pass the tile id and get that image regarding that id
        tiles[id] = this;
    }
    
    public void update(){
        
        
        
    }
    
    public void render(Graphics g, int x, int y){
        
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
        
    }
    
    // if it's not solid,player can walk on it
    public boolean isSolid(){
        return false;
    }
    
    public int getId(){
        return id;
    }
    
}
