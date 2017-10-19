package State;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import Tileset.*;
import Tileset.GameObject.ObjectType;


public class Tile extends Group implements Serializable {
	private final Coord coordinates; 
	
	private TextureRegion floor_texture;
	private TextureRegion object_texture;
	
	
	//*************************//
	//******** GENERAL ********//
	//*************************//

	// Create empty Tile
	public Tile(Coord coords){
		super();
		this.coordinates = coords;
		this.setSize(40, 40);
		this.setPosition(coords.getX() * 40, coords.getY() * 40);
		clear();
	}
	
	private void addActor(Actor actor, String name, int order) {
		actor.setName(name);
		actor.setZIndex(order);
		this.addActor(actor);
	}
	
	private void removeActor(String name) {
		if (this.hasActor(name)) 
			this.getActor(name).remove();
	}
	
	private Actor getActor(String name) {
	    return this.findActor(name);
	}
	
	private boolean hasActor(String name) {
		return this.getActor(name) != null ? true : false;
	}
	
	/*
	 * Clear the cell
	 * Keeps the empty texture 
	 */
	public void clear() {
		this.clearChildren();
		Texture cur_texture = new Texture(Gdx.files.internal("EditorScreen/empty_grid.png"));
		TextureRegion gnd = new TextureRegion(cur_texture);
		Image empty = new Image(gnd);
		addActor(empty, "empty", 0);
	}
	


	
	/*
	 * Checks if this grid is valid (can't have object on null cell)
	 */
	public boolean isValid() {
		return !(this.hasObject() && !(this.hasActor("floor")));
	}

	
	
	public Coord getCoord() {
		return this.coordinates;
	}
	
		
	//*************************//
	//********* FLOOR *********//
	//*************************//
	
	public boolean hasFloor () {
		return this.hasActor("floor");
	}
	
	public void setFloor(TextureRegion txt) {
		Image floor_img = new Image(txt);
		this.addActor(floor_img, "floor", 1);
		// FOR JAMES
		this.floor_texture = txt;
	}
		
	

	public void deleteFloor() {
		this.removeActor("floor");
	}
	
	
	// FOR JAMES
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
		return (this.hasActor("object") || this.hasActor("d_object"));
	}

	public GameObject getObject() {
		if (this.hasObject()) {
			if (this.hasActor("object"))
				return ((GameObject)this.getActor("object"));
			else
				return ((GameObject)this.getActor("d_object"));
		}
		return null;
		
	}

	// Overwrites current object if any

	// Setters overwrite current object if any
	/*
	 * The general object setter for ITEMS, WALLS, PLAYERS AND ENEMIES
	 */

	public void setObject(GameObject new_object) {

		this.removeActor("d_object");
		this.object_texture = new_object.getTexture();
		try {
			GameObject object = new_object.clone();
			this.addActor(object, "object", 2);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}	
	
	
	/*
	 * Object setter for dynamic objects (player, enemy)
	 * Only 1 GameObject should be allowed at once
	 */
	public void setDynamicObject(DynamicObject new_d_object) {
		this.removeActor("object");
		object_texture = new_d_object.getTexture();
		try {
			DynamicObject d_object = (DynamicObject) new_d_object.clone();
			this.addActor(d_object, "d_object", 2);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}		
	}
	
	public ObjectType getObjectType() {
		if (this.hasObject()) {
			return this.getObject().getType();
		}
		return null;
	}
	
	public void deleteObject() {
		this.removeActor("object");
		this.removeActor("d_object");
		// FOR JAMES
		this.object_texture = null;
	}
	

	// FOR JAMES
	public String getObjectPath() {
		if (object_texture != null) {
			String k = ((FileTextureData)object_texture.getTexture().getTextureData()).getFileHandle().path();
			return k;
		}
		return null;

	}
}