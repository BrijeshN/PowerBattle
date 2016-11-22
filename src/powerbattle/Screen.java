package powerbattle;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.*;
import javax.swing.*;

public class Screen extends JPanel implements ActionListener{
    
    Player p;
    //Contains background image
    Image bg;
    Timer time;
    
    public Screen() {
    
        p = new Player();
        addKeyListener(new AL());
        //When you press keys it allows you to move left and right
        setFocusable(true);
        ImageIcon i = new ImageIcon("res/BG.png");
        // Store background image in img
        bg = i.getImage();
        // Every 5ms it updates the image, it makes our character looks like its moving
        time =  new Timer(5, this);
        time.start();
        
}
    
    public void actionPerformed(ActionEvent e){
        p.move();
        repaint();
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        // to display to the frame
        if((p.getX() - 70) % 2000 == 0){
            p.nx = 0;
        }
        if ((p.getX() - 1070)% 2000 == 0){
            p.nx2 = 0;
        }
        g2d.drawImage(bg, 985 - p.nx2, 0, null);
        //System.out.println(p.getX());
        if(p.getX() >= 70){
            g2d.drawImage(bg, 985 - p.nx, 0, null);
        }
        g2d.drawImage(p.getImage(), p.left ,p.getY(), null);
    }
    
    public class AL extends KeyAdapter{
        
        //Stop player movement
        public void keyReleased(KeyEvent e){
            p.keyReleased(e);
        }
        
        //Move player
        public void keyPressed(KeyEvent e){
            p.keyPressed(e);
        }
        
    }
    
}
