/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiles;

import graphics.Assets;

/**
 *
 * @author Brijesh
 */
public class BoxTile  extends Tile {
    
    public BoxTile(int id){
          
        super(Assets.box, id);
        
    }
    
     // if it's solid,player can not walk on it
    @Override
    public boolean isSolid(){
        return true;
    }
    
}
