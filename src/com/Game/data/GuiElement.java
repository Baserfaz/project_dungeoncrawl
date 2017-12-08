package com.Game.data;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Game.enumerations.GuiElementType;
import com.Game.gameobjects.Item;
import com.Game.utilities.RenderUtils;

public class GuiElement {

    protected Rectangle rect;
    protected BufferedImage img;
    protected BufferedImage tempImg;
    protected String name;
    protected Color color;
    protected boolean enabled;
    protected boolean visible;
    protected boolean highlighted;
    protected GuiElementType type;
    protected boolean enableColorManipulation;
    
    public GuiElement(String name, boolean enabled, boolean visible, boolean enableColorManipulation, 
            GuiElementType type, Rectangle rect, BufferedImage img) {
        this.rect = rect;
        this.img = img;
        this.name = name;
        this.enabled = enabled;
        this.type = type;
        this.visible = visible;
        this.highlighted = false;
        this.enableColorManipulation = enableColorManipulation;
    }

    public GuiElement(String name, boolean enabled, boolean visible, boolean enableColorManipulation,
            GuiElementType type, Rectangle rect, Color color) {
        this.rect = rect;
        this.img = null;
        this.name = name;
        this.enabled = enabled;
        this.type = type;
        this.visible = visible;
        this.highlighted = false;
        this.color = color;
        this.enableColorManipulation = enableColorManipulation;
    }
    
    public void onClick() {
        System.out.println("Clicked " + this.name);
        if(this.type == GuiElementType.BUTTON) {}
    }
    
    public void onDrop(Item item) {
        System.out.println("Dropped item " + item.getName() + " on " + this.name);
    }

    public void unhighlight() {
        if(this.highlighted) {
            setImg(this.tempImg);
        }
    }

    public void highlight() {
        if(this.type == GuiElementType.BUTTON) {
            setTempImg(this.img);
            BufferedImage img = RenderUtils.tintWithColor(this.img, Color.black);
            setImg(img);
            setHighlighted(true);
        }
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public GuiElementType getType() {
        return type;
    }

    public void setType(GuiElementType type) {
        this.type = type;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public BufferedImage getTempImg() {
        return tempImg;
    }

    public void setTempImg(BufferedImage tempImg) {
        this.tempImg = tempImg;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isEnableColorManipulation() {
        return enableColorManipulation;
    }

    public void setEnableColorManipulation(boolean enableColorManipulation) {
        this.enableColorManipulation = enableColorManipulation;
    }
}