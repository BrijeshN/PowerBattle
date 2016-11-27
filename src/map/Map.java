/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.awt.Graphics;
import powerbattle.Game;
import powerbattle.Handler;
import tiles.Tile;
import util.Utils;

/**
 *
 * @author Brijesh
 */
public class Map {
    
    private Handler handler;
    private int width, height;
    private int[][] tiles;
    private int spawnX, spawnY;
    
    public Map(Handler handler, String path){
        this.handler = handler;
        loadMap(path);
        
       
    }
    
    public void update(){
        
    }
    
    public void render(Graphics g){
        
        // making rendering map better, only render the part of the map which is visible on the screen
        
        //return the max number, avoiding negative mumber
        // gets xOffset, and tile width
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset()/ Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
        
        //gets yOffset, and tile height
        int yStart = (int) Math.max(0,handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);
        
        for(int y = yStart; y < yEnd; y++){
            for (int x = xStart; x < xEnd; x++){
                getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), 
                        (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
            }
        }
        
    }
    
    public Tile getTile(int x, int y){
        
        if((x < 0) || (y < 0) || (x >= width) || (y >= height)){
            return Tile.emptyTile;
        }
        
        // load tiles at index
        Tile t = Tile.tiles[tiles[x][y]];
        
        // if no tile specified, load this default tile
        if(t == null){
            return Tile.dirtTile;
        }
        return t;
    }
    
    // load map for the file
    private void loadMap(String path){
     
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        // Player spwan positon on x and y axis
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[width][height];
        
        // testing
       for(int x = 0; x < width; x++){
            for (int y = 0; y< height; y++){
                tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
            }
        }
        
    }
    
}
