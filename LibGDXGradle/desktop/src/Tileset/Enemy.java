package Tileset;

import State.MoveBehaviour;
import State.Coordinates;
import State.State;

public class Enemy extends GameObject {
	private double hp;
	// Other enemy fields
	private int moveRate;
	private int sinceLastMove;
	private MoveBehaviour moveBehaviour;
	
	public Enemy(double hp, int moveRate, Coordinates position) {
		super(position);
		this.setHp(hp);
		this.moveRate = moveRate;
	}
	
	public Enemy(double hp, int height, int width, Coordinates position) {
		super(height, width, position);
		this.setHp(hp);
		this.moveBehaviour = null;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}
	
	public void damage(double hp) {
		this.hp -= hp;
	}
	
	public void setBehaviour(MoveBehaviour b) {
		moveBehaviour = b;
	}
	
	
	
	public void step(State s) {
		super.step(s);
		
		// handle movement behaviour
		if (sinceLastMove == moveRate) {
			// move one step
			if (moveBehaviour != null) {
				
				
				
			}
			
			
		} else {
			sinceLastMove++;
		}
		
		
		
	}
	
	/*
	 public void nextMove();
	 */
}
