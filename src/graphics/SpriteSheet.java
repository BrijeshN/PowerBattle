/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.image.BufferedImage;

/**
 *
 * @author Brijesh
 */
public class SpriteSheet {
    
    // Stores sprite sheet
    private BufferedImage sheet;
    
    public SpriteSheet(BufferedImage sheet){
        
        // Sets sheet to what ever image we passed
        this.sheet = sheet;
    }
    
    public BufferedImage crop(int x, int y, int width, int height){
        return sheet.getSubimage(x, y, width, height);
    }
    
}
