package Tileset;

import com.badlogic.gdx.graphics.Texture;

import State.Coordinates;

public class Terrain extends GameObject {
	protected boolean passable;
	
	
	public Terrain( Coordinates position,  boolean passable, Texture texture) {
		super(ObjectType.TERRAIN,  position, texture);
		this.passable = passable;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
}
