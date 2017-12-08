package com.Game.data;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Game.enumerations.GuiElementType;
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
    }
    
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
