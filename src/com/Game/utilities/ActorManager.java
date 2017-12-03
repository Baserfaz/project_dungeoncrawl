package com.Game.utilities;

import java.util.ArrayList;
import java.util.List;

import com.Game.engine.Game;
import com.Game.enumerations.ActorType;
import com.Game.enumerations.SpriteType;
import com.Game.gameobjects.Actor;

public class ActorManager {

	private List<Actor> actorInstances;
	private Actor playerInstance;
	
	public ActorManager() {
	    actorInstances = new ArrayList<Actor>();
	    setPlayerInstance(null);
	}
	
	public Actor createActorInstance(Coordinate pos, SpriteType spriteType, int spriteSize, int spriteSizeMult, ActorType actorType) {
	    
	    Actor actor = null;
	    
	    if(actorType == ActorType.Player) {
	        actor = new Actor(pos, spriteType, spriteSize, spriteSizeMult);
	        playerInstance = actor;
	        Game.instance.getCamera().setFollowTarget(actor);
	    } else if (actorType == ActorType.Enemy) {
	        actor = new Actor(pos, spriteType, spriteSize, spriteSizeMult);
	        actorInstances.add(actor);
	    }
		return actor;
	}

	public void removeActor(Actor go) {
	    for(Actor actor : actorInstances) {
	        if(actor.equals(go)) {
	            actorInstances.remove(go);
	            break;
	        }
	    }
	}

    public List<Actor> getActorInstances() {
        return actorInstances;
    }

    public void setActorInstances(List<Actor> actorInstances) {
        actorInstances.addAll(actorInstances);
    }

    public Actor getPlayerInstance() {
        return playerInstance;
    }

    public void setPlayerInstance(Actor playerInstance) {
        this.playerInstance = playerInstance;
    }

}
