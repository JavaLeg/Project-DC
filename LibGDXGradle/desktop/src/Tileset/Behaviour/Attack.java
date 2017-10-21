package Tileset.Behaviour;

import java.util.LinkedList;
import java.util.List;

import java.io.Serializable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
	
	private final int NUM_FRAMES = 4;
	
	private Coord coord;
	
	private Sprite[] animationFrames;
	
	private String path = "SpriteFamily/attack/";
	
	private Animation animation;
	private Dir dir;
	
	private enum Type {
		ONE, TWO, THREE, FOUR
	}
	
	public enum Dir {
		LEFT, RIGHT, DOWN, UP
	}
	
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
	
	public Attack() {
		this.animationFrames = new Sprite[NUM_FRAMES];
		// load the attack image
		this.coord = null;
		this.dir = Dir.LEFT;
		this.loadImage(Type.ONE);
	}
	
	public Attack(Dir dir, Coord coord) {
		this.animationFrames = new Sprite[NUM_FRAMES];
		this.coord = coord;
		this.dir = dir;
		// load the attack image
		this.loadImage(Type.ONE);
	}
	
	public Animation getAttack() {
		return animation;
	}
	
	public Sprite[] getAnimationFrames() {
		return this.animationFrames;
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
	private void loadImage(Type type) {
		switch (type) {
		case ONE:
			path += "cut_a/";
			break;
		case TWO:
			path += "cut_b/";
			break;
		case THREE:
			path += "cut_c/";
			break;
		default:
			path += "cut_d/";
			break;
		}
		
		for (int i = 0; i < NUM_FRAMES; i++) {
			String filePath = path + (i + 1) + ".png";
			Sprite sprite = new Sprite(new Texture(filePath));
			// sets the size and position
			sprite.setBounds(0, 0, 40, 40);
			// sets the rotation origin to the middle
			sprite.setOrigin(sprite.getWidth()/2f, sprite.getHeight()/2f);
			// rotate
			sprite.setRotation(this.getRotation());
			
			animationFrames[i] = sprite;
		}
		animation = new Animation(1f/20f, animationFrames);
	}
	
	private float getRotation() {
		switch (this.dir) {
		case LEFT:
			return 0;
		case RIGHT:
			return 180;
		case DOWN:
			return 90;
		case UP:
			return 270;
		default:
			return 0;	
		}
	}
	
	
	public int getAttackCooldown() {
		return cooldown;
	}
	
	public List<Coord> getHitBox() {
		return hitbox;
	}
}
