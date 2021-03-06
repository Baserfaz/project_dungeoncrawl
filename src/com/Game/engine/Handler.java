package com.Game.engine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.Game.gameobjects.Actor;
import com.Game.gameobjects.GameObject;
import com.Game.gameobjects.Item;

public class Handler {

    private List<GameObject> objects = new ArrayList<GameObject>();

    public void tick() {
        for(int i = 0; i < objects.size(); i++) {
            GameObject current = objects.get(i);
            if(current != null) current.tick();
        }
    }

    public void render(Graphics g) {

        // references
        // Camera cam = Game.instance.getCamera();
        Actor player = Game.instance.getActorManager().getPlayerInstance();
        if(player == null) return;
        
        GameObject draggedObj = null;

        // render all game objects 
        for(int i = 0; i < objects.size(); i++) {
            GameObject current = objects.get(i);
            
            // don't render invisible objects.
            if(current.getIsVisible() == false) continue;
            
            // don't render objects that are not on the same tile as we are
            if(current.getTilePosition().equals(player.getTilePosition())) {
                if(current instanceof Item) {
                    if(((Item) current).isDragging()) { draggedObj = objects.get(i);
                    } else { objects.get(i).render(g); }
                } else { objects.get(i).render(g); }
            }
        }

        // after rendering other gameobjects, render the dragged item.
        if(draggedObj != null) {

            // here we put the draggedObj to the last item in
            // the render queue -> ensure that the item stays on top.
            if(objects.contains(draggedObj)) {
                objects.remove(draggedObj);
                objects.add(draggedObj);
            }

            draggedObj.render(g);
        }
    }

    public void AddObject(GameObject go) { this.objects.add(go); }	
    public void RemoveObject(GameObject go) { this.objects.remove(go); }

    public List<GameObject> getObjects() { return objects; }
    public void setObjects(List<GameObject> objects) { this.objects = objects; }

}
