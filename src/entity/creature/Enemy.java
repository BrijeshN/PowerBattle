/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import graphics.Assets;
import java.awt.Graphics;
import java.util.Random;
import powerbattle.Handler;
import state.GameState;

public class Enemy extends Creature {

    boolean dead = false, deadAni = false, first = false, restart = false;
    boolean isRight = false, attack = false, hit = false, hitByPlayer = false;
    int preTime, action = 0;
    final int IDLE = 0, MOVELEFT = 1, MOVERIGHT = 2;
    final float MOVESPEED = 1.0f, JUMPSPEED = 10f;
    Random r = new Random();
    int time = 0;
    boolean firstCall = true, notDraw = false;
    int id, deadTime = 0;

    public Enemy(Handler handler, float x, float y, int id) {
        super(handler, x, y);
        this.id = id;
        health = 100;

        bounds.x = 32;
        bounds.y = 32;
        bounds.width = 40;
        bounds.height = 55;

    }

    public void resetPos() {
        if (id == 0) {
            x = GameState.ENEMYPOS[0][0];
            y = GameState.ENEMYPOS[0][1];
        }
        if (id == 1) {
            x = GameState.ENEMYPOS[1][0];
            y = GameState.ENEMYPOS[1][1];
        }

        if (id == 2) {
            x = GameState.ENEMYPOS[2][0];
            y = GameState.ENEMYPOS[2][1];
        }
    }

    public void update(Player player) {
        if (restart) {
            resetPos();
            attack = dead = notDraw = deadAni = first = restart = false;
            action = IDLE;
            health = 100;
        }
        
        if(health < 0){
            dead = true;
        }

        if (dead) {
            if (time / 10 - deadTime / 10 == 3) {
                notDraw = true;
            }
            attack = false;
            return;
        }

        int timeElapse = time / 10;

        if (timeElapse % (id + 2) == 0) {
            if (firstCall) {
                firstCall = false;
                getAction();
                if (r.nextInt(3) != 1) {
                    attack = true;
                } else {
                    attack = false;
                }
            }
        } else {
            firstCall = true;
        }
        hit = false;
        if (attack) {

            if (isRight) {
                if (x > player.getX() - 55 && x < player.getX() + 15 && y > player.getY() - 60 && y < player.getY() + 60) {
                    hit = true;
                    player.dead = true;
                    player.jump = false;
                }

            } else if (x > player.getX() - 15 && x < player.getX() + 55 && y > player.getY() - 60 && y < player.getY() + 60) {
                hit = true;
                player.dead = true;
                player.jump = false;
            }

        }

        yMove = JUMPSPEED;
        resetState();

        move();
    }

    public void resetState() {
        if (id == 0) {
            if (x + xMove > 320) {
                xMove = 0;
                isRight = false;
                action = IDLE;
            }

            if (x + xMove < 100) {
                xMove = 0;
                isRight = true;
                action = IDLE;
            }
        }

        if (id == 1) {
            if (x + xMove < 450) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 577) {
                xMove = 0;
                isRight = false;
                action = IDLE;
            }
        }

        if (id == 2) {
            if (x + xMove < 450) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 720) {
                xMove = 0;
                isRight = false;
                action = IDLE;
            }
        }

        if (id == 3) {
            if (x + xMove < 865) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 1097) {
                xMove = 0;
                isRight = false;
                action = IDLE;
            }
        }
    }

    public void getAction() {

        action = r.nextInt(3);

        switch (action) {
            case MOVELEFT:
                isRight = false;
                xMove = -MOVESPEED;
                break;
            case MOVERIGHT:
                isRight = true;
                xMove = MOVESPEED;
                break;
            case IDLE:
                xMove = 0;
                break;
            default:
                break;
        }
    }

    public void stop() {
        xMove = yMove = 0;
    }

    public void render(Graphics g, int time) {
        this.time = time;
        if(notDraw){
            return;
        }
        if (dead) {
            stop();
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
                g.drawImage(Assets.zombieDeadRight[11], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            } else {
                g.drawImage(Assets.zombieDeadLeft[11], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            }
            return;
        }
        if (attack) {
            if (isRight) {
                animationAttackRight(time, g);
            } else {
                animationAttackLeft(time, g);
            }
        } else {
            if (action == MOVERIGHT) {
                animationMoveRight(time, g);
            }

            if (action == MOVELEFT) {
                animationMoveLeft(time, g);
            }
            if (action == IDLE) {
                if (isRight) {
                    animationIdleRight(time, g);
                } else {
                    animationIdleLeft(time, g);
                }
            }
        }

    }

    public void animationAttackRight(int time, Graphics g) {
        g.drawImage(Assets.zombieAttackRight[time % 8], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationAttackLeft(int time, Graphics g) {
        g.drawImage(Assets.zombieAttackLeft[time % 8], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationMoveRight(int time, Graphics g) {
        g.drawImage(Assets.zombieRunRight[time % 9], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationMoveLeft(int time, Graphics g) {
        g.drawImage(Assets.zombieRunLeft[time % 9], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationIdleRight(int time, Graphics g) {
        g.drawImage(Assets.zombieIdleRight[time % 15], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationIdleLeft(int time, Graphics g) {
        g.drawImage(Assets.zombieIdleLeft[time % 15], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationDeadRight(int time, Graphics g) {
        if (time / 2 - preTime / 2 < 12) {
            g.drawImage(Assets.zombieDeadRight[time / 2 - preTime / 2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            deadAni = true;
            g.drawImage(Assets.zombieDeadRight[11], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void animationDeadLeft(int time, Graphics g) {
        if (time / 2 - preTime / 2 < 12) {
            g.drawImage(Assets.zombieDeadLeft[time / 2 - preTime / 2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            deadAni = true;
            g.drawImage(Assets.zombieDeadLeft[11], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }
}
