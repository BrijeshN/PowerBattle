/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import Audio.JukeBox;
import powerbattle.ClickListener;
import UserInterface.UIImageButton;
import UserInterface.UIManager;
import state.State;
import graphics.Assets;
import graphics.ImageLoader;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import powerbattle.Game;
import powerbattle.Handler;

/**
 *
 * @author Brijesh
 */
public class MenuState extends State {

    private UIManager uiManager;
    private BufferedImage logo;

    

    public MenuState(final Handler handler) {

        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);
        
        logo = ImageLoader.loadImage("/Menu/PowerBattle.png");


          uiManager.addObject(new UIImageButton(240, 350, 150, 76, Assets.play, new ClickListener() {
            @Override
            public void onClick() {
                JukeBox.play("menuselect");
                ModeState modeState = new ModeState(handler);
                State.setState(modeState);
            }
        }));
        
        uiManager.addObject(new UIImageButton(640, 350, 150, 76, Assets.controls, new ClickListener() {
            @Override
            public void onClick() {
                JukeBox.play("menuselect");
                ControlState controlState = new ControlState(handler);
                State.setState(controlState);
            }
        }));
        
        uiManager.addObject(new UIImageButton(440, 525, 150, 76, Assets.quit, new ClickListener() {
            @Override
            public void onClick() {
                 JukeBox.play("menuselect");
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
        
        g.drawImage(logo, 60, 60, 882, 113, null);

        uiManager.render(g);
    }

}
