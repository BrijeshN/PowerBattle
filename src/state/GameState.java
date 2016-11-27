/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import entity.creature.Player;
import graphics.Assets;
import java.awt.Graphics;
import map.Map;
import powerbattle.Game;
import powerbattle.Handler;
import tiles.Tile;

// Entity - anything that is not a tile
// Item, enemy and player are entity
// Entity wil contain all the entity
public class GameState extends State {

    private Player player;
    private Map map;

    public GameState(Handler handler) {
        super(handler);
        map = new Map(handler, "res/Maps/map1.txt");
        handler.setMap(map);
        player = new Player(handler, 100, 550);

    }

    @Override
    public void update(int count) {
        map.update();
        player.update(count);

    }

    @Override
    public void render(Graphics g, int count) {
        map.render(g);
        player.render(g, count);
        //g.drawImage(Assets.run, 0, 0, null);
        //Tile.tiles[1].render(g, -10, 600);

    }

}
