// to store image, sounds etc.
package graphics;

import java.awt.image.BufferedImage;

/**
 *
 * @author Brijesh
 */
public class Assets {
    
    // heigh and the width of the images
    private static final int width = 130;
    private static final int height = 111;
    
    public static BufferedImage dead, idle, run, melee, jump, shoot;
    
    public static void init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/Character/PlayerSprite.png"));
        
        dead = sheet.crop(0, 0, width, height);
        idle = sheet.crop(0, height, width, height);
        run = sheet.crop(width * 3, height * 4, width, height);
        melee = sheet.crop(0, height * 3, width, height);
        jump = sheet.crop( 0, height * 2, width, height);
        shoot = sheet.crop(width * 5, 0, width, height);
        
    }
    
}
