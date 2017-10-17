package Tileset;

import com.badlogic.gdx.graphics.Texture;

import State.Coord;
import State.State;
import Tileset.Behaviour.Attack;
import Tileset.Behaviour.Direction;

public class Player extends DynamicObject {
	
	//private static Attack lightAttack = new Attack()
	private Direction currentFacing;
	private Attack light;
	private Attack special; //heavy
	
	
	public Player(Coord position, double hp, double damage, Attack light, Attack special, Texture texture) {
		// type, width, height, coords, hp, damage
		super(ObjectType.PLAYER, position, hp, damage, texture);
		this.light = light;
		this.special = special;
	}
	
	@Override
	public void step(State s) {
		super.step(s);
	}
}
