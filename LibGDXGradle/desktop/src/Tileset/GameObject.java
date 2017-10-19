package Tileset;

import java.io.Serializable;

import State.Coord;

public class GameObject implements Cloneable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7501274792615810029L;


	public static enum ObjectType {
		PLAYER, ENEMY, WALL, ITEM, FLOOR;
	}
	
	private ObjectType type;
	private Coord position;
	private boolean passable;
	private String name;
	private String imgPath;
	// height and width are in Actor
	
	
	public GameObject(ObjectType type, String imgPath) {
		this.type = type;
		this.position = null;
		this.setImgPath(imgPath);
	}
	
	
	/*
	 * Oscar's game object initialisation using co-ordinates
	 */
	public GameObject(ObjectType type, Coord position, String imgPath) {
		this.type = type;
		this.position = position;
		this.setImgPath(imgPath);
	}

	
	public GameObject clone() throws CloneNotSupportedException {
		return (GameObject)super.clone();
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
	
	public String getName() {
		return name;
	}

	public void setName(String s) {
		this.name = s;
	}


	public String getImgPath() {
		return imgPath;
	}


	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
}
