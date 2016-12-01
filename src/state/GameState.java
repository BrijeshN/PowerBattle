/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import entity.creature.Enemy;
import entity.creature.Player;
import java.awt.Graphics;
import java.util.ArrayList;
import map.Map;
import powerbattle.Handler;

// Entity - anything that is not a tile
// Item, enemy and player are entity
// Entity wil contain all the entity
public class GameState extends State {

    private Player player;
    private Map map;
    private Enemy[] enemy;
    final int ENEMYNUM = 4;
    public static final int[][] ENEMYPOS = {{290, 1080}, {500, 1080}, {664, 1080}, {980, 1080}};
    public static final int PLAYER_SPAWN_X_POSITION = 150;
    public static final int PLAYER_SPAWN_Y_POSITION = 1475;

    public ArrayList<Enemy> enemis;

    public GameState(Handler handler) {
        super(handler);
        map = new Map(handler, "res/Maps/map1.txt");
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
        //g.drawImage(Assets.run, 0, 0, null);
        //Tile.tiles[1].render(g, -10, 600);

    }

}
