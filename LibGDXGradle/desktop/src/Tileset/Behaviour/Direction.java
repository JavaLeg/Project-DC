package Tileset.Behaviour;

import State.Coord;

public enum Direction {NORTH, SOUTH, EAST, WEST;
	
	// naive, only handles adjacent coords
	public Direction getDirection(Coord from, Coord to) {
		if (from.getX() == to.getX() - 1) {
			return EAST;
		} else if (from.getX() == to.getX() + 1) {
			return WEST;
		} else if (from.getY() == to.getY() - 1) {
			return NORTH;
		} else if (from.getX() == to.getX() + 1) {
			return SOUTH;
		}
		return null;
	}

}
