/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import graphics.Assets;
import java.awt.Graphics;
import powerbattle.Handler;

public class Bullet extends Creature {

    public int bulletSpeed = 8, distance = 0;

    boolean remove = false, isRight = false, first = false;
    boolean restart = false, isNormal = false, hitEnemy = false;

    public Bullet(Handler handler, float x, float y, int width, int height, boolean isNormal) {
        super(handler, x, y, width, height);
        this.isNormal = isNormal;
    }

    public void render(Graphics g, int time, boolean isRobot) {
        if (!isNormal) {
            if (isRobot) {
                if (isRight) {
                    g.drawImage(Assets.robotbullet, (int) (x - handler.getGameCamera().getxOffset() + 100), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                } else {
                    g.drawImage(Assets.robotbulletLeft, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
                }
            } else if (isRight) {
                g.drawImage(Assets.bullet, (int) (x - handler.getGameCamera().getxOffset() + 100), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            } else {
                g.drawImage(Assets.bulletLeft, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            }
        } else if (isRobot) {
            if (isRight) {
                g.drawImage(Assets.robotnormalBullet, (int) (x - handler.getGameCamera().getxOffset() + 100), (int) (y - handler.getGameCamera().getyOffset() + 50), width, height, null);
            } else {
                g.drawImage(Assets.robotnormalBulletLeft, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() + 50), width, height, null);
            }
        } else if (isRight) {
            g.drawImage(Assets.normalBullet, (int) (x - handler.getGameCamera().getxOffset() + 100), (int) (y - handler.getGameCamera().getyOffset() + 50), width, height, null);
        } else {
            g.drawImage(Assets.normalBulletLeft, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() + 50), width, height, null);
        }
    }

    public void update() {

        if (isRight) {
            xMove = bulletSpeed;
        } else {
            xMove = -bulletSpeed;
        }
        distance += bulletSpeed;
        move();

    }

}
