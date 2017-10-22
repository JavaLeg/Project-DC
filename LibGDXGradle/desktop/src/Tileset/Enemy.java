package Tileset;


import java.io.Serializable;

import State.Coord;
import State.State;
import Tileset.Behaviour.Attack;
import Tileset.Behaviour.AttackAnimation;
import Tileset.Behaviour.AttackAnimation.AttackType;
import Tileset.Behaviour.Direction;
import Tileset.Behaviour.MoveBehaviour;
import Tileset.DynamicObject.ActionState;

public class Enemy extends DynamicObject implements Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5193796609184048030L;
	private int moveRate;
	private int sinceLastMove;
	private MoveBehaviour moveBehaviour;
	
	// currently only supports one type of attack
	// Change to HASHMAP<Attack, int> 
	private int attackTime;
	private int sinceLastAttack;
	private Attack attack; 


	public Enemy(Coord position, double hp, double damage, int moveRate, MoveBehaviour b, Attack attack, String img_path) {
		super(ObjectType.ENEMY, position,  hp, damage, img_path);
		this.moveRate = moveRate;
		this.sinceLastMove = 0;
		this.attackTime = 0;
		this.sinceLastAttack = 0;
		this.moveBehaviour = b;
		this.attack = attack;
	}
	

	public Enemy(double hp, double damage, int moveRate, MoveBehaviour b, String img_path) {
		super(ObjectType.ENEMY, hp, damage, img_path);
		this.moveRate = moveRate;
		this.sinceLastMove = 0;
		this.sinceLastAttack = 0;
		this.moveBehaviour = b;
	}
	
	public Enemy(double hp, double damage, int moveRate, MoveBehaviour b, Attack attack, String img_path) {
		super(ObjectType.ENEMY, hp, damage, img_path);
		this.moveRate = moveRate;
		this.sinceLastMove = 0;
		this.sinceLastAttack = 0;
		this.moveBehaviour = b;
		this.attack = attack;
	}
	
	
	
	/*
	 * Initialized from the editor
	 */
	public Enemy(String img_path) {
		super(ObjectType.ENEMY, img_path);
		this.sinceLastMove = 0;
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
	
	@Override
	public void step(State s) {
		
		switch (getActionState()) {
		case ATTACK:
			attack.applyAttack(s, getCoord(), getFacing());
			if (attackTime > 0) {
				attackTime--;
			} else {
				//System.out.print("Switched to Move.\n");
				setActionState(ActionState.MOVE);
			}
			break;
		case MOVE:
			//System.out.println(sinceLastMove + " " + moveRate + " " + moveBehaviour);
			// movement behavior
			if (sinceLastMove >= moveRate && moveBehaviour != null) {
				// move one step
				
				Coord next;
				next = moveBehaviour.nextStep(s, this.getCoord());
				if (s.findPlayer().equals(next)) { // may never happen how movement currently is handled
					s.getPlayer().damage(this.getContactDamage()); // contact damage
				} else {
					if (!s.isBlocked(next)) {
						s.moveObject(getCoord(), next);
						setFacing(getFacing().getDirection(getCoord(), next));
					}
				}
				
				this.setLastMove(0);
			} else {
				sinceLastMove++;
			}
			
			// attack behaviour
			if (attack != null) {
				if (sinceLastAttack >= attack.getAttackCooldown()) {
					// face player
					AttackAnimation a = new AttackAnimation(AttackType.TWO);
					Direction d = getFacing().getDirection(getCoord(), s.findPlayer());
					Coord coord = d.moveInDirection(getCoord());
					a.add(coord,  d);
					//AttackAnimation att = new AttackAnimation(p.getDirection() , coord);
					s.addActor(a);

					
					setActionState(ActionState.ATTACK);
					//System.out.print("Switched to Attack.\n");
					attackTime = attack.getAttackSpeed();
					sinceLastAttack = 0;
				} else {
					sinceLastAttack++;
				}
			}
			
			break;
		case DISABLED:
			break;
		default:
			break;
		
		}
		super.step(s);
	}
	
	public Enemy clone() {
		return new Enemy(getHp(), getContactDamage(), moveRate, moveBehaviour, attack, getImgPath());
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

	public int getSinceLastAttack() {
		return sinceLastAttack;
	}

	public void setSinceLastAttack(int sinceLastAttack) {
		this.sinceLastAttack = sinceLastAttack;
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
