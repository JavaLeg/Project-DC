package Tileset;

import java.io.Serializable;
import java.util.HashMap;


import State.Coord;
import State.State;
import Tileset.Behaviour.Attack;
import Tileset.Behaviour.Direction;

// DynamicObject is in charge of: hp
public class DynamicObject extends GameObject implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6521298265497519435L;

	// Dynamic types are: Enemy, Player, Trap
	// Not dynamic types are: Terrain, Item
	public static enum DynamicObjectType {
		PLAYER, ENEMY, ITEM
	} 
	
	public static enum Status {
		POISON, STUN, SLOW
	}

	public static enum ActionState {
		MOVE, // standard state, can move and switch to ATTACK state
		ATTACK, // state when attacking, does not move
		DISABLED // disables all actions and stepping
	}

	private double hp;
	private double maxHp;
	private double contactDamage; // how much damage entity deals
	
	private int iFrames;
	private static int iFramesMax = 30; // quarter second
	
	private HashMap<Status, Integer> statuses;
	private ActionState state;
	private Direction facing;
	
	public DynamicObject(ObjectType type, Coord position, double hp, double damage, String img_path) {
		super(type, position, img_path);
		this.hp = hp;
		this.contactDamage = damage;
		this.iFrames = 0;
		statuses = new HashMap<Status, Integer>();
		facing = Direction.NORTH;
		state = ActionState.MOVE;
	}
	
	public DynamicObject(ObjectType type, double hp, double damage, String img_path) {
		super(type, img_path);
		this.hp = hp;
		this.maxHp = hp;
		this.contactDamage = damage;
		this.iFrames = 0;
		statuses = new HashMap<Status, Integer>();
		state = ActionState.MOVE;
	}
	
	public DynamicObject(ObjectType type, String img_path) {
		super(type, img_path);
		this.iFrames = 0;
		statuses = new HashMap<Status, Integer>();
		facing = Direction.NORTH;
		state = ActionState.MOVE;
	}
	
	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}
	
	public void damage(double hp) {
		if (iFrames > 0) return;
		this.hp -= hp;
		iFrames = iFramesMax;
		System.out.println("Got hit! You have " + this.hp + " Hp Left...");
	}
	
	public void heal(double hp) {
		this.hp += hp;
		if (this.hp > maxHp) {
			this.hp = maxHp;
		}
	}
	
	
	public double getContactDamage() {
		return contactDamage;
	}

	public void setContactDamage(double damage) {
		this.contactDamage = damage;
	}

	public boolean hasStatus(Status s) {
		return statuses.get(s) != null;
	}
	
	public void destroy(State s) {
		// animation!!!
		s.deleteObject(this);
	}
	
	
	public void step(State s) {
		// potential ongoing effects
		//System.out.print("step\n");
		
		// statuses
		for (Status stat : Status.values()) {
			Integer stepsLeft = statuses.get(stat);
			if (stepsLeft != null) {
				
				// handle effects
				switch(stat) {
				case POISON:
					break;
				case SLOW:
					break;
				case STUN:
					break;
				default:
					break;
				}
				
				// handle time left
				stepsLeft--;
				if (stepsLeft == 0) {
					statuses.remove(stat);
				} else {
					statuses.put(stat, stepsLeft);
				}
			}
		}
		
		// general management
		if (getHp() <= 0) {
			destroy(s);
		}
		if (iFrames > 0) {
			iFrames--;
		}
	}
	
	public void setActionState(ActionState s) {
		state = s;
	}
	
	public ActionState getActionState() {
		return state;
	}
	
	public boolean canChangePosition() {
		// test state here
		return (state == ActionState.MOVE);
	}

	public DynamicObject clone() {
		return new DynamicObject(getType(), hp, contactDamage, getImgPath());
	}
	
	public void setCoord(Coord coord) {
		if (getCoord() != null)
			facing = facing.getDirection(getCoord(), coord);
		else 
			facing = Direction.NORTH;
		super.setCoord(coord);
	}
	
	public Direction getFacing() {
		return facing;
	}

	public Attack getAttack() {
		return null;
	}
	
}
