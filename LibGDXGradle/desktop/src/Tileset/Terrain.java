package Tileset;

import com.badlogic.gdx.graphics.Texture;

import State.Coordinates;

public class Terrain extends GameObject {
	protected boolean passable;
	
	
	public Terrain(int width, int height, Coordinates position, Texture texture, boolean passable) {
		super(ObjectType.TERRAIN, width, height, position, texture);
		this.passable = passable;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
}
