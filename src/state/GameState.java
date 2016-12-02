/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import entity.creature.Enemy;
import entity.creature.Player;
import graphics.ImageLoader;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import map.Map;
import powerbattle.Handler;

// Entity - anything that is not a tile
// Item, enemy and player are entity
// Entity wil contain all the entity
public class GameState extends State {

    public BufferedImage heartImage;

    private Player player;
    private Map map;
    private Enemy[] enemy;
    public static final int ENEMYNUM = 12;
    public static final int[][] ENEMYPOS = {{500, 1455}, {1614, 1205}, {1776, 1205}, {2739, 1455},
    {3423, 1455}, {2520, 1075}, {2004, 685}, {1401, 685}, {369, 805}, {528, 545}, {1590, 160}
    , {1956, 160}};
    public static final int PLAYER_SPAWN_X_POSITION = 150;
    public static final int PLAYER_SPAWN_Y_POSITION = 1455;

    public ArrayList<Enemy> enemis;

    public GameState(Handler handler) {
        super(handler);
        map = new Map(handler, "res/Maps/map1.txt");
        heartImage = ImageLoader.loadImage("/heart.png");

        handler.setMap(map);
        enemis = new ArrayList<>();
        enemy = new Enemy[ENEMYNUM];

        player = new Player(handler, PLAYER_SPAWN_X_POSITION, PLAYER_SPAWN_Y_POSITION);

        for (int i = 0; i < ENEMYNUM; i++) {
            enemy[i] = new Enemy(handler, ENEMYPOS[i][0], ENEMYPOS[i][1], i);
        }

        for (Enemy e : enemy) {
            enemis.add(e);
        }

    }

    @Override
    public void update() {
        map.update();

        for (Enemy e : enemy) {
            e.update(player);
        }
        player.update(enemis);

    }

    @Override
    public void render(Graphics g, int count) {
        map.render(g);
        player.render(g, count);
        for (Enemy e : enemy) {
            e.render(g, count);
        }
        
        g.drawImage(heartImage, 10, 10,null);
        g.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 17));
        g.setColor(Color.BLACK);
        g.drawString("Ammo: " + player.numOfNormalBullet,7,90);
        g.drawString("Magical Ammo: " + player.numOfMagicalBullet, 7, 110);
        
        //g.drawImage(Assets.run, 0, 0, null);
        //Tile.tiles[1].render(g, -10, 600);

    }

}
