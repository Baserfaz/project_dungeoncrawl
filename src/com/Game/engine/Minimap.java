package com.Game.engine;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Game.data.Tile;
import com.Game.enumerations.TileType;

public class Minimap {
    
    private BufferedImage imgUnknown;
    private BufferedImage imgFloor;
    private BufferedImage imgWall;
    private int marginBetweenTiles = 1;
    
    public Minimap() {
        // create image that represents the tile
        this.imgUnknown = Game.instance.getSpriteCreator().CreateCustomSizeSprite(0, 8 * 32, Game.SPRITEGRIDSIZE, Game.SPRITEGRIDSIZE, 1);
        this.imgWall = Game.instance.getSpriteCreator().CreateCustomSizeSprite(32, 8 * 32, Game.SPRITEGRIDSIZE, Game.SPRITEGRIDSIZE, 1);
        this.imgFloor = Game.instance.getSpriteCreator().CreateCustomSizeSprite(64, 8 * 32, Game.SPRITEGRIDSIZE, Game.SPRITEGRIDSIZE, 1);
    }
    
    public void render(Graphics g) {
        
        // get camera and bounds
        Camera cam = Game.instance.getCamera();
        Rectangle camBounds = cam.getCameraBounds();
        
        // loop the world tile data 
        for(Tile tile : Game.instance.getWorld().getTiles()) {
            
            // tile pos
            int tilex = tile.getPosition().x;
            int tiley = tile.getPosition().y;
            
            // calculate position
            int x = camBounds.x + 25 + (tilex * 32) + (tilex * marginBetweenTiles);
            int y = camBounds.y + 25 + (tiley * 32) + (tiley * marginBetweenTiles);
            
            // cache vars
            BufferedImage img;
            TileType type = tile.getTileType();
            
            // determine graphics
            switch(type) {
            case Floor:
                img = this.imgFloor;
                break;
            case Wall:
                img = this.imgWall;
                break;
            default:
                img = this.imgUnknown;
            }
            
            // draw tile
            g.drawImage(img, x, y, null);
            
            // check if the player is on this tile
            // TODO
            
        }
    }
}
