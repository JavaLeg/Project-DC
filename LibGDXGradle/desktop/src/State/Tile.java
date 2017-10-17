package State;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import Tileset.*;
import Tileset.GameObject.ObjectType;

public class Tile extends Stack implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Image floor;
	private GameObject object;
	private GameObject d_object;
	private Image empty;
	
	private TextureRegion terrain_texture;
	private TextureRegion object_texture;
	
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
	
	public void setFloor(TextureRegion txt) {
		this.clearChildren();
		terrain_texture = txt;
		
		Image floor_img = new Image(txt);
		floor = floor_img;
		this.add(floor);
		
		if (this.hasObject() == true) {
			this.add(object);
		}
	}
	
	/*
	 * The general object setter for ITEMS, WALLS, PLAYERS AND ENEMIES
	 */
	public void setObject(GameObject new_object) {
		this.clearChildren();
		object_texture = new_object.getTexture();
		try {
			object = new_object.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		this.add(floor);
		this.add(object);
	}
	
	/*
	 * Object setter for dynamic objects (player, enemy)
	 * Only 1 GameObject should be allowed at once
	 */
	public void setDynamicObject(DynamicObject new_d_object) {
		this.clearChildren();
		try {
			d_object = new_d_object.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		this.add(floor);
		this.add(d_object);
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
		this.floor = empty;
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
	
	// Deletes object if exists, returns false if does not exist or invalid type
	public void deleteObject() {
		this.object = null;
		this.clearChildren();
		this.add(floor);
	}
	
	public ObjectType getObjectType() {
		if (object != null) return object.getType();
		return null;
	}
	
	/*
	 * Returns the path to the texture in String format
	 */
	
	public String getTerrainPath() {
		if (terrain_texture != null) {
			String k = ((FileTextureData)terrain_texture.getTexture().getTextureData()).getFileHandle().path();
			return k;
		}
		return null;
	}
	
	public String getObjectPath() {
		if (object_texture != null) {
			String k = ((FileTextureData)object_texture.getTexture().getTextureData()).getFileHandle().path();
			return k;
		}
		return null;
	}

}