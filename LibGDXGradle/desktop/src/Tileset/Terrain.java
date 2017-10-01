package Tileset;

public class Terrain extends GameObject {
	/*
	public static enum Elevation {
		LEVEL, UPPER, LOWER, UPRAMP, DOWNRAMP
	}
	*/

	protected boolean passable;
	// protected Elevation level;
	
	public boolean checkPassable() {
		return passable;
	}
	
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
	
	/*
	public boolean checkWalkable(Elevation currentLevel) {
		if (this.level == currentLevel) return true;
		// TO DO: able to go up/down if UPRAMP/DOWNRAMP
	}
	*/
}
