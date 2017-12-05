package com.Game.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Game.enumerations.Direction;
import com.Game.utilities.Coordinate;

public class RenderUtils {

    // render with 90 degree rotation 
    public static void RenderSprite(BufferedImage sprite, Coordinate pos, Direction dir, Graphics g) {

        double angle = 0.0;
        if(dir == Direction.East) angle = 180.0;
        else if(dir == Direction.West) angle = 0.0;
        else if(dir == Direction.South) angle = 270.0;
        else if(dir == Direction.North) angle = 90.0;

        RenderSpriteWithRotation(sprite, pos, angle, g);
    }

    // render with free angle
    public static void RenderSpriteWithRotation(BufferedImage sprite, Coordinate pos, double angle, Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        int spriteWidth = sprite.getWidth();
        int spriteHeight = sprite.getHeight();

        double rot = Math.toRadians(angle);
        int x = pos.x;
        int y = pos.y;
        int halfOfSpriteSize = spriteWidth / 2;
        int xcenter = x + halfOfSpriteSize;
        int ycenter = y + halfOfSpriteSize;

        g2d.rotate(rot, xcenter, ycenter);
        g.drawImage(sprite, x, y, spriteWidth, spriteHeight, null);
        g2d.rotate(-rot, xcenter, ycenter);
    }

    public static void renderButton(String txt, Coordinate pos, 
            Coordinate size, Color fontCol, Color rectColor, int fontSize, boolean fill, Graphics2D g2d) {

        int calc_fontsize = fontSize * 2 - 4;

        // font
        Font font = new Font(Game.instance.getCustomFont().getFontName(), Font.PLAIN, calc_fontsize);

        // font settings
        g2d.setFont(font);

        // positions
        int y = pos.y;
        int x = pos.x;
        int width = size.x;
        int height = size.y;

        // -------------------------- RENDER ------------------------

        // set color for the rectangle
        g2d.setColor(rectColor);

        // render rectangle and fill it
        g2d.drawRect(x, y, width, height);
        if(fill) { g2d.fillRect(x, y, width, height); }

        // calculate text position inside the button
        Coordinate txtpos = new Coordinate(x + width/2 - 25, y + height/2 - 15);

        // render string
        renderString(txt, txtpos, fontCol, fontSize, g2d);
    }

    public static void FillScreenWithImage(Graphics g, Image img) { g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);}

    public static void FillRectWithImage(Rectangle rect, Graphics g, Image img) {
        g.drawImage(img, rect.x, rect.y, rect.width, rect.height, null);
    }

    public static void RenderSpriteWithBorder(BufferedImage sprite, Coordinate pos, Graphics g, Color borderColor) {
        BufferedImage img = Util.highlightTileBorders(sprite, borderColor);
        g.drawImage(img, pos.x, pos.y, sprite.getWidth(), sprite.getHeight(), null);
    }

    public static void RenderSpriteWithTint(BufferedImage sprite, Coordinate pos, Graphics g, Color tint) {
        BufferedImage img = Util.tintWithColor(sprite, tint);
        g.drawImage(img, pos.x, pos.y, sprite.getWidth(), sprite.getHeight(), null);
    }

    // render without rotation
    public static void RenderSprite(BufferedImage sprite, Coordinate pos, Graphics g) { 
        g.drawImage(sprite, pos.x, pos.y, sprite.getWidth(), sprite.getHeight(), null);
    }

    public static Rectangle renderRect(Rectangle rect, boolean isFilled, boolean hasBorder, Color borderColor, Color fillColor, Graphics g2d) {
        // positions
        int y = rect.y;
        int x = rect.x;
        int width = rect.width;
        int height = rect.height;

        if(hasBorder) {
            // set color for the border
            g2d.setColor(borderColor);
    
            // render rectangle and fill it
            g2d.drawRect(x, y, width, height);
        }
        
        if(isFilled) { 
            g2d.setColor(fillColor);
            g2d.fillRect(x, y, width, height); 
        }
        return new Rectangle(x, y, width, height);
    }

    public static void renderString(String txt, Coordinate pos, Color baseColor, int fontSize, Graphics2D g2d) {

        int calc_fontsize = fontSize * 2 - 4;

        // create font
        Font font = new Font(Game.instance.getCustomFont().getFontName(), Font.PLAIN, calc_fontsize);

        // font settings
        g2d.setFont(font);
        g2d.setColor(baseColor);

        int y = pos.y;
        int x = pos.x;

        // https://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring
        for (String line : txt.split("\n")) { 
            y += g2d.getFontMetrics().getHeight() + Game.LINEHEIGHT;
            g2d.setColor(baseColor);
            g2d.drawString(line, x, y);
        }
    }

}
