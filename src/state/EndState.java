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
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;
import static javafx.util.Duration.millis;
import powerbattle.ClickListener;
import powerbattle.Handler;

/**
 *
 * @author alien
 */
public class EndState extends State {

    private UIManager uiManager;
    int time;
    String hms;
    String winner;

    public EndState(Handler handler, int time, String winner) {
        super(handler);
        this.winner = winner;
        this.time = time * 100;
        hms = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(this.time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this.time)),
                TimeUnit.MILLISECONDS.toSeconds(this.time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.time)));
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);

        uiManager.addObject(new UIImageButton(425, 300, 150, 76, Assets.back, new ClickListener() {
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
        g.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 17));
        g.setColor(Color.BLACK);
        g.drawString("Congratulations to " + winner + "! Your game time is " + hms, 300, 200);
        uiManager.render(g);
    }

}
