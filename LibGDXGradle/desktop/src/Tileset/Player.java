package Tileset;

import com.badlogic.gdx.graphics.Texture;

import State.Coord;

public class Player extends DynamicObject {

	// private int hpPots; // items
	// private Weapon type;
	
	public Player(Coord position,  double hp, double damage, Texture texture) {
		// type, width, height, coords, hp, damage
		super(ObjectType.PLAYER, position, hp, damage, texture);
	}
}
