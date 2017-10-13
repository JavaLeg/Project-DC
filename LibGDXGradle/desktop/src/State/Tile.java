package State;

import java.io.Serializable;

import Tileset.*;
import Tileset.EnemyFactory.EnemyType;
import Tileset.GameObject.ObjectType;

public class Tile implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Coordinates coord;
	private GameObject object;
	
	
	//************************//
	//******* CREATORS *******//
	//************************//
	
	// Default Create a Tile
	public Tile(){
		this.object = null;
		this.coord = new Coordinates();
	}
	
	// Create a Tile given x and y coords as Coordinates object
	public Tile(Coordinates coord){
		this.object = null;
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
	
	// Checks if Tile has object type
	public ObjectType checkObject() {
		return this.object.getType();
	}
	
	// Check  has object
	public boolean hasObject() {
		return this.object != null;
	}

	
	// Gets object, can return null
	public GameObject getObject() {
		return this.object;
	}
	
	// Set object if empty, returns false if already has object
	public void setObject(GameObject newObject) {
		this.object = newObject;
		newObject.setCoord(this.coord);
	}
	
	// Deletes object if exists, returns false if does not exist or invalid type
	public void deleteObject() {
		this.object = null;
	}
}