/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import entity.Entity;
import powerbattle.Game;
import powerbattle.Handler;
import tiles.Tile;

/**
 *
 * @author Brijesh
 */
public abstract class Creature extends Entity {

    public static final int DEFAULT_HEALTH = 10;
    public static final float RUN_SPEED = 3.0f;

    //Set size of the character
    public static final int DEFAULT_CREATURE_WIDTH = 130;
    public static final int DEFAULT_CREATURE_HEIGHT = 111;

    protected int health;
    protected float runSpeed;

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
        moveX();
        moveY();
    }

    //move collision
    public void moveX() {

        //if greater than 0, moving right
        if (xMove > 0) {

            int tx = (int) (x + xMove+ bounds.x + bounds.width) / Tile.TILEWIDTH;
            // if its not solid then move
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
                    && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += xMove;
            }

        } else if (xMove < 0) { // if less than 0, moving left
            int tx = (int) (x + xMove) / Tile.TILEWIDTH;
            // if its not solid then move
            if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
                    && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                x += xMove;
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
