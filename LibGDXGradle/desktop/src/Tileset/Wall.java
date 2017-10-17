package Tileset;

import com.badlogic.gdx.graphics.Texture;

import State.Coord;

public class Wall extends GameObject {
	
	public Wall(Coord position,  boolean passable, Texture texture) {
		super(ObjectType.TERRAIN,  position, texture);
	}
}
