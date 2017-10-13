package Tileset;

import com.badlogic.gdx.graphics.Texture;

import State.Coordinates;

public class Player extends DynamicObject {

	// private int hpPots; // items
	// private Weapon type;
	
	public Player(Coordinates position, Texture texture, double hp, double damage) {
		// type, width, height, coords, hp, damage
		super(ObjectType.PLAYER, 16, 16, position, texture, hp, damage);
	}
}
