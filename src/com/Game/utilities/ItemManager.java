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
            
            Item item = new Item(type.toString(), ItemType.OTHER, new Coordinate(x, y), new Coordinate(0, 0), type, 32, Game.ITEMSPRITESIZEMULT);
            _items.add(item);
            items.add(item);
            
            x += spriteSize;
        }
        
        return _items;
    }
    
    public static Item createItem(String name, ItemType itemType, Coordinate worldPos, Coordinate tilePos,
            SpriteType spriteType, int spriteSize, int spriteSizeMult) {
        Item item = new Item(name, itemType, worldPos, tilePos, spriteType, spriteSize, spriteSizeMult);
        items.add(item);
        return item;
    }

}
