package com.Engine.engine;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.Engine.enumerations.GUIElementType;

public class GuiElementCreator {
    
    public static List<GuiElement> createGuiElements() {
        List<GuiElement> elements = new ArrayList<GuiElement>();
        
        // 1. create image
        BufferedImage mockupImg = Game.instance.getSpriteCreator().CreateCustomSizeSprite(0, 0, 14 * 32, 8 * 32, Game.SCREEN_MULTIPLIER);
        
        // 2. create rectangle 
        Rectangle mockupRect = new Rectangle(0, 0, mockupImg.getWidth(), mockupImg.getHeight());
        
        // 3. create GUIElement
        GuiElement mockupEl = new GuiElement("mockup", true, true, GUIElementType.Other, mockupRect, mockupImg);
        
        // 4. Add the GUIElement to elements list
        elements.add(mockupEl);
        
        // return list of elements
        return elements;
    }

    public static List<GuiString> createGuiStrings() {
        List<GuiString> strings = new ArrayList<GuiString>();
        
        // add all strings here.
        //strings.add(new GuiString("Project Dungeoncrawl, version: pre-fetus.", 10, 10, new Color(50, 50, 50, 255)));
        
        return strings;
    }
}
