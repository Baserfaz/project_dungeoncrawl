package com.Engine.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Engine.engine.Game;
import com.Engine.enumerations.SpriteType;
import com.Engine.utilities.Coordinate;

public abstract class GameObject {

	protected Coordinate worldPosition;
	protected BufferedImage sprite;
	protected int spriteSize;
	protected int spriteSizeMult;
	protected int size;
	protected boolean dragging;
	
	public GameObject(Coordinate worldPos, SpriteType type, int spriteSize, int spriteSizeMult) {
		
		// create world coordinates
		this.worldPosition = worldPos;
		
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
		
	public String GetInfo() {
		return "GameObject: " + this.toString() + " worldPos: (" + this.GetWorldPosition().x + ", " + this.GetWorldPosition().y + ")";
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle GetBounds();
	
	public void SetSprite(BufferedImage i) { this.sprite = i; }
	public BufferedImage GetSprite() { return this.sprite; }
	public void SetWorldPosition(int x, int y) { this.worldPosition = new Coordinate(x, y); }
	public void SetWorldPosition(Coordinate pos) { this.worldPosition = pos; }
	public Coordinate GetWorldPosition() { return this.worldPosition; }
    public boolean isDragging() { return dragging; }
    public void setDragging(boolean dragging) { this.dragging = dragging; }
}
