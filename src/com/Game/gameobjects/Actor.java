package com.Game.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.Game.data.Health;
import com.Game.data.Inventory;
import com.Game.engine.Game;
import com.Game.enumerations.Direction;
import com.Game.enumerations.SpriteType;
import com.Game.utilities.Coordinate;

public class Actor extends GameObject {

    private Health HP;
    private Inventory inv;
    private Direction lookDir;
    private boolean isMoving = false;

    public Actor(Coordinate pos, SpriteType spriteType, int spriteSize, int spriteSizeMult) {
        super(pos, spriteType, spriteSize, spriteSizeMult);
        setHP(new Health());
        this.inv = new Inventory();
        setLookDir(Direction.East);
    }

    public void tick() {
        if(HP.isDead()) {
            // TODO. die
        } else {

            // move the actor
            this.move();

        }
    }

    public void render(Graphics g) {
        g.drawImage(sprite, worldPosition.x, worldPosition.y, null);
    }

    public Rectangle GetBounds() {
        return new Rectangle(worldPosition.x, worldPosition.y, this.spriteSize, this.spriteSize);
    }

    public void move() {

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

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

}
