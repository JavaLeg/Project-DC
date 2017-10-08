package Tileset;

import State.Coordinates;

public class Player extends DynamicObject {

	// private int hpPots; // items
	// private Weapon type;
	
	public Player(Coordinates position, double hp, double damage) {
		// type, width, height, coords, hp, damage
		super(ObjectType.PLAYER, 16, 16, position, hp, damage);
	}
}
