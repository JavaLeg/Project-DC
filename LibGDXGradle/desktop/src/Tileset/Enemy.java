package Tileset;


import java.io.Serializable;

import State.Coord;
import State.State;
import Tileset.Behaviour.Attack;
import Tileset.Behaviour.MoveBehaviour;

public class Enemy extends DynamicObject implements Cloneable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5193796609184048030L;
	private int moveRate;
	private int sinceLastMove;
	private MoveBehaviour moveBehaviour;
	
	// currently only supports one type of attack
	// Change to HASHMAP<Attack, int> 
	private int attackRate;
	private int sinceLastAttack;
	private int attackTime;
	private Attack attack; 

	public Enemy(Coord position, double hp, double damage, int moveRate, MoveBehaviour b, String img_path) {
		super(ObjectType.ENEMY, position,  hp, damage, img_path);
		this.moveRate = moveRate;
		this.sinceLastMove = 0;
		this.attackRate = 60;
		this.sinceLastAttack = 0;
		this.moveBehaviour = b;
	}
	
	/*
	 * Initialized from the editor
	 */
	public Enemy(String img_path) {
		super(ObjectType.ENEMY, img_path);
		this.moveRate = moveRate;
		this.sinceLastMove = 0;
		this.attackRate = 60;
		this.sinceLastAttack = 0;
		//this.moveBehaviour = b;
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
		
		// movement behavior
		if (sinceLastMove >= moveRate && moveBehaviour != null) {
			// move one step
			Coord next = null;
			next = moveBehaviour.nextStep(s, this.getCoord());
			if (s.findPlayer().equals(next)) {
				s.getPlayer().damage(this.getContactDamage()); // contact damage
			} else {
				this.setCoord(next);
			}
			this.setLastMove(0);
		} else {
			sinceLastMove++;
		}
		
		
		// attack behaviour
		if (sinceLastAttack >= attackRate && attack != null) {
			attack.applyAttack(s);
		} else {
			sinceLastAttack++;
		}
	}
	
	@Override
	public Enemy clone() throws CloneNotSupportedException {
		return (Enemy) super.clone();
	}

	public int getMoveRate() {
		return moveRate;
	}

	public void setMoveRate(int moveRate) {
		this.moveRate = moveRate;
	}

	public int getSinceLastMove() {
		return sinceLastMove;
	}

	public void setSinceLastMove(int sinceLastMove) {
		this.sinceLastMove = sinceLastMove;
	}

	public MoveBehaviour getMoveBehaviour() {
		return moveBehaviour;
	}

	public void setMoveBehaviour(MoveBehaviour moveBehaviour) {
		this.moveBehaviour = moveBehaviour;
	}

	public int getAttackRate() {
		return attackRate;
	}

	public void setAttackRate(int attackRate) {
		this.attackRate = attackRate;
	}

	public int getSinceLastAttack() {
		return sinceLastAttack;
	}

	public void setSinceLastAttack(int sinceLastAttack) {
		this.sinceLastAttack = sinceLastAttack;
	}

	public Attack getAttack() {
		return attack;
	}

	public void setAttack(Attack attack) {
		this.attack = attack;
	}

	public int getAttackTime() {
		return attackTime;
	}

	public void setAttackTime(int attackTime) {
		this.attackTime = attackTime;
	}
}
