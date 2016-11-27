/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import graphics.Assets;
import java.awt.Graphics;
import powerbattle.Game;
import static graphics.Assets.shootRight;
import powerbattle.Handler;

/**
 *
 * @author Brijesh
 */
public class Player extends Creature {

    int forward = 0;
    int backward = 1000;
    long startTime;
    long endTime;
    boolean isRight = true;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {

        getInput();
        move();
        // center on this player entity
        handler.getGameCamera().centerOnEntity(this);

    }

    private void getInput() {

        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up) {
            yMove = -speed;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
        }
        if (handler.getKeyManager().right) {
            xMove = speed;
        }
    }

    @Override
    public void render(Graphics g) {
       // g.drawImage(Assets.idleRight,(int) (x - game.getGameCamera().getxOffset()), (int) (y - game.getGameCamera().getyOffset()), width, height, null);
        if (handler.getKeyManager().up ||handler.getKeyManager().right) {
            animationRunRight(forward, g);
            isRight = true;
        } else if (handler.getKeyManager().down || handler.getKeyManager().left) {
            animationRunLeft(forward, g);
            isRight = false;
        } else if (handler.getKeyManager().attack) {
            if (isRight) {
                animationAtackRight(forward, g);
            } else {
                animationAtackLeft(forward, g);
            }
        } else if (handler.getKeyManager().shoot) {
            if (isRight) {
                g.drawImage(Assets.shootRight, (int) x, (int) y, width, height, null);
            } else {
                g.drawImage(Assets.shootLeft, (int) x, (int) y, width, height, null);
            }
        } else if (isRight) {
            g.drawImage(Assets.idleRight, (int) x, (int) y, width, height, null);
        } else {
            g.drawImage(Assets.idleLeft, (int) x, (int) y, width, height, null);
        }

        endTime = System.currentTimeMillis();
        if ((endTime - startTime) % 4 == 0) {
            forward++;
            backward--;
            if (backward == -1) {
                backward = 1000;
            }
        }
    }

    public void animationRunRight(int state, Graphics g) {
        g.drawImage(Assets.runRight[state % 5], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationAtackRight(int state, Graphics g) {
        g.drawImage(Assets.meleeRight[state % 4], (int) x, (int) y, width, height, null);
    }

    public void animationRunLeft(int state, Graphics g) {
        g.drawImage(Assets.runLeft[state % 5], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationAtackLeft(int state, Graphics g) {
        g.drawImage(Assets.meleeLeft[state % 4], (int) x, (int) y, width, height, null);
    }
}
