/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Brijesh
 */
public class ImageLoader {
    
    //Java stores images in BufferedImage object
    // We can acees it form anywhere
    // Takes path of the image
    public static BufferedImage loadImage(String path){
        
        try {
            // load an Image
            // returns buffered image object of ourloaded image
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();  
            // If we don't load image quit
            System.exit(1);
        }
        
        return null;
    }
    
}
