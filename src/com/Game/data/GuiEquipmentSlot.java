package com.Game.data;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Game.enumerations.EquipmentSlot;
import com.Game.enumerations.GuiElementType;
import com.Game.gameobjects.Item;

public class GuiEquipmentSlot extends GuiElement {

    private EquipmentSlot slot;
    
    public GuiEquipmentSlot(String name, boolean enabled, boolean visible, boolean enableColorManipulation,
            GuiElementType type, Rectangle rect, BufferedImage img, EquipmentSlot slot) {
        super(name, enabled, visible, enableColorManipulation, type, rect, img);
        
        // set the slot
        this.slot = slot;
    }

    public void onDrop(Item item) {
        if(item != null) {
            // TODO: equip item
            System.out.println("Equip item " + item.getName() + " in " + this.slot.toString());
        }
    }
    
    public EquipmentSlot getSlot() {
        return slot;
    }

    public void setSlot(EquipmentSlot slot) {
        this.slot = slot;
    }

}
