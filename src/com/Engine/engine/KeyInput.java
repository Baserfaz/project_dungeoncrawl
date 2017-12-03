package com.Engine.engine;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Engine.enumerations.GameState;

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
            if(Game.drawGUIRects) Game.drawGUIRects = false;
            else Game.drawGUIRects = true;
        } else if(key == KeyEvent.VK_F2) {
            if(Game.drawCameraRect) Game.drawCameraRect = false;
            else Game.drawCameraRect = true;
        } else if(key == KeyEvent.VK_F3) {
            if(Game.drawItemRects) Game.drawItemRects = false;
            else Game.drawItemRects = true;
        } else if(key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if(key == KeyEvent.VK_F4 ) {
            if(Game.renderMinimap) Game.renderMinimap = false;
            else Game.renderMinimap = true;
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
