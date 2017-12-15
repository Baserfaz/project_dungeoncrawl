package com.Game.data;

import java.util.ArrayList;
import java.util.List;

import com.Game.enumerations.TileType;
import com.Game.utilities.Coordinate;

public class World {

    private int width;
    private int height;
    private List<Tile> tiles;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new ArrayList<Tile>();

        create();
    }

    public void create() {
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                Tile tile = null;
                if(isOuterTile(x, y)) {
                    tile = new Tile(x, y, TileType.Wall);
                } else {
                    tile = new Tile(x, y, TileType.Floor);
                }
                tiles.add(tile);
            }   
        }
    }

    private boolean isOuterTile(int x, int y) {
        return y == 0 || y == this.height - 1 || x == 0 || x == this.width - 1;
    }
    
    public Tile getTileAtPos(int x, int y) { return this.getTileAtPos(new Coordinate(x, y)); }
    public Tile getTileAtPos(Coordinate c) {
        for(Tile t : this.tiles) {
            Coordinate pos = t.getPosition();
            if(pos.equals(c)) return t;
        }
        return null;
    }
    
    
    public boolean isTileWalkable(int x, int y) { return this.isTileWalkable(new Coordinate(x, y)); }
    public boolean isTileWalkable(Coordinate pos) {
        Tile tile = this.getTileAtPos(pos);
        if(tile != null) return tile.getTileType() != TileType.Wall;
        return false;
    }
    
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

}
