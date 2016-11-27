/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiles;

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
        public static Tile grassTile = new GrassTile(1);
        public static Tile dirtTile = new DirtTile(2);
        public static Tile waterTile = new WaterTile(3);
        
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
