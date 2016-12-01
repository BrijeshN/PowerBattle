/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import graphics.Assets;
import java.awt.Graphics;
import powerbattle.Handler;

public class MagicalBullet extends Bullet {

    public static final int DEFAULT_BULLET_WIDTH = 50;
    public static final int DEFAULT_BULLET_HEIGHT = 100;

    public static final int MAGICAL_BULLET_SPEED = 8;

    public MagicalBullet(Handler handler, float x, float y, boolean isRight) {
        super(handler, x, y, DEFAULT_BULLET_WIDTH, DEFAULT_BULLET_HEIGHT, false);
        this.isRight = isRight;
        bounds.height = 10;
        this.bulletSpeed = MAGICAL_BULLET_SPEED;
    }

}
