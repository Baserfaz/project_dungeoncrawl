package com.Game.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Game.enumerations.GuiSpriteType;
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
                if(element.isEnabled() || element.isEnableColorManipulation() == false) {
                    if(element.getImg() != null) {
                        RenderUtils.FillRectWithImage(element.getRect(), g2d, element.getImg());
                    } else {
                        RenderUtils.renderRect(element.getRect(), true, false, null, element.getColor(), g2d);   
                    }
                } else {
                    if(element.getImg() != null) {
                        RenderUtils.FillRectWithImage(element.getRect(), g2d, Util.tintWithColor(element.getImg(), Color.black));
                    } else {
                        RenderUtils.renderRect(element.getRect(), true, false, null, element.getColor().darker(), g2d);  
                    }
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
            case EMPTY_GEM:
                img = creator.CreateCustomSizeSprite(0, 9 * 32, 7, 8, Game.SCREEN_MULTIPLIER);
                break;
            case FULL_HP_GEM:
                img = creator.CreateCustomSizeSprite(7, 9 * 32, 7, 8, Game.SCREEN_MULTIPLIER);
                break;
            case HALF_HP_GEM:
                img = creator.CreateCustomSizeSprite(2 * 7, 9 * 32, 7, 8, Game.SCREEN_MULTIPLIER);
                break;
            case FULL_STAMINA_GEM:
                img = creator.CreateCustomSizeSprite(3 * 7, 9 * 32, 7, 8, Game.SCREEN_MULTIPLIER);
                break;
            case HALF_STAMINA_GEM:
                img = creator.CreateCustomSizeSprite(4 * 7, 9 * 32, 7, 8, Game.SCREEN_MULTIPLIER);
                break;
            case FULL_MANA_GEM:
                img = creator.CreateCustomSizeSprite(5 * 7, 9 * 32, 7, 8, Game.SCREEN_MULTIPLIER);
                break;
            case HALF_MANA_GEM:
                img = creator.CreateCustomSizeSprite(6 * 7, 9 * 32, 7, 8, Game.SCREEN_MULTIPLIER);
                break;
                
            case AGREE:
                img = creator.CreateCustomSizeSprite(0, 9 * 32 + 8, 11, 12, Game.SCREEN_MULTIPLIER);
                break;
            case DISAGREE:
                img = creator.CreateCustomSizeSprite(11, 9 * 32 + 8, 11, 12, Game.SCREEN_MULTIPLIER);
                break;
                
            case INV_SLOT_LEFT:
                img = creator.CreateCustomSizeSprite(0, 10 * 32, 32, 34, Game.SCREEN_MULTIPLIER);
                break;
            case INV_SLOT_RIGHT:
                img = creator.CreateCustomSizeSprite(32, 10 * 32, 34, 34, Game.SCREEN_MULTIPLIER);
                break;
                
            case EQUIP_SLOT_BIG:
                img = creator.CreateCustomSizeSprite(66, 318, 32, 33, Game.SCREEN_MULTIPLIER);
                break;
            case EQUIP_SLOT_SMALL:
                img = creator.CreateCustomSizeSprite(3 * 32 + 2, 10 * 32, 16, 17, Game.SCREEN_MULTIPLIER);
                break;
                
            case REST:
                img = creator.CreateCustomSizeSprite(0, 352, 40, 21, Game.SCREEN_MULTIPLIER);
                break;
            case MENU:
                img = creator.CreateCustomSizeSprite(0, 373, 40, 21, Game.SCREEN_MULTIPLIER);
                break;
            case MAP:
                img = creator.CreateCustomSizeSprite(0, 394, 40, 21, Game.SCREEN_MULTIPLIER);
                break;
                
            case ACTION_1:
                img = creator.CreateCustomSizeSprite(0, 415, 40, 21, Game.SCREEN_MULTIPLIER);
                break;
            case ACTION_2:
                img = creator.CreateCustomSizeSprite(0, 436, 40, 21, Game.SCREEN_MULTIPLIER);
                break;
                
            case TURN_LEFT:
                img = creator.CreateCustomSizeSprite(40, 352, 25, 27, Game.SCREEN_MULTIPLIER);
                break;
            case FORWARD:
                img = creator.CreateCustomSizeSprite(40, 379, 25, 27, Game.SCREEN_MULTIPLIER);
                break;
            case TURN_RIGHT:
                img = creator.CreateCustomSizeSprite(40, 406, 25, 27, Game.SCREEN_MULTIPLIER);
                
                break;
            case STRAFE_LEFT:
                img = creator.CreateCustomSizeSprite(65, 352, 25, 27, Game.SCREEN_MULTIPLIER);
                break;
            case BACKWARD:
                img = creator.CreateCustomSizeSprite(65, 379, 25, 27, Game.SCREEN_MULTIPLIER);
                break;
            case STRAFE_RIGHT:
                img = creator.CreateCustomSizeSprite(65, 406, 25, 27, Game.SCREEN_MULTIPLIER);
                
                break;
                
            case EVENT_BAR:
                img = creator.CreateCustomSizeSprite(0, 480, 173, 16, Game.SCREEN_MULTIPLIER);
                break;
            case ENEMY_BAR:
                img = creator.CreateCustomSizeSprite(0, 496, 173, 16, Game.SCREEN_MULTIPLIER);
                break;
                
            case DARK_CHARACTER:
                img = creator.CreateCustomSizeSprite(0, 512, 70, 12, Game.SCREEN_MULTIPLIER);
                break;
            case LIGHT_CHARACTER:
                img = creator.CreateCustomSizeSprite(0, 524, 70, 12, Game.SCREEN_MULTIPLIER);
                break;
            case LIGHT_SPELLBOOK:
                
                img = creator.CreateCustomSizeSprite(70, 512, 71, 12, Game.SCREEN_MULTIPLIER);
                break;
            case DARK_SPELLBOOK:
                img = creator.CreateCustomSizeSprite(70, 524, 71, 12, Game.SCREEN_MULTIPLIER);
                break;
                
            case PRIMARY_STATS_LIGHT:
                img = creator.CreateCustomSizeSprite(0, 544, 42, 14, Game.SCREEN_MULTIPLIER);
                break;
            case SECONDARY_STATS_DARK:
                img = creator.CreateCustomSizeSprite(42, 544, 70, 14, Game.SCREEN_MULTIPLIER);
                break;
                
            case PRIMARY_STATS_DARK:
                img = creator.CreateCustomSizeSprite(160, 544, 42, 14, Game.SCREEN_MULTIPLIER);
                break;
            case SECONDARY_STATS_LIGHT:
                img = creator.CreateCustomSizeSprite(202, 544, 70, 14, Game.SCREEN_MULTIPLIER);
                break;
                
            case STATS_PRIMARY:
                img = creator.CreateCustomSizeSprite(0, 560, 131, 68, Game.SCREEN_MULTIPLIER);
                break;
            case STATS_SECONDARY:
                img = creator.CreateCustomSizeSprite(160, 560, 131, 68, Game.SCREEN_MULTIPLIER);
                break;
                
            case EQUIPMENT:
                img = creator.CreateCustomSizeSprite(0, 640, 141, 110, Game.SCREEN_MULTIPLIER);
                break;
            case SPELLBOOK:
                img = creator.CreateCustomSizeSprite(160, 640, 141, 110, Game.SCREEN_MULTIPLIER);
                break;
            case INVENTORY:
                img = creator.CreateCustomSizeSprite(320, 544, 198, 83, Game.SCREEN_MULTIPLIER);
                break;
                
            default:
                System.out.println("No Gui-sprite of type " + type + " was found.");
                continue;
            }

            // new dictionary element
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
