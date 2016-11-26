/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import java.awt.Graphics;
import powerbattle.Game;


public abstract class State {
    
    // GameStateManager, has nothing to do with the abstract State class
    private static State currentState = null;
    
    public static void setState(State state){
        currentState = state;
    }
    
    public static State getState(){
        return currentState;
    }
    
   //CLASS
    
    protected Game game;
    
    public State(Game game){
        
        this.game = game;
        
    }
    
   public abstract void update();
   
   public abstract void render(Graphics g);
   
   
    
}
