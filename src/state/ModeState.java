/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import UserInterface.UIImageButton;
import UserInterface.UIManager;
import graphics.Assets;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import powerbattle.ClickListener;
import powerbattle.Handler;

/**
 *
 * @author alien
 */
public class ModeState extends State {

    private UIManager uiManager;

    public ModeState(Handler handler) {
        super(handler);

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);

        uiManager.addObject(new UIImageButton(640, 525, 140, 70, Assets.back, new ClickListener() {
            @Override
            public void onClick() {
                State.setState(new MenuState(handler));
            }
        }));

        uiManager.addObject(new UIImageButton(240, 350, 140, 70, Assets.single, new ClickListener() {
            @Override
            public void onClick() {
                State.setState(new GameState(handler, false, false));
            }
        }));

        uiManager.addObject(new UIImageButton(240, 525, 140, 70, Assets.coop, new ClickListener() {
            @Override
            public void onClick() {
                State.setState(new GameState(handler, false, true));
            }
        }));

        uiManager.addObject(new UIImageButton(640, 350, 140, 70, Assets.chaotic, new ClickListener() {
            @Override
            public void onClick() {
                State.setState(new GameState(handler, true, false));
            }
        }));
    }

    @Override
    public void update() {
        uiManager.update();

    }

    @Override
    public void render(Graphics g, int time) {
        g.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 35));
        g.setColor(Color.WHITE);
        g.drawString("Please SELECT GameMode", 300, 300);
        uiManager.render(g);
    }

}
