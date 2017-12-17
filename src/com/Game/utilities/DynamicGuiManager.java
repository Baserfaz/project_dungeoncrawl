package com.Game.utilities;

import com.Game.engine.GuiString;
import com.Game.gameobjects.Item;

public class DynamicGuiManager {
    
    // this class caches dynamically
    // created GUI-elements and data
    
    private GuiString playerName;
    private GuiString playerClass;
    private GuiString playerLevel;
    
    private Item mouseHoverItem;
    
    public GuiString getPlayerName() {
        return playerName;
    }
   
    public void setPlayerName(GuiString name) {
        this.playerName = name;
    }
    
    public GuiString getPlayerClass() {
        return playerClass;
    }
    public void setPlayerClass(GuiString pClass) {
        this.playerClass = pClass;
    }
    public GuiString getPlayerLevel() {
        return playerLevel;
    }
    public void setPlayerLevel(GuiString lvl) {
        this.playerLevel = lvl;
    }

    public Item getMouseHoverItem() {
        return mouseHoverItem;
    }

    public void setMouseHoverItem(Item mouseHoverItem) {
        this.mouseHoverItem = mouseHoverItem;
    }
    
}
