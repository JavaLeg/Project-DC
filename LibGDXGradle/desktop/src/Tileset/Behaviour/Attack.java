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
	private List<Coord> hitbox;
	private List<ObjectType> targets;
	
	
	// likely takes in some sort of id or animaton as well
	// does not clone lists
	public Attack(List<Coord> hitbox, int damage, List<ObjectType> targets) {
		this.hitbox = hitbox;
		this.damage = damage;
		this.targets = targets;
	}
	
	public void applyAttack(State s, Coord origin) {
		// grabs valid objects from State and damages all
		for (Coord c : applyHitBox(origin)) {
			DynamicObject g = s.getDynamicObject(c);
			if (g != null && targets.contains(g.getType())) {
				g.damage(damage);
			}
		}
		
	}
	
	public List<Coord> applyHitBox(Coord origin) {
		List<Coord> hits = new LinkedList<Coord>();
		for (Coord c : hitbox) {
			hits.add(new Coord(c.getX() + origin.getX(), c.getY() + origin.getY()));
		}
		return hits;
	}
	
}
