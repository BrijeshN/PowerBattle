/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Brijesh
 */
public class Display {

    // Create a JFrame called frame to display it on the screen
    private JFrame frame;
    
    // Draw images to canvas then add it to JFame to we are able to see it on the screen
    private Canvas canvas;

    // Title of the frame
    private String title;
    // width and the height of the frame
    private int width, height;

    //constructor that takes title, width and height parameter
    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();
    }

    private void createDisplay() {

        // Set title of the frame
        frame = new JFrame(title);
        
        //Set width and height of the frame
        frame.setSize(width, height);
        
        // Stop/Close running the program after user click on the CLOSE
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Fix window size, user can't change the size
        frame.setResizable(false);
        
        // Creates windown in the middle of the computer screen
        frame.setLocationRelativeTo(null);
        
        // To make frame visible, so that we can see it
        frame.setVisible(true);
        
        // create new canvas
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        
        //Add it to JFrame
        frame.add(canvas);
        // Resize the frame so we are able to see canvas fully
        frame.pack();
        
    }
    
    // Since Canvas is private, we need getter to access Canvas
    // So that we can access canvas from other classes
    public Canvas getCanvas(){
        return canvas;
    }

    // Access frame outside of the display class
    public JFrame getFrame(){
        return frame;
    }
    
}
