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

    public Player(Game game, float x, float y) {
        super(game, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {

        getInput();
        move();
        // center on this player entity
        game.getGameCamera().centerOnEntity(this);

    }

    private void getInput() {

        xMove = 0;
        yMove = 0;

        if (game.getKeyManager().up) {
            yMove = -speed;
        }
        if (game.getKeyManager().down) {
            yMove = speed;
        }
        if (game.getKeyManager().left) {
            xMove = -speed;
        }
        if (game.getKeyManager().right) {
            xMove = speed;
        }
    }

    @Override
    public void render(Graphics g) {
       // g.drawImage(Assets.idleRight,(int) (x - game.getGameCamera().getxOffset()), (int) (y - game.getGameCamera().getyOffset()), width, height, null);
        if (game.getKeyManager().up ||game.getKeyManager().right) {
            animationRunRight(forward, g);
            isRight = true;
        } else if (game.getKeyManager().down || game.getKeyManager().left) {
            animationRunLeft(forward, g);
            isRight = false;
        } else if (game.getKeyManager().attack) {
            if (isRight) {
                animationAtackRight(forward, g);
            } else {
                animationAtackLeft(forward, g);
            }
        } else if (game.getKeyManager().shoot) {
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
        g.drawImage(Assets.runRight[state % 5], (int) (x - game.getGameCamera().getxOffset()), (int) (y - game.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationAtackRight(int state, Graphics g) {
        g.drawImage(Assets.meleeRight[state % 4], (int) x, (int) y, width, height, null);
    }

    public void animationRunLeft(int state, Graphics g) {
        g.drawImage(Assets.runLeft[state % 5], (int) (x - game.getGameCamera().getxOffset()), (int) (y - game.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationAtackLeft(int state, Graphics g) {
        g.drawImage(Assets.meleeLeft[state % 4], (int) x, (int) y, width, height, null);
    }
}
