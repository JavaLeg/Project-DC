package Tileset.Behaviour;

import State.Coord;

public enum Direction {NORTH, SOUTH, EAST, WEST;

	public Direction getDirection(Coord from, Coord to) {
		if (to == null || from == null) return this;
		if (from.getX() < to.getX()) {
			return EAST;
		} else if (from.getX() > to.getX()) {
			return WEST;
		} else if (from.getY() < to.getY()) {
			return NORTH;
		} else if (from.getX() > to.getX()) {
			return SOUTH;
		}
		return this;
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
