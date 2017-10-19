package Tileset;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;

import Interface.ObjectModel;
import State.Coord;
import State.State;
import Tileset.Behaviour.Direction;

// DynamicObject is in charge of: hp
public class DynamicObject extends GameObject implements Cloneable{
	// Dynamic types are: Enemy, Player, Trap
	// Not dynamic types are: Terrain, Item
	public static enum DynamicObjectType {
		PLAYER, ENEMY
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
	private static int iFramesMax = 15; // one second
	
	private HashMap<Status, Integer> statuses;
	private ActionState state;
	private Direction facing;
	
	public DynamicObject(ObjectType type, Coord position, double hp, double damage, Texture texture) {
		super(type, position, new TextureRegion(texture));
		this.hp = hp;
		this.contactDamage = damage;
		this.iFrames = 0;
		statuses = new HashMap<Status, Integer>();
	}
	
	public DynamicObject(ObjectType type, double hp, double damage, Texture texture) {
		super(type, new TextureRegion(texture));
		this.hp = hp;
		this.maxHp = hp;
		this.contactDamage = damage;
		this.iFrames = 0;
		statuses = new HashMap<Status, Integer>();
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
		s.getTile(this.getCoord()).deleteObject();
	}
	
	public void step(State s) {
		// potential ongoing effects

		
		// statuses
		for (Status stat : Status.values()) {
			Integer stepsLeft = statuses.get(stat);
			if (stat != null) {
				
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
		if (getHp() < 0) {
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
		return state == ActionState.MOVE;
	}
	
	/*
	 * Used only for dynamic objects (EDITOR SIDE)
	 */
	public ObjectModel getModel() {
		Texture texture = super.getTexture().getTexture();
		String texturePath = ((FileTextureData)texture.getTextureData()).getFileHandle().path();
		DynamicObjectType type = DynamicObjectType.valueOf(super.getType().toString());
		ObjectModel model = new ObjectModel(hp, contactDamage, texturePath, super.getName(), type);
		return model;
	}
	
	public DynamicObject clone() throws CloneNotSupportedException {
		return (DynamicObject)super.clone();
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
	
}
