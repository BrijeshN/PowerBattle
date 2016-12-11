/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import Audio.JukeBox;
import graphics.Assets;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import powerbattle.Handler;
import state.GameState;

public class Enemy extends Creature {

    //Set size of the character
    public static final int DEFAULT_ZOMBIE_WIDTH = 130;
    public static final int DEFAULT_ZOMBIE_HEIGHT = 111;

    boolean dead = false, deadAni = false, first = false, restart = false;
    boolean isRight = false, attack = false, hitRight = false, hitByPlayer = false;
    boolean magicalBulletHit = false, normalBulletHit = false, hitLeft = false;
    int preTime, action = 0;
    final int IDLE = 0, MOVELEFT = 1, MOVERIGHT = 2;
    final float MOVESPEED = 1.0f, JUMPSPEED = 10f;
    Random r = new Random();
    int time = 0, i = 0;
    public boolean firstCall = true, notDraw = false, aimPlayer = false, deadTimeSet = false;
    int id, deadTime = 0;
    boolean isAmmo = false, pickedByPlayer = false, isHeart = false, isMagicalAmmo = false;
    boolean isDraw = false, hitByMagicalBullet = false, hitByRobot = false;

    public Enemy(Handler handler, float x, float y, int id) {
        super(handler, x, y, DEFAULT_ZOMBIE_WIDTH, DEFAULT_ZOMBIE_HEIGHT);
        this.id = id;
        health = 100;

        bounds.x = 32;
        bounds.y = 32;
        bounds.width = 40;
        bounds.height = 55;

    }

    public void resetPos() {
        for (int i = 0; i < GameState.ENEMYNUM; i++) {
            if (id == i) {
                x = GameState.ENEMYPOS[i][0];
                y = GameState.ENEMYPOS[i][1];
            }
        }
    }

    public void doAction() {
        if (firstCall) {
            firstCall = false;
            getAction();
            action(action);
            attack = r.nextInt(5) != 1;
        }
    }

    public void update(Creature player) {
        if (restart) {
            resetPos();
            attack = dead = notDraw = deadAni = first = restart = deadTimeSet = false;
            pickedByPlayer = isAmmo = isHeart = isMagicalAmmo = isDraw = false;
            hitByMagicalBullet = false;
            action = IDLE;
            health = 100;
        }

        if (health <= 0) {
            dead = true;
            if (!deadTimeSet) {
                deadTime = time;
                deadTimeSet = true;
            }
        }

        if (dead) {
            if (pickedByPlayer) {
                notDraw = true;
                x = -100;
                y = -100;
            }
            attack = false;
            return;
        }

        int timeElapse = time / 10;
        //System.out.println(aimPlayer);
        if (aimPlayer) {
            if (isRight && x > player.getX()) {
                action = MOVELEFT;
            } else if (!isRight && x < player.getX()) {
                action = MOVERIGHT;
            } else if (isRight) {
                action = MOVERIGHT;
            } else {
                action = MOVELEFT;
            }
            if (timeElapse % 3 == 0) {
                if (!firstCall) {
                    firstCall = true;
                    attack = r.nextInt(10) != 1;
                }
            }
            action(action);
        } else if (id % 2 == 0) {
            if (timeElapse % 2 == 0) {
                doAction();
            } else {
                firstCall = true;
            }
        } else if (id % 3 == 0) {
            if (timeElapse % 3 == 0) {
                doAction();
            } else {
                firstCall = true;
            }
        } else if (timeElapse % 4 == 0) {
            doAction();
        } else {
            firstCall = true;
        }

        if (attack) {

            if (isRight) {
                if (x > player.getX() - 55 && x < player.getX() + 15 && y > player.getY() - 60 && y < player.getY() + 60) {
                    if (!hitRight) {
                        hitRight = true;
                        player.health -= 1;
                        JukeBox.play("playerhit");
                    }
                } else {
                    hitRight = false;
                }

            } else if (x > player.getX() - 15 && x < player.getX() + 55 && y > player.getY() - 60 && y < player.getY() + 60) {
                if (!hitLeft) {
                    hitLeft = true;
                    player.health -= 1;
                    JukeBox.play("playerhit");
                }
            } else {
                hitLeft = false;
            }

        }
        yMove = JUMPSPEED;

        if (GameState.coop) {
            coopResetState();
        } else {
            resetState();
        }

        move();
    }

    public void coopResetState() {
        if (id == 2) {
            if (x + xMove > 1756) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove < 1624) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 3) {
            if (x + xMove > 4220) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove < 3848) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 4) {

            if (x + xMove < 5656) {
                xMove = 0;
                action = IDLE;
            }
        }

        if (id == 5) {

            if (x + xMove > 3440) {
                xMove = 0;
                action = IDLE;
            }

            if (x + xMove < 3064) {
                xMove = 0;
                action = IDLE;
            }
        }
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
            } else {

                if (!isDraw) {
                    i = r.nextInt(20);
                    isDraw = true;
                }
                if (i % 2 == 0 && i < 10) {

                    g.drawImage(Assets.ammo, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() + 40), 100, 100, null);
                    isAmmo = true;
                } else if (i == 1 || i == 3) {
                    g.drawImage(Assets.magicalAmmo, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() + 40), 60, 60, null);
                    isMagicalAmmo = true;
                } else if (i < 10) {
                    g.drawImage(Assets.heartImage, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() + 50), null);
                    isHeart = true;
                }
            }

            return;
        }

        g.setColor(Color.white);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset() + 30), (int) (y - handler.getGameCamera().getyOffset()), 50, 15); //draws healthbar outline
        g.setColor(Color.red);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset() + 30), (int) (y - handler.getGameCamera().getyOffset()), health / 2, 15); //draws health

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
