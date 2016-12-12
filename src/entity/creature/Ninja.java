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
import java.util.ArrayList;
import powerbattle.Handler;
import state.GameState;
import tiles.Tile;

public class Ninja extends Enemy {

    public static final int NINJAWIDTH = 130;
    public static final int NINJAHEIGHT = 111, NINJAMOVESPEED = 3;

    float jumpspeed = 10; //Check how high the player can jump
    int jumpTimer = 0; //Make the player can jump again using this timer

    boolean shoot = false, kunaiShoot = false, shootAni = false, firstShoot = false, jump = false;
    boolean fall = false, doJump = false;

    int preTime;

    ArrayList<NormalBullet> kunai = new ArrayList<>();

    public Ninja(Handler handler, float x, float y) {
        super(handler, x, y, -1);

        health = 200;
    }

    @Override
    public void update(Creature player) {
        if (restart) {
            x = GameState.NINJA_SPAWN_X_POSITION;
            y = GameState.NINJA_SPAWN_Y_POSITION;
            attack = dead = notDraw = deadAni = first = restart = deadTimeSet = false;
            pickedByPlayer = isAmmo = isHeart = isMagicalAmmo = isDraw = false;
            hitByMagicalBullet = false;
            action = IDLE;
            health = 200;
        }

        if (health <= 0) {
            dead = true;
            if (!deadTimeSet) {
                deadTime = time;
                deadTimeSet = true;
            }
        }

        if (dead) {
            kunai.clear();
            attack = false;
            return;
        }

        int timeElapse = time / 10;
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

                    doJump = r.nextInt(2) != 1;
                    shoot = r.nextInt(2) != 1;
                    attack = r.nextInt(10) != 1;
                }
            }
            action(action);
        } else if (timeElapse % 2 == 0) {
            doAction();
        } else {
            firstCall = true;
        }

        if (doJump) {
            if (!jump && !fall) {
                jump = true;
                fall = false;
            }
        }

        if (jump) {
            jumpTimer++;
            yMove = -jumpspeed;
            if (jumpTimer >= 24) {
                jumpTimer = 0;
                jump = false;
                fall = true;
            }
        } else {
            yMove = jumpspeed;
        }

        if (shoot) {
            if (!kunaiShoot) {
                NormalBullet bullet = new NormalBullet(handler, x, y, isRight);
                kunai.add(bullet);
                kunaiShoot = true;
            }
        } else {
            kunaiShoot = false;
            firstShoot = false;
            shootAni = false;

        }

        if (attack && !shoot) {
            if (isRight) {
                if (x > player.getX() - 55 && x < player.getX() + 15 && y > player.getY() - 60 && y < player.getY() + 60) {
                    if (!hitRight) {
                        hitRight = true;
                        player.health -= 1;
                    }
                } else {
                    hitRight = false;
                }

            } else if (x > player.getX() - 15 && x < player.getX() + 55 && y > player.getY() - 60 && y < player.getY() + 60) {
                if (!hitLeft) {
                    hitLeft = true;
                    player.health -= 1;
                }
            } else {
                hitLeft = false;
            }
        }

        int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
        if (collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
                || collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {

            fall = false;
            if (!jump && !played) {
                played = true;
            }
        } else {
            played = false;
            fall = true;
        }

        for (NormalBullet b : kunai) {
            b.update();

            if (Math.abs(b.getX() - player.getX()) < 45 && b.getY() > player.getY() - 60 && b.getY() < player.getY() + 60) {
                if (!b.hitEnemy) {
                    b.hitEnemy = true;
                    player.health -= NORMALPBULLETDAMAGE;
                }
            }

        }

        for (int i = 0; i < kunai.size(); i++) {
            if (kunai.get(i).distance > 200 || kunai.get(i).hitWall || kunai.get(i).hitEnemy) {
                kunai.remove(i);
            }
        }

        if (x + xMove < 5652) {
            xMove = 0;
            action = IDLE;
        }

        move();
    }

    @Override
    public void stop() {
        xMove = 0;
        jump = false;
    }

    @Override
    public void action(int action) {
        switch (action) {
            case MOVELEFT:
                isRight = false;
                xMove = -NINJAMOVESPEED;
                break;
            case MOVERIGHT:
                isRight = true;
                xMove = NINJAMOVESPEED;
                break;
            case IDLE:
                xMove = 0;
                break;
            default:
                break;
        }
    }

    @Override
    public void getAction() {
        action = r.nextInt(3);
    }

    @Override
    public void doAction() {
        if (firstCall) {
            firstCall = false;
            getAction();
            action(action);
            doJump = r.nextInt(2) != 1;
            shoot = r.nextInt(2) != 1;
            attack = r.nextInt(5) != 1;

        }
    }

    public void render(Graphics g, int time) {

        this.time = time;

        for (NormalBullet b : kunai) {
            b.render(g, time, Bullet.NINJA);
        }

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
            }
            return;
        }

        g.setColor(Color.white);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset() + 30), (int) (y - handler.getGameCamera().getyOffset()) - 20, 100, 15); //draws healthbar outline
        g.setColor(Color.red);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset() + 30), (int) (y - handler.getGameCamera().getyOffset() - 20), health / 2, 15); //draws health

        if (shoot) {
            if (!firstShoot) {
                firstShoot = true;
                preTime = time;
            }
            if (!shootAni) {
                if (isRight) {
                    animationShootRight(time, g);
                } else {
                    animationShootLeft(time, g);
                }
            } else if (jump || fall) {
                if (isRight) {
                    animationJumpRight(time, g);
                } else {
                    animationJumpLeft(time, g);
                }
            } else if (isRight && action == MOVERIGHT) {
                animationMoveRight(time, g);
            } else if (!isRight && action == MOVELEFT) {
                animationMoveLeft(time, g);
            } else if (isRight) {
                animationIdleRight(time / 3, g);
            } else {
                animationIdleLeft(time / 3, g);
            }
        } else if (attack) {
            if (isRight) {
                animationAttackRight(time, g);
            } else {
                animationAttackLeft(time, g);
            }
        } else if ((jump || fall) && !attack && !shoot) {
            if (isRight) {
                animationJumpRight(time, g);
            } else {
                animationJumpLeft(time, g);
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

    public void animationShootRight(int time, Graphics g) {
        if (time - preTime < 10) {
            g.drawImage(Assets.ninjashootRight[time - preTime], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            shootAni = true;
            shoot = false;
            g.drawImage(Assets.ninjashootRight[9], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void animationShootLeft(int time, Graphics g) {
        if (time - preTime < 10) {
            g.drawImage(Assets.ninjashootLeft[time - preTime], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            shootAni = true;
            shoot = false;
            g.drawImage(Assets.ninjashootLeft[9], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    @Override
    public void animationAttackRight(int time, Graphics g) {
        g.drawImage(Assets.ninjameleeRight[time % 10], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void animationAttackLeft(int time, Graphics g) {
        g.drawImage(Assets.ninjameleeLeft[time % 10], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void animationMoveRight(int time, Graphics g) {
        g.drawImage(Assets.ninjarunRight[time % 10], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void animationMoveLeft(int time, Graphics g) {
        g.drawImage(Assets.ninjarunLeft[time % 10], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void animationIdleRight(int time, Graphics g) {
        g.drawImage(Assets.ninjaidleRight, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void animationIdleLeft(int time, Graphics g) {
        g.drawImage(Assets.ninjaidleLeft, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void animationDeadRight(int time, Graphics g) {
        if (time / 2 - preTime / 2 < 9) {
            g.drawImage(Assets.ninjadeadRight[time / 2 - preTime / 2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            deadAni = true;
            g.drawImage(Assets.ninjadeadRight[8], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    @Override
    public void animationDeadLeft(int time, Graphics g) {
        if (time / 2 - preTime / 2 < 9) {
            g.drawImage(Assets.ninjadeadLeft[time / 2 - preTime / 2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            deadAni = true;
            g.drawImage(Assets.ninjadeadLeft[8], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void animationJumpRight(int time, Graphics g) {
        g.drawImage(Assets.ninjajumpRight[time % 10], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationJumpLeft(int time, Graphics g) {
        g.drawImage(Assets.ninjajumpLeft[time % 10], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

}
