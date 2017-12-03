package com.Game.data;

public class Health {

    private int currentHP;
    private int maxHP = 100;
    private boolean isDead = false;
    
    public Health() {
        this.setCurrentHP(maxHP);
    }

    public void takeDamage(int amount) {
        this.setCurrentHP(currentHP - amount);
        if(currentHP <= 0) { setDead(true); }
    }
    
    public void healDamage(int amount) {
        this.setCurrentHP(currentHP + amount);
        if(currentHP > maxHP) currentHP = maxHP;
    }
    
    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }
    
}
