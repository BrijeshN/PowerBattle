/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import entity.NormalBullet;
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

    //Set size of the character
    public static final int DEFAULT_CREATURE_WIDTH = 130;
    public static final int DEFAULT_CREATURE_HEIGHT = 111;

    final float BULLETSPEED = 5f;
    final int ATTACKDAMAGE = 30, DIEHEIGHT = 1280;
    int forward = 0;
    boolean isRight = true, shootAni = false, firstShoot = false, normalBulletShoot = false;
    boolean jump = false, fall = false, flag = true, dead = false, attackAni = false;
    float jumpspeed = 10; //Check how high the player can jump
    int jumpTimer = 0; //Make the player can jump again using this timer
    Timer timer;
    int preTime, time;
    boolean deadAni = false, first = false, hit = false, bulletShoot = false, firstHit = false;
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<NormalBullet> normalBullets = new ArrayList<>();

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);

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

    }

    public void stop() {
        xMove = 0;
        fall = jump = false;
    }

    private void getInput(ArrayList<Enemy> enemis) {

        if (handler.getKeyManager().restart) {
            first = false;
            isRight = true;
            dead = false;
            deadAni = false;
            y = 1080;
            x = 100;

            bullets.clear();
            normalBullets.clear();

            for (Enemy e : enemis) {
                e.restart = true;
            }
        }

        if (y > DIEHEIGHT) {
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

        if (handler.getKeyManager().magicalShoot) {
            if (!bulletShoot) {
                Bullet bullet = new Bullet(handler, x, y, isRight);
                bullets.add(bullet);
                bulletShoot = true;
            }
        } else {
            bulletShoot = false;
        }

        if (handler.getKeyManager().normalShoot && !handler.getKeyManager().magicalShoot) {
            if (!normalBulletShoot) {
                System.out.println(x);
                NormalBullet normalBullet = new NormalBullet(handler, x, y, isRight);
                normalBullets.add(normalBullet);
                normalBulletShoot = true;
            }
        } else {
            normalBulletShoot = false;
        }

        if (!handler.getKeyManager().attack) {
            for (Enemy e : enemis) {
                e.hitByPlayer = false;
            }
            first = false;
            attackAni = false;
        }

        if (!handler.getKeyManager().magicalShoot && !handler.getKeyManager().normalShoot) {
            firstShoot = false;
            shootAni = false;
        }

        if (handler.getKeyManager().attack) {
            if (isRight) {
                for (Enemy e : enemis) {
                    if (x > e.getX() - 75 && x < e.getX() + 15 && y > e.getY() - 60 && y < e.getY() + 60) {
                        if (!e.hitByPlayer) {
                            e.hitByPlayer = true;
                            e.health -= ATTACKDAMAGE;
                            e.deadTime = time;
                        }
                    }
                }
            } else {
                for (Enemy e : enemis) {
                    if (x > e.getX() - 15 && x < e.getX() + 75 && y > e.getY() - 60 && y < e.getY() + 60) {
                        e.hitByPlayer = true;
                        e.health -= ATTACKDAMAGE;
                        e.deadTime = time;
                    }
                }
            }
        }
        for (Bullet b : bullets) {
            b.update();
            if (isRight) {
                for (Enemy e : enemis) {
                    if (Math.abs(b.getX() - e.getX()) < 100 && b.getY() > e.getY() - 60 && b.getY() < e.getY() + 60) {
                        e.dead = true;
                        e.deadTime = time;
                    }
                }
            } else {
                for (Enemy e : enemis) {
                    if (Math.abs(b.getX() - e.getX()) < 35 && b.getY() > e.getY() - 60 && b.getY() < e.getY() + 60) {
                        e.dead = true;
                        e.deadTime = time;
                    }
                }
            }
        }

        for (NormalBullet b : normalBullets) {
            b.update();
            if (isRight) {
                for (Enemy e : enemis) {
                    if (Math.abs(b.getX() -e.getX()) > 35 && b.getY() > e.getY() - 60 && b.getY() < e.getY() + 60) {
                        e.dead = true;
                        e.deadTime = time;
                    }
                }
            } else {
                for (Enemy e : enemis) {
                    if (b.getX() > e.getX() - 15 && b.getY() > e.getY() - 60 && b.getY() < e.getY() + 60) {
                        e.dead = true;
                        e.deadTime = time;
                    }
                }
            }
        }

        for (int i = 0; i < bullets.size(); i++) {
            if (Math.abs(bullets.get(i).getX() - x) > 300) {
                bullets.remove(i);
            }
        }

//        for (int i = 0; i < normalBullets.size(); i++) {
//           int tx = (int) (normalBullets.get(i).getX() + normalBullets.get(i).xMove + normalBullets.get(i).bounds.x + normalBullets.get(i).bounds.width) / Tile.TILEWIDTH;
//            if (Math.abs(normalBullets.get(i).getX() - x) > 200 || collisionWithTile(tx, (int) (normalBullets.get(i).getY() + normalBullets.get(i).bounds.y) / Tile.TILEHEIGHT)
//                    || collisionWithTile(tx, (int) (normalBullets.get(i).getY() + normalBullets.get(i).bounds.y +normalBullets.get(i).bounds.height))) {
//                normalBullets.remove(i);
//            }
//        }
    }

    public void render(Graphics g, int time) {
        this.time = time;

        for (Bullet b : bullets) {
            b.render(g, time);
        }

        for (NormalBullet b : normalBullets) {
            b.render(g, time);
        }

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
            if (!first) {
                first = true;
                preTime = time;
            }
            if (!attackAni) {
                if (isRight) {
                    animationAttackRight(time, g);
                } else {
                    animationAttackLeft(time, g);
                }
            } else if (jump || fall) {
                if (isRight) {
                    animationJumpRight(time, g);
                } else {
                    animationJumpLeft(time, g);
                }
            } else if (isRight && handler.getKeyManager().right) {
                animationRunRight(time, g);
            } else if (!isRight && handler.getKeyManager().left) {
                animationRunLeft(time, g);
            } else if (isRight) {
                animationIdleRight(time / 3, g);
            } else {
                animationIdleLeft(time / 3, g);
            }
        } else if (handler.getKeyManager().magicalShoot || handler.getKeyManager().normalShoot) {
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
            } else if (isRight && handler.getKeyManager().right) {
                animationRunRight(time, g);
            } else if (!isRight && handler.getKeyManager().left) {
                animationRunLeft(time, g);
            } else if (isRight) {
                animationIdleRight(time / 3, g);
            } else {
                animationIdleLeft(time / 3, g);
            }
        }

        if ((jump || fall) && !handler.getKeyManager().attack && !handler.getKeyManager().magicalShoot && !handler.getKeyManager().normalShoot) {
            if (isRight) {
                animationJumpRight(time, g);
            } else {
                animationJumpLeft(time, g);
            }
        } else if (handler.getKeyManager().right && !handler.getKeyManager().attack && !handler.getKeyManager().magicalShoot && !handler.getKeyManager().normalShoot) {
            animationRunRight(time, g);
        } else if (handler.getKeyManager().left && !handler.getKeyManager().attack && !handler.getKeyManager().magicalShoot && !handler.getKeyManager().normalShoot) {
            animationRunLeft(time, g);
            isRight = false;
        } else if (isRight && !handler.getKeyManager().attack && !handler.getKeyManager().magicalShoot && !handler.getKeyManager().normalShoot) {
            animationIdleRight(time / 3, g);
        } else if (!handler.getKeyManager().attack && !handler.getKeyManager().magicalShoot && !handler.getKeyManager().normalShoot) {
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

    public void animationAttackRight(int time, Graphics g) {
        if (time * 3 - preTime * 3 < 4) {
            g.drawImage(Assets.meleeRight[time * 3 - preTime * 3], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            attackAni = true;
            g.drawImage(Assets.meleeRight[3], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }

    }

    public void animationRunLeft(int time, Graphics g) {
        g.drawImage(Assets.runLeft[time % 5], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    public void animationAttackLeft(int time, Graphics g) {
        if (time * 3 - preTime * 3 < 4) {
            g.drawImage(Assets.meleeLeft[time * 3 - preTime * 3], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            attackAni = true;
            g.drawImage(Assets.meleeLeft[3], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void animationShootRight(int time, Graphics g) {
        if (time - preTime < 3) {
            g.drawImage(Assets.shootRight[time - preTime], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            shootAni = true;
            g.drawImage(Assets.shootRight[2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void animationShootLeft(int time, Graphics g) {
        if (time - preTime < 3) {
            g.drawImage(Assets.shootLeft[time - preTime], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            shootAni = true;
            g.drawImage(Assets.shootLeft[2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }
}
