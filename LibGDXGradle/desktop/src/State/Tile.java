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
	private GameObject object;
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
		return this.object != null;
	}
	
	
	// Overwrites current object if any
	public void setObject(GameObject new_object) {
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
	
	
	public ObjectType getObjectType() {
		if(this.object == null) return null;
		return this.object.getType();
	}
		
	
	// Gets object, can return null
	public GameObject getObject() {
		return this.object;
	}
	
	
	public void deleteObject() {
		this.object = null;
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