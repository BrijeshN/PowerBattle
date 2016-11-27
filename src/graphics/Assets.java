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

    public static BufferedImage runRight[], meleeRight[], runLeft[], meleeLeft[], shootLeft[], shootRight[];

    public static BufferedImage dead, idleRight, idleLeft, jump, empty;
    public static BufferedImage dirt1, dirt2, water1, water2;

    public static void init() {
        runRight = new BufferedImage[5];
        meleeRight = new BufferedImage[4];
        runLeft = new BufferedImage[5];
        meleeLeft = new BufferedImage[4];
        shootLeft = new BufferedImage[3];
        shootRight = new BufferedImage[3];

        SpriteSheet playerRight = new SpriteSheet(ImageLoader.loadImage("/Character/PlayerSprite.png"));
        SpriteSheet playerLeft = new SpriteSheet(ImageLoader.loadImage("/CharacterLeft/PlayerSpriteLeft.png"));
        SpriteSheet tile = new SpriteSheet(ImageLoader.loadImage("/Map/png/Tiles/TileSprite.png"));

        for (int i = 0; i < 5; i++) {
            runRight[i] = playerRight.crop(width * i, height * 5, width, height);
        }
        for (int i = 0; i < 4; i++) {
            meleeRight[i] = playerRight.crop(width * i, height * 3, width, height);
        }
        for (int i = 0; i < 5; i++) {
            runLeft[i] = playerLeft.crop(width * i, height * 5, width, height);
        }
        for (int i = 0; i < 4; i++) {
            meleeLeft[i] = playerLeft.crop(width * i, height * 3, width, height);
        }

        jump = playerRight.crop(0, height * 2, width, height);
        for (int i = 0; i < 3; i++) {
            shootRight[i] = playerRight.crop(width * 5, height * i, width, height);
        }
        for (int i = 0; i < 3; i++) {
            shootLeft[i] = playerLeft.crop(width * 5, height * i, width, height);
        }

        dead = playerRight.crop(0, 0, width, height);

        idleRight = playerRight.crop(0, height, width, height);
        idleLeft = playerLeft.crop(0, height, width, height);

        dirt1 = tile.crop(0, 0, w, h);
        dirt2 = tile.crop(w, 0, w, h);
        water1 = tile.crop(w * 17, 0, w, h);

        empty = playerRight.crop(width * 5, height * 3, width, height);

    }

}
