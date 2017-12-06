package com.Game.data;

import java.util.HashMap;
import java.util.Map;

public class Experience {

    private int currentExp = 0;
    private int currentLevel = 1;
    private int maxLevel = 99;
    private int expNeeded = 0;
    private Map<Integer, Integer> levelToExp;
    
    // used to calculate the exp gaps between levels
    private int baseExp = 200;
    private double expMultiplier = 1.5;
    
    public Experience() {
        this.levelToExp = calculateExpNeeded();
        this.expNeeded = this.levelToExp.get(this.currentLevel += 1);
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
        this.currentLevel++;
        int leftOverExp = this.currentExp - this.expNeeded;
        this.currentExp = leftOverExp;
        this.expNeeded = levelToExp.get(this.currentLevel += 1);
    }
    
    public int getMaxLevel() {
        return maxLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentExp() {
        return currentExp;
    }
    
    public void addCurrentExp(int amount) {
        this.currentExp += amount;
        if(this.currentExp >= this.expNeeded) {
            levelUp();
        }
    }
}
