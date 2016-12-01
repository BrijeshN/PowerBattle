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
    public boolean up, down, left, right, attack, magicalShoot, restart, normalShoot;
    public boolean cheatMode, cheatModeOff;

    public KeyManager() {

        keys = new boolean[256];

    }

    public void update() {

        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        attack = keys[KeyEvent.VK_J];
        magicalShoot = keys[KeyEvent.VK_L];
        restart = keys[KeyEvent.VK_R];
        normalShoot = keys[KeyEvent.VK_K];
        cheatMode = keys[KeyEvent.VK_C];
        cheatModeOff = keys[KeyEvent.VK_V];

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
