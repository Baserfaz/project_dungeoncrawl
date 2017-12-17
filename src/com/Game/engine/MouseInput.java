package com.Game.engine;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import com.Game.data.Equipment;
import com.Game.data.GuiElement;
import com.Game.data.GuiEquipmentSlot;
import com.Game.data.GuiInventorySlot;
import com.Game.enumerations.CursorMode;
import com.Game.enumerations.GameState;
import com.Game.gameobjects.Item;
import com.Game.gameobjects.Player;
import com.Game.utilities.ItemManager;

public class MouseInput implements MouseMotionListener, MouseListener {

    private GuiElement clickedElement;
    private Item clickedItem;

    public void mousePressed(MouseEvent e) {
        if(Game.instance.getGamestate() == GameState.Menu) {
            handleMousePressedInMenu(e);
        } else if(Game.instance.getGamestate() == GameState.Ingame) {
            handleMousePressedInGame(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(Game.instance.getGamestate() == GameState.Menu) {
            handleMouseReleaseInMenu(e);
        } else if(Game.instance.getGamestate() == GameState.Ingame) {
            handleMouseReleaseInGame(e);
        }
    }

    // mouse hover
    public void mouseMoved(MouseEvent e) {
        if(Game.instance.getGamestate() == GameState.Ingame) {
            
            // cache mouse position
            Game.instance.setMousePos(e.getPoint());
            
            // set hover item to null
            Game.instance.getDynamicGuiManager().setMouseHoverItem(null);
            
            // if we are hovering over an item
            for(Item item : ItemManager.items) {
                if(item.getIsVisible() && item.getIsEnabled()) {
                    if(item.getBounds().contains(e.getPoint())) {
                     
                        // hovering on an item
                        Game.instance.getDynamicGuiManager().setMouseHoverItem(item);
                        break;
                        
                    }
                }
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        if(Game.instance.getGamestate() == GameState.Ingame) {
            Game.instance.setMousePos(e.getPoint());
            
            // set hover item to null
            Game.instance.getDynamicGuiManager().setMouseHoverItem(null);
        }
    }

    // not used
    public void mouseEntered(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    private void handleMouseReleaseInGame(MouseEvent e) {

        if(clickedElement != null) clickedElement.unhighlight();
        if(clickedItem != null) clickedItem.setDragging(false);

        // set the default cursor when whatever mouse button is released.
        Game.instance.getWindow().setCursor(CursorMode.Default);

        // refs
        List<GuiElement> guiElements = Game.instance.getAllGuiElements();
        
        // calculate mouse position
        Point point = e.getPoint();

        // on which mouse button click
        if(e.getButton() == MouseEvent.BUTTON1) {

            List<GuiElement> stackedElements = new ArrayList<GuiElement>();

            for(GuiElement element : guiElements) {    
                if(element.isEnabled() == false) continue;
                if(element.getRect().contains(point)) {
                    stackedElements.add(element);
                }
            }

            // get the top element (e.g. last item in the list)
            if(stackedElements.size() > 0) {
                GuiElement element = stackedElements.get(stackedElements.size() - 1);
                if(element == clickedElement) element.onClick();
                else { 
                    if(clickedItem != null) element.onDrop(clickedItem);
                }
            }

        } else if(e.getButton() == MouseEvent.BUTTON2) {
        } else if(e.getButton() == MouseEvent.BUTTON3 ) {}
        
        // reset flags
        clickedItem = null;
        clickedElement = null;
    }

    private void handleMousePressedInGame(MouseEvent e) {
        
        // refs
        List<GuiElement> guiElements = Game.instance.getAllGuiElements();
        List<Item> items = ItemManager.items;

        // calculate mouse position
        Point point = e.getPoint();

        // on which mouse button click
        if(e.getButton() == MouseEvent.BUTTON1) {

            // set dragging cursor
            Game.instance.getWindow().setCursor(CursorMode.Dragging);

            List<GuiElement> stackedElements = new ArrayList<GuiElement>();
            List<Item> stackedItems = new ArrayList<Item>();

            for(GuiElement element : guiElements) {    
                if(element.isEnabled() == false) continue;
                if(element.getRect().contains(point)) {
                    stackedElements.add(element);
                }
            }

            for(Item item : items) {
                
                if(item.getIsEnabled() == false) continue;
                
                if(item.getBounds().contains(point)) {
                    stackedItems.add(item);
                }
            }

            if(stackedItems.size() > 0) {
                Item item = stackedItems.get(stackedItems.size() - 1);
                
                // ref to player
                Player player = (Player) Game.instance.getActorManager().getPlayerInstance();
                if(player == null) return;
                
                // references
                Equipment equipment = player.getEquipment();
                
                // unequip item from equipment
                for(GuiElement el : Game.instance.getEquipmentSlots()) {
                    GuiEquipmentSlot eqSlot = ((GuiEquipmentSlot) el);
                    if(equipment.getItem(eqSlot.getSlot()) == item) {
                        equipment.unequipItem(eqSlot.getSlot());
                        System.out.println("Unequipped " + item.getName() + " from " +
                                eqSlot.getSlot().toString());
                        break;
                    }
                }
                
                // remove item from inventory
                for(GuiElement el : Game.instance.getInventorySlots()) {
                    GuiInventorySlot invSlot = ((GuiInventorySlot) el);
                    if(invSlot.getItem() == item) {
                        invSlot.setItem(null);
                        System.out.println("Removed " + item.getName() + " from inventory.");
                        break;
                    }
                }
                
                // secondly start dragging the item
                item.setDragging(true);
                item.setDraggingStartPosition(item.getWorldPosition());
                clickedItem = item;
                return;
            }

            if(stackedElements.size() > 0) {
                GuiElement item = stackedElements.get(stackedElements.size() - 1);
                item.highlight();
                clickedElement = item;
            }

        } else if(e.getButton() == MouseEvent.BUTTON2) {
        } else if(e.getButton() == MouseEvent.BUTTON3 ) {}
    }

    private void handleMousePressedInMenu(MouseEvent e) {}
    private void handleMouseReleaseInMenu(MouseEvent e) {}
}
