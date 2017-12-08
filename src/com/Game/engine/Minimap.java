package com.Game.engine;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Game.data.Tile;
import com.Game.enumerations.TileType;
import com.Game.gameobjects.Actor;
import com.Game.utilities.Coordinate;
import com.Game.utilities.RenderUtils;

public class Minimap {

    private BufferedImage imgUnknown;
    private BufferedImage imgFloor;
    private BufferedImage imgWall;
    private BufferedImage imgPlayer;
    
    private int marginBetweenTiles = 1;

    public Minimap() {
        // create image that represents the tile
        this.imgUnknown = Game.instance.getSpriteCreator().CreateCustomSizeSprite(0, 8 * 32, Game.SPRITEGRIDSIZE, Game.SPRITEGRIDSIZE, 1);
        this.imgWall = Game.instance.getSpriteCreator().CreateCustomSizeSprite(32, 8 * 32, Game.SPRITEGRIDSIZE, Game.SPRITEGRIDSIZE, 1);
        this.imgFloor = Game.instance.getSpriteCreator().CreateCustomSizeSprite(64, 8 * 32, Game.SPRITEGRIDSIZE, Game.SPRITEGRIDSIZE, 1);
        this.imgPlayer = Game.instance.getSpriteCreator().CreateCustomSizeSprite(96, 8 * 32, Game.SPRITEGRIDSIZE, Game.SPRITEGRIDSIZE, 1);
    }

    public void render(Graphics g) {

        // get camera and bounds
        Camera cam = Game.instance.getCamera();
        Rectangle camBounds = cam.getCameraBounds();

        // get player
        Actor player = Game.instance.getActorManager().getPlayerInstance();
        
        if(player == null) return;
        
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
            int px = player.getTilePosition().x;
            int py = player.getTilePosition().y;
            if(px == tilex && py == tiley) {
                
                // rotate the image to be inline with the player's rotation
                RenderUtils.RenderSprite(imgPlayer, new Coordinate(x, y), player.getLookDir(), g);
                
                //g.drawImage(imgPlayer, x, y, null);
            }

        }
    }
}
