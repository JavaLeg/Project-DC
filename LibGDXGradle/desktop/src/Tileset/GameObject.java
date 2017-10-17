package Tileset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import State.Coord;

// Game object is in charge of: image/sprite, ObjectType, Coordinates
public class GameObject extends Image implements Cloneable {
	public static enum ObjectType {
		PLAYER, ENEMY, WALL, ITEM, FLOOR;
	}
	
	private ObjectType type;
	private Coord position;
	private TextureRegion cur_texture;
	private String name;
	// height and width are in Actor
	/* 
	 * Needs to handle TextureRegions
	 */
	public GameObject(ObjectType type, TextureRegion texture) {
		super(texture);
		this.type = type;
		this.cur_texture = texture;
	}
	
	/*
	 * Oscar's game object initialisation using co-ordinates
	 */
	public GameObject(ObjectType item, Coord position, TextureRegion texture) {
		// TODO Auto-generated constructor stub
		super(texture);
		this.type = type;
		this.position = position;
		this.cur_texture = texture;
	}

	public GameObject clone() throws CloneNotSupportedException {
		return (GameObject)super.clone();
	}
	
	public TextureRegion getTexture() {
		return this.cur_texture;
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
	
	public String getName() {
		return name;
	}

	public void setName(String s) {
		this.name = s;
	}
}
