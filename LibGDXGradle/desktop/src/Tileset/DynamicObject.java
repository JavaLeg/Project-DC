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

public class DynamicObject extends GameObject implements Cloneable{
	
	public static enum DynamicObjectType {
		PLAYER, ENEMY
	} 
	
	public static enum Status {
		POISON, STUN, SLOW
	}
	
	
	private double curHp;
	private double maxHp;
	private double contactDamage; // how much damage entity deals
	
	private int iFrames;
	private static int iFramesMax = 30; // one second
	
	private HashMap<Status, Integer> statuses;
	
	
	public DynamicObject(ObjectType type, Coord position, double hp, double damage, Texture texture) {
		super(type, position, new TextureRegion(texture));
		this.maxHp = hp;
		this.curHp = hp;
		this.contactDamage = damage;
		this.iFrames = 0;
		statuses = new HashMap<Status, Integer>();
	}
	
	public DynamicObject(ObjectType type, double hp, double damage, Texture texture) {
		super(type, new TextureRegion(texture));
		this.maxHp = hp;
		this.curHp = hp;
		this.contactDamage = damage;
		this.iFrames = 0;
		statuses = new HashMap<Status, Integer>();
	}
	
	public double getHp() {
		return curHp;
	}

	public void setHp(double hp) {
		if (hp > maxHp) {
			curHp = maxHp;
			return;
		}
		this.curHp = hp;
	}
	
	public double getMaxHp() {
		return maxHp;
	}
	
	public void setMaxHp(double hp) {
		this.maxHp = hp;
	}
	
	public void damage(double hp) {
		if (iFrames > 0) return;
		this.curHp -= hp;
		iFrames = iFramesMax;
	}
	
	public void heal(double hp) {
		this.curHp += hp;
		if (curHp > maxHp) {
			curHp = maxHp;
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
	public ObjectModel getModel() {
		Texture texture = super.getTexture().getTexture();
		String texturePath = ((FileTextureData)texture.getTextureData()).getFileHandle().path();
		DynamicObjectType type = DynamicObjectType.valueOf(super.getType().toString());
		ObjectModel model = new ObjectModel(curHp, contactDamage, texturePath, super.getName(), type);
		return model;
	}
	
	public DynamicObject clone() throws CloneNotSupportedException {
		return (DynamicObject)super.clone();
	}
}
