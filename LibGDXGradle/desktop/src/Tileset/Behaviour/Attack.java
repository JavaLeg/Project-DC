package Tileset.Behaviour;

import java.util.LinkedList;
import java.util.List;

import State.Coord;
import State.State;
import Tileset.DynamicObject;
import Tileset.GameObject;
import Tileset.GameObject.ObjectType;

public class Attack {
	private int damage;
	private List<Coord> hitbox; // treats default facing as NORTH
	private List<ObjectType> targets;
	private int speed;
	private int cooldown;
	
	
	// likely takes in some sort of id or animaton as well
	// does not clone lists
	public Attack(List<Coord> hitbox, int damage, List<ObjectType> targets, int speed, int cooldown) {
		this.hitbox = hitbox;
		this.damage = damage;
		this.targets = targets;
		this.speed = speed;
		this.cooldown = cooldown;
	}
	
	
	public void applyAttack(State s, Coord origin, Direction facing) {
		// grabs valid objects from State and damages all
		// TODO: rotate with facing
		List<Coord> adjustedHitBox = new LinkedList<Coord>();
		for (Coord c : hitbox) {
			adjustedHitBox.add(facing.rotate(c));
		}
		
		for (Coord c : applyHitBox(adjustedHitBox, origin)) {
			DynamicObject g = s.getDynamicObject(c);
			if (g != null && targets.contains(g.getType())) {
				g.damage(damage);
			}
		}
	}

	
	public List<Coord> applyHitBox(List<Coord> hitbox, Coord origin) {
		List<Coord> hits = new LinkedList<Coord>();
		for (Coord c : hitbox) {
			hits.add(new Coord(c.getX() + origin.getX(), c.getY() + origin.getY()));
		}
		return hits;
	}
	
	public int getAttackSpeed() {
		return speed;
	}
	
	public int getAttackCooldown() {
		return cooldown;
	}
	
	public List<Coord> getHitBox() {
		return hitbox;
	}
}
