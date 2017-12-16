package com.Game.data;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Game.engine.Game;
import com.Game.enumerations.GuiElementType;
import com.Game.enumerations.GuiSpriteType;
import com.Game.gameobjects.Actor;
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
    protected GuiElementType elementType;
    protected GuiSpriteType spriteType;
    protected boolean enableColorManipulation;
    
    public GuiElement(String name, boolean enabled, boolean visible, boolean enableColorManipulation, 
            GuiElementType type, GuiSpriteType spriteType, Rectangle rect, BufferedImage img) {
        this.rect = rect;
        this.img = img;
        this.name = name;
        this.enabled = enabled;
        this.elementType = type;
        this.spriteType = spriteType;
        this.visible = visible;
        this.highlighted = false;
        this.enableColorManipulation = enableColorManipulation;
    }
    
    public GuiElement(String name, boolean enabled, boolean visible, boolean enableColorManipulation, 
            GuiElementType type, Rectangle rect, BufferedImage img) {
        this.rect = rect;
        this.img = img;
        this.name = name;
        this.enabled = enabled;
        this.elementType = type;
        this.spriteType = GuiSpriteType.NONE;
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
        this.elementType = type;
        this.visible = visible;
        this.highlighted = false;
        this.color = color;
        this.enableColorManipulation = enableColorManipulation;
    }
    
    public void onClick() {
        
        System.out.println("Clicked: " + this.name);
        
        if(this.elementType == GuiElementType.BUTTON) {
            this.doClickFunction();
        }
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
        if(this.elementType == GuiElementType.BUTTON) {
            setTempImg(this.img);
            BufferedImage img = RenderUtils.tintWithColor(this.img, Color.black);
            setImg(img);
            setHighlighted(true);
        }
    }

    private void doClickFunction() {
        
        // get player reference
        Actor player = Game.instance.getActorManager().getPlayerInstance();
        
        switch(this.spriteType) {
            
            // player actions
            case ACTION_1:
                break;
            case ACTION_2:
                break;
                
            // other stuff
            case AGREE:
                break;
            case DISAGREE:
                break;
                
            // movement buttons
            case BACKWARD:
                player.moveBackward();
                break;
            case FORWARD:
                player.moveForward();
                break;
            case STRAFE_LEFT:
                player.strafeLeft();
                break;
            case STRAFE_RIGHT:
                player.strafeRight();
                break;
            case TURN_LEFT:
                player.turnLeft();
                break;
            case TURN_RIGHT:
                player.turnRight();
                break;
                
            // top buttons
            // light tinted buttons are clickable
            case LIGHT_CHARACTER:
                
                // 1. hide spell book
                // 2. show equipment panel 
                // -----------------------------
                // 3. show light spellbook button
                // 4. hide dark spellbook button
                // 5. show dark character button
                // 6. hide light character button
                // -----------------------------
                // also we have to enable the rendering of 
                // equipment slots.
                
                // this is now light character
                this.visible = false;
                this.enabled = false;
                
                for(GuiElement e : Game.instance.getAllGuiElements()) {
                    GuiSpriteType type = e.getSpriteType();
                    if(type == GuiSpriteType.SPELLBOOK) {
                        e.visible = false;
                    } else if(type == GuiSpriteType.EQUIPMENT) {
                        e.visible = true;
                    } else if(type == GuiSpriteType.DARK_SPELLBOOK) {
                        e.visible = false;
                        e.enabled = false;
                    } else if(type == GuiSpriteType.DARK_CHARACTER) {
                        e.visible = true;
                        e.enabled = false;
                    } else if(type == GuiSpriteType.LIGHT_SPELLBOOK) {
                        e.visible = true;
                        e.enabled = true;
                    }
                }
                
                // enable equipment slots
                for(GuiElement slot : Game.instance.getEquipmentSlots()) {
                    slot.enabled = true;
                }
                
                Game.renderEquipmentSlots = true;
                
                break;
            case LIGHT_SPELLBOOK:
                
                // 1. hide equipment panel
                // 2. show spellbook 
                // -----------------------------
                // 3. hide light spellbook button
                // 4. show dark spellbook button
                // 5. hide dark character button
                // 6. show light character button
                // -----------------------------
                // also we have to disable the rendering of 
                // equipment slots.
                // -----------------------------
                // disable equipment slots
                
                // this is the light spell book
                this.visible = false;
                this.enabled = false;
                
                for(GuiElement e : Game.instance.getAllGuiElements()) {
                    GuiSpriteType type = e.getSpriteType();
                    if(type == GuiSpriteType.SPELLBOOK) {
                        e.visible = true;
                    } else if(type == GuiSpriteType.EQUIPMENT) {
                        e.visible = false;
                    } else if(type == GuiSpriteType.DARK_SPELLBOOK) {
                        e.visible = true;
                        e.enabled = false;
                    } else if(type == GuiSpriteType.DARK_CHARACTER) {
                        e.visible = false;
                        e.enabled = false;
                    } else if(type == GuiSpriteType.LIGHT_CHARACTER) {
                        e.visible = true;
                        e.enabled = true;
                    }
                }
                
                // disable equipment slots
                for(GuiElement slot : Game.instance.getEquipmentSlots()) {
                    slot.enabled = false;
                }
                
                Game.renderEquipmentSlots = false;
                break;

            // misc. buttons
            case MAP:
                Game.renderMinimap = !Game.renderMinimap;
                break;
            case MENU:
                break;
            case REST:
                break;
                
            // stats buttons
            // dark tinted buttons are clickable
            case SECONDARY_STATS_DARK:
                
                // hide secondary stats dark
                this.visible = false;
                this.enabled = false;
                
                for(GuiElement e : Game.instance.getAllGuiElements()) {
                    GuiSpriteType type = e.getSpriteType();
                    if(type == GuiSpriteType.PRIMARY_STATS_LIGHT) {
                        e.visible = false;
                        e.enabled = false;
                    } else if(type == GuiSpriteType.PRIMARY_STATS_DARK) {
                        e.visible = true;
                        e.enabled = true;
                    } else if(type == GuiSpriteType.SECONDARY_STATS_LIGHT) {
                        e.visible = true;
                        e.enabled = false;
                    } else if(type == GuiSpriteType.STATS_PRIMARY) {
                        e.visible = false;
                    } else if(type == GuiSpriteType.STATS_SECONDARY) {
                        e.visible = true;
                    }
                }
                
                Game.renderPrimaryStats = false;
                
                break;
            case PRIMARY_STATS_DARK:
                
                // hide primary stats dark
                this.visible = false;
                this.enabled = false;
                
                for(GuiElement e : Game.instance.getAllGuiElements()) {
                    GuiSpriteType type = e.getSpriteType();
                    if(type == GuiSpriteType.PRIMARY_STATS_LIGHT) {
                        e.visible = true;
                        e.enabled = false;
                    } else if(type == GuiSpriteType.SECONDARY_STATS_LIGHT) {
                        e.visible = false;
                        e.enabled = false;
                    } else if(type == GuiSpriteType.SECONDARY_STATS_DARK) {
                        e.visible = true;
                        e.enabled = true;
                    } else if(type == GuiSpriteType.STATS_PRIMARY) {
                        e.visible = true;
                    } else if(type == GuiSpriteType.STATS_SECONDARY) {
                        e.visible = false;
                    }
                }
                
                Game.renderPrimaryStats = true;
                
                break;
                
            default:
                System.out.println("Not matching function for type: " + this.spriteType 
                        + ". This shouldn't be a button or enabled.");
                break;
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
        return elementType;
    }

    public void setType(GuiElementType type) {
        this.elementType = type;
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
    
    public GuiSpriteType getSpriteType() {
        return this.spriteType;
    }
}
