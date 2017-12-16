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
import com.Game.utilities.Coordinate;

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
                
                // on success
                if(returnedError == ErrorType.NO_ERRORS) {
                    
                    System.out.println("Equipped item: " + item.getName() +
                            " (" + item.getItemType() + ") to: " + this.slot.toString());
                    
                    // move the item to the center of the slot
                    this.snapItemToSlot(item);

                    
                } else if(returnedError == ErrorType.ITEM_NOT_COMPATIBLE_WITH_SLOT ||
                        returnedError == ErrorType.SLOT_OCCUPIED)  {
                    
                    System.out.println("Failed (" + returnedError.toString() + ") " +
                        "to equip item: " + item.getName() +
                        " (" + item.getItemType() + ") to: " + this.slot.toString());
                    
                    // put the item back to the start position
                    item.setWorldPosition(item.getDraggingStartPosition());
                    
                }
            }
        }
    }
    
    private void snapItemToSlot(Item item) {
        int slotcentx = this.getRect().x + this.getRect().width / 2;
        int slotcenty = this.getRect().y + this.getRect().height / 2;
        
        Rectangle ipos = item.getBounds();
        
        int itemcentx = ipos.width / 2;
        int itemcenty = ipos.height / 2;
        
        item.setWorldPosition(slotcentx - itemcentx, slotcenty - itemcenty);
    }
    
    public EquipmentSlot getSlot() {
        return slot;
    }

    public void setSlot(EquipmentSlot slot) {
        this.slot = slot;
    }

}
