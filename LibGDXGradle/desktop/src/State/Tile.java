package State;

import java.io.Serializable;

import Tileset.*;
import Tileset.EnemyFactory.EnemyType;
import Tileset.GameObject.ObjectType;

public class Tile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Coordinates coord;
	private Player player;
	private Enemy enemy;
	private Trap trap;
	private Item item;
	private Terrain terrain;
	
	
	//************************//
	//******* CREATORS *******//
	//************************//
	
	// Default Create a Tile
	public Tile(){
		this.player = null;
		this.enemy = null;
		this.trap = null;
		this.item = null;
		this.terrain = null;
		this.coord = new Coordinates();
	}
	
	// Create a Tile given x and y coords as Coordinates object
	public Tile(Coordinates coord){
		this.player = null;
		this.enemy = null;
		this.trap = null;
		this.item = null;
		this.terrain = null;
		this.coord = new Coordinates();
		this.coord.setX(coord.getX());
		this.coord.setY(coord.getY());
	}
	
	
	
	//************************//
	//****** COORDINATES *****//
	//************************//
	
	// Get Tile coords as Coordinates object
	public Coordinates getCoord(){
		return this.coord;
	}
	
	// Set Tile coords given Coordinate object
	public void setCoord(Coordinates coord){
		this.coord.setX(coord.getX());
		this.coord.setY(coord.getY());
	}
	
	
	
	//************************//
	//******* GENERAL ********//
	//************************//
	
	// Checks if Tile has object type, returns false if invalid type
	public boolean hasObject(ObjectType type) {
		switch(type) {
		case PLAYER:
			return (this.player != null);
		case ENEMY:
			return (this.enemy != null);
		case TRAP:
			return (this.trap != null);
		case ITEM:
			return (this.item != null);
		case TERRAIN:
			return (this.terrain != null);
		default:
			return false;
		}
	}
	
	// Gets object, can return null
	public GameObject getObject(ObjectType type) {
		switch(type) {
		case PLAYER:
			return this.player;
		case ENEMY:
			return this.enemy;
		case TRAP:
			return this.trap;
		case ITEM:
			return this.item;
		case TERRAIN:
			return this.terrain;
		default:
			return null;
		}
	}
	
	// Set object if empty, returns false if already has object of that type or new object is invalid
	public boolean setObject(GameObject newObject) {
		ObjectType type = newObject.getType();
		
		switch(type) {
		case PLAYER:
			if(this.player != null) return false;
			this.player = (Player) newObject;
			return true;
		case ENEMY:
			if(this.enemy != null) return false;
			this.enemy = (Enemy) newObject;
			return true;
		case TRAP: 
			if(this.trap != null) return false;
			this.trap = (Trap) newObject;
			return true;
		case ITEM:
			if(this.item != null) return false;
			this.item = (Item) newObject;
			return true;
		case TERRAIN:
			if(this.terrain != null) return false;
			this.terrain = (Terrain) newObject;
			return true;
		default:
			return false;
		}
	}
	
	// Deletes object if exists, returns false if does not exist or invalid type
	public boolean deleteObject(ObjectType type) {
		switch(type) {
		case PLAYER:
			if(this.player == null) return false;
			this.player = null;
			return true;
		case ENEMY:
			if(this.enemy == null) return false;
			this.enemy = null;
			return true;
		case TRAP:
			if(this.trap == null) return false;
			this.trap = null;
			return true;
		case ITEM:
			if(this.item == null) return false;
			this.item = null;
			return true;
		case TERRAIN:
			if(this.terrain == null) return false;
			this.terrain = null;
			return true;
		default:
			return false;
		}
	}
}