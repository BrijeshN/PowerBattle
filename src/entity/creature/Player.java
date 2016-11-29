/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import graphics.Assets;
import java.awt.Graphics;
import java.util.ArrayList;
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
    int preTime;
    boolean deadAni = false, first = false, hit = false;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        bounds.x = 32;
        bounds.y = 32;
        bounds.width = 40;
        bounds.height = 50;
    }

    public void update(ArrayList<Enemy> enemies) {

        getInput(enemies);
        move();
        // center on this player entity
        handler.getGameCamera().centerOnEntity(this);

        //System.out.println(x);//358 445
    }

    public void stop() {
        xMove = 0;
        fall = jump = false;
    }

    private void getInput(ArrayList<Enemy> enemis) {

        System.out.println(x + " " + y);
        if (handler.getKeyManager().restart) {
            first = false;
            isRight = true;
            dead = false;
            deadAni = false;
            y = 560;
            x = 100;

            for (Enemy e : enemis) {
                e.restart = true;
            }
        }

        if (y > 680) {
            dead = true;
            yMove = 0;
            stop();
            return;
        }

        if (dead) {
            yMove = jumpspeed;
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

        hit = false;
        if (handler.getKeyManager().attack) {
            if (isRight) {
                for (Enemy e : enemis) {
                    if (x > e.getX() - 75 && x < e.getX() + 15 && y > e.getY() - 60 && y < e.getY() + 60) {
                        hit = true;
                        e.dead = true;
                    }
                }
            } else {
                for (Enemy e : enemis) {
                    if (x > e.getX() - 15 && x < e.getX() + 75 && y > e.getY() - 60 && y < e.getY() + 60) {
                        hit = true;
                        e.dead = true;
                    }
                }
            }
        }

        //System.out.println(y);
    }

    @Override
    public void render(Graphics g, int time) {
        if (dead) {
            if (!first) {
                first = true;
                preTime = time;
            }
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
            animationIdleRight(time / 3, g);
        } else if (!handler.getKeyManager().attack && !handler.getKeyManager().shoot) {
            animationIdleLeft(time / 3, g);

        }
    }

    public void animationDeadRight(int time, Graphics g) {

        if (time / 2 - preTime / 2 < 5) {
            g.drawImage(Assets.deadRight[time / 2 - preTime / 2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            deadAni = true;
            g.drawImage(Assets.deadRight[4], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void animationDeadLeft(int time, Graphics g) {
        if (time / 2 - preTime / 2 < 5) {
            g.drawImage(Assets.deadLeft[time / 2 - preTime / 2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            deadAni = true;
            g.drawImage(Assets.deadLeft[4], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void animationIdleRight(int time, Graphics g) {
        g.drawImage(Assets.idleRight[time % 5], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationIdleLeft(int time, Graphics g) {
        g.drawImage(Assets.idleLeft[time % 5], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
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
