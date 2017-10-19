package Tileset;


import State.Coord;
import State.State;

public class Player extends DynamicObject {
	

	/*
	 * Initialized from the editor
	 */
	public Player(String img_path) {
		super(ObjectType.PLAYER, img_path);
	}
	
	public Player(Coord position,  double hp, double damage, String img_path) {
		// type, width, height, coords, hp, damage
		super(ObjectType.PLAYER, position, hp, damage, img_path);
	}
	
	@Override
	public void step(State s) {
		super.step(s);

	}
}
