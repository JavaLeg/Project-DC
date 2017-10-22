package Tileset.Behaviour;

import java.io.Serializable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import State.Coord;

public class AttackAnimation extends Actor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8542670504107495365L;
	
	private final int NUM_FRAMES = 4;
	private Sprite[] animationFrames;
	private String path = "SpriteFamily/attack/";
	private Animation<Sprite> animation;
	
	private enum Type {
		ONE, TWO, THREE, FOUR
	}

	
	public AttackAnimation() {
		this.animationFrames = new Sprite[NUM_FRAMES];
		// load the attack image
		this.loadImage(Type.ONE, null);
		this.setVisible(false);
	}
	
	public AttackAnimation(String imgPath) {
		this.animationFrames = new Sprite[NUM_FRAMES];
		// load the attack image
		this.loadImage(Type.ONE, imgPath);
		this.setVisible(false);
	}
	
	public void renderAttack(Coord c, Direction d) {
		for (Sprite sprite : animation.getKeyFrames()) {
			sprite.setBounds(c.getX() * 40, c.getY() * 40, 40, 40);
			sprite.setRotation(getRotation(d));
		}
		//animation.
	}
	
	
	private void loadImage(Type type, String imgPath) {
		if (imgPath == null)
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
		else {
			path += imgPath;
		}
		for (int i = 0; i < NUM_FRAMES; i++) {
			String filePath = path + (i + 1) + ".png";
			Sprite sprite = new Sprite(new Texture(filePath));
			// sets the size and position
			
			// sets the rotation origin to the middle
			sprite.setOrigin(sprite.getWidth()/2f, sprite.getHeight()/2f);
			// rotate
			
			animationFrames[i] = sprite;
		}
		animation = new Animation<Sprite>(1f/20f, animationFrames);
	}
	
	private float getRotation(Direction dir) {
		switch (dir) {
		case WEST:
			return 0;
		case EAST:
			return 180;
		case SOUTH:
			return 90;
		case NORTH:
			return 270;
		default:
			return 0;	
		}
	}
	
	float time = 0f;
	
	@Override
	public void act(float delta) {
		if (this.isVisible()) {
			time += delta;
		}
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		 if (this.isVisible()) {
			 if (time < this.animation.getAnimationDuration()) {
				 Sprite s = (Sprite)this.animation.getKeyFrame(time,false);
			 	 s.draw(batch);
			 } else if (time > this.animation.getAnimationDuration()) {
				 this.setVisible(false);
				 time = 0f;
				 
			 }
		 }
		 
	}
	
	
	public Animation getAttack() {
		return animation;
	}
	
	public Sprite[] getAnimationFrames() {
		return this.animationFrames;
	}
	
}
