package Tileset;

import com.badlogic.gdx.graphics.Texture;

import State.Coord;
import State.State;
import Tileset.Behaviour.MoveBehaviour;

public class Enemy extends DynamicObject {
	
	private int moveRate;
	private int sinceLastMove;
	private MoveBehaviour moveBehaviour;
	
	
	public Enemy(Coord position, double hp, double damage, int moveRate, MoveBehaviour b, Texture texture) {
		super(ObjectType.ENEMY, position,  hp, damage, texture);
		this.moveRate = moveRate;
		this.sinceLastMove = 0;
		this.moveBehaviour = b;
	}
	
	public void setBehaviour(MoveBehaviour b) {
		moveBehaviour = b;
	}
	
	public void removeBehaviour() {
		moveBehaviour = null;
	}
	
	// Can use to stun enemy
	public void setLastMove(int time) {
		this.sinceLastMove = time;
	}
	
	public void step(State s) {
		super.step(s);
		// handle movement behavior
		if (sinceLastMove == moveRate) {
			// move one step
			if (moveBehaviour != null) {
				Coord next = null;
				next = moveBehaviour.nextStep(s, this.getCoord());
				if (s.findPlayer().equals(next)) {
					s.getPlayer().damage(this.getContactDamage()); // contact damage
				} else {
					this.setCoord(next);
				}
				this.setLastMove(0);
			}
		} else {
			sinceLastMove++;
		}
		
		
		
	}
}
