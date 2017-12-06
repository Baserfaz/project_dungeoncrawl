package com.Game.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

public class GuiString {

    private String text;
    private int x;
    private int y;
    private Color color;
    private GuiRenderer guiRenderer;
    private List<BufferedImage> renderText;
    
    public GuiString(String txt, int x, int y, Color color) {
        this.guiRenderer = Game.instance.getGuiRenderer();
        this.renderText = this.guiRenderer.createText(txt);
        this.text = txt;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void render(Graphics g) {
        int margin = 1 * Game.SCREEN_MULTIPLIER;
        int count = 0;
        for(BufferedImage img : this.renderText) {
            g.drawImage(img, this.x + img.getWidth() * count + margin * count, this.y, null);
            count += 1;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
