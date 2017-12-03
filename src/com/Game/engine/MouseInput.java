package com.Game.engine;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import com.Game.enumerations.CursorMode;
import com.Game.enumerations.GameState;
import com.Game.gameobjects.Item;
import com.Game.utilities.Coordinate;
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
        Game.instance.setMousePos(e.getPoint());
    }

    public void mouseDragged(MouseEvent e) {
        Game.instance.setMousePos(e.getPoint());
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
        List<GuiElement> guiElements = Game.instance.getGuiElements();

        // calculate mouse position
        Coordinate pos = new Coordinate(e.getX(), e.getY());
        Point point = e.getPoint();

        // on which mouse button click
        if(e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Left click released at " + pos);

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
            }

        } else if(e.getButton() == MouseEvent.BUTTON2) {
            System.out.println("Middle mouse released click at " + pos);
        } else if(e.getButton() == MouseEvent.BUTTON3 ) {
            System.out.println("Right click released at " + pos);
        }
    }

    private void handleMousePressedInGame(MouseEvent e) {
        // refs
        List<GuiElement> guiElements = Game.instance.getGuiElements();
        List<Item> items = ItemManager.items;

        // calculate mouse position
        Coordinate pos = new Coordinate(e.getX(), e.getY());
        Point point = e.getPoint();

        // on which mouse button click
        if(e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Left click at " + pos);

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
                if(item.GetBounds().contains(point)) {
                    stackedItems.add(item);
                }
            }

            if(stackedItems.size() > 0) {
                Item item = stackedItems.get(stackedItems.size() - 1);
                item.setDragging(true);
                clickedItem = item;
                return;
            }

            if(stackedElements.size() > 0) {
                GuiElement item = stackedElements.get(stackedElements.size() - 1);
                item.highlight();
                clickedElement = item;
            }

        } else if(e.getButton() == MouseEvent.BUTTON2) {
            System.out.println("Middle mouse click at " + pos);
        } else if(e.getButton() == MouseEvent.BUTTON3 ) {
            System.out.println("Right click at " + pos);

            // set inspection cursor
            Game.instance.getWindow().setCursor(CursorMode.Inspect);

            List<Item> stackedItems = new ArrayList<Item>();

            // which item was clicked
            for(Item item : items) {
                if(item.GetBounds().contains(point)) {
                    stackedItems.add(item);
                }
            }

            if(stackedItems.size() > 0) {
                Item item = stackedItems.get(stackedItems.size() - 1);
                item.inspect();
            }
        }

    }

    private void handleMousePressedInMenu(MouseEvent e) {}
    private void handleMouseReleaseInMenu(MouseEvent e) {}
}
