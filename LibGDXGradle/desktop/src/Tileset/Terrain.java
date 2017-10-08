package Tileset;

import State.Coordinates;

public class Terrain extends GameObject {
	protected boolean passable;
	
	
	public Terrain(int width, int height, Coordinates position, boolean passable) {
		super(ObjectType.TERRAIN, width, height, position);
		this.passable = passable;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
}
