/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.creature;

import entity.Entity;
import powerbattle.Game;

/**
 *
 * @author Brijesh
 */
public abstract class Creature extends Entity{
    
    public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 3.0f;
    
    //Set size of the character
    public static final int DEFAULT_CREATURE_WIDTH = 130;
    public static final int DEFAULT_CREATURE_HEIGHT = 111;

    
    protected int health;
    protected float speed;
    
    //Movement
    protected float xMove, yMove;
    
    public Creature(Game game, float x, float y, int width, int height){
        super(game, x, y, DEFAULT_CREATURE_WIDTH,DEFAULT_CREATURE_HEIGHT );
        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        
    }

    public void move(){
        x += xMove;
        y += yMove;
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
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
}
