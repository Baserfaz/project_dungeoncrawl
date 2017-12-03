package com.Game.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Game.enumerations.GuiSpriteType;
import com.Game.enumerations.TileType;
import com.Game.utilities.SpriteCreator;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class GUIRenderer {

    public Map<Character, BufferedImage> alphabets = new HashMap<Character, BufferedImage>(); 
    public Map<GuiSpriteType, BufferedImage> guiSprites = new HashMap<GuiSpriteType, BufferedImage>();
    
    public void render(Graphics g) {
        
        // refs
        Graphics2D g2d = (Graphics2D) g;
        List<GuiElement> elements = Game.instance.getGuiElements();
        List<GuiString> strings = Game.instance.getGuiStrings();
        
        // render all GUI-elements
        for(GuiElement element : elements) {
            
            if(element.isVisible()) {
                if(element.isEnabled()) {
                    RenderUtils.FillRectWithImage(element.getRect(), g2d, element.getImg());
                } else {
                    RenderUtils.FillRectWithImage(element.getRect(), g2d, Util.tintWithColor(element.getImg(), Color.black));
                }
            }
            
            if(Game.drawGUIRects) {
                Rectangle r = element.getRect();
                g.setColor(Game.GUIDebugRectColor);
                g.drawRect(r.x, r.y, r.width, r.height);
            }
        }
        
        // render text after the GUIElements.
        for(GuiString string : strings) {
            string.tick();
            string.render(g);
        }
        
        // render minimap
        if(Game.renderMinimap) {
            Game.instance.getMinimap().render(g);
        }
    }   
    
    public void loadGuiSprites() {
        
        // cache sprite creator
        SpriteCreator creator = Game.instance.getSpriteCreator();
        
        for(GuiSpriteType type : GuiSpriteType.values()) {
            
            BufferedImage img = null;
            
            switch(type) {
                
                default:
                    System.out.println("No Gui-sprite of type " + type + " was found.");
            }
            
            this.guiSprites.put(type, img);
        }
    }
    
    public void initiateAlphabets() {
        
        String alphabetOrder = "abcdefghijklmnopqrstuvwxyz 1234567890_,.?()-+!=*[]:;\\/'";
        System.out.println("Supported characters: " + alphabetOrder);
        
        int spriteWidth = 6;
        int spriteHeight = 8;
        int marginx = 1;
        int marginy = 2;
        
        int startx = 6 * 32 + 2;
        int starty = 9 * 32 + 2;
        
        int cachedStartxPos = startx;
        
        SpriteCreator sc = Game.instance.getSpriteCreator();
        
        for(char c : alphabetOrder.toCharArray()) {
            
            BufferedImage img = sc.CreateCustomSizeSprite(startx, starty, spriteWidth, spriteHeight, Game.SCREEN_MULTIPLIER);
            alphabets.put(c, img);
            startx += spriteWidth + marginx;
            
            // space is the last character on the first row
            if(Character.isWhitespace(c)) {
                starty += spriteHeight + marginy;
                startx = cachedStartxPos;
            }
        }
    }
    
    public void renderText(Graphics g, String txt, int x, int y, int margin, Color color) {
        int currentMargin = 0;
        txt = txt.toLowerCase();
        for(char c : txt.toCharArray()) {
            if(this.alphabets.containsKey(c)) {
                BufferedImage img = this.alphabets.get(c);
                
                img = Util.changeImageColor(img, color);
                
                g.drawImage(img, x + currentMargin, y, img.getWidth(), img.getHeight(), null);
                currentMargin += margin + img.getWidth();
            } else {
                System.out.println("System doesn't support character: " + c);
                return;
            }
        }
    }
}
