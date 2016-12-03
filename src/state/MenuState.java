/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import powerbattle.ClickListener;
import UserInterface.UIImageButton;
import UserInterface.UIManager;
import state.State;
import graphics.Assets;
import java.awt.Color;
import java.awt.Graphics;
import powerbattle.Game;
import powerbattle.Handler;

/**
 *
 * @author Brijesh
 */
public class MenuState extends State {

    private UIManager uiManager;

    public MenuState(final Handler handler) {

        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);

        uiManager.addObject(new UIImageButton(425, 300, 140, 70, Assets.start, new ClickListener() {
            @Override
            public void onClick() {
                GameState gameState = new GameState(handler, false, false);
                State.setState(gameState);
            }
        }));

        uiManager.addObject(new UIImageButton(425, 400, 140, 70, Assets.start, new ClickListener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                GameState gameState = new GameState(handler, false, true);
                State.setState(gameState);
            }
        }));

        uiManager.addObject(new UIImageButton(425, 500, 140, 70, Assets.start, new ClickListener() {
            @Override
            public void onClick() {
                GameState gameState = new GameState(handler, true, false);
                gameState.chaotic = false;
                gameState.coop = true;
                State.setState(gameState);
            }
        }));
        uiManager.addObject(new UIImageButton(425, 600, 140, 70, Assets.start, new ClickListener() {
            @Override
            public void onClick() {
                System.exit(0);
            }
        }));

    }

    @Override
    public void update() {
        uiManager.update();

    }

    @Override

    public void render(Graphics g, int time) {

        uiManager.render(g);
    }

}
