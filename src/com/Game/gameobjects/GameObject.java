package com.Game.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Game.engine.Game;
import com.Game.enumerations.SpriteType;
import com.Game.utilities.Coordinate;

public abstract class GameObject {

    protected Coordinate worldPosition;
    protected Coordinate tilePosition;
    protected BufferedImage sprite;
    protected int spriteSize;
    protected int spriteSizeMult;
    protected int size;
    protected boolean dragging;

    public GameObject(Coordinate worldPos, Coordinate tilePos, SpriteType type, int spriteSize, int spriteSizeMult) {

        // set world position
        this.worldPosition = worldPos;

        // set tile position
        this.tilePosition = tilePos;
        
        // cache sprite size & mult
        this.spriteSize = spriteSize;
        this.spriteSizeMult = spriteSizeMult;

        // calculate size 
        this.size = spriteSize * spriteSizeMult;

        // create sprite
        this.sprite = Game.instance.getSpriteCreator().CreateSprite(type, spriteSize, spriteSizeMult);

        // add to handler
        Game.instance.getHandler().AddObject(this);
    }

    public String getInfo() {
        return "GameObject: " + this.toString() + " worldPos: (" + this.getWorldPosition().x + ", " + this.getWorldPosition().y + ")";
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public void setSprite(BufferedImage i) { this.sprite = i; }
    public BufferedImage getSprite() { return this.sprite; }
    public void setWorldPosition(int x, int y) { this.worldPosition = new Coordinate(x, y); }
    public void setWorldPosition(Coordinate pos) { this.worldPosition = pos; }
    public Coordinate getWorldPosition() { return this.worldPosition; }
    public Coordinate getTilePosition() { return this.tilePosition; }
    public boolean isDragging() { return dragging; }
    public void setDragging(boolean dragging) { this.dragging = dragging; }
}
