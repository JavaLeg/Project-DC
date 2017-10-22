package Tileset.Behaviour;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import java.io.Serializable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import State.Coord;
import State.State;
import Tileset.DynamicObject;
import Tileset.GameObject.ObjectType;

public class Attack implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7134641387590361982L;
	private int damage;
	private List<Coord> hitbox; // treats default facing as NORTH
	private List<ObjectType> targets;
	private int speed;
	private int cooldown;
	

	

	

	
	// likely takes in some sort of id or animaton as well
	// does not clone lists
	/**
	 * @param hitbox : Relative squares it will damage
	 * @param damage : Amount of damage inflicted on enemies
	 * @param targets : ObjectTypes it damages
	 * @param speed : Amount of time the Attack lasts
	 * @param cooldown : Amount of time it takes to use attack again (applicable to enemies)
	 */
	public Attack(List<Coord> hitbox, int damage, List<ObjectType> targets, int speed, int cooldown) {
		this.hitbox = hitbox;
		this.damage = damage;
		this.targets = targets;
		this.speed = speed;
		this.cooldown = cooldown;
	}
	
	public Attack(int damage, int speed, int cooldown) {
		this.hitbox = Arrays.asList(new Coord(0,1));
		this.damage = damage;
		this.targets = Arrays.asList(ObjectType.PLAYER);
		this.speed = speed;
		this.cooldown = cooldown;
	}



	
	
	public void applyAttack(State s, Coord origin, Direction facing) {
		// grabs valid objects from State and damages all
		List<Coord> adjustedHitBox = new LinkedList<Coord>();
		for (Coord c : hitbox) {
			//System.out.println(facing.rotate(c).toString());
			adjustedHitBox.add(facing.rotate(c));
		}
		
		for (Coord c : applyHitBox(adjustedHitBox, origin)) {
			if (!s.isValid(c)) continue;
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
	
	public boolean isTarget(ObjectType t) {
		return targets.contains(t);
	}
	
	public int getDamage() {
		return damage;
	}
	
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
