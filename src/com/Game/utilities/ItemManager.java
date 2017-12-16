package com.Game.utilities;

import java.util.ArrayList;
import java.util.List;

import com.Game.engine.Camera;
import com.Game.engine.Game;
import com.Game.enumerations.ItemType;
import com.Game.enumerations.SpriteType;
import com.Game.gameobjects.Item;

public class ItemManager {

    public static List<Item> items = new ArrayList<Item>();

    public static List<Item> createAllItems() {
        List<Item> _items = new ArrayList<Item>();
        
        // get camera
        Camera cam = Game.instance.getCamera();
        
        int yCount = 0;
        int spriteSize = 32 * Game.ITEMSPRITESIZEMULT;
        
        int startx = cam.getCameraBounds().x + 50;
        
        int x = startx;
        int y = 20;
        
        for(SpriteType type : SpriteType.values()) {
            
            if(x > Game.WIDTH * Game.SCREEN_MULTIPLIER - 350) {
                yCount += 1;
                x = startx;
                y = 20 + spriteSize * yCount;
            }
            
            ItemType itemType = getItemType(type);
            
            Item item = new Item(type.toString(), itemType, new Coordinate(x, y), new Coordinate(0, 0), type, 32, Game.ITEMSPRITESIZEMULT);
            _items.add(item);
            items.add(item);
            
            x += spriteSize;
        }
        
        return _items;
    }
    
    private static ItemType getItemType(SpriteType type) {
        
        // default type
        ItemType it = ItemType.OTHER;
        
        if(type == SpriteType.AXE_IRON_ONE_BLADED ||
                type == SpriteType.AXE_IRON_TWO_BLADED ||
                type == SpriteType.DAGGER_IRON ||
                type == SpriteType.HAMMER_IRON ||
                type == SpriteType.STAFF_WOODEN ||
                type == SpriteType.SWORD_FLAMING ||
                type == SpriteType.SWORD_GOLD ||
                type == SpriteType.SWORD_IRON ||
                type == SpriteType.SWORD_SAPPHIRE ||
                type == SpriteType.WAND_RUBY ||
                type == SpriteType.WAND_SAPPHIRE || 
                type == SpriteType.WAND_WOODEN) {
            
            it = ItemType.WEAPON;
        } else if(type == SpriteType.SHIELD_IRON_KITE ||
                type == SpriteType.SHIELD_IRON_TOWER ||
                type == SpriteType.SHIELD_WOODEN_ROUND ||
                type == SpriteType.SHIELD_WOODEN_TOWER) {
            it = ItemType.SHIELD;
        } else if(type == SpriteType.BLUE_POTION_BIG ||
                type == SpriteType.BLUE_POTION_SMALL ||
                type == SpriteType.RED_POTION_BIG ||
                type == SpriteType.RED_POTION_SMALL) {
            it = ItemType.POTION;
        } else if(type == SpriteType.RING_GEM_EMERALD ||
                type == SpriteType.RING_GOLDEN ||
                type == SpriteType.RING_IRON) {
            it = ItemType.RING;
        } else if(type == SpriteType.KEY_GOLDEN ||
                type == SpriteType.KEY_IRON) {
            it = ItemType.KEY;
        }
            
        return it;
    }

    public static Item createItem(String name, ItemType itemType, Coordinate worldPos, Coordinate tilePos,
            SpriteType spriteType, int spriteSize, int spriteSizeMult) {
        Item item = new Item(name, itemType, worldPos, tilePos, spriteType, spriteSize, spriteSizeMult);
        items.add(item);
        return item;
    }

}
