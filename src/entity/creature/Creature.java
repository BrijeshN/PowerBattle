/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import Audio.JukeBox;
import entity.Entity;
import powerbattle.Handler;
import tiles.Tile;

/**
 *
 * @author Brijesh
 */
public abstract class Creature extends Entity {

    public static final int DEFAULT_HEALTH = 10;
    public static final float RUN_SPEED = 4.0f;
    final int ATTACKDAMAGE = 50, DIEHEIGHT = 1600, NORMALBULLETDAMAGE = 25, MAGICALBULLETDAMAGE = 75;
    final int ATTACKPDAMAGE = 2, NORMALPBULLETDAMAGE = 1, MAGICALPBULLETDAMAGE = 3, CHAOTICDIEHEIGHT = 600;
    final int COOPDIEHEIGHT = 1200;
    boolean played = false, playedAttack = false;

    //Set size of the character
    public int health;
    protected float runSpeed;
    boolean hitWall = false;

    //Movement
    protected float xMove, yMove;

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        health = DEFAULT_HEALTH;
        runSpeed = RUN_SPEED;
        xMove = 0;
        yMove = 0;

    }

    public void move() {
        playSound();
        moveX();
        moveY();
    }

    public void playSound() {
//        if (handler.getKeyManager().attack) {
//            if (!playedAttack) {
//                JukeBox.play("attack");
//                playedAttack = true;
//            }
//        } else {
//            playedAttack = false;
//        }
    }

    //move collision
    public void moveX() {

        //if greater than 0, moving right
        if (xMove > 0) {

            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
            // if its not solid then move
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
                    && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += xMove;
                hitWall = false;
            } else {
                hitWall = true;
            }

        } else if (xMove < 0) { // if less than 0, moving left
            int tx = (int) (x + xMove) / Tile.TILEWIDTH;
            // if its not solid then move
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
                    && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += xMove;
                hitWall = false;
            } else {
                hitWall = true;
            }
        }

    }

    // for collision
    public void moveY() {
//          if (y > 550) {
//            y = 550;
//        }
        // y += yMove;
        // Moving up
        //System.out.println(yMove);
        if (yMove < 0) {

            int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
                    && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += yMove;
            }

        } else if (yMove > 0) {// Moving Down
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;

            if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
                    && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                y += yMove;
            }

        }

    }

    // takse x and y coordinate of the tile and check if it's solid or not
    protected boolean collisionWithTile(int x, int y) {

        return handler.getMap().getTile(x, y).isSolid();
    }

    //GETTERS and SETTERS
    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return runSpeed;
    }

    public void setSpeed(float speed) {
        this.runSpeed = speed;
    }

}
