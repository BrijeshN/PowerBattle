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

    //Set size of the character
    public static final int DEFAULT_ZOMBIE_WIDTH = 130;
    public static final int DEFAULT_ZOMBIE_HEIGHT = 111;

    boolean dead = false, deadAni = false, first = false, restart = false;
    boolean isRight = false, attack = false, hit = false, hitByPlayer = false;
    int preTime, action = 0;
    final int IDLE = 0, MOVELEFT = 1, MOVERIGHT = 2;
    final float MOVESPEED = 1.0f, JUMPSPEED = 10f;
    Random r = new Random();
    int time = 0;
    boolean firstCall = true, notDraw = false, aimPlayer = false;
    int id, deadTime = 0;

    public Enemy(Handler handler, float x, float y, int id) {
        super(handler, x, y, DEFAULT_ZOMBIE_WIDTH, DEFAULT_ZOMBIE_HEIGHT);
        this.id = id;
        health = 100;

        bounds.x = 32;
        bounds.y = 32;
        bounds.width = 40;
        bounds.height = 55;

    }

    public void aimForPlayer(Player player) {
        if (Math.abs(player.getX() - x) < 200 && Math.abs(player.getY() - y) < 20) {
            aimPlayer = Math.abs(player.getX() - x) >= 20;
        } else {
            aimPlayer = false;
        }

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

        if (health < 0) {
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

        aimForPlayer(player);
        //System.out.println(aimPlayer);
        if (aimPlayer) {
            if (isRight && x > player.getX()) {
                action = MOVELEFT;
            } else if (!isRight && x < player.getX()) {
                action = MOVERIGHT;
            }
            action(action);
        } else if (id % 2 == 0) {
            if (timeElapse % 2 == 0) {
                if (firstCall) {
                    firstCall = false;
                    getAction();
                    action(action);
                    if (r.nextInt(3) != 1) {
                        attack = false;
                    } else {
                        attack = false;
                    }
                }
            } else {
                firstCall = true;
            }
        } else if (id % 3 == 0) {
            if (timeElapse % 3 == 0) {
                if (firstCall) {
                    firstCall = false;
                    getAction();
                    action(action);
                    if (r.nextInt(3) != 1) {
                        attack = false;
                    } else {
                        attack = false;
                    }
                }
            } else {
                firstCall = true;
            }
        } else if (timeElapse % 4 == 0) {
            if (firstCall) {
                firstCall = false;
                getAction();
                action(action);
                if (r.nextInt(3) != 1) {
                    attack = false;
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
            if (x + xMove > 582) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove < 132) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 1 || id == 2) {
            if (x + xMove > 1890) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove < 1500) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 3) {
            if (x + xMove < 2661) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 2799) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 4) {
            if (x + xMove < 3183) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 3567) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 5) {
            if (x + xMove < 2406) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 2673) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 6) {
            if (x + xMove < 1785) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 2277) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 7) {
            if (x + xMove < 1104) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 1623) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 8) {
            if (x + xMove < 264) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 585) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 9) {
            if (x + xMove < 333) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 717) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 10) {
            if (x + xMove < 1365) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 1677) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 11) {
            if (x + xMove < 1833) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove > 2145) {
                xMove = 0;
                action = IDLE;
            }
        }
    }

    public void action(int action) {

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

    public void getAction() {

        action = r.nextInt(3);
    }

    public void stop() {
        xMove = yMove = 0;
    }

    public void render(Graphics g, int time) {
        this.time = time;
        if (notDraw) {
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
