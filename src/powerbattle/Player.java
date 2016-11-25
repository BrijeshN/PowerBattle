package powerbattle;

import java.awt.Image;
import java.awt.event.*;
import javax.swing.ImageIcon;

public class Player {
    //dx, dy is change in x and y
    int x, dx, y, dy, nx,nx2, left;
    // Contains player image
    Image player;
    ImageIcon i = new ImageIcon("res/Character/Idle (1).png");
    ImageIcon l = new ImageIcon("res/CharacterLeft/Idle (1).png");
    public Player(){
        
        //Store player image in player
        player = i.getImage();
        
        left = 150;
        x = 75;
        nx2 = 985;
        nx = 0;
        y = 350;
    }
    
    public void move(){
        if(dx != -1){
            if(left + dx <= 150){
                left = left + dx;
            } else {
            x = x + dx;
            nx2 = nx2 +dx;
            nx = nx + dx;
            }
        } else {
            if(left + dx > 0){
                left = left + dx;
            }
        }
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public Image getImage(){
        return player;
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        //int movementSpeed = 2;
        if(key == KeyEvent.VK_LEFT){
            dx = -1;
            player = l.getImage();
        }
            
        if(key == KeyEvent.VK_RIGHT){
          
            dx = 1;
            player = i.getImage();
        }
    }
    
    // If player is not moving
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_LEFT){
            dx = 0;
        }
            
        if(key == KeyEvent.VK_RIGHT){
            dx = 0;
        }
    }
}
