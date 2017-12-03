package com.Game.engine;

import java.awt.Color;
import java.awt.Graphics;

public class GuiString {

    private String text;
    private int x;
    private int y;
    private Color color;
    private GUIRenderer guiRenderer;

    public GuiString(String txt, int x, int y, Color color) {
        this.text = txt;
        this.x = x;
        this.y = y;
        this.color = color;
        this.guiRenderer = Game.instance.getGuiRenderer();
    }

    public void tick() {
        // some cool animations here
    }

    public void render(Graphics g) {
        this.guiRenderer.renderText(g, this.text, this.x, this.y, Game.SCREEN_MULTIPLIER, this.color);
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
