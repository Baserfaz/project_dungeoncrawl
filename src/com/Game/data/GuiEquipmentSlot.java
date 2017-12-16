package com.Game.data;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Game.engine.Game;
import com.Game.enumerations.EquipmentSlot;
import com.Game.enumerations.ErrorType;
import com.Game.enumerations.GuiElementType;
import com.Game.gameobjects.Actor;
import com.Game.gameobjects.Item;
import com.Game.gameobjects.Player;

public class GuiEquipmentSlot extends GuiElement {

    private EquipmentSlot slot;
    
    public GuiEquipmentSlot(String name, boolean enabled, boolean visible, boolean enableColorManipulation,
            Rectangle rect, BufferedImage img, EquipmentSlot slot) {
        super(name, enabled, visible, enableColorManipulation, GuiElementType.EQUIPMENT_SLOT, rect, img);
        
        // set the slot
        this.slot = slot;
    }

    public void onDrop(Item item) {
        if(item != null && this.enabled) {
            
            Actor player = Game.instance.getActorManager().getPlayerInstance();
            if(player != null) {
                Equipment eq = ((Player) player).getEquipment();
                ErrorType returnedError = eq.equipItem(item, this.slot);
                
                // debug
                if(returnedError == ErrorType.ITEM_NOT_COMPATIBLE_WITH_SLOT ||
                        returnedError == ErrorType.SLOT_OCCUPIED)  {
                    System.out.println("Failed (" + returnedError.toString() + ") " +
                        "to equip item: " + item.getName() +
                        " (" + item.getItemType() + ") to: " + this.slot.toString());
                } else {
                    System.out.println("Equipped item: " + item.getName() +
                            " (" + item.getItemType() + ") to: " + this.slot.toString()); 
                }
            }
        }
    }
    
    public EquipmentSlot getSlot() {
        return slot;
    }

    public void setSlot(EquipmentSlot slot) {
        this.slot = slot;
    }

}
