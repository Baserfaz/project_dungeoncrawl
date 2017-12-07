package com.Game.utilities;

import java.util.ArrayList;
import java.util.List;

import com.Game.engine.Game;
import com.Game.engine.GuiElementCreator;
import com.Game.engine.GuiString;
import com.Game.enumerations.ActorType;
import com.Game.enumerations.SpriteType;
import com.Game.gameobjects.Actor;

public class ActorManager {

    private List<Actor> actorInstances;
    private Actor playerInstance;

    private GuiString playerNameSpriteString;
    
    public ActorManager() {
        actorInstances = new ArrayList<Actor>();
        setPlayerInstance(null);
    }

    public Actor createActorInstance(String actorName, Coordinate worldPos, Coordinate tilePos, SpriteType spriteType, int spriteSize, 
            int spriteSizeMult, ActorType actorType, int health, int mana, int energy) {

        Actor actor = null;

        if(actorType == ActorType.Player) {
            actor = new Actor(actorName, worldPos, tilePos, spriteType, spriteSize, spriteSizeMult, health, mana, energy);
            playerInstance = actor;
            Game.instance.getCamera().setFollowTarget(actor);
            
            // create name using sprite font
            playerNameSpriteString = GuiElementCreator.createPlayerName(actorName);
        } else if (actorType == ActorType.Enemy) {
            actor = new Actor(actorName, worldPos, tilePos, spriteType, spriteSize, spriteSizeMult, health, mana, energy);
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

    public GuiString getPlayerNameSpriteString() {
        return playerNameSpriteString;
    }

    public void setPlayerNameSpriteString(GuiString playerNameString) {
        this.playerNameSpriteString = playerNameString;
    }

}
