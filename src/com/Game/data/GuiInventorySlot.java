package com.Game.data;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Game.engine.Game;
import com.Game.enumerations.GuiElementType;
import com.Game.gameobjects.Actor;
import com.Game.gameobjects.Item;

public class GuiInventorySlot extends GuiElement {

    private Item item;
    
    public GuiInventorySlot(String name, boolean enabled, boolean visible, boolean enableColorManipulation,
            GuiElementType type, Rectangle rect, BufferedImage img) {
        super(name, enabled, visible, enableColorManipulation, type, rect, img);
        
        this.item = null;
    }

    public void onDrop(Item item) {
        System.out.println("Add item " + item.getName() + " to inventory.");
        Actor player = Game.instance.getActorManager().getPlayerInstance();
       
        if(player != null) {
            if(this.item == null) {
                
                this.item = item;
                this.snapItemToSlot(item);
                
            } else {
             
                System.out.println("Inventory slot is already occupied!");
                
                // put the item back to the start position
                item.setWorldPosition(item.getDraggingStartPosition());
                
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
    
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
