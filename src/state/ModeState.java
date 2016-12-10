/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import Audio.JukeBox;
import UserInterface.UIImageButton;
import UserInterface.UIManager;
import graphics.Assets;
import graphics.ImageLoader;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import powerbattle.ClickListener;
import powerbattle.Handler;

/**
 *
 * @author alien
 */
public class ModeState extends State {

    private UIManager uiManager;
    private BufferedImage modeLogo;

    public ModeState(Handler handler) {
        super(handler);

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);

        modeLogo = ImageLoader.loadImage("/Menu/GamemodeLogo.png");

        uiManager.addObject(new UIImageButton(630, 525, 150, 76, Assets.back, new ClickListener() {
            @Override
            public void onClick() {
                JukeBox.play("menuselect");
                State.setState(new MenuState(handler));
            }
        }));

        uiManager.addObject(new UIImageButton(230, 350, 150, 76, Assets.single, new ClickListener() {
            @Override
            public void onClick() {
                JukeBox.stop("menuback");
                JukeBox.play("menuselect");
                JukeBox.loop("singleback");
                State.setState(new GameState(handler, false, false));
            }
        }));

        uiManager.addObject(new UIImageButton(230, 525, 150, 76, Assets.coop, new ClickListener() {
            @Override
            public void onClick() {
                JukeBox.stop("menuback");
                JukeBox.play("menuselect");
                JukeBox.loop("coopback");
                State.setState(new GameState(handler, false, true));
            }
        }));

        uiManager.addObject(new UIImageButton(630, 350, 150, 76, Assets.chaotic, new ClickListener() {
            @Override
            public void onClick() {
                JukeBox.stop("menuback");
                JukeBox.play("menuselect");
                JukeBox.loop("chaoticback");
                handler.getGameCamera().setxOffset(0);
                handler.getGameCamera().setyOffset(0);
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

        g.drawImage(modeLogo, 110, 60, 784, 113, null);

        g.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 35));
        g.setColor(Color.WHITE);
        g.drawString("Please SELECT GameMode", 280, 300);
        
        g.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Created By: Da Lin, Brijesh Nayak", 330, 670);
        uiManager.render(g);
    }

}
