/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerbattle;

import graphics.GameCamera;
import map.Map;
import userInput.KeyManager;
import userInput.MouseManager;

/**
 *
 * @author Brijesh
 */
public class Handler {
    
    // judt pass handler object and
    // allows us to access game object, map object etc.
    
    private Game game;
    private Map map;
    
     public Handler(Game game){
        this.game = game;
    }
     
    public GameCamera getGameCamera(){
        return game.getGameCamera();
    }
     
    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }
    
    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }
     
    public int getWidth(){
        return game.getWidth();
    }
    
    public int getHeight(){
        return game.getHeight();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    
    
}
