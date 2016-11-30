/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import graphics.Assets;
import java.awt.Graphics;
import powerbattle.Handler;

public class Bullet extends Entity {

    public static final int DEFAULT_BULLET_WIDTH = 130;
    public static final int DEFAULT_BULLET_HEIGHT = 111;

    public static final int DEFAULT_BULLET_SPEED = 8;

    public Bullet(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_BULLET_WIDTH, DEFAULT_BULLET_HEIGHT);
    }

    @Override
    public void render(Graphics g, int time) {
        g.drawImage(Assets.bullet, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void update() {
        x += DEFAULT_BULLET_SPEED;
    }

}
