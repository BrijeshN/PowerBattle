/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import powerbattle.Handler;

public class NormalBullet extends Bullet {

    public static final int DEFAULT_BULLET_WIDTH = 20;
    public static final int DEFAULT_BULLET_HEIGHT = 20;

    public static final int DEFAULT_BULLET_SPEED = 6;

    public static final int BULLET_DAMAGE = 50;

    public NormalBullet(Handler handler, float x, float y, boolean isRight) {
        super(handler, x, y, DEFAULT_BULLET_WIDTH, DEFAULT_BULLET_HEIGHT, true);
        this.isRight = isRight;
        bounds.x = 100;
        this.bulletSpeed = DEFAULT_BULLET_SPEED;

    }

}
