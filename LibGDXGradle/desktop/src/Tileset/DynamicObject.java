package Tileset;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;

import Interface.ObjectModel;
import State.Coord;
import State.State;
import Tileset.Behaviour.MoveBehaviour;
import Tileset.Behaviour.MoveTrack;

// DynamicObject is in charge of: hp
public class DynamicObject extends GameObject implements Cloneable, Serializable{
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
	
	private MoveTrack moves;
	private double hp;
	private double contactDamage; // how much damage entity deals
	
	private int iFrames;
	private static int iFramesMax = 30; // one second
	
	private HashMap<Status, Integer> statuses;
	
	
	public DynamicObject(ObjectType type, Coord position, double hp, double damage, String img_path) {
		super(type, position, img_path);
		this.hp = hp;
		this.contactDamage = damage;
		this.iFrames = 0;
		statuses = new HashMap<Status, Integer>();
	}
	
	public DynamicObject(ObjectType type, double hp, double damage, String img_path) {
		super(type, img_path);
		this.hp = hp;
		this.contactDamage = damage;
		this.iFrames = 0;
		statuses = new HashMap<Status, Integer>();
	}
	
	public DynamicObject(ObjectType type, String img_path) {
		super(type, img_path);
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
		if (moves.nextStep(s, this.getCoord()) != null) {
			
		}
		// general management
		if (getHp() < 0) {
			destroy(s);
		}
		if (iFrames > 0) {
			iFrames--;
		}
	}
	
	public boolean canChangePosition() {
		// test state here
		return true;
	}
	
	/*
	 * Used only for dynamic objects (EDITOR SIDE)
	 */
	/* No longer needed
	public ObjectModel getModel() {
		Texture texture = super.getTexture().getTexture();
		String texturePath = ((FileTextureData)texture.getTextureData()).getFileHandle().path();
		DynamicObjectType type = DynamicObjectType.valueOf(super.getType().toString());
		ObjectModel model = new ObjectModel(hp, contactDamage, texturePath, super.getName(), type);
		return model;
	}*/
	
	public DynamicObject clone() throws CloneNotSupportedException {
		return new DynamicObject(getType(), hp, contactDamage, getImgPath());
	}
}
