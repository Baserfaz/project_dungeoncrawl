package com.Game.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.Game.data.Energy;
import com.Game.data.Equipment;
import com.Game.data.Experience;
import com.Game.data.Health;
import com.Game.data.Mana;
import com.Game.enumerations.ActorType;
import com.Game.enumerations.Direction;
import com.Game.enumerations.SpriteType;
import com.Game.utilities.Coordinate;

public class Actor extends GameObject {

    private Direction lookDir;
    private String name;
    
    private Health HP;
    private Energy energy;
    private Mana MP;
    
    public Actor(String name, Coordinate worldPos, Coordinate tilePos,
            SpriteType spriteType, int spriteSize, int spriteSizeMult, int hp, int mp, int energy) {
        super(worldPos, tilePos, spriteType, spriteSize, spriteSizeMult);
        
        this.name = name;
        this.lookDir = Direction.North;
        
        setHP(new Health(hp));
        setMP(new Mana(mp));
        setEnergy(new Energy(energy));
    }

    public void tick() {
        if(HP.isDead() == false) {
            this.move();
        }
    }

    public void render(Graphics g) {
        g.drawImage(sprite, worldPosition.x, worldPosition.y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(worldPosition.x, worldPosition.y, this.spriteSize, this.spriteSize);
    }

    public void move() {

    }

    public void turnLeft() {
        switch(this.lookDir) {
        case North:
            this.lookDir = Direction.West;
            break;
        case South:
            this.lookDir = Direction.East;
            break;
        case East:
            this.lookDir = Direction.North;
            break;
        case West:
            this.lookDir = Direction.South;
            break;
        default:
            break;
        }
    }
    
    public void turnRight() {
        switch(this.lookDir) {
        case North:
            this.lookDir = Direction.East;
            break;
        case South:
            this.lookDir = Direction.West;
            break;
        case East:
            this.lookDir = Direction.South;
            break;
        case West:
            this.lookDir = Direction.North;
            break;
        default:
            break;
        }
    }
    
    public Health getHP() {
        return HP;
    }

    public void setHP(Health hP) {
        HP = hP;
    }

    public Direction getLookDir() {
        return lookDir;
    }

    public void setLookDir(Direction lookDir) {
        this.lookDir = lookDir;
    }
    
    public Energy getEnergy() {
        return energy;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

    public Mana getMP() {
        return MP;
    }

    public void setMP(Mana mP) {
        MP = mP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
