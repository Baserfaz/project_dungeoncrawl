package com.Game.engine;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Game.enumerations.GameState;

public class KeyInput extends KeyAdapter {

    private List<Integer> buttons = new ArrayList<Integer>();

    public KeyInput() {}

    public void keyPressed(KeyEvent e) {

        // get the pressed key 
        int key = e.getKeyCode();

        // if the key event already exists, dont do anything.
        if(buttons.contains(key)) return;

        // add pressed button to a list of buttons
        buttons.add(key);

        // -------------- HANDLE INPUTS ------------------

        if(Game.instance.getGamestate() == GameState.Menu) {
            handleKeysInMenu(e);
        } else if(Game.instance.getGamestate() == GameState.Ingame) {
            handleKeysInGame(e);
        }

        // debugging keys
        if(key == KeyEvent.VK_F1) {
            Game.drawGUIRects = !Game.drawGUIRects;
        } else if(key == KeyEvent.VK_F2) {
            Game.drawCameraRect = !Game.drawCameraRect;
        } else if(key == KeyEvent.VK_F3) {
            Game.drawItemRects = !Game.drawItemRects;
        } else if(key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

    }

    public void keyReleased(KeyEvent e) {

        // get the pressed key 
        int key = e.getKeyCode();

        // remove all occurrences of key code from buttons
        buttons.removeAll(Arrays.asList(key));
    }

    private void handleKeysInGame(KeyEvent e) {
        int key = e.getKeyCode();
    }

    private void handleKeysInMenu(KeyEvent e) {
        int key = e.getKeyCode();   
    }
}
