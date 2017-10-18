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
	private final Coord coordinates; 
	
	private Image floor;
	private GameObject object; // can only have object or d_object
	private DynamicObject d_object;
	private Image empty;
	
	private TextureRegion floor_texture;
	private TextureRegion object_texture;
	
	
	//*************************//
	//******** GENERAL ********//
	//*************************//

	// Create empty Tile
	public Tile(Coord coords){
		super();
		this.coordinates = coords;
		Texture cur_texture = new Texture(Gdx.files.internal("EditorScreen/empty_grid.png"));
		TextureRegion gnd = new TextureRegion(cur_texture);
		floor = new Image(gnd);
		empty = floor;
		
		this.add(floor);
	}
	
	
	/*
	 * Clear the cell
	 * Keeps the empty texture 
	 */
	public void clear() {
		System.out.print("clearing");
		this.clearChildren();
		this.object = null;
		this.d_object = null;
		this.floor = null;
		this.add(empty);
	}

	
	/*
	 * Checks if this grid is valid (can't have object on null cell)
	 */
	public boolean isValid() {
		if ((object != null || d_object != null) && floor == null) return false;
		return true;
	}
	
	
	public Coord getCoord() {
		return this.coordinates;
	}
	
		
	//*************************//
	//********* FLOOR *********//
	//*************************//
	
	public boolean hasFloor (){
		return this.floor != null;
	}
	
	
	public void setFloor(TextureRegion txt) {
		this.clearChildren();
		floor_texture = txt;
		
		Image floor_img = new Image(txt);
		floor = floor_img;
		this.add(floor);
		
		if (this.hasObject()) {
			this.add(object);
		}
	}
		
	
	public void deleteFloor() {
		this.floor = null;
		this.clearChildren();
		this.add(empty);
		this.add(object);
	}
	
	
	public String getFloorPath() {
		if (floor_texture != null) {
			String k = ((FileTextureData)floor_texture.getTexture().getTextureData()).getFileHandle().path();
			return k;
		}
		return null;
	}
	
	
	//*************************//
	//******** OBJECT *********//
	//*************************//
	
	public boolean hasObject() {
		if(this.object != null || this.d_object != null) {
			return true;
		}
		return false;
	}
	
	
	
	// Setters overwrite current object if any
	/*
	 * The general object setter for ITEMS, WALLS, PLAYERS AND ENEMIES
	 */
	public void setObject(GameObject new_object) {
		if(this.d_object != null) {
			this.d_object = null;
		}
		
		this.clearChildren();
		object_texture = new_object.getTexture();
		try {
			object = new_object.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if(this.hasFloor()) {
			this.add(floor);
		} else {
			this.add(empty);
		}
		this.add(object);
	}	
	
	
	/*
	 * Object setter for dynamic objects (player, enemy)
	 * Only 1 GameObject should be allowed at once
	 */
	public void setDynamicObject(DynamicObject new_d_object) {
		if(this.object != null) {
			this.object = null;
		}
		
		this.clearChildren();
		object_texture = new_d_object.getTexture();
		try {
			d_object = (DynamicObject) new_d_object.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if(this.hasFloor()) {
			this.add(floor);
		} else {
			this.add(empty);
		}
		this.add(d_object);
	}
	
	
	public ObjectType getObjectType() {
		if(this.object != null) {
			return this.object.getType();
		}
		if(this.d_object != null) {
			return this.d_object.getType();
		}
		return null;
	}
		
	
	// Gets object, can return null
	public GameObject getObject() {
		if(this.object != null) {
			return this.object;
		}
		if(this.d_object != null) {
			return (GameObject) this.d_object;
		}
		return null;
	}
	
	
	public void deleteObject() {
		this.object = null;
		this.d_object = null;
		this.clearChildren();
		if (this.hasFloor()) {
			this.add(floor);
		} else {
			this.add(empty);
		}
	}
	
	
	public String getObjectPath() {
		if (object_texture != null) {
			String k = ((FileTextureData)object_texture.getTexture().getTextureData()).getFileHandle().path();
			return k;
		}
		return null;
	}

}