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
    public boolean up, down, left, right, attack, magicalShoot, normalShoot;
    public boolean cheatMode, cheatModeOff, restart, menu;
    public boolean pup, pdown, pleft, pright, pattack, pmagicalShoot, pnormalShoot;

    public KeyManager() {

        keys = new boolean[1000];

    }

    public void update() {

        menu = keys[KeyEvent.VK_ESCAPE];

        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        attack = keys[KeyEvent.VK_F];
        magicalShoot = keys[KeyEvent.VK_H];
        normalShoot = keys[KeyEvent.VK_G];

        pup = keys[KeyEvent.VK_UP];
        pdown = keys[KeyEvent.VK_DOWN];
        pleft = keys[KeyEvent.VK_LEFT];
        pright = keys[KeyEvent.VK_RIGHT];
        pattack = keys[KeyEvent.VK_L];
        pmagicalShoot = keys[KeyEvent.VK_QUOTE];
        pnormalShoot = keys[KeyEvent.VK_SEMICOLON];

        restart = keys[KeyEvent.VK_R];
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
