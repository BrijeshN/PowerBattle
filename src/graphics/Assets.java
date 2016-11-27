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

    private static final int w = 130;
    private static final int h = 129;

    public static BufferedImage run[], melee[];

    public static BufferedImage dead, idle, jump, shoot, empty;
    public static BufferedImage dirt1, dirt2, water1, water2;

    public static void init() {
        run = new BufferedImage[5];
        melee = new BufferedImage[4];

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/Character/PlayerSprite.png"));
        SpriteSheet sheet2 = new SpriteSheet(ImageLoader.loadImage("/Map/png/Tiles/TileSprite.png"));

        dead = sheet.crop(0, 0, width, height);
        idle = sheet.crop(0, height, width, height);
        for (int i = 0; i < 5; i++) {
            run[i] = sheet.crop(width * i, height * 5, width, height);
        }
        for (int i = 0; i < 4; i++) {
            melee[i] = sheet.crop(width * i, height * 3, width, height);
        }
        jump = sheet.crop(0, height * 2, width, height);
        shoot = sheet.crop(width * 5, 0, width, height);

        dirt1 = sheet2.crop(0, 0, w, h);
        dirt2 = sheet2.crop(w, 0, w, h);
        water1 = sheet2.crop(w * 17, 0, w, h);
        
        empty = sheet.crop(width * 5, height * 3, width, height);

    }

}
