package com.Game.engine;

import com.Game.gameobjects.GameObject;
import com.Game.utilities.Coordinate;

import java.awt.Rectangle;

public class Camera {
    
	private Rectangle cameraBounds;
	private GameObject followTarget;
	private boolean following;
	
	public Camera() {
		this.cameraBounds = new Rectangle(Game.CAMERA_POSX * Game.SCREEN_MULTIPLIER, Game.CAMERA_POSY * Game.SCREEN_MULTIPLIER, Game.CAMERA_WIDTH * Game.SCREEN_MULTIPLIER, Game.CAMERA_HEIGHT * Game.SCREEN_MULTIPLIER);
		this.followTarget = null;
		this.setFollowing(false);
	}

	public void setFollowTarget(GameObject obj) { this.followTarget = obj; }
	public GameObject getFollowTarget() { return this.followTarget; }
	public void Update(Coordinate pos, int width, int height) { cameraBounds.setBounds(pos.x, pos.y, width, height); }
	public Rectangle getCameraBounds() { return cameraBounds; }
	
    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean allowFollowing) {
        this.following = allowFollowing;
    }
}
