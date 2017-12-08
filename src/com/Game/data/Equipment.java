package com.Game.data;

import java.util.ArrayList;
import java.util.List;

import com.Game.enumerations.EquipmentSlot;
import com.Game.enumerations.ItemType;
import com.Game.gameobjects.Item;

public class Equipment {

    private Item helmet;
    private Item armor;
    private Item offHandItem;
    private Item mainHandItem;
    private Item ring01;
    private Item ring02;
    private Item ring03;
    private Item ring04;
    
    public Equipment() {
        this.helmet = null;
        this.armor = null;
        this.offHandItem = null;
        this.mainHandItem = null;
        this.ring01 = null;
        this.ring02 = null;
        this.ring03 = null;
        this.ring04 = null;
    }
    
    public boolean checkItemToSlotCombatibility(Item item, EquipmentSlot slot) {
        boolean retval = false;
        ItemType type = item.getItemType();
        if(slot == EquipmentSlot.ARMOR && type == ItemType.ARMOR || 
                slot == EquipmentSlot.HELMET && type == ItemType.HELMET ||
                slot == EquipmentSlot.MAINHANDITEM && type == ItemType.SHIELD ||
                slot == EquipmentSlot.MAINHANDITEM && type == ItemType.WEAPON ||
                slot == EquipmentSlot.OFFHANDITEM && type == ItemType.SHIELD ||
                slot == EquipmentSlot.OFFHANDITEM && type == ItemType.WEAPON ||
                ((slot == EquipmentSlot.RING01 ||
                slot == EquipmentSlot.RING02 ||
                slot == EquipmentSlot.RING03 ||
                slot == EquipmentSlot.RING04) && type == ItemType.RING)) {
            retval = true;
        }
        return retval;
    }
    
    public void equipItem(Item item, EquipmentSlot slot) {
        
        // check if the item can be equipped in this slot.
        if(checkItemToSlotCombatibility(item, slot) == false) return;
        
        switch(slot) {
        case ARMOR:
            this.armor = item;
            break;
        case HELMET:
            this.helmet = item;
            break;
        case MAINHANDITEM:
            this.mainHandItem = item;
            break;
        case OFFHANDITEM:
            this.offHandItem = item;
            break;
        case RING01:
            this.ring01 = item;
            break;
        case RING02:
            this.ring02 = item;
            break;
        case RING03:
            this.ring03 = item;
            break;
        case RING04:
            this.ring04 = item;
            break;
        }
    }
    
    public Item getItem(EquipmentSlot slot) {
        Item item = null;
        switch(slot) {
        case ARMOR:
            item = this.armor;
            break;
        case HELMET:
            item = this.helmet;
            break;
        case MAINHANDITEM:
            item = this.mainHandItem;
            break;
        case OFFHANDITEM:
            item = this.offHandItem;
            break;
        case RING01:
            item = this.ring01;
            break;
        case RING02:
            item = this.ring02;
            break;
        case RING03:
            item = this.ring03;
            break;
        case RING04:
            item = this.ring04;
            break;
        }
        return item;
    }
    
    public Item getHelmet() {
        return helmet;
    }

    public Item getArmor() {
        return armor;
    }

    public Item getOffHandItem() {
        return offHandItem;
    }

    public Item getMainHandItem() {
        return mainHandItem;
    }

    public Item getRing01() {
        return ring01;
    }

    public Item getRing02() {
        return ring02;
    }

    public Item getRing03() {
        return ring03;
    }

    public Item getRing04() {
        return ring04;
    }

    public List<Item> getAllEquipment() {
        List<Item> items = new ArrayList<Item>();
        items.add(this.armor);
        items.add(this.helmet);
        items.add(this.mainHandItem);
        items.add(this.offHandItem);
        items.add(this.ring01);
        items.add(this.ring02);
        items.add(this.ring03);
        items.add(this.ring04);
        return items;
    }
}
