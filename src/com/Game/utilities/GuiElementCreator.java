package com.Game.utilities;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.Game.data.GuiElement;
import com.Game.data.GuiEquipmentSlot;
import com.Game.data.GuiInventorySlot;
import com.Game.engine.Game;
import com.Game.engine.GuiString;
import com.Game.enumerations.EquipmentSlot;
import com.Game.enumerations.GuiElementType;
import com.Game.enumerations.GuiSpriteType;
import com.Game.gameobjects.Actor;

public class GuiElementCreator {

    // create all gui elements here
    // and cache them in a list.
    public static List<GuiElement> createGuiElements() {
        List<GuiElement> elements = new ArrayList<GuiElement>();
        
        // bottom background
        Rectangle bottomBackground = new Rectangle(0, 5 * 32 * Game.SCREEN_MULTIPLIER, 14 * 32 * Game.SCREEN_MULTIPLIER, 3 * 32 * Game.SCREEN_MULTIPLIER);
        elements.add(new GuiElement("bottomBackground", false, true, false, GuiElementType.BACKGROUND, bottomBackground, new Color(75, 86, 99, 255)));
        
        // sidebar background
        Rectangle sidebarBackground = new Rectangle(0, 0, 141 * Game.SCREEN_MULTIPLIER, 8 * 32 * Game.SCREEN_MULTIPLIER);
        elements.add(new GuiElement("sidebarBackground", false, true, false, GuiElementType.BACKGROUND, sidebarBackground, new Color(85, 98, 112, 255)));
        
        // cache sprite map
        Map<GuiSpriteType, BufferedImage> sprites = Game.instance.getGuiSprites();
        
        // these are created at run time and not here.
        List<GuiSpriteType> dontCreateAtAll = Arrays.asList(GuiSpriteType.EMPTY_GEM, GuiSpriteType.FULL_HP_GEM, 
                GuiSpriteType.FULL_MANA_GEM, GuiSpriteType.FULL_ENERGY_GEM, GuiSpriteType.HALF_HP_GEM,
                GuiSpriteType.HALF_MANA_GEM, GuiSpriteType.HALF_ENERGY_GEM, GuiSpriteType.DISAGREE, GuiSpriteType.AGREE,
                GuiSpriteType.EVENT_BAR, GuiSpriteType.ENEMY_BAR, GuiSpriteType.EQUIP_SLOT_BIG, GuiSpriteType.EQUIP_SLOT_SMALL,
                GuiSpriteType.INV_SLOT_1, GuiSpriteType.INV_SLOT_2, GuiSpriteType.INV_SLOT_3, GuiSpriteType.INV_SLOT_4);
        
        // don't render these at start
        List<GuiSpriteType> notWantedToRender = Arrays.asList(GuiSpriteType.LIGHT_CHARACTER, 
                GuiSpriteType.DARK_SPELLBOOK, GuiSpriteType.PRIMARY_STATS_DARK, GuiSpriteType.SECONDARY_STATS_LIGHT,
                GuiSpriteType.STATS_SECONDARY, GuiSpriteType.SPELLBOOK);
        
        // isClickable list
        List<GuiSpriteType> isClickableElement = Arrays.asList(GuiSpriteType.ACTION_1, GuiSpriteType.ACTION_2, 
                GuiSpriteType.AGREE, GuiSpriteType.BACKWARD, GuiSpriteType.DARK_CHARACTER, GuiSpriteType.DISAGREE,
                GuiSpriteType.FORWARD, GuiSpriteType.LIGHT_SPELLBOOK, GuiSpriteType.MAP, GuiSpriteType.MENU, 
                GuiSpriteType.PRIMARY_STATS_LIGHT, GuiSpriteType.REST, GuiSpriteType.SECONDARY_STATS_DARK, 
                GuiSpriteType.STRAFE_LEFT, GuiSpriteType.STRAFE_RIGHT, GuiSpriteType.TURN_LEFT, GuiSpriteType.TURN_RIGHT);
        
        // background element
        List<GuiSpriteType> backgroundElements = Arrays.asList(GuiSpriteType.EQUIPMENT, GuiSpriteType.STATS_PRIMARY, 
                GuiSpriteType.STATS_SECONDARY, GuiSpriteType.SPELLBOOK, GuiSpriteType.INVENTORY);

        for(Entry<GuiSpriteType, BufferedImage> obj : sprites.entrySet()) {

            if(dontCreateAtAll.contains(obj.getKey())) continue;
            
            boolean isClickable = false;
            boolean isVisibleAtStart = true;
            GuiElementType type = GuiElementType.OTHER;
            
            if(isClickableElement.contains(obj.getKey())) {
                isClickable = true;
                type = GuiElementType.BUTTON;
            }
            
            if(backgroundElements.contains(obj.getKey())) {
                type = GuiElementType.BACKGROUND;
            }
            
            if(notWantedToRender.contains(obj.getKey())) {
                isVisibleAtStart = false;
                isClickable = false;
            }
            
            BufferedImage img = obj.getValue();
            Coordinate pos = getPosition(obj.getKey());
            Rectangle guiElementRect = new Rectangle(pos.x, pos.y, img.getWidth(), img.getHeight());
            elements.add(new GuiElement(obj.getKey().toString(), isClickable, isVisibleAtStart, isClickable, type, guiElementRect, img));
        }
        
        // return list of elements
        return elements;
    }
    
    public static List<GuiElement> createInventorySlots() {
        List<GuiElement> elements = new ArrayList<GuiElement>();
        
        // positions
        int startx = 149 * Game.SCREEN_MULTIPLIER;
        int starty = 180 * Game.SCREEN_MULTIPLIER;
        
        // cache imgs
        BufferedImage slot_1 = Game.instance.getGuiSprites().get(GuiSpriteType.INV_SLOT_1);
        BufferedImage slot_2 = Game.instance.getGuiSprites().get(GuiSpriteType.INV_SLOT_2);
        BufferedImage slot_3 = Game.instance.getGuiSprites().get(GuiSpriteType.INV_SLOT_3);
        BufferedImage slot_4 = Game.instance.getGuiSprites().get(GuiSpriteType.INV_SLOT_4);
        
        for(int i = 0; i < 12; i++) {
            
            // decide which image we use
            BufferedImage img = null;
            
            if(i < 5) img = slot_1;
            else if(i == 5) img = slot_2;
            else if(i > 5 && i < 11) img = slot_3;
            else img = slot_4;
            
            // calculate the position of each image
            int x = 0;
            int y = 0;
            
            if(i < 5) {
                x = startx + i * img.getWidth();
                y = starty;
            } else if(i == 5) {
                x = startx + i * 32 * Game.SCREEN_MULTIPLIER;
                y = starty;
            } else if(i > 5 && i < 11) {
                x = startx + (i - 6) * img.getWidth();
                y = starty + 32 * Game.SCREEN_MULTIPLIER;
            } else {
                x = startx + 5 * 32 * Game.SCREEN_MULTIPLIER;
                y = starty + 32 * Game.SCREEN_MULTIPLIER;
            }
            
            // create rectangle
            Rectangle rect = new Rectangle(x, y, img.getWidth(), img.getHeight());
            
            // create element
            elements.add(new GuiInventorySlot("inv_slot_" + i, true, true, false, GuiElementType.INVENTORY_SLOT, rect, img));
        }
        
        return elements;
    }
    
    public static GuiString createPlayerName() {
        Actor player = Game.instance.getActorManager().getPlayerInstance();
        if(player == null) return null;
        return new GuiString(player.getName(), 15 * Game.SCREEN_MULTIPLIER, 152 * Game.SCREEN_MULTIPLIER, Color.white);
    }
    
    public static GuiString createGuiString(String name, int x, int y, Color color) {
        return new GuiString(name, x * Game.SCREEN_MULTIPLIER, y * Game.SCREEN_MULTIPLIER, color);
    }
    
    public static List<GuiElement> createEquipmentSlots() {
        List<GuiElement> elements = new ArrayList<GuiElement>();
        
        // cache sprite map
        Map<GuiSpriteType, BufferedImage> sprites = Game.instance.getGuiSprites();
        
        // get the sprites
        BufferedImage bigSlotImg = sprites.get(GuiSpriteType.EQUIP_SLOT_BIG);
        BufferedImage smallSlotImg = sprites.get(GuiSpriteType.EQUIP_SLOT_SMALL);
        
        // positions
        Coordinate leftRing01 = new Coordinate(15 * Game.SCREEN_MULTIPLIER, 59 * Game.SCREEN_MULTIPLIER);
        Coordinate leftRing02 = new Coordinate(32 * Game.SCREEN_MULTIPLIER, 59 * Game.SCREEN_MULTIPLIER);
        Coordinate rightRing01 = new Coordinate(92 * Game.SCREEN_MULTIPLIER, 59 * Game.SCREEN_MULTIPLIER);
        Coordinate rightRing02 = new Coordinate(109 * Game.SCREEN_MULTIPLIER, 59 * Game.SCREEN_MULTIPLIER);
        Coordinate helmet = new Coordinate(54 * Game.SCREEN_MULTIPLIER, 43 * Game.SCREEN_MULTIPLIER);
        Coordinate leftWep = new Coordinate(15 * Game.SCREEN_MULTIPLIER, 80 * Game.SCREEN_MULTIPLIER);
        Coordinate armor = new Coordinate(54 * Game.SCREEN_MULTIPLIER, 80 * Game.SCREEN_MULTIPLIER);
        Coordinate rightWep = new Coordinate(92 * Game.SCREEN_MULTIPLIER, 80 * Game.SCREEN_MULTIPLIER);
        
        // create rectangles
        Rectangle rectLeftRing01 = new Rectangle(leftRing01.x, leftRing01.y, smallSlotImg.getWidth(), smallSlotImg.getHeight());
        Rectangle rectLeftRing02 = new Rectangle(leftRing02.x, leftRing02.y, smallSlotImg.getWidth(), smallSlotImg.getHeight());
        Rectangle rectHelmet = new Rectangle(helmet.x, helmet.y, bigSlotImg.getWidth(), bigSlotImg.getHeight());
        Rectangle rectRightRing01 = new Rectangle(rightRing01.x, rightRing01.y, smallSlotImg.getWidth(), smallSlotImg.getHeight());
        Rectangle rectRightRing02 = new Rectangle(rightRing02.x, rightRing02.y, smallSlotImg.getWidth(), smallSlotImg.getHeight());
        Rectangle rectLeftWep = new Rectangle(leftWep.x, leftWep.y, bigSlotImg.getWidth(), bigSlotImg.getHeight());
        Rectangle rectArmor = new Rectangle(armor.x, armor.y, bigSlotImg.getWidth(), bigSlotImg.getHeight());
        Rectangle rectRightWep = new Rectangle(rightWep.x, rightWep.y, bigSlotImg.getWidth(), bigSlotImg.getHeight());
        
        // create + add gui-elements
        elements.add(new GuiEquipmentSlot(EquipmentSlot.RING01.toString(), true, true, false, GuiElementType.EQUIPMENT_SLOT, rectLeftRing01, smallSlotImg, EquipmentSlot.RING01));
        elements.add(new GuiEquipmentSlot(EquipmentSlot.RING02.toString(), true, true, false, GuiElementType.EQUIPMENT_SLOT, rectLeftRing02, smallSlotImg, EquipmentSlot.RING02));
        elements.add(new GuiEquipmentSlot(EquipmentSlot.HELMET.toString(), true, true, false, GuiElementType.EQUIPMENT_SLOT, rectHelmet, bigSlotImg, EquipmentSlot.HELMET));
        elements.add(new GuiEquipmentSlot(EquipmentSlot.RING03.toString(), true, true, false, GuiElementType.EQUIPMENT_SLOT, rectRightRing01, smallSlotImg, EquipmentSlot.RING03));
        elements.add(new GuiEquipmentSlot(EquipmentSlot.RING04.toString(), true, true, false, GuiElementType.EQUIPMENT_SLOT, rectRightRing02, smallSlotImg, EquipmentSlot.RING04));
        elements.add(new GuiEquipmentSlot(EquipmentSlot.MAINHANDITEM.toString(), true, true, false, GuiElementType.EQUIPMENT_SLOT, rectLeftWep, bigSlotImg, EquipmentSlot.MAINHANDITEM));
        elements.add(new GuiEquipmentSlot(EquipmentSlot.ARMOR.toString(), true, true, false, GuiElementType.EQUIPMENT_SLOT, rectArmor, bigSlotImg, EquipmentSlot.ARMOR));
        elements.add(new GuiEquipmentSlot(EquipmentSlot.OFFHANDITEM.toString(), true, true, false, GuiElementType.EQUIPMENT_SLOT, rectRightWep, bigSlotImg, EquipmentSlot.OFFHANDITEM));
        
        // return all items
        return elements;
    }
    
    private static Coordinate getPosition(GuiSpriteType type) {
        
        // helper function:
        // sets the starting positions of the gui elements
        
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
        case SECONDARY_STATS_DARK:
            coord.x = 46;
            coord.y = 129;
            break;
        case SECONDARY_STATS_LIGHT:
            coord.x = 46;
            coord.y = 129;
            break;
            
        case REST:
            coord.x = 93;
            coord.y = 226;
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
}
