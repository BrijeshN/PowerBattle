/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import entity.Entity;
import graphics.Assets;
import java.awt.Graphics;
import java.util.ArrayList;
import powerbattle.Handler;

public class Bullet extends Entity {

    public static final int DEFAULT_BULLET_WIDTH = 50;
    public static final int DEFAULT_BULLET_HEIGHT = 100;

    public static final int DEFAULT_BULLET_SPEED = 8;

    boolean remove = false, isRight = false, first = false;
    boolean restart = false;

    public Bullet(Handler handler, float x, float y, boolean isRight) {
        super(handler, x, y, DEFAULT_BULLET_WIDTH, DEFAULT_BULLET_HEIGHT);
        this.isRight = isRight;
    }

    public void render(Graphics g, int time) {

        if (isRight) {
            g.drawImage(Assets.bullet, (int) (x - handler.getGameCamera().getxOffset() + 100), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            g.drawImage(Assets.bulletLeft, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void update() {

        if (isRight) {
            x += DEFAULT_BULLET_SPEED;
        } else {
            x -= DEFAULT_BULLET_SPEED;
        }

    }

}
