/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import graphics.Assets;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Timer;
import powerbattle.Handler;
import tiles.Tile;

/**
 *
 * @author Brijesh
 */
public class Player extends Creature {

    int forward = 0;
    boolean isRight = true;
    boolean jump = false, fall = false, flag = true, dead = false;
    float jumpspeed = 10; //Check how high the player can jump
    int jumpTimer = 0; //Make the player can jump again using this timer
    Timer timer;
    int count = 0;
    boolean deadAni = false;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        bounds.x = 32;
        bounds.y = 32;
        bounds.width = 40;
        bounds.height = 50;
    }

    @Override
    public void update() {

        getInput();
        move();
        // center on this player entity
        handler.getGameCamera().centerOnEntity(this);

    }

    public void stop() {
        xMove = yMove = 0;
        fall = jump = false;
    }

    private void getInput() {
        if (handler.getKeyManager().restart) {
            dead = false;
            deadAni = false;
            y = 550;
            x = 100;
        }

        if (y > 680) {
            dead = true;
            stop();
            return;
        }

        xMove = 0;
        if (handler.getKeyManager().up) {
            if (!jump && !fall) {
                yMove = -jumpspeed;
                jump = true;
                fall = false;
            }
        }
        if (handler.getKeyManager().left) {
            xMove = -runSpeed;
            isRight = false;
        }
        if (handler.getKeyManager().right) {
            xMove = runSpeed;
            isRight = true;
        }

        //System.out.println(jump + " " + time);
        if (jump) {
            jumpTimer++;
            if (jumpTimer >= 24) {
                jumpTimer = 0;
                jump = false;
                fall = true;
            }
        } else {
            yMove = jumpspeed;
        }

        int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
        if (collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
                || collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
            fall = false;
        }

    }

    @Override
    public void render(Graphics g, int time) {
        if (dead) {
            if (!deadAni) {
                if (isRight) {
                    animationDeadRight(time, g);
                } else {
                    animationDeadLeft(time, g);
                }
            } else if (isRight) {
                g.drawImage(Assets.deadRight[4], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            } else {
                g.drawImage(Assets.deadLeft[4], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            }
            return;
        }

        if (handler.getKeyManager().attack) {
            if (isRight) {
                animationAtackRight(time, g);
            } else {
                animationAtackLeft(time, g);
            }
        } else if (handler.getKeyManager().shoot) {
            if (isRight) {
                animationShootRight(time, g);
            } else {
                animationShootLeft(time, g);
            }
        }

        if ((jump || fall) && !handler.getKeyManager().attack && !handler.getKeyManager().shoot) {
            if (isRight) {
                animationJumpRight(time, g);
            } else {
                animationJumpLeft(time, g);
            }
        } else if (handler.getKeyManager().right && !handler.getKeyManager().attack && !handler.getKeyManager().shoot) {
            animationRunRight(time, g);
        } else if (handler.getKeyManager().left && !handler.getKeyManager().attack && !handler.getKeyManager().shoot) {
            animationRunLeft(time, g);
            isRight = false;
        } else if (isRight && !handler.getKeyManager().attack && !handler.getKeyManager().shoot) {
            g.drawImage(Assets.idleRight, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

        } else if (!handler.getKeyManager().attack && !handler.getKeyManager().shoot) {
            g.drawImage(Assets.idleLeft, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

        }
    }

    public void animationDeadRight(int time, Graphics g) {
        count++;
        g.drawImage(Assets.deadRight[time % 5], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        if (count == 5) {
            deadAni = true;
            count = 0;
        }
    }

    public void animationDeadLeft(int time, Graphics g) {
        count++;
        g.drawImage(Assets.deadLeft[time % 5], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        if (count == 5) {
            deadAni = true;
            count = 0;
        }
    }

    public void animationJumpRight(int time, Graphics g) {
        g.drawImage(Assets.jumpRight[time % 5], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationJumpLeft(int time, Graphics g) {
        g.drawImage(Assets.jumpLeft[time % 5], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationRunRight(int time, Graphics g) {
        g.drawImage(Assets.runRight[time % 5], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    public void animationAtackRight(int time, Graphics g) {
        g.drawImage(Assets.meleeRight[time % 4], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    public void animationRunLeft(int time, Graphics g) {
        g.drawImage(Assets.runLeft[time % 5], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    public void animationAtackLeft(int time, Graphics g) {
        g.drawImage(Assets.meleeLeft[time % 4], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    public void animationShootRight(int time, Graphics g) {
        g.drawImage(Assets.shootRight[time % 3], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    public void animationShootLeft(int time, Graphics g) {
        g.drawImage(Assets.shootLeft[time % 3], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }
}
