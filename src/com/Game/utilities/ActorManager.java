package com.Game.utilities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.Game.engine.Game;
import com.Game.engine.GuiString;
import com.Game.enumerations.ActorType;
import com.Game.enumerations.PlayerClass;
import com.Game.enumerations.SpriteType;
import com.Game.gameobjects.Actor;
import com.Game.gameobjects.Player;

public class ActorManager {

    private List<Actor> actorInstances;
    private Actor playerInstance;
    
    public ActorManager() {
        actorInstances = new ArrayList<Actor>();
        setPlayerInstance(null);
    }

    public Actor createActorInstance(String actorName, ActorType actorType, Coordinate worldPos, Coordinate tilePos, SpriteType spriteType, int spriteSize, 
            int spriteSizeMult, int health, int mana, int energy) {

        Actor actor = null;
        
        if(actorType == ActorType.Player) {
            
            // create player object
            Player player = new Player(actorName, worldPos, tilePos, spriteType, 
                    spriteSize, spriteSizeMult, health, mana, energy, PlayerClass.ADVENTURER);
            
            // set variables
            actor = player;
            playerInstance = actor;
            
            // set camera
            Game.instance.getCamera().setFollowTarget(actor);
            
            // get reference
            DynamicGuiManager mngr = Game.instance.getDynamicGuiManager();
            
            // create sprite fonts
            mngr.setPlayerName(GuiElementCreator.createGuiString(actorName, 15, 152, Color.white));
            mngr.setPlayerClass(GuiElementCreator.createGuiString(
                    player.getPlayerClass().toString(), 57, 162, Color.white));
            mngr.setPlayerLevel(GuiElementCreator.createGuiString(
                    "LVL." + player.getExperience().getCurrentLevel(), 15, 162, Color.white));
            
            
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
}
