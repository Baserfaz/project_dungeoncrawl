package com.Game.gameobjects;

import com.Game.data.Equipment;
import com.Game.data.Experience;
import com.Game.enumerations.PlayerClass;
import com.Game.enumerations.SpriteType;
import com.Game.utilities.Coordinate;

public class Player extends Actor {

    private PlayerClass playerClass;
    private Experience experience;
    private Equipment equipment;
    
    public Player(String name, Coordinate worldPos, Coordinate tilePos, SpriteType spriteType,
            int spriteSize, int spriteSizeMult, int hp, int mp, int energy, PlayerClass playerClass) {
        super(name, worldPos, tilePos, spriteType, spriteSize, spriteSizeMult, hp, mp, energy);
        this.playerClass = playerClass;
        
        this.experience = new Experience();
        this.equipment = new Equipment();
    }
    
    public PlayerClass getPlayerClass() {
        return this.playerClass;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

}
