/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.awt.Graphics;
import powerbattle.Game;
import tiles.Tile;
import util.Utils;

/**
 *
 * @author Brijesh
 */
public class Map {
    
    private Game game;
    private int width, height;
    private int[][] tiles;
    private int spawnX, spawnY;
    
    public Map(Game game, String path){
        this.game = game;
        loadMap(path);
        
       
    }
    
    public void update(){
        
    }
    
    public void render(Graphics g){
        
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                getTile(x, y).render(g, x*Tile.TILEWIDTH, y*Tile.TILEHEIGHT);
            }
        }
        
    }
    
    public Tile getTile(int x, int y){
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
