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
public class ControlState extends State {

    private UIManager uiManager;
    private BufferedImage controlLogo;
    private BufferedImage controlsImage;



    public ControlState(Handler handler) {
        super(handler);

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);
        
        controlLogo = ImageLoader.loadImage("/Menu/ControlsLogo.png");
        controlsImage = ImageLoader.loadImage("/Menu/Controls.png");



        uiManager.addObject(new UIImageButton(425, 580, 150, 76, Assets.back, new ClickListener() {
            @Override
            public void onClick() {
                JukeBox.play("menuselect");
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
        
        g.drawImage(controlLogo, 190, 60, 602, 113, null);
        g.drawImage(controlsImage, 75, 175, 851, 400, null);
       

        uiManager.render(g);
    }

}
