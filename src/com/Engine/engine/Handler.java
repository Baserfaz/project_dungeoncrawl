package com.Engine.engine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.Engine.gameobjects.GameObject;

public class Handler {

	private List<GameObject> objects = new ArrayList<GameObject>();

	public void tick() {
		for(int i = 0; i < objects.size(); i++) {
			GameObject current = objects.get(i);
			if(current != null) current.tick();
		}
	}

	public void render(Graphics g) {
	    
	    // Camera cam = Game.instance.getCamera();
	    GameObject draggedObj = null;
	    
	    // render all gameobjects 
	    for(int i = 0; i < objects.size(); i++) {
	        if(objects.get(i).isDragging()) {
	            draggedObj = objects.get(i);
	        } else {
	            objects.get(i).render(g);
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
