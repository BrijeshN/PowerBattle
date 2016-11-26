/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiles;

import graphics.Assets;
import java.awt.image.BufferedImage;

/**
 *
 * @author Brijesh
 */
public class WaterTile extends Tile{
    
    public WaterTile(int id) {
        super(Assets.water1,id);
    }
    
    // if it's solid,player can not walk on it
    @Override
    public boolean isSolid(){
        return true;
    }
    
}
