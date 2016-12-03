// to store image, sounds etc.
package graphics;

import java.awt.image.BufferedImage;

/**
 *
 * @author Brijesh
 */
public class Assets {

    // heigh and the width of the images
    private static final int WIDTH = 130;
    private static final int HEIGHT = 111;
    private static final int ZOMBIEHIGHT = 100;
    private static final int ROBOTWIDTH = 125;
    private static final int NINJAWIDTH = 125;
    private static final int NINJAHEIGHT = 100;
   
    private static final int w = 128;
    private static final int h = 128;

    public static final int bw = 200;
    public static final int bh = 70;

    private static final int waterWidth = 128;
    private static final int waterHeight = 99;

    public static BufferedImage runRight[], meleeRight[], runLeft[], meleeLeft[], shootLeft[], shootRight[];
    public static BufferedImage jumpRight[], jumpLeft[], deadLeft[], deadRight[];

    public static BufferedImage idleRight[], idleLeft[];

    public static BufferedImage robotrunRight[], robotmeleeRight[], robotrunLeft[], robotmeleeLeft[], robotshootLeft[], robotshootRight[];
    public static BufferedImage robotjumpRight[], robotjumpLeft[], robotdeadLeft[], robotdeadRight[];

    public static BufferedImage robotidleRight[], robotidleLeft[];

    public static BufferedImage empty, invisibleWall;

    public static BufferedImage heartImage, ammo, magicalAmmo;

    public static BufferedImage grass1, grass2, grass3;
    public static BufferedImage dirt1, dirt2, dirt3, dirt4, dirt5, dirt6, dirt7, dirt8;
    public static BufferedImage floatDirt1, floatDirt2, floatDirt3;
    public static BufferedImage water1, heart, star;

    public static BufferedImage bullet, bulletLeft;
    public static BufferedImage normalBullet, normalBulletLeft;
    public static BufferedImage robotbullet, robotbulletLeft;
    public static BufferedImage robotnormalBullet, robotnormalBulletLeft;

    public static BufferedImage zombieIdleRight[], zombieDeadRight[];
    public static BufferedImage zombieIdleLeft[], zombieDeadLeft[];
    public static BufferedImage zombieRunLeft[], zombieRunRight[];
    public static BufferedImage zombieAttackLeft[], zombieAttackRight[];
    
    //Menu Width, heights and images
      
    private static final int menuHeight = 59;
    
    private static final int playWidth = 118;
    
    private static final int chaoticWidth = 168;
  
    private static final int singleWidth = 148;
    
    private static final int coopWidth = 134;
   
    private static final int controlsWidth = 194;
    
    private static final int backWidth = 126;

    private static final int quitWidth = 114;
    
    //Menu Images

    public static BufferedImage[] play;
    public static BufferedImage[] single;
    public static BufferedImage[] chaotic;
    public static BufferedImage[] coop;
    public static BufferedImage[] controls;
    public static BufferedImage[] quit;
    public static BufferedImage[] back;
    

    public static void init() {
        SpriteSheet playMenu = new SpriteSheet(ImageLoader.loadImage("/Menu/ButtonSprite/PlaySprite.png"));
        play = new BufferedImage[2];
        play[0] = playMenu.crop(0, 0, playWidth, menuHeight);
        play[1] = playMenu.crop(playWidth, 0, playWidth, menuHeight);
        
        SpriteSheet singleMenu = new SpriteSheet(ImageLoader.loadImage("/Menu/ButtonSprite/SingleSprite.png"));
        single = new BufferedImage[2];
        single[0] = singleMenu.crop(0, 0, singleWidth, menuHeight);
        single[1] = singleMenu.crop(singleWidth, 0, singleWidth, menuHeight);
        
        SpriteSheet chaoticMenu = new SpriteSheet(ImageLoader.loadImage("/Menu/ButtonSprite/ChaoticSprite.png"));
        chaotic = new BufferedImage[2];
        chaotic[0] = chaoticMenu.crop(0, 0, chaoticWidth, menuHeight);
        chaotic[1] = chaoticMenu.crop(chaoticWidth, 0, chaoticWidth, menuHeight);
        
        SpriteSheet coopMenu = new SpriteSheet(ImageLoader.loadImage("/Menu/ButtonSprite/CoopSprite.png"));
        coop = new BufferedImage[2];
        coop[0] = coopMenu.crop(0, 0, coopWidth, menuHeight);
        coop[1] = coopMenu.crop(coopWidth, 0, coopWidth, menuHeight);
        
        SpriteSheet controlsMenu = new SpriteSheet(ImageLoader.loadImage("/Menu/ButtonSprite/ControlsSprite.png"));
        controls = new BufferedImage[2];
        controls[0] = controlsMenu.crop(0, 0, controlsWidth, menuHeight);
        controls[1] = controlsMenu.crop(controlsWidth, 0, controlsWidth, menuHeight);
        
        SpriteSheet quitMenu = new SpriteSheet(ImageLoader.loadImage("/Menu/ButtonSprite/QuitSprite.png"));
        quit = new BufferedImage[2];
        quit[0] = quitMenu.crop(0, 0, quitWidth, menuHeight);
        quit[1] = quitMenu.crop(quitWidth, 0, quitWidth, menuHeight);
        
        SpriteSheet backMenu = new SpriteSheet(ImageLoader.loadImage("/Menu/ButtonSprite/BackSprite.png"));
        back = new BufferedImage[2];
        back[0] = backMenu.crop(0, 0, backWidth, menuHeight);
        back[1] = backMenu.crop(backWidth, 0, backWidth, menuHeight);

        initBullet();
        initTile();
        initPlayerImages();
        initEnemyImages();
        initRobotImages();
        initRobotBullet();

        heartImage = ImageLoader.loadImage("/heart.png");
        ammo = ImageLoader.loadImage("/Bullet/ammo.png");
        magicalAmmo = ImageLoader.loadImage("/Bullet/magicalAmmo.png");
        star = ImageLoader.loadImage("/star.png");

    }

    public static void initEnemyImages() {
        zombieIdleRight = new BufferedImage[15];
        zombieIdleLeft = new BufferedImage[15];
        zombieDeadRight = new BufferedImage[12];
        zombieDeadLeft = new BufferedImage[12];
        zombieRunRight = new BufferedImage[9];
        zombieRunLeft = new BufferedImage[9];
        zombieAttackLeft = new BufferedImage[8];
        zombieAttackRight = new BufferedImage[8];

        SpriteSheet zombieRight = new SpriteSheet(ImageLoader.loadImage("/Zombies/ZombiesSprite.png"));
        SpriteSheet zombieLeft = new SpriteSheet(ImageLoader.loadImage("/Zombies/ZombiesLeftSprite.png"));
        for (int i = 0; i < 8; i++) {
            zombieAttackRight[i] = zombieRight.crop(WIDTH * i, 0, WIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 8; i++) {
            zombieAttackLeft[i] = zombieLeft.crop(WIDTH * i, 0, WIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 15; i++) {
            zombieIdleRight[i] = zombieRight.crop(WIDTH * (i + 20), 0, WIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 15; i++) {
            zombieIdleLeft[i] = zombieLeft.crop(WIDTH * (i + 20), 0, WIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 12; i++) {
            zombieDeadRight[i] = zombieRight.crop(WIDTH * (i + 8), 0, WIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 12; i++) {
            zombieDeadLeft[i] = zombieLeft.crop(WIDTH * (i + 8), 0, WIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 9; i++) {
            zombieRunRight[i] = zombieRight.crop(WIDTH * (i + 35), 0, WIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 9; i++) {
            zombieRunLeft[i] = zombieLeft.crop(WIDTH * (i + 35), 0, WIDTH, ZOMBIEHIGHT);
        }

    }

    public static void initBullet() {
        bullet = ImageLoader.loadImage("/Bullet/magicalBullet.png");
        bulletLeft = ImageLoader.loadImage("/Bullet/magicalBulletLeft.png");
        normalBullet = ImageLoader.loadImage("/Bullet/regBullet.png");
        normalBulletLeft = ImageLoader.loadImage("/Bullet/regBulletLeft.png");
    }

    public static void initRobotBullet() {
        robotbullet = ImageLoader.loadImage("/Player2/Muzzle.png");
        robotbulletLeft = ImageLoader.loadImage("/Player2/Muzzle.png");
        robotnormalBullet = ImageLoader.loadImage("/Player2/BulletRight.png");
        robotnormalBulletLeft = ImageLoader.loadImage("/Player2/BulletLeft.png");
    }

    public static void initTile() {
        SpriteSheet tile = new SpriteSheet(ImageLoader.loadImage("/MapTile/mapSprite.png"));
        SpriteSheet tile2 = new SpriteSheet(ImageLoader.loadImage("/MapTile/png/waterSprite.png"));

        grass1 = tile.crop(0, 0, w, h);
        grass2 = tile.crop(w * 2, h * 2, w, h);
        grass3 = tile.crop(w * 3, h * 2, w, h);
        floatDirt1 = tile.crop(0, h, w, h);
        floatDirt2 = tile.crop(w, h, w, h);
        floatDirt3 = tile.crop(w * 2, h, w, h);
        water1 = tile2.crop(0, 0, waterWidth, waterHeight);

    }

    public static void initRobotImages() {
        runRight = new BufferedImage[5];
        meleeRight = new BufferedImage[4];
        runLeft = new BufferedImage[5];
        meleeLeft = new BufferedImage[4];
        shootLeft = new BufferedImage[3];
        shootRight = new BufferedImage[3];
        jumpLeft = new BufferedImage[5];
        jumpRight = new BufferedImage[5];
        deadLeft = new BufferedImage[5];
        deadRight = new BufferedImage[5];
        idleLeft = new BufferedImage[5];
        idleRight = new BufferedImage[5];

        SpriteSheet playerRight = new SpriteSheet(ImageLoader.loadImage("/Character/PlayerSprite.png"));
        SpriteSheet playerLeft = new SpriteSheet(ImageLoader.loadImage("/Character/PlayerSpriteLeft.png"));

        heart = ImageLoader.loadImage("/heart.png");

        for (int i = 0; i < 5; i++) {
            runRight[i] = playerRight.crop(WIDTH * i, HEIGHT * 5, WIDTH, HEIGHT);
        }
        for (int i = 0; i < 4; i++) {
            meleeRight[i] = playerRight.crop(WIDTH * i, HEIGHT * 3, WIDTH, HEIGHT);
        }
        for (int i = 0; i < 5; i++) {
            runLeft[i] = playerLeft.crop(WIDTH * i, HEIGHT * 5, WIDTH, HEIGHT);
        }
        for (int i = 0; i < 4; i++) {
            meleeLeft[i] = playerLeft.crop(WIDTH * i, HEIGHT * 3, WIDTH, HEIGHT);
        }

        for (int i = 0; i < 3; i++) {
            shootRight[i] = playerRight.crop(WIDTH * 5, HEIGHT * i, WIDTH, HEIGHT);
        }
        for (int i = 0; i < 3; i++) {
            shootLeft[i] = playerLeft.crop(WIDTH * 5, HEIGHT * i, WIDTH, HEIGHT);
        }

        for (int i = 0; i < 5; i++) {
            deadRight[i] = playerRight.crop(WIDTH * i, 0, WIDTH, HEIGHT);
        }
        for (int i = 0; i < 5; i++) {
            deadLeft[i] = playerLeft.crop(WIDTH * i, 0, WIDTH, HEIGHT);
        }

        for (int i = 0; i < 5; i++) {
            jumpRight[i] = playerRight.crop(WIDTH * i, HEIGHT * 2, WIDTH, HEIGHT);
        }
        for (int i = 0; i < 5; i++) {
            jumpLeft[i] = playerLeft.crop(WIDTH * i, HEIGHT * 2, WIDTH, HEIGHT);
        }

        for (int i = 0; i < 5; i++) {
            idleRight[i] = playerRight.crop(WIDTH * i, HEIGHT * 1, WIDTH, HEIGHT);
        }
        for (int i = 0; i < 5; i++) {
            idleLeft[i] = playerLeft.crop(WIDTH * i, HEIGHT * 1, WIDTH, HEIGHT);
        }

        empty = playerRight.crop(WIDTH * 5, HEIGHT * 3, WIDTH, HEIGHT);
        invisibleWall = playerRight.crop(WIDTH * 5, HEIGHT * 3, WIDTH, HEIGHT);
    }

    public static void initPlayerImages() {
        robotrunRight = new BufferedImage[8];
        robotrunLeft = new BufferedImage[8];
        robotmeleeRight = new BufferedImage[8];
        robotmeleeLeft = new BufferedImage[8];
        robotshootLeft = new BufferedImage[3];
        robotshootRight = new BufferedImage[3];
        robotjumpLeft = new BufferedImage[10];
        robotjumpRight = new BufferedImage[10];
        robotdeadLeft = new BufferedImage[10];
        robotdeadRight = new BufferedImage[10];
        robotidleLeft = new BufferedImage[10];
        robotidleRight = new BufferedImage[10];

        SpriteSheet playerRight = new SpriteSheet(ImageLoader.loadImage("/Player2/RobotSprite.png"));
        SpriteSheet playerLeft = new SpriteSheet(ImageLoader.loadImage("/Player2/RobotSpriteLeft.png"));

        for (int i = 0; i < 10; i++) {
            robotdeadRight[i] = playerRight.crop(ROBOTWIDTH * i, 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }
        for (int i = 0; i < 10; i++) {
            robotdeadLeft[i] = playerLeft.crop(ROBOTWIDTH * i, 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 10; i++) {
            robotidleRight[i] = playerRight.crop(ROBOTWIDTH * (i + 10), 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }
        for (int i = 0; i < 10; i++) {
            robotidleLeft[i] = playerLeft.crop(ROBOTWIDTH * (i + 10), 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 10; i++) {
            robotjumpRight[i] = playerRight.crop(ROBOTWIDTH * (i + 20), 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }
        for (int i = 0; i < 10; i++) {
            robotjumpLeft[i] = playerLeft.crop(ROBOTWIDTH * (i + 20), 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 8; i++) {
            robotmeleeRight[i] = playerRight.crop(ROBOTWIDTH * (i + 30), 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 8; i++) {
            robotmeleeLeft[i] = playerLeft.crop(ROBOTWIDTH * (i + 30), 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 8; i++) {
            robotrunRight[i] = playerRight.crop(ROBOTWIDTH * (i + 38), 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 8; i++) {
            robotrunLeft[i] = playerLeft.crop(ROBOTWIDTH * (i + 38), 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 2; i++) {
            robotshootRight[i] = playerRight.crop(ROBOTWIDTH * (i + 46), 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }

        for (int i = 0; i < 2; i++) {
            robotshootLeft[i] = playerLeft.crop(ROBOTWIDTH * (i + 46), 0, ROBOTWIDTH, ZOMBIEHIGHT);
        }
    }

}
