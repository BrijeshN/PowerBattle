/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import UserInterface.ClickListener;
import UserInterface.UIImageButton;
import UserInterface.UIManager;
import state.State;
import graphics.Assets;
import java.awt.Graphics;
import powerbattle.Game;
import powerbattle.Handler;

/**
 *
 * @author Brijesh
 */
public class MenuState extends State {
    
    public UIManager uiManager;

    public MenuState(Handler handler){
        
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);
        
        uiManager.addObject(new UIImageButton(200, 200, 128, 64, Assets.start, new ClickListener(){
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                State.setState(handler.getGame().gameState); 
            }
        }));
        
    }
    
    @Override
    public void update() {
        
        uiManager.update();
    }

    @Override
    public void render(Graphics g, int count) {
        
        uiManager.render(g);
    }
    
}
