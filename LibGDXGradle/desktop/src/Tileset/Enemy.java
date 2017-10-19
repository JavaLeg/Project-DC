package Tileset;

import com.badlogic.gdx.graphics.Texture;

import State.Coord;
import State.State;
import Tileset.Behaviour.Attack;
import Tileset.Behaviour.Direction;
import Tileset.Behaviour.MoveBehaviour;

public class Enemy extends DynamicObject {
	
	private int moveRate;
	private int sinceLastMove;
	private MoveBehaviour moveBehaviour;
	
	// currently only supports one type of attack
	// Change to HASHMAP<Attack, int> 
	private int attackTime;
	private int sinceLastAttack;
	private int attackCooldown;
	private Direction facing;
	private Attack attack; 
	
	
	public Enemy(Coord position, double hp, double damage, int moveRate, MoveBehaviour b, Attack attack, Texture texture) {
		super(ObjectType.ENEMY, position,  hp, damage, texture);
		this.moveRate = moveRate;
		this.sinceLastMove = 0;
		this.attackTime = 0;
		this.sinceLastAttack = 0;
		this.moveBehaviour = b;
		this.attack = attack;
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
		
		switch (getActionState()) {
		case ATTACK:
			attack.applyAttack(s, getCoord(), facing);
			if (attackTime > 0) {
				attackTime--;
			} else {
				setActionState(ActionState.MOVE);
			}
			break;
		case MOVE:
			// movement behavior
			if (sinceLastMove >= moveRate && moveBehaviour != null) {
				// move one step
				Coord next = null;
				next = moveBehaviour.nextStep(s, this.getCoord());
				if (s.findPlayer().equals(next)) { // may never happen how movement currently is handled
					s.getPlayer().damage(this.getContactDamage()); // contact damage
				} else {
					s.moveObject(getCoord(), next);
				}
				this.setLastMove(0);
			} else {
				sinceLastMove++;
			}
			
			// attack behaviour
			if (sinceLastAttack >= attack.getAttackCooldown() && attack != null) {
				setActionState(ActionState.ATTACK);
				attackTime = attack.getAttackSpeed();
			} else {
				sinceLastAttack++;
			}
			break;
		case DISABLED:
			break;
		default:
			break;
		
		}
		
		
		
		
		
	}
}
