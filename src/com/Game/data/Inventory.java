package com.Game.data;

import java.util.ArrayList;
import java.util.List;

import com.Game.gameobjects.GameObject;

public class Inventory {

    private int slotAmount;
    private List<GameObject> inventory;
    
    public Inventory() {
        this.slotAmount = 14;
        this.inventory = new ArrayList<GameObject>();
    }

    public int getSize() {
        return this.inventory.size();
    }
    
    public void removeItem(int index) {
        this.inventory.remove(index);
    }
    
    public void removeItem(GameObject item) {
        if(this.inventory.contains(item)) {
            this.inventory.remove(item);
        }
    }
    
    public void addItem(GameObject item) {
        if(this.inventory.size() < this.slotAmount) {
            this.inventory.add(item);
        }
    }
    
    public List<GameObject> getInventory() {
        return inventory;
    }

    public void setInventory(List<GameObject> inventory) {
        this.inventory = inventory;
    }
    
}
