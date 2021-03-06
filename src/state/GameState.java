/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import Audio.JukeBox;
import UserInterface.UIManager;
import entity.creature.Creature;
import entity.creature.Enemy;
import entity.creature.Ninja;
import entity.creature.Player;
import entity.creature.Robot;
import graphics.Assets;
import graphics.ImageLoader;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import map.Map;
import powerbattle.Handler;

// Entity - anything that is not a tile
// Item, enemy and player are entity
// Entity wil contain all the entity
public class GameState extends State {

    private UIManager uiManager;

    private Ninja ninja;
    private Player player;
    private Robot robot;
    private Map map;
    private Enemy[] enemy;
    public static final int ENEMYNUM = 12, COOPENEMYNUM = 6;
    public static final int[][] ENEMYPOS = {{500, 1455}, {1614, 1205}, {1776, 1205}, {2739, 1455},
    {3423, 1455}, {2520, 1075}, {2004, 685}, {1401, 685}, {369, 805}, {528, 545}, {1590, 160}, {1956, 160}};

    public static final int[][] COOPENEMYPOS = {{872, 950}, {556, 950}, {1708, 950}, {4072, 815}, {6256, 945}, {3240, 170}};
    public static final int PLAYER_SPAWN_X_POSITION = 150;
    public static final int PLAYER_SPAWN_Y_POSITION = 1475;
    public static final int CHAOTIC_PLAYER1_SPAWN_X_POSITION = 20;
    public static final int CHAOTIC_PLAYER1_SPAWN_Y_POSITION = 400;
    public static final int CHAOTIC_PLAYER2_SPAWN_X_POSITION = 890;
    public static final int CHAOTIC_PLAYER2_SPAWN_Y_POSITION = 400;
    public static final float STAR_X_POSITION = 3682;
    public static final float STAR_Y_POSITION = 175;
    public static boolean chaotic = false, coop = false, played = false;

    public static final int NINJA_SPAWN_X_POSITION = 7080;
    public static final int NINJA_SPAWN_Y_POSITION = 950;

    public static final int COOP_SPAWN_X_POSITION = 272;
    public static final int COOP_SPAWN_Y_POSITION = 950;

    int time = 0;

    private BufferedImage bg;

    public ArrayList<Enemy> enemis;

    public GameState(Handler handler, boolean chaotic, boolean coop) {
        super(handler);
        handler.getMouseManager().setUIManager(null);

        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                time++;
            }
        }, 0, 100);
        
        this.coop = coop;
        this.chaotic = chaotic;
        if (this.chaotic) {
            map = new Map(handler, "map2.txt");
            bg = ImageLoader.loadImage("/BG.png");
        } else if (this.coop) {
            map = new Map(handler, "map3.txt");
            bg = ImageLoader.loadImage("/Map2/BG/BG.png");
        } else {
            map = new Map(handler, "map1.txt");
            bg = ImageLoader.loadImage("/BG.png");
        }

        handler.setMap(map);
        enemis = new ArrayList<>();

        if (chaotic) {
            player = new Player(handler, CHAOTIC_PLAYER2_SPAWN_X_POSITION, CHAOTIC_PLAYER2_SPAWN_Y_POSITION);
        } else if (this.coop) {
            player = new Player(handler, COOP_SPAWN_X_POSITION, COOP_SPAWN_Y_POSITION);
        } else {
            player = new Player(handler, PLAYER_SPAWN_X_POSITION, PLAYER_SPAWN_Y_POSITION);
        }

        if (chaotic) {
            robot = new Robot(handler, CHAOTIC_PLAYER1_SPAWN_X_POSITION, CHAOTIC_PLAYER1_SPAWN_Y_POSITION);
        } else if (this.coop) {
            robot = new Robot(handler, COOP_SPAWN_X_POSITION, COOP_SPAWN_Y_POSITION);
        } else {
            robot = new Robot(handler, PLAYER_SPAWN_X_POSITION, PLAYER_SPAWN_Y_POSITION);
        }

        if (!chaotic && !coop) {
            enemy = new Enemy[ENEMYNUM];
            for (int i = 0; i < ENEMYNUM; i++) {
                enemy[i] = new Enemy(handler, ENEMYPOS[i][0], ENEMYPOS[i][1], i);
            }
        } else if (chaotic) {
            int i = 0;
            enemy = new Enemy[1];
            enemy[i] = new Enemy(handler, ENEMYPOS[i][0], ENEMYPOS[i][1], i);
        } else {
            ninja = new Ninja(handler, NINJA_SPAWN_X_POSITION, NINJA_SPAWN_Y_POSITION);
            enemy = new Enemy[COOPENEMYNUM];
            for (int i = 0; i < COOPENEMYNUM; i++) {
                enemy[i] = new Enemy(handler, COOPENEMYPOS[i][0], COOPENEMYPOS[i][1], i);
            }
        }

        for (Enemy e : enemy) {
            enemis.add(e);
        }

    }

    @Override
    public void update() {

        if (chaotic) {
            if (robot.dead && robot.deadAni) {
                JukeBox.stop("chaoticback");
                JukeBox.loop("menuback");
                State.getState().setState(new EndState(handler, time, "Human"));
            } else if (player.dead && player.deadAni) {
                JukeBox.stop("chaoticback");
                JukeBox.loop("menuback");
                State.getState().setState(new EndState(handler, time, "Robot"));
            }
        }

        boolean aimRobot = false, ninjaAimRobot = false;
        map.update();

        for (Enemy e : enemy) {
            if (aimForPlayer(robot, e)) {
                aimRobot = true;
            }
        }

        if (coop) {
            if (ninjaAimForPlayer(robot, ninja)) {
                ninjaAimRobot = true;
            }
        }

        if (chaotic || coop) {
            for (Enemy e : enemy) {
                if (!aimRobot && aimForPlayer(player, e)) {
                }
            }
            if (coop) {
                if (!ninjaAimRobot && ninjaAimForPlayer(player, ninja)) {
                }
            }

            if (chaotic) {
                player.update(enemis, robot, null);
            } else if (coop) {
                player.update(enemis, null, ninja);
            } else {
                player.update(enemis, null, null);
            }

        }

        if (chaotic) {
            robot.update(enemis, player, null);
        } else if (coop) {
            robot.update(enemis, null, ninja);
        } else {
            robot.update(enemis, null, null);
        }

        for (Enemy e : enemy) {
            if (aimRobot) {
                e.update(robot);
            } else if (chaotic || coop) {
                e.update(player);
            } else {
                e.update(robot);
            }
        }

        if (coop) {
            if (ninjaAimRobot) {
                ninja.update(robot);
            } else if (chaotic || coop && !player.dead) {
                ninja.update(player);
            } else {
                ninja.update(robot);
            }
        }

        // center on this player entity
        if (coop) {

            if (robot.dead && player.dead && robot.deadAni && player.deadAni) {
                JukeBox.stop("coopback");
                JukeBox.loop("menuback");
                State.getState().setState(new EndState(handler, time, "None"));
            }

            if (!robot.dead) {
                handler.getGameCamera().centerOnEntity(robot);
            } else {
                handler.getGameCamera().centerOnEntity(player);
            }
        } else if (!chaotic) {
            handler.getGameCamera().centerOnEntity(robot);
        }
    }

    public boolean aimForPlayer(Creature player, Enemy e) {
        if (Math.abs(player.getX() - e.x) < 200 && Math.abs(player.getY() - e.y) < 20) {
            e.aimPlayer = Math.abs(player.getX() - e.x) >= 20;
        } else {
            e.aimPlayer = false;
        }

        return e.aimPlayer;
    }

    public boolean ninjaAimForPlayer(Creature player, Ninja ninja) {
        if (Math.abs(player.getX() - ninja.x) < 200 && Math.abs(player.getY() - ninja.y) < 20) {
            ninja.aimPlayer = Math.abs(player.getX() - ninja.x) >= 20;
        } else {
            ninja.aimPlayer = false;
        }

        return ninja.aimPlayer;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bg, 0, 0, null);

        map.render(g);

        if (chaotic || coop) {
            player.render(g, time);
        }

        robot.render(g, time);

        if (coop) {
            ninja.render(g, time);
        }

        for (Enemy e : enemy) {
            e.render(g, time);
        }
        if (!chaotic && !coop) {
            g.drawImage(Assets.star, (int) (STAR_X_POSITION - handler.getGameCamera().getxOffset()), (int) (STAR_Y_POSITION - handler.getGameCamera().getyOffset()), 100, 100, null);
        }
        for (int i = 0; i < robot.health; i++) {
            g.drawImage(Assets.heartImage, 60 * (i + 1) - 55, 25, 50, 50, null);
        }
        g.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 17));
        g.setColor(Color.BLACK);
        g.drawString("Ammo: " + robot.numOfNormalBullet, 7, 95);
        g.drawString("Magical Ammo: " + robot.numOfMagicalBullet, 7, 115);
        g.drawString("Robot:", 7, 18);

        if (chaotic || coop) {
            for (int i = 0; i < player.health; i++) {
                g.drawImage(Assets.heartImage, 60 * (i + 1) + 625, 25, 50, 50, null);
            }
            g.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 17));
            g.setColor(Color.BLACK);
            g.drawString("Ammo: " + player.numOfNormalBullet, 685, 95);
            g.drawString("Magical Ammo: " + player.numOfMagicalBullet, 685, 115);
            g.drawString("Human:", 685, 18);
        }
        //g.drawImage(Assets.run, 0, 0, null);
        //Tile.tiles[1].render(g, -10, 600);
    }

}
