package Tileset;

import com.badlogic.gdx.graphics.Texture;

import State.Coord;
import State.State;
import Tileset.Behaviour.Attack;

public class Player extends DynamicObject {
	public static enum PlayerMode {
		MOVE, ATTACK, DISABLED;
	}
	//private static Attack lightAttack = new Attack()
	
	
	public Player(Coord position,  double hp, double damage, Texture texture) {
		// type, width, height, coords, hp, damage
		super(ObjectType.PLAYER, position, hp, damage, texture);
	}
	
	@Override
	public void step(State s) {
		super.step(s);
		
		
		
	}
}
