package Tileset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import State.Coord;

// Game object is in charge of: image/sprite, ObjectType, Coordinates
public class GameObject extends Image {
	public static enum ObjectType {
		PLAYER, ENEMY, TERRAIN, ITEM;
	}
	
	private ObjectType type;
	private Coord position;
	private Sprite sprite;
	// height and width are in Actor

	// TODO: WE MAY NOT NEED POSITION
	public GameObject(ObjectType type, Coord position, Texture texture) {
		super(texture);
		this.type = type;
		this.position = position;
		// initial position on screen 0,0 is bottom left
//		this.setPosition(x, y);
	}
	
	/* 
	 * Needs to handle TextureRegions
	 */
	public GameObject(ObjectType type, TextureRegion texture) {
		super(texture);
		this.type = type;
		// initial position on screen 0,0 is bottom left
//		this.setPosition(x, y);
	}
	
	/*
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch);
	}
	*/
	
	public boolean isDynamic() {
		if(type == ObjectType.PLAYER || type == ObjectType.ENEMY) {
			return true;
		} else {
			return false;
		}
	}
	
	public ObjectType getType() {
		return this.type; 
	}
	
	public Coord getCoord() {
		return position;
	}
	
	public void setCoord(Coord coord) {
		position = coord;
	}
	
	// use the whole image
	public static Texture getTexture(String imageName) {
//		this.sprite = new Sprite(new Texture(Gdx.files.internal(imageName)));
		return new Texture(Gdx.files.internal(imageName));
	}

}
