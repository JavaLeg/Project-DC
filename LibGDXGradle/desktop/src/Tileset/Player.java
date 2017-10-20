package Tileset;


import java.io.Serializable;

import State.Coord;
import State.State;

public class Player extends DynamicObject implements Cloneable, Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 629680615400266941L;

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
	
	@Override
	public Player clone() throws CloneNotSupportedException {
		return (Player) super.clone();
	}
	
	
}
