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

	
	// assumes initial direction was NORTH
	public Coord rotate(Coord from) {
		switch(this) {
		case EAST:
			return new Coord(from.getY(), -from.getX());
		case NORTH:
			return from.clone();
		case SOUTH:
			return new Coord(-from.getX(), -from.getY());
		case WEST:
			return new Coord(-from.getY(), from.getX());
		}
		return from.clone();
	}
	
}
