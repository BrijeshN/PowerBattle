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
public class ControlState extends State {

    private UIManager uiManager;

    public ControlState(Handler handler) {
        super(handler);

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);

        uiManager.addObject(new UIImageButton(425, 300, 140, 70, Assets.back, new ClickListener() {
            @Override
            public void onClick() {
                State.setState(new MenuState(handler));
            }
        }));
    }

    @Override
    public void update() {
        uiManager.update();

    }

    @Override
    public void render(Graphics g, int time) {
        g.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 17));
        g.setColor(Color.BLACK);
        g.drawString("Ammo: ", 500, 95);
        uiManager.render(g);
    }

}
