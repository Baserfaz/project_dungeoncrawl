package com.Game.utilities;

import com.Game.engine.GuiString;

public class GuiStringManager {

    // this class' idea is that,
    // it stores all modifiable
    // gui-strings, that are 
    // created with using sprite font.
    
    private GuiString playerName;
    private GuiString playerClass;
    private GuiString playerLevel;
    
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
    
}
