package Interface;

import java.io.Serializable;

import Tileset.DynamicObject;
import Tileset.Enemy;
import Tileset.GameObject;
import Tileset.GameObject.ObjectType;
import Tileset.Item;
import Tileset.Player;

public class TileTuple implements Serializable{
	/**			
	 * 			{PLAYER}{ENEMY}{ITEM}	- Only one can be on this upper layer at one time
	 * 				{FLOOR} {WALL}
	 * 			
	 * 
	 * 			The above should be handled in state
	 * 			Valid saving guarantees valid loading of maps
	 */			
	private static final long serialVersionUID = -7131326769212761462L;
	private GameObject base;
	private DynamicObject obj;
	
	private ObjectType ID;
			
	/*
	 * Allows re-assembly of objects using their types
	 */
	public ObjectType getID() {
		return this.ID;
	}
	
	public void setBase(GameObject obj) {
		this.ID = obj.getType();
		this.base = obj;
	}
	

	public void setDynamic(DynamicObject obj) {
		this.ID = obj.getType();
		//BEFORE
		this.obj = obj;
	}
	
	/*
	 * This will most likely be called
	 * Handle null return in State's restore function
	 */
	
	public GameObject getBase() {
		return base;
	}
	
	public DynamicObject getDynamic() {
		return obj;
	}
	
	
	/*
	 * ID is not set if none of the setters are called
	 * Ensures the tile is empty
	 */
	public boolean isEmpty() {
		if(ID == null)
			return true;
		
		return false;
	}

	
}
