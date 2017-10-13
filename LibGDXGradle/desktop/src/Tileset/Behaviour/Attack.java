package Tileset.Behaviour;

import java.util.List;

import State.Coord;
import State.State;
import Tileset.GameObject.ObjectType;

public class Attack {
	private int damage;
	private List<Coord> hitbox;
	private List<ObjectType> targets;
	
	
	public Attack(List<Coord> hitbox, int damage, List<ObjectType> targets) {
		this.hitbox = hitbox;
		this.damage = damage;
		this.targets = targets;
	}
	
	public void applyAttack(State s) {
		// grabs valid objects from State and damages all
		
		// TODO STUB UNTIL STATE UPDATED
		
	}
	
	
}
