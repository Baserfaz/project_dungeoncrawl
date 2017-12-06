package com.Game.utilities;

import java.util.ArrayList;
import java.util.List;

import com.Game.enumerations.ItemType;
import com.Game.enumerations.SpriteType;
import com.Game.gameobjects.Item;

public class ItemManager {

    public static List<Item> items = new ArrayList<Item>();

    public static Item createItem(String name, ItemType itemType, Coordinate worldPos, Coordinate tilePos, SpriteType spriteType, int spriteSize, int spriteSizeMult) {
        Item item = new Item(name, itemType, worldPos, tilePos, spriteType, spriteSize, spriteSizeMult);
        items.add(item);
        return item;
    }

}
