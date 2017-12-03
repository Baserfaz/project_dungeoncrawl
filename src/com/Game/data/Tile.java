package com.Game.data;

import com.Game.enumerations.TileType;
import com.Game.utilities.Coordinate;

public class Tile {

    private TileType tileType;
    private Coordinate position;

    public Tile(int x, int y, TileType tileType) {
        this.tileType = tileType;
        this.position = new Coordinate(x, y);
    }

    public Tile(Coordinate pos, TileType tileType) {
        this.tileType = tileType;
        this.position = pos;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

}
