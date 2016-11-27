/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Brijesh
 */
public class KeyManager implements KeyListener {

    private boolean[] keys;
    public boolean up, down, left, right, attack, shoot;

    public KeyManager() {

        keys = new boolean[256];

    }

    public void update() {

        up = keys[KeyEvent.VK_W];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        attack = keys[KeyEvent.VK_J];
        shoot = keys[KeyEvent.VK_K];

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;

    }

}
