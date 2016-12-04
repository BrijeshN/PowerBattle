/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import Audio.JukeBox;
import graphics.Assets;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.Timer;
import powerbattle.Handler;
import state.EndState;
import state.GameState;
import state.MenuState;
import state.State;
import tiles.Tile;

/**
 *
 * @author Brijesh
 */
public class Robot extends Creature {

    //Set size of the character
    public static final int DEFAULT_PLAYER_WIDTH = 130;
    public static final int DEFAULT_PLAYER_HEIGHT = 111;

    public int numOfNormalBullet = 25, numOfMagicalBullet = 5;

    final float BULLETSPEED = 5f;
    int forward = 0;
    boolean isRight = true, shootAni = false, firstShoot = false, normalBulletShoot = false;
    public boolean jump = false, fall = false, flag = true, dead = false, attackAni = false;
    public boolean cheat = false, hitByPlayer = false, hitByMagicalBullet = false;
    ;
    float jumpspeed = 10; //Check how high the player can jump
    int jumpTimer = 0; //Make the player can jump again using this timer
    Timer timer;
    int preTime, time;
    boolean deadAni = false, first = false, hit = false, bulletShoot = false, firstHit = false;
    ArrayList<MagicalBullet> bullets = new ArrayList<>();
    ArrayList<NormalBullet> normalBullets = new ArrayList<>();

    public Robot(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_PLAYER_WIDTH, DEFAULT_PLAYER_HEIGHT);

        played = true;

        health = 5;
        bounds.x = 32;
        bounds.y = 32;
        bounds.width = 40;
        bounds.height = 50;
    }

    public void update(ArrayList<Enemy> enemies, Player player, boolean chaotic) {
        // System.out.println(x + " " + y);

        if (Math.abs(x - GameState.STAR_X_POSITION) < 20 && Math.abs(y - GameState.STAR_Y_POSITION) < 20) {
            if (GameState.coop) {
                JukeBox.stop("coopback");
                JukeBox.loop("menuback");
            } else {
                JukeBox.stop("singleback");
                JukeBox.loop("menuback");
            }
            State.setState(new EndState(handler, time, "you"));
        }

        if (handler.getKeyManager().menu) {
            if (GameState.chaotic) {
                JukeBox.stop("chaoticback");
                JukeBox.loop("menuback");
            } else if (GameState.coop) {
                JukeBox.stop("coopback");
                JukeBox.loop("menuback");
            } else {
                JukeBox.stop("singleback");
                JukeBox.loop("menuback");
            }
            State.setState(new MenuState(handler));
        }
        getInput(enemies, player, chaotic);

        move();

    }

    public void stop() {
        xMove = 0;
        fall = jump = false;
    }

    private void getInput(ArrayList<Enemy> enemis, Player player, boolean chaotic) {

        if (handler.getKeyManager().cheatMode) {
            health = 15;
            jumpspeed = 15;
            runSpeed = 8.0f;
            numOfMagicalBullet = 999;
            numOfNormalBullet = 999;
            cheat = true;
        } else if (handler.getKeyManager().cheatModeOff) {
            jumpspeed = 10;
            runSpeed = Creature.RUN_SPEED;
            numOfMagicalBullet = 5;
            numOfNormalBullet = 25;
            cheat = false;
        }

        if (handler.getKeyManager().restart) {
            health = 5;
            runSpeed = Creature.RUN_SPEED;
            jumpspeed = 10;
            numOfMagicalBullet = 5;
            numOfNormalBullet = 25;
            first = false;
            isRight = true;
            dead = false;
            deadAni = false;
            cheat = false;
            if (!chaotic) {
                y = GameState.PLAYER_SPAWN_Y_POSITION;
                x = GameState.PLAYER_SPAWN_X_POSITION;
            } else {
                y = GameState.CHAOTIC_PLAYER1_SPAWN_Y_POSITION;
                x = GameState.CHAOTIC_PLAYER1_SPAWN_X_POSITION;
            }

            bullets.clear();
            normalBullets.clear();

            for (Enemy e : enemis) {
                e.restart = true;
            }
        }

        if (health <= 0) {
            dead = true;
        }

        if (y > DIEHEIGHT) {
            if (!cheat) {
                dead = true;
            }
            yMove = 0;
            stop();
        }

        if (dead) {
            health = 0;
            stop();
            return;
        }

        xMove = 0;
        if (handler.getKeyManager().up) {
            if (!jump && !fall) {
                JukeBox.play("jump");
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
            yMove = -jumpspeed;
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
            if (!jump && !played) {
                played = true;
                JukeBox.play("land");
            }
        } else {
            played = false;
            fall = true;
        }

        hit = false;

        if (handler.getKeyManager().magicalShoot && numOfMagicalBullet > 0) {
            if (!bulletShoot) {
                MagicalBullet bullet = new MagicalBullet(handler, x, y, isRight);
                bullets.add(bullet);
                numOfMagicalBullet--;
                bulletShoot = true;
                JukeBox.play("magicalammo");
            }
        } else {
            bulletShoot = false;
        }

        if (handler.getKeyManager().normalShoot && !handler.getKeyManager().magicalShoot) {
            if (!normalBulletShoot && numOfNormalBullet > 0) {
                NormalBullet normalBullet = new NormalBullet(handler, x, y, isRight);
                normalBullets.add(normalBullet);
                numOfNormalBullet--;
                normalBulletShoot = true;
                JukeBox.play("ammo");
            }
        } else {
            normalBulletShoot = false;
        }

        if (!handler.getKeyManager().attack) {
            if (chaotic) {
                player.hitByPlayer = false;
            }
            for (Enemy e : enemis) {
                e.hitByRobot = false;
            }
            first = false;
            attackAni = false;
        }

        if (!handler.getKeyManager().magicalShoot && !handler.getKeyManager().normalShoot) {
            firstShoot = false;
            shootAni = false;
        }

        for (Enemy e : enemis) {
            if (isRight) {
                if (e.isAmmo) {
                    if (x > e.getX() - 55 && x < e.getX() + 15 && y > e.getY() - 60 && y < e.getY() + 60) {
                        JukeBox.play("ammoclip");
                        e.pickedByPlayer = true;
                        numOfNormalBullet += 5;
                    }
                } else if (e.isMagicalAmmo) {
                    if (x > e.getX() - 55 && x < e.getX() + 15 && y > e.getY() - 60 && y < e.getY() + 60) {
                        JukeBox.play("ammoclip");
                        e.pickedByPlayer = true;
                        numOfMagicalBullet += 1;
                    }
                } else if (e.isHeart) {
                    if (x > e.getX() - 55 && x < e.getX() + 15 && y > e.getY() - 60 && y < e.getY() + 60) {
                        e.pickedByPlayer = true;
                        health += 1;
                        JukeBox.play("heart");
                    }
                }
            } else if (e.isAmmo) {
                if (x > e.getX() - 15 && x < e.getX() + 15 && y > e.getY() - 60 && y < e.getY() + 60) {
                    e.pickedByPlayer = true;
                    numOfNormalBullet += 5;
                    JukeBox.play("ammoclip");
                }
            } else if (e.isMagicalAmmo) {
                if (x > e.getX() - 15 && x < e.getX() + 15 && y > e.getY() - 60 && y < e.getY() + 60) {
                    e.pickedByPlayer = true;
                    numOfMagicalBullet += 1;
                    JukeBox.play("ammoclip");
                }
            } else if (e.isHeart) {
                if (x > e.getX() - 15 && x < e.getX() + 15 && y > e.getY() - 60 && y < e.getY() + 60) {
                    e.pickedByPlayer = true;
                    health += 1;
                    JukeBox.play("heart");
                }
            }
        }

        if (handler.getKeyManager().attack) {
            if (isRight) {
                if (chaotic) {
                    if (x > player.getX() - 75 && x < player.getX() + 15 && y > player.getY() - 60 && y < player.getY() + 60) {
                        if (!player.hitByPlayer) {
                            player.hitByPlayer = true;
                            player.health -= ATTACKPDAMAGE;
                            JukeBox.play("playerhit");
                        }
                    }
                }
                for (Enemy e : enemis) {
                    if (x > e.getX() - 75 && x < e.getX() + 15 && y > e.getY() - 60 && y < e.getY() + 60) {
                        if (!e.hitByRobot) {
                            e.hitByRobot = true;
                            e.health -= ATTACKDAMAGE;
                            JukeBox.play("enemyhit");
                        }
                    }
                }
            } else {
                if (chaotic) {
                    if (x > player.getX() - 15 && x < player.getX() + 75 && y > player.getY() - 60 && y < player.getY() + 60) {
                        if (!player.hitByPlayer) {
                            player.hitByPlayer = true;
                            player.health -= ATTACKPDAMAGE;
                            JukeBox.play("playerhit");
                        }
                    }
                }
                for (Enemy e : enemis) {
                    if (x > e.getX() - 15 && x < e.getX() + 75 && y > e.getY() - 60 && y < e.getY() + 60) {
                        if (!e.hitByRobot) {
                            e.hitByRobot = true;
                            e.health -= ATTACKDAMAGE;
                            JukeBox.play("enemyhit");
                        }
                    }
                }
            }
        }
        for (MagicalBullet b : bullets) {
            b.update();
            if (chaotic) {
                if (Math.abs(b.getX() - player.getX()) < 85 && b.getY() > player.getY() - 60 && b.getY() < player.getY() + 60) {
                    if (!player.hitByMagicalBullet) {
                        b.hitEnemy = false;
                    }
                    if (!b.hitEnemy) {
                        b.hitEnemy = true;
                        player.health -= MAGICALPBULLETDAMAGE;
                        JukeBox.play("playerhit");
                        if (!player.hitByMagicalBullet) {
                            player.hitByMagicalBullet = true;
                        }
                    }
                } else {
                    player.hitByMagicalBullet = false;
                }
            }

            for (Enemy e : enemis) {
                if (Math.abs(b.getX() - e.getX()) < 85 && b.getY() > e.getY() - 60 && b.getY() < e.getY() + 60) {
                    if (!e.hitByMagicalBullet) {
                        b.hitEnemy = false;
                    }
                    if (!b.hitEnemy) {
                        b.hitEnemy = true;
                        e.health -= MAGICALBULLETDAMAGE;
                        JukeBox.play("enemyhit");
                        if (!e.hitByMagicalBullet) {
                            e.hitByMagicalBullet = true;
                        }
                    }
                } else {
                    e.hitByMagicalBullet = false;
                }
            }

        }

        for (NormalBullet b : normalBullets) {
            b.update();
            if (chaotic) {
                if (Math.abs(b.getX() - player.getX()) < 45 && b.getY() > player.getY() - 60 && b.getY() < player.getY() + 60) {
                    if (!b.hitEnemy) {
                        b.hitEnemy = true;
                        player.health -= NORMALPBULLETDAMAGE;
                        JukeBox.play("playerhit");
                    }
                }
            }

            for (Enemy e : enemis) {
                if (Math.abs(b.getX() - e.getX()) < 45 && b.getY() > e.getY() - 60 && b.getY() < e.getY() + 60) {
                    if (!b.hitEnemy) {
                        b.hitEnemy = true;
                        e.health -= NORMALBULLETDAMAGE;
                        JukeBox.play("enemyhit");
                    }
                }
            }

        }

        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).distance > 300 || bullets.get(i).hitWall) {
                bullets.remove(i);
            }
        }

        for (int i = 0; i < normalBullets.size(); i++) {
            if (normalBullets.get(i).distance > 200 || normalBullets.get(i).hitWall || normalBullets.get(i).hitEnemy) {
                normalBullets.remove(i);
            }
        }

        if (y > DIEHEIGHT && cheat) {
            if (!jump) {
                yMove = 0;
            }
            fall = false;
        }
    }

    public void render(Graphics g, int time) {
        this.time = time;

        //g.setColor(Color.GRAY);
        // g.setFont(new Font("TimesRoman", Font.PLAIN, 17));
        // g.drawString("Margical Bullets remain:" + numOfMagicalBullet, (int) (x - handler.getGameCamera().getxOffset() - 430), (int) (y - handler.getGameCamera().getyOffset() - 130));
        // g.drawString("Normal   Bullets remain:" + numOfNormalBullet, (int) (x - handler.getGameCamera().getxOffset() - 430), (int) (y - handler.getGameCamera().getyOffset() - 115));
        // g.drawImage(Assets.heart, (int) (x - handler.getGameCamera().getxOffset() - 430), (int) (y - handler.getGameCamera().getyOffset() - 105), width, height, null);
        for (MagicalBullet b : bullets) {
            b.render(g, time, true);
        }

        for (NormalBullet b : normalBullets) {
            b.render(g, time, true);
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
                g.drawImage(Assets.robotdeadRight[9], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            } else {
                g.drawImage(Assets.robotdeadLeft[9], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            }
            return;
        }

        if (handler.getKeyManager().attack) {
            if (!first) {
                JukeBox.play("attack");
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

        if (time / 2 - preTime / 2 < 10) {
            g.drawImage(Assets.robotdeadRight[time / 2 - preTime / 2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            deadAni = true;
            g.drawImage(Assets.robotdeadRight[9], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void animationDeadLeft(int time, Graphics g) {
        if (time / 2 - preTime / 2 < 10) {
            g.drawImage(Assets.robotdeadLeft[time / 2 - preTime / 2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            deadAni = true;
            g.drawImage(Assets.robotdeadLeft[9], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void animationIdleRight(int time, Graphics g) {
        g.drawImage(Assets.robotidleRight[time % 10], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationIdleLeft(int time, Graphics g) {
        g.drawImage(Assets.robotidleLeft[time % 10], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationJumpRight(int time, Graphics g) {
        g.drawImage(Assets.robotjumpRight[time % 10], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationJumpLeft(int time, Graphics g) {
        g.drawImage(Assets.robotjumpLeft[time % 10], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void animationRunRight(int time, Graphics g) {
        g.drawImage(Assets.robotrunRight[time % 8], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    public void animationAttackRight(int time, Graphics g) {
        if (time - preTime < 8) {
            g.drawImage(Assets.robotmeleeRight[time - preTime], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            attackAni = true;
            g.drawImage(Assets.robotmeleeRight[7], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }

    }

    public void animationRunLeft(int time, Graphics g) {
        g.drawImage(Assets.robotrunLeft[time % 8], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    public void animationAttackLeft(int time, Graphics g) {
        if (time - preTime < 8) {
            g.drawImage(Assets.robotmeleeLeft[time - preTime], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            attackAni = true;
            g.drawImage(Assets.robotmeleeLeft[7], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void animationShootRight(int time, Graphics g) {
        if (time - preTime < 3) {
            g.drawImage(Assets.robotshootRight[time - preTime], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            shootAni = true;
            g.drawImage(Assets.robotshootRight[2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

    public void animationShootLeft(int time, Graphics g) {
        if (time - preTime < 3) {
            g.drawImage(Assets.robotshootLeft[time - preTime], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            shootAni = true;
            g.drawImage(Assets.robotshootLeft[2], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }
}
