/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.creature.Creature;
import graphics.Assets;
import java.awt.Graphics;
import powerbattle.Handler;

public class NormalBullet extends Creature {

    public static final int DEFAULT_BULLET_WIDTH = 20;
    public static final int DEFAULT_BULLET_HEIGHT = 20;

    public static final int DEFAULT_BULLET_SPEED = 6;

    public static final int BULLET_DAMAGE = 50;

    boolean isRight = false, first = false;
    boolean restart = false, remove = true;

    public NormalBullet(Handler handler, float x, float y, boolean isRight) {
        super(handler, x, y, DEFAULT_BULLET_WIDTH, DEFAULT_BULLET_HEIGHT);
        this.isRight = isRight;
        
        bounds.x = 100;

    }

    public void render(Graphics g, int time) {

        if (isRight) {
            g.drawImage(Assets.normalBullet, (int) (x - handler.getGameCamera().getxOffset() + 100), (int) (y - handler.getGameCamera().getyOffset() + 50), width, height, null);
        } else {
            g.drawImage(Assets.normalBulletLeft, (int) (x - handler.getGameCamera().getxOffset() ), (int) (y - handler.getGameCamera().getyOffset() + 50), width, height, null);
        }

    }

    public void update() {

        if (isRight) {
            xMove = DEFAULT_BULLET_SPEED;
        } else {
            xMove = -DEFAULT_BULLET_SPEED;
        }

        move();

    }

}
