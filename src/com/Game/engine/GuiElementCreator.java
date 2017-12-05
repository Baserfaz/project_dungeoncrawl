package com.Game.engine;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.Game.enumerations.GUIElementType;
import com.Game.enumerations.GuiSpriteType;
import com.Game.utilities.Coordinate;

public class GuiElementCreator {

    // create all gui elements here
    // and cache them in a list.
    public static List<GuiElement> createGuiElements() {
        List<GuiElement> elements = new ArrayList<GuiElement>();
        
        // bottom background
        Rectangle bottomBackground = new Rectangle(0, 5 * 32 * Game.SCREEN_MULTIPLIER, 14 * 32 * Game.SCREEN_MULTIPLIER, 3 * 32 * Game.SCREEN_MULTIPLIER);
        elements.add(new GuiElement("bottomBackground", false, true, false, GUIElementType.BACKGROUND, bottomBackground, new Color(75, 86, 99, 255)));
        
        // sidebar background
        Rectangle sidebarBackground = new Rectangle(0, 0, 141 * Game.SCREEN_MULTIPLIER, 8 * 32 * Game.SCREEN_MULTIPLIER);
        elements.add(new GuiElement("sidebarBackground", false, true, false, GUIElementType.BACKGROUND, sidebarBackground, new Color(85, 98, 112, 255)));
        
        // cache sprite map
        Map<GuiSpriteType, BufferedImage> sprites = Game.instance.getGuiRenderer().guiSprites;
        
        // don't render these at start
        List<GuiSpriteType> notWantedToRender = Arrays.asList(GuiSpriteType.EMPTY_GEM, GuiSpriteType.FULL_HP_GEM, 
                GuiSpriteType.FULL_MANA_GEM, GuiSpriteType.FULL_STAMINA_GEM, GuiSpriteType.HALF_HP_GEM,
                GuiSpriteType.HALF_MANA_GEM, GuiSpriteType.HALF_STAMINA_GEM, GuiSpriteType.LIGHT_CHARACTER, 
                GuiSpriteType.DARK_SPELLBOOK, GuiSpriteType.PRIMARY_STATS_DARK, GuiSpriteType.SECONDARY_STATS_LIGHT,
                GuiSpriteType.INV_SLOT_LEFT, GuiSpriteType.INV_SLOT_RIGHT, GuiSpriteType.STATS_SECONDARY, 
                GuiSpriteType.SPELLBOOK, GuiSpriteType.EQUIP_SLOT_BIG, GuiSpriteType.EQUIP_SLOT_SMALL,
                GuiSpriteType.DISAGREE, GuiSpriteType.AGREE, GuiSpriteType.EVENT_BAR, GuiSpriteType.ENEMY_BAR);
        
        // isClickable list
        List<GuiSpriteType> isClickableElement = Arrays.asList(GuiSpriteType.ACTION_1, GuiSpriteType.ACTION_2, GuiSpriteType.AGREE, GuiSpriteType.BACKWARD,
                GuiSpriteType.DARK_CHARACTER, GuiSpriteType.DISAGREE, GuiSpriteType.FORWARD,
                GuiSpriteType.LIGHT_SPELLBOOK, GuiSpriteType.MAP, GuiSpriteType.MENU, GuiSpriteType.PRIMARY_STATS_DARK, GuiSpriteType.PRIMARY_STATS_LIGHT,
                GuiSpriteType.REST, GuiSpriteType.SECONDARY_STATS_DARK, GuiSpriteType.SECONDARY_STATS_LIGHT, GuiSpriteType.STRAFE_LEFT, GuiSpriteType.STRAFE_RIGHT,
                GuiSpriteType.TURN_LEFT, GuiSpriteType.TURN_RIGHT);
        
        // background element
        List<GuiSpriteType> backgroundElements = Arrays.asList(GuiSpriteType.EQUIPMENT, GuiSpriteType.STATS_PRIMARY, 
                GuiSpriteType.STATS_SECONDARY, GuiSpriteType.SPELLBOOK, GuiSpriteType.INVENTORY);
        
        // equipment slots
        List<GuiSpriteType> equipmentSlotElements = Arrays.asList(GuiSpriteType.EQUIP_SLOT_BIG, GuiSpriteType.EQUIP_SLOT_SMALL);
        
        // inventory elements
        List<GuiSpriteType> inventorySlotElements = Arrays.asList(GuiSpriteType.INV_SLOT_LEFT, GuiSpriteType.INV_SLOT_RIGHT);
        
        for(Entry<GuiSpriteType, BufferedImage> obj : sprites.entrySet()) {

            boolean isClickable = false;
            boolean isVisibleAtStart = true;
            GUIElementType type = GUIElementType.OTHER;
            
            if(isClickableElement.contains(obj.getKey())) {
                isClickable = true;
                type = GUIElementType.BUTTON;
            }
            
            if(backgroundElements.contains(obj.getKey())) {
                type = GUIElementType.BACKGROUND;
            }
            
            if(inventorySlotElements.contains(obj.getKey())) {
                type = GUIElementType.INVENTORY_SLOT;
            }
            
            if(equipmentSlotElements.contains(obj.getKey())) {
                type = GUIElementType.EQUIPMENT_SLOT;
            }
            
            if(notWantedToRender.contains(obj.getKey())) {
                isVisibleAtStart = false;
            }
            
            BufferedImage img = obj.getValue();
            Coordinate pos = getPosition(obj.getKey());
            Rectangle guiElementRect = new Rectangle(pos.x, pos.y, img.getWidth(), img.getHeight());
            elements.add(new GuiElement(obj.getKey().toString(), isClickable, isVisibleAtStart, isClickable, type, guiElementRect, img));
        }
        
        // return list of elements
        return elements;
    }

    private static Coordinate getPosition(GuiSpriteType type) {
        
        // this sets the starting positions of the gui elements
        
        Coordinate coord = new Coordinate(0, 0);
        
        switch(type) {
        case ACTION_1:
            coord.x = 358;
            coord.y = 166;
            break;
        case ACTION_2:
            coord.x = 403;
            coord.y = 166;
            break;
        case BACKWARD:
            coord.x = 388;
            coord.y = 222;
            break;
        case DARK_CHARACTER:
            coord.x = 0;
            coord.y = 0;
            break;
        case LIGHT_CHARACTER:
            coord.x = 0;
            coord.y = 0;
            break;
        case DARK_SPELLBOOK:
            coord.x = 70;
            coord.y = 0;
            break;
        case LIGHT_SPELLBOOK:
            coord.x = 70;
            coord.y = 0;
            break;
        case INVENTORY:
            coord.x = 147;
            coord.y = 165;
            break;
        case EQUIPMENT:
            coord.x = 0;
            coord.y = 12;
            break;
        case FORWARD:
            coord.x = 388;
            coord.y = 192;
            break;
        case MAP:
            coord.x = 49;
            coord.y = 226;
            break;
        case MENU:
            coord.x = 5;
            coord.y = 226;
            break;
        case PRIMARY_STATS_DARK:
            coord.x = 4;
            coord.y = 129;
            break;
        case PRIMARY_STATS_LIGHT:
            coord.x = 4;
            coord.y = 129;
            break;
        case REST:
            coord.x = 93;
            coord.y = 226;
            break;
        case SECONDARY_STATS_DARK:
            coord.x = 46;
            coord.y = 129;
            break;
        case SECONDARY_STATS_LIGHT:
            coord.x = 46;
            coord.y = 129;
            break;
        case SPELLBOOK:
            coord.x = 0;
            coord.y = 12;
            break;
        case STATS_PRIMARY:
            coord.x = 4;
            coord.y = 143;
            break;
        case STATS_SECONDARY:
            coord.x = 4;
            coord.y = 143;
            break;
        case STRAFE_LEFT:
            coord.x = 358;
            coord.y = 222;
            break;
        case STRAFE_RIGHT:
            coord.x = 418;
            coord.y = 222;
            break;
        case TURN_LEFT:
            coord.x = 358;
            coord.y = 192;
            break;
        case TURN_RIGHT:
            coord.x = 418;
            coord.y = 192;
            break;
        default:
            break;
        }
        
        coord.x *= Game.SCREEN_MULTIPLIER;
        coord.y *= Game.SCREEN_MULTIPLIER;
        
        return coord;
    }
    
    public static List<GuiString> createGuiStrings() {
        List<GuiString> strings = new ArrayList<GuiString>();

        // add all strings here.
        //strings.add(new GuiString("Project Dungeoncrawl, version: pre-fetus.", 10, 10, new Color(50, 50, 50, 255)));

        return strings;
    }
}
