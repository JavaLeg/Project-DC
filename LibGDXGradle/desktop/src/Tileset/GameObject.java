package Tileset;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import State.Coord;

public class GameObject extends Image implements Cloneable {
	
	public static enum ObjectType {
		PLAYER, ENEMY, WALL, ITEM, FLOOR;
	}
	
	private ObjectType type;
	private Coord position;
	private TextureRegion cur_texture;
	private boolean passable;
	// height and width are in Actor
	
	
	public GameObject(ObjectType type, TextureRegion texture) {
		super(texture);
		this.type = type;
		this.position = null;
		this.cur_texture = texture;
	}
	
	
	/*
	 * Oscar's game object initialisation using co-ordinates
	 */
	public GameObject(ObjectType type, Coord position, TextureRegion texture) {
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
	

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

}
