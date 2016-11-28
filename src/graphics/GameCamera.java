/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import entity.Entity;
import powerbattle.Game;
import powerbattle.Handler;
import tiles.Tile;

/**
 *
 * @author Brijesh
 */
public class GameCamera {
    
    private Handler handler;
    private float xOffset, yOffset;
    
    public GameCamera(Handler handler, float xOffset, float yOffset){
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        
    }
    
    // this method help us to not show out side of the map
    public void checkBlankSpace(){
        if(xOffset < 0){
            xOffset = 0;
        } else if(xOffset > handler.getMap().getWidth() * Tile.TILEWIDTH - handler.getWidth()){
            xOffset = handler.getMap().getWidth() * Tile.TILEWIDTH - handler.getWidth();
        }
        
        if(yOffset < 0){
            yOffset = 0;
        } else if(yOffset > handler.getMap().getHeight() * Tile.TILEHEIGHT - handler.getHeight()){
            yOffset = handler.getMap().getHeight() * Tile.TILEHEIGHT - handler.getHeight();
        }
    }
    
    public void centerOnEntity(Entity e){
        
        // divide by two this way player gets centered on the screen
        xOffset = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
        yOffset = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
        
        checkBlankSpace();

    }

    public void move(float xAmt, float yAmt){
        xOffset += xAmt;
        yOffset += yAmt;
        checkBlankSpace();

    }
    
    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
    
}
