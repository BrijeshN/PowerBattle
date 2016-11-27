/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import graphics.Assets;
import static graphics.Assets.shoot;
import java.awt.Graphics;
import powerbattle.Game;

/**
 *
 * @author Brijesh
 */
public class Player extends Creature {

    private Game game;
    int forward = 0;
    int backward = 1000;
    long startTime;
    long endTime;

    public Player(Game game, float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {

        getInput();
        move();

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
        if (game.getKeyManager().up || game.getKeyManager().right) {
            animationRun(forward, g);
        } else if (game.getKeyManager().down || game.getKeyManager().left) {
            animationRun(backward, g);
        } else if (game.getKeyManager().attack) {
            animationAtack(forward, g);
        } else if (game.getKeyManager().shoot) {
            g.drawImage(Assets.shoot, (int) x, (int) y, width, height, null);
        } else {
            g.drawImage(Assets.idle, (int) x, (int) y, width, height, null);
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

    public void animationRun(int state, Graphics g) {
        g.drawImage(Assets.run[state % 5], (int) x, (int) y, width, height, null);
    }

    public void animationAtack(int state, Graphics g) {
        g.drawImage(Assets.melee[state % 4], (int) x, (int) y, width, height, null);
    }
}
