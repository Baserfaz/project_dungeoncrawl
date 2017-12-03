package com.Game.utilities;


import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.Game.engine.Game;
import com.Game.enumerations.SpriteType;
import com.Game.utilities.Coordinate;

public class SpriteCreator {

    private String path;
    private int height;
    private int width;
    private int[] pixels;

    public SpriteCreator(String path) {

        BufferedImage image = null;

        System.out.println("Loading spritesheet from path: " + path);

        // get the sprite sheet
        try { image = ImageIO.read(getClass().getResourceAsStream("/" + path)); }
        catch (IOException e) { e.printStackTrace(); }

        if(image == null) {
            System.out.println("SpriteCreator: No image found!");
            return;
        } else {
            System.out.println("Spritesheet \'" + path + "\' loaded succesfully!");
        }

        // set vars
        this.path = path;
        this.height = image.getHeight();
        this.width = image.getWidth();

        // load the color data
        pixels = image.getRGB(0, 0, width, height, null, 0, width);
    }

    // https://stackoverflow.com/questions/9558981/flip-image-with-graphics2d
    public BufferedImage FlipSpriteVertically(BufferedImage img) {
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -img.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage image = op.filter(img, null);
        return image;
    }

    // https://stackoverflow.com/questions/9558981/flip-image-with-graphics2d
    public BufferedImage FlipSpriteHorizontally(BufferedImage img) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-img.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage retimage = op.filter(img, null);
        return retimage;
    }

    // uses the same spritesheet
    public BufferedImage CreateCustomSizeSprite(int startx, int starty, int spriteWidth, int spriteHeight, int spriteSizeMult) {

        BufferedImage sprite = new BufferedImage(spriteWidth, spriteHeight, BufferedImage.TYPE_INT_ARGB);
        int[] spritePixelData = new int[sprite.getWidth() * sprite.getHeight()];

        // calculate tile's pixel locations.
        int startX = startx;
        int endX = startx + spriteWidth;
        int startY = starty;
        int endY = starty + spriteHeight;

        int currentPixel = 0;

        // get the pixel array
        for(int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                spritePixelData[currentPixel] = pixels[y * width + x];  
                currentPixel ++;
            }
        }

        // set pixels
        for (int y = 0; y < sprite.getHeight(); y++) {
            for (int x = 0; x < sprite.getWidth(); x++) {
                sprite.setRGB(x, y, spritePixelData[y * sprite.getWidth() + x]);
            }
        }

        AffineTransform tx = new AffineTransform();
        tx.scale(spriteSizeMult, spriteSizeMult);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        sprite = op.filter(sprite, null);

        return sprite;
    }

    public BufferedImage CreateSprite(SpriteType type, int spriteSize, int spriteSizeMult) {	

        BufferedImage sprite = new BufferedImage(spriteSize, spriteSize, BufferedImage.TYPE_INT_ARGB);
        int[] spritePixelData = new int[sprite.getWidth() * sprite.getHeight()];

        // get spritetype coordinates in the spritesheet
        Coordinate pos = getSpriteCoordinates(type);

        int column = pos.x;
        int row = pos.y; 

        // calculate tile's pixel locations.
        int startX = column * Game.SPRITEGRIDSIZE;
        int endX = startX + spriteSize;
        int startY = row * Game.SPRITEGRIDSIZE;
        int endY = startY + spriteSize;

        int currentPixel = 0;

        // get the pixel array
        for(int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                spritePixelData[currentPixel] = pixels[y * width + x];
                currentPixel ++;
            }
        }

        // set pixels
        for (int y = 0; y < sprite.getHeight(); y++) {
            for (int x = 0; x < sprite.getWidth(); x++) {
                sprite.setRGB(x, y, spritePixelData[y * sprite.getWidth() + x]);
            }
        }

        AffineTransform tx = new AffineTransform();
        tx.scale(spriteSizeMult, spriteSizeMult);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        sprite = op.filter(sprite, null);

        return sprite;
    }

    private Coordinate getSpriteCoordinates(SpriteType type) {

        Coordinate pos = new Coordinate(0, 0);

        switch(type) {
        case RedPotion:
            pos.x = 6;
            pos.y = 11;
            break;
        default:
            break;
        }

        return pos;
    }

    public String GetPath() { return this.path; }
    public int[] GetPixelArray() { return this.pixels; }
    public int GetWidth() { return this.width; }
    public int GetHeight() { return this.height; }
}
