package Tileset.Behaviour;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

import State.Coord;
import State.State;
import Tileset.GameObject.ObjectType;

public class Attack {
	
	private final int NUM_FRAMES = 4;
	private int damage;
	private List<Coord> hitbox;
	private List<ObjectType> targets;
	
	private Sprite[] animationFrames;
	
	private String path = "SpriteFamily/attack/";
	
	private Animation animation;
	
	private enum Type {
		ONE,
		TWO,
		THREE,
		FOUR
	}
	
	// likely takes in some sort of id or animaton
	public Attack(List<Coord> hitbox, int damage, List<ObjectType> targets) {
		this.hitbox = hitbox;
		this.damage = damage;
		this.targets = targets;
		this.animationFrames = new Sprite[NUM_FRAMES];
		// load the attack image
		this.loadImage(Type.FOUR);
	}
	
	public Attack() {
		this.animationFrames = new Sprite[NUM_FRAMES];
		// load the attack image
		this.loadImage(Type.ONE);
	}
	
	public Animation getAttack() {
		return animation;
	}
	
	public Sprite[] getAnimationFrames() {
		return this.animationFrames;
	}
	
	public void applyAttack(State s) {
		// grabs valid objects from State and damages all
		
		// TODO STUB UNTIL STATE UPDATED
		
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
//			sprite.setScale((float) 0.1);
			sprite.setBounds(0, 0, 40, 40);
			System.out.println("scale = " + sprite.getScaleX());
			animationFrames[i] = sprite;
		}
		animation = new Animation(1f/20f, animationFrames);
	}
	
	
}
