package com.Game.gameobjects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.Game.engine.Game;
import com.Game.enumerations.ItemType;
import com.Game.enumerations.SpriteType;
import com.Game.utilities.Coordinate;

public class Item extends GameObject {

    private ItemType itemType;
    private String name;

    public Item(String name, ItemType itemType, Coordinate worldPos, Coordinate tilePos, SpriteType spriteType, int spriteSize, int spriteSizeMult) {
        super(worldPos, tilePos, spriteType, spriteSize, spriteSizeMult);
        this.itemType = itemType;
        this.name = name;
        this.dragging = false;
    }

    public void tick() {
        if(dragging) {
            Point p = Game.instance.getMousePos();
            int offset = (spriteSize * spriteSizeMult) / 2;
            setWorldPosition(p.x - offset, p.y - offset);
        }
    }

    public void render(Graphics g) {
        g.drawImage(this.sprite, this.worldPosition.x, this.worldPosition.y, null);
        if(Game.drawItemRects) {
            g.setColor(Game.itemRectColor);
            g.drawRect(this.worldPosition.x, this.worldPosition.y, this.size, this.size);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(this.worldPosition.x, this.worldPosition.y, this.size, this.size);
    }

    public void inspect() {
        System.out.println("Inspect: " + this.getInfo());
    }

    public String getInfo() {
        String s = super.getInfo();
        s += " name: " + this.name;
        return s;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
