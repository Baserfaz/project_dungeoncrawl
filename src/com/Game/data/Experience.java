package com.Game.data;

import java.util.HashMap;
import java.util.Map;

public class Experience {

    private int currentExp = 0;
    private int currentLevel = 1;
    private int maxLevel = 99;
    private int expNeeded = 0;
    private Map<Integer, Integer> levelAndExp;
    
    // used to calculate the exp gaps between levels
    private int baseExp = 200;
    private double expMultiplier = 1.5;
    
    public Experience() {
        this.levelAndExp = calculateExpNeeded();
        this.expNeeded = this.levelAndExp.get(this.currentLevel + 1);
    }

    private Map<Integer, Integer> calculateExpNeeded() {
        Map<Integer, Integer> lvltoexp = new HashMap<Integer, Integer>();
        for(int i = 2; i < this.maxLevel; i++) {
            double calcExpNeededToLvl = i * baseExp * expMultiplier;
            lvltoexp.put(i, (int)calcExpNeededToLvl);
        }
        return lvltoexp;
    }

    public void levelUp() {
        this.currentLevel += 1;
        int leftOverExp = this.currentExp - this.expNeeded;
        this.currentExp = leftOverExp;
        this.expNeeded = levelAndExp.get(this.currentLevel + 1);
    }
    
    public int getMaxLevel() {
        return this.maxLevel;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public int getCurrentExp() {
        return this.currentExp;
    }
    
    public void addCurrentExp(int amount) {
        this.currentExp += amount;
        if(this.currentExp >= this.expNeeded) {
            levelUp();
        }
    }
}
