package com.Game.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.Game.data.Energy;
import com.Game.data.GuiElement;
import com.Game.data.Health;
import com.Game.data.Mana;
import com.Game.enumerations.GuiSpriteType;
import com.Game.gameobjects.Actor;
import com.Game.gameobjects.Item;
import com.Game.utilities.Coordinate;
import com.Game.utilities.DynamicGuiManager;
import com.Game.utilities.RenderUtils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class GuiRenderer {

    public void render(Graphics g) {

        // refs
        Graphics2D g2d = (Graphics2D) g;
        List<GuiElement> elements = Game.instance.getGuiElements();

        // render all GUI-elements
        for(GuiElement element : elements) {

            if(element.isVisible()) {
                if(element.isEnabled() || element.isEnableColorManipulation() == false) {
                    if(element.getImg() != null) {
                        RenderUtils.FillRectWithImage(element.getRect(), g2d, element.getImg());
                    } else {
                        RenderUtils.renderRect(element.getRect(), true, false, null, element.getColor(), g2d);   
                    }
                } else {
                    if(element.getImg() != null) {
                        RenderUtils.FillRectWithImage(element.getRect(), g2d,
                                RenderUtils.tintWithColor(element.getImg(), Color.black));
                    } else {
                        RenderUtils.renderRect(element.getRect(), true, false,
                                null, element.getColor().darker(), g2d);  
                    }
                }
            }

            if(Game.drawGUIRects) {
                Rectangle r = element.getRect();
                g.setColor(Game.GUIDebugRectColor);
                g.drawRect(r.x, r.y, r.width, r.height);
            }
        }

        // render hp, mana and energy gems
        // and also equipment and inventory slots.
        renderStats(g);
        renderEquipmentSlots(g);
        renderInventorySlots(g);
    }   

    public void renderHoverText(Graphics g) {
        
        Item hoverItem = Game.instance.getDynamicGuiManager().getMouseHoverItem();
        if(hoverItem == null) return;
        Point mousePos = Game.instance.getMousePos();
        
        int x = mousePos.x + 32 + 8;
        int y = mousePos.y + 32 + 8;
        
        this.renderString(hoverItem.getInfo(), x, y, Color.black, g);
    }
    
    public void renderMinimap(Graphics g) {
        if(Game.renderMinimap) {
            Game.instance.getMinimap().render(g);
        }
    }
    
    private void renderInventorySlots(Graphics g) {
        for(GuiElement element : Game.instance.getInventorySlots()) {
            RenderUtils.FillRectWithImage(element.getRect(), g, element.getImg());
            
            if(Game.drawGUIRects) {
                Rectangle r = element.getRect();
                g.setColor(Game.GUIDebugRectColor);
                g.drawRect(r.x, r.y, r.width, r.height);
            }   
        }
    }
    
    private void renderEquipmentSlots(Graphics g) {
        if(Game.renderEquipmentSlots) {
            for(GuiElement element : Game.instance.getEquipmentSlots()) {
                RenderUtils.FillRectWithImage(element.getRect(), g, element.getImg());
                
                if(Game.drawGUIRects) {
                    Rectangle r = element.getRect();
                    g.setColor(Game.GUIDebugRectColor);
                    g.drawRect(r.x, r.y, r.width, r.height);
                }
            }
        }
    }
    
    private void renderStats(Graphics g) {
        if(Game.renderPrimaryStats) this.renderPrimaryStats(g);
        else this.renderSecondaryStats(g);
    }

    private void renderSecondaryStats(Graphics g) {}
    
    private void renderPrimaryStats(Graphics g) {
        // cache player instance
        Actor player = Game.instance.getActorManager().getPlayerInstance();
        if(player == null) return;
        
        Health hp = player.getHP();
        Energy energy = player.getEnergy();
        Mana mp = player.getMP();
        
        // load player data
        int maxHP = hp.getMaxHP();
        int currentHP = hp.getCurrentHP();
        
        int maxMP = mp.getMaxMana();
        int currentMP = mp.getCurrentMana();
        
        int maxEnergy = energy.getMaxEnergy();
        int currentEnergy = energy.getCurrentEnergy();
        
        // hardcoded start positions for the gems
        int posx = 57 * Game.SCREEN_MULTIPLIER;
        int hpGemPosy = 177 * Game.SCREEN_MULTIPLIER;
        int energyGemPosy = 187 * Game.SCREEN_MULTIPLIER;
        int manaGemPosy = 197 * Game.SCREEN_MULTIPLIER;
        
        int gemWidth = 7 * Game.SCREEN_MULTIPLIER; 
        int margin = 1 * Game.SCREEN_MULTIPLIER;
        
        // cache sprite information
        Map<GuiSpriteType, BufferedImage> guiSprites = Game.instance.getGuiSprites();
        
        // create HP gems
        for(int i = 0; i < maxHP; i++) {
            
            // calculate new position for each gem
            Coordinate pos = new Coordinate(posx + gemWidth * i + margin * i, hpGemPosy);
            
            if(currentHP > i) {
                // create full gem
                RenderUtils.RenderSprite(guiSprites.get(GuiSpriteType.FULL_HP_GEM), pos, g);
            } else {
                // create empty gem
                RenderUtils.RenderSprite(guiSprites.get(GuiSpriteType.EMPTY_GEM), pos, g);
            }
        }
        
        // create energy gems
        for(int i = 0; i < maxEnergy; i++) {
            
            // calculate new position for each gem
            Coordinate pos = new Coordinate(posx + gemWidth * i + margin * i, energyGemPosy);
            
            if(currentEnergy > i) {
                // create full gem
                RenderUtils.RenderSprite(guiSprites.get(GuiSpriteType.FULL_ENERGY_GEM), pos, g);
            } else {
                // create empty gem
                RenderUtils.RenderSprite(guiSprites.get(GuiSpriteType.EMPTY_GEM), pos, g);
            }
        }
        
        // create mana gems
        for(int i = 0; i < maxMP; i++) {
            
            // calculate new position for each gem
            Coordinate pos = new Coordinate(posx + gemWidth * i + margin * i, manaGemPosy);
            
            if(currentMP > i) {
                // create full gem
                RenderUtils.RenderSprite(guiSprites.get(GuiSpriteType.FULL_MANA_GEM), pos, g);
            } else {
                // create empty gem
                RenderUtils.RenderSprite(guiSprites.get(GuiSpriteType.EMPTY_GEM), pos, g);
            }
        }
        
        // cache actor manager instance
        DynamicGuiManager mngr = Game.instance.getDynamicGuiManager();
        
        // draw name, level and class
        mngr.getPlayerName().render(g);
        mngr.getPlayerClass().render(g);
        mngr.getPlayerLevel().render(g);
    }
    
    public List<BufferedImage> createText(String txt) {
        
        // cache alphabets
        Map<Character, BufferedImage> alphabets = Game.instance.getAlphabets();
        
        List<BufferedImage> imgs = new ArrayList<BufferedImage>();
        txt = txt.toLowerCase();
        for(char c : txt.toCharArray()) {
            if(alphabets.containsKey(c)) {
                imgs.add(alphabets.get(c));
            } else {
                System.out.println("System doesn't support character: " + c);
                continue;
            }
        }
        return imgs;
    }
    
    public void renderString(String msg, int x, int y, Color color, Graphics g) {
         Font font = Game.instance.getCustomFont().deriveFont(Font.PLAIN, 40F);
         g.setColor(Color.red);
         g.setFont(font);
         g.drawString(msg, x, y);
    }
    
    public void renderText(Graphics g, String txt, int x, int y, int margin, Color color) {
        
        // cache alphabets
        Map<Character, BufferedImage> alphabets = Game.instance.getAlphabets();
        
        int currentMargin = 0;
        txt = txt.toLowerCase();
        for(char c : txt.toCharArray()) {
            if(alphabets.containsKey(c)) {
                BufferedImage img = alphabets.get(c);
                img = RenderUtils.changeImageColor(img, color);
                g.drawImage(img, x + currentMargin, y, img.getWidth(), img.getHeight(), null);
                currentMargin += margin + img.getWidth();
            } else {
                System.out.println("System doesn't support character: " + c);
                return;
            }
        }
    }
}
