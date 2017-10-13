package State;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import Tileset.*;
import Tileset.GameObject.ObjectType;

import Interface.Stages.Selections.ToolbarSelection;

public class Tile extends Stack implements Serializable{
	
	private static final long serialVersionUID = 1L;
	// private GameObject object;

	private Texture currTexture;
	private Image terrain;
	private Coord coord;
	private GameObject object;
	private Image empty;

	private boolean terrain_exists;
	private boolean object_exists;
	
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
		terrain = new Image(gnd);
		empty = terrain;
		
		terrain_exists = false;
		object_exists = false;
		
		this.add(terrain);

		// this.object = null;
		this.coord = new Coord();
	}
	
	// Create a Tile given x and y coords as Coordinates object
	public Tile(Coord coord){
		super();
		Texture cur_texture = new Texture(Gdx.files.internal("EditorScreen/empty_grid.png"));
		//TextureRegion gnd = new TextureRegion(cur_texture, 40, 40);
		// Changed, we can lock grid size. Done in the loop in preview
		TextureRegion gnd = new TextureRegion(cur_texture);
		terrain = new Image(gnd);
		empty = terrain;
		
		terrain_exists = false;
		object_exists = false;
		
		this.add(terrain);
		this.object = null;
		// Can't this.coord = coord?
		this.coord = new Coord();

		this.coord.setX(coord.getX());
		this.coord.setY(coord.getY());
	}
	
	
	
	//************************//
	//****** COORDINATES *****//
	//************************//
	
	// Get Tile coords as Coordinates object
	public Coord getCoord(){
		return this.coord;
	}
	
	// Set Tile coords given Coordinate object
	public void setCoord(Coord coord){
		this.coord.setX(coord.getX());
		this.coord.setY(coord.getY());
	}
	

	/*
	public int getStatus() {
		return this.status;
	}
	
	/*
	 * Holds the new block type
	 * Possible unnecessary
	
	public void setStatus(int start) {
		this.status = start;
	}
	
	// Increments the status (UNUSED)
	public void changeStatus() {
		this.status++;
		if (this.status > 2) this.status = 0;
	}
	*/
	
	
	public void setTextureTerrain(Image i) {
		if (i == null) return;			// If no current texture, don't do shit
		
		this.clearChildren();

		terrain_exists = true;
		terrain = i;
		this.add(i);
		
		if (object_exists == true) {
			this.add(object);
		}
	}

	public void setTextureObject(GameObject i) {
		object_exists = true;
		object = i;
		this.add(terrain);
		this.add(i);
	}

	
	/*
	 * Clear the cell
	 * Keeps the empty texture 
	 */
	public void clear() {
		this.clearChildren();
		this.add(empty);
		object_exists = false;
		terrain_exists = false;
	}
	
	/*
	 * When setting all to ground / empty
	 */
	public void setToTerrain(Image terrain) {
		this.clearChildren();
		this.add(terrain);
		object_exists = false;
	}
	
	/*
	 * Checks if this grid is valid (can't have object on null cell)
	 */
	public boolean isValid() {
		if (object_exists == true && terrain_exists == false) return false;
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