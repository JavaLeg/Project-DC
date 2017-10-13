package State;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import Tileset.*;
import Tileset.GameObject.ObjectType;

public class Tile extends Stack implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Image floor;
	private GameObject object;
	private Image empty;
	
	//************************//
	//******* CREATORS *******//
	//************************//
	
	// Default Create a Tile
	public Tile(){
		super();
		Texture cur_texture = new Texture(Gdx.files.internal("EditorScreen/empty_grid.png"));
		//TextureRegion gnd = new TextureRegion(cur_texture, 40, 40);
		// Changed, we can lock grid size. Done in the loop in preview
		TextureRegion gnd = new TextureRegion(cur_texture);
		floor = new Image(gnd);
		empty = floor;
		
		this.add(floor);
	}
	
	public void setFloor(Image i) {
		this.clearChildren();
		floor = i;
		this.add(i);
		
		if (this.object != null) {
			this.add(object);
		}
	}
	
	public void deleteFloor() {
		this.floor = null;
		this.clearChildren();
		this.add(object);
	}
	
	public boolean hasFloor (){
		return this.floor != null;
	}
	
	/*
	 * Clear the cell
	 * Keeps the empty texture 
	 */
	public void clear() {
		this.clearChildren();
		this.object = null;
		this.floor = null;
		this.add(empty);
	}
	
	
	/*
	 * Checks if this grid is valid (can't have object on null cell)
	 */
	public boolean isValid() {
		if (object != null && floor == null) return false;
		return true;
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
	
	public void setObject(GameObject i) {
		this.clearChildren();
		object = i;
		this.add(floor);
		this.add(i);
	}
	
	// Deletes object if exists, returns false if does not exist or invalid type
	public void deleteObject() {
		this.object = null;
		this.clearChildren();
		this.add(floor);
	}
}