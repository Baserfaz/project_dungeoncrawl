package com.Game.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.Game.data.Energy;
import com.Game.data.Health;
import com.Game.data.Mana;
import com.Game.engine.Game;
import com.Game.enumerations.Direction;
import com.Game.enumerations.SpriteType;
import com.Game.utilities.Coordinate;

public class Actor extends GameObject {

    private Direction lookDir;
    private String name;
    
    private Health HP;
    private Energy energy;
    private Mana MP;
    
    public Actor(String name, Coordinate worldPos, Coordinate tilePos,
            SpriteType spriteType, int spriteSize, int spriteSizeMult, int hp, int mp, int energy) {
        super(worldPos, tilePos, spriteType, spriteSize, spriteSizeMult);
        
        this.name = name;
        this.lookDir = Direction.North;
        
        this.HP = new Health(hp);
        this.MP = new Mana(mp);
        this.energy = new Energy(energy);
    }

    public void tick() {}

    public void render(Graphics g) {
        g.drawImage(sprite, worldPosition.x, worldPosition.y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(worldPosition.x, worldPosition.y, this.spriteSize, this.spriteSize);
    }

    public void moveForward() {
        
        Coordinate pos = this.getTilePosition();
        int x = pos.x;
        int y = pos.y;
        
        switch(this.lookDir) {
            case East:
                x += 1;
                break;
            case North:
                y -= 1;
                break;
            case South:
                y += 1;
                break;
            case West:
                x -= 1;
                break;
            default:
                System.out.println("Not supported direction: " + this.lookDir);
                break;
        }
        
        if(Game.instance.getWorld().isTileWalkable(x, y)) this.setTilePosition(x, y);
    }
    
    public void moveBackward() {
        Coordinate pos = this.getTilePosition();
        int x = pos.x;
        int y = pos.y;
        
        switch(this.lookDir) {
            case East:
                x -= 1;
                break;
            case North:
                y += 1;
                break;
            case South:
                y -= 1;
                break;
            case West:
                x += 1;
                break;
            default:
                System.out.println("Not supported direction: " + this.lookDir);
                break;
        }
        
        if(Game.instance.getWorld().isTileWalkable(x, y)) this.setTilePosition(x, y);
    }

    public void turnLeft() {
        switch(this.lookDir) {
        case North:
            this.lookDir = Direction.West;
            break;
        case South:
            this.lookDir = Direction.East;
            break;
        case East:
            this.lookDir = Direction.North;
            break;
        case West:
            this.lookDir = Direction.South;
            break;
        default:
            break;
        }
    }
    
    public void turnRight() {
        switch(this.lookDir) {
        case North:
            this.lookDir = Direction.East;
            break;
        case South:
            this.lookDir = Direction.West;
            break;
        case East:
            this.lookDir = Direction.South;
            break;
        case West:
            this.lookDir = Direction.North;
            break;
        default:
            break;
        }
    }
    
    public void strafeLeft() {
        Coordinate pos = this.getTilePosition();
        int x = pos.x;
        int y = pos.y;
        
        switch(this.lookDir) {
            case East:
                y -= 1;
                break;
            case North:
                x -= 1;
                break;
            case South:
                x += 1;
                break;
            case West:
                y += 1;
                break;
            default:
                System.out.println("Not supported direction: " + this.lookDir);
                break;
        }
        
        if(Game.instance.getWorld().isTileWalkable(x, y)) this.setTilePosition(x, y);
    }
    
    public void strafeRight() {
        Coordinate pos = this.getTilePosition();
        int x = pos.x;
        int y = pos.y;
        
        switch(this.lookDir) {
            case East:
                y += 1;
                break;
            case North:
                x += 1;
                break;
            case South:
                x -= 1;
                break;
            case West:
                y -= 1;
                break;
            default:
                System.out.println("Not supported direction: " + this.lookDir);
                break;
        }
        
        if(Game.instance.getWorld().isTileWalkable(x, y)) this.setTilePosition(x, y);
    }
    
    public Health getHP() {
        return HP;
    }

    public Direction getLookDir() {
        return lookDir;
    }

    public void setLookDir(Direction lookDir) {
        this.lookDir = lookDir;
    }
    
    public Energy getEnergy() {
        return energy;
    }

    public Mana getMP() {
        return MP;
    }

    public String getName() {
        return name;
    }
}
