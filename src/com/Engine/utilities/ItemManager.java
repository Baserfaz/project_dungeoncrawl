package com.Engine.utilities;

import java.util.ArrayList;
import java.util.List;

import com.Engine.enumerations.ItemType;
import com.Engine.enumerations.SpriteType;
import com.Engine.gameobjects.Item;

public class ItemManager {

    public static List<Item> items = new ArrayList<Item>();
    
    public static Item createItem(String name, ItemType itemType, Coordinate pos, SpriteType spriteType, int spriteSize, int spriteSizeMult) {
        Item item = new Item(name, itemType, pos, spriteType, spriteSize, spriteSizeMult);
        items.add(item);
        return item;
    }
    
}
