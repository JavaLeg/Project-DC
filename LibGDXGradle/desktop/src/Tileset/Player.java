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
	private Attack selected;
	
	private int attackCooldown;
	
	
	public Player(Coord position, double hp, double damage, Attack light, Attack special, Texture texture) {
		// type, width, height, coords, hp, damage
		super(ObjectType.PLAYER, position, hp, damage, texture);
		this.light = light;
		this.special = special;
		
		this.selected = light;
	}
	
	@Override
	public void step(State s) {
		super.step(s);
		switch (this.getActionState()) {
		case ATTACK:
			selected.applyAttack(s, getCoord(), currentFacing);
			if (attackCooldown > 0) {
				attackCooldown--;
			} else {
				this.setActionState(ActionState.MOVE);
			}
			break;
		case DISABLED:
			break;
		case MOVE:
			break;
		
		}
		
		
	}
	
	public void selectSpecial() {
		selected = special;
	}
	
	public void selectLight() {
		selected = light;
	}
	
	public Direction getFacing() {
		return currentFacing;
	}
	
	public void setFacing(Direction d) {
		currentFacing = d;
	}
}
