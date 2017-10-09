package Tileset;

import State.Coordinates;
import State.State;

// DynamicObject is in charge of: hp
public class DynamicObject extends GameObject {
	// Dynamic types are: Enemy, Player, Trap
	// Not dynamic types are: Terrain, Item
	public static enum DynamicObjectType {
		PLAYER, ENEMY
	} 
	
	private double hp;
	private double damage; // how much damage entity deals
	
	
	public DynamicObject(ObjectType type, int width, int height, Coordinates position, double hp, double damage) {
		super(type, width, height, position);
		this.hp = hp;
		this.damage = damage;
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
	
	public void heal(double hp) {
		this.hp += hp;
	}
	
	
	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	
	public void destroy(State s) {
		s.getTile(this.getCoord()).deleteObject();
	}
	
	public void step(State activeState) {
		// Does nothing here
		// Implemented in Enemy and Trap
		// Ignored by player
	}
}
