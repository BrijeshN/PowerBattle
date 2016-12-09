/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Map2;

import graphics.Assets;
import tiles.Tile;

/**
 *
 * @author Brijesh
 */
public class DirtTile2 extends Tile{
    
    public DirtTile2(int id){
        super(Assets.mdirt2, id);
    }
    
      // if it's solid,player can not walk on it
    @Override
    public boolean isSolid(){
        return true;
    }
    
}
