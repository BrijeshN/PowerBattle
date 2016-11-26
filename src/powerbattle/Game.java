// Main class of the game that holds everything
// Start, run and close the game
package powerbattle;

import state.GameState;
import state.MenuState;
import state.State;
import graphics.Assets;
import display.Display;
import graphics.ImageLoader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import userInput.KeyManager;

/**
 *
 * @author Brijesh
 */
public class Game implements Runnable {

    // Instance of the Display class
    private Display display;

    // Global parameter called width, height , and title
    public int width, height;
    public String title;

    //Create new thread called thread
    private Thread thread;

    private boolean running = false;

    // BuffereStrategy Tells the computer a way to draw things on the screen
    private BufferStrategy bs;
    private Graphics g;

    // States
    private State gameState;
    private State menuState;

    // User Input
    private KeyManager keyManager;

    private BufferedImage test;
//    private SpriteSheet sheet;
    // Game constructor, pass title, width and height since Diplay takes these three parameter
    public Game(String title, int width, int height) {

        //Set width, height, and titile
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();

    }

    // Run once, initilize graphics for the game.
    // Contains game loop, and updates and draw everything
    public void init() {

        //Initilize the display, so that it runs on the thread
        // Create display  on the screen
        display = new Display(title, width, height);
        // Getting the frame and adding key listener
        display.getFrame().addKeyListener(keyManager);

        // loads this image
         test = ImageLoader.loadImage("/BG.png");
        Assets.init();

        gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(gameState);
    }
//int x = 0;
    // Updates everything for the game

    private void update() {
        keyManager.update();
        if (State.getState() != null) {

            // update method from the State Class
            State.getState().update();
        }

    }

    // Draw things to screen
    private void render() {

        // BuffereStrategy Tells the computer a way to draw things on the screen
        bs = display.getCanvas().getBufferStrategy();

        // if you are running for the first time
        // if canvas doesn't have a canvas
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        // Draw to canvas
        g = bs.getDrawGraphics();

        
        // Before we draw clear the screen
        g.clearRect(0, 0, width, height);
        
        g.drawImage(test, 0, 0, null);

        // End Drawing
        if (State.getState() != null) {

            // update method from the State Class
            State.getState().render(g);
        }

        // Tell JAVA that we are done drawing
        bs.show();
        // Make sure graphics object finish/done properly
        g.dispose();

    }

    // Always need this method when you implement Runnable
    public void run() {

        // Call the method called init, will be called only once
        init();

        // update and render 60 times every second
        int fps = 60;

        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int updates = 0;

        // Run when running is set to true
        while (running) {
            // current time of the computer
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            // if delta is greter than 1 update and render to mantain fps of 60
            if (delta >= 1) {
                update();
                render();
                updates++;
                delta--;
            }

            // if timer is runnig for more than 1 sec
            if (timer >= 1000000000) {
                System.out.println("Updates and Frames: " + updates);
                updates = 0;
                timer = 0;
            }

        }

        // To stop the thread
        stop();

    }
    
    // return keyManager object so other classes can use it
    public KeyManager getKeyManager(){
        
        return keyManager;
        
    }

    // Start new thread
    public synchronized void start() {

        // if running is already set to true, don't do anything
        if (running) {
            return;
        }
        //Set runni is true
        running = true;
        // Initilize the thread that takes game class
        thread = new Thread(this);
        // Calls run method
        thread.start();

    }

    // Stop new thread / Close down thread properly
    public synchronized void stop() {

        //if program is already is stop, don't do anything
        if (!running) {
            return;
        }

        // Set running to false
        running = false;
        // thread.join needs to be surrounded by try-catch statement
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}