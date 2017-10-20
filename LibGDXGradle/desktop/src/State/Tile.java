package State;


import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import Tileset.*;
import Tileset.GameObject.ObjectType;


public class Tile extends Group{
	private final Coord coordinates; 
	
	private DynamicObject d_obj;
	private GameObject g_obj;
	
	
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
		this.addActor(actor);
		actor.setZIndex(order);
		actor.setBounds(0, 0, 40, 40);
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
	
	public void setFloor(GameObject obj) {
		this.addActor(processPath(obj.getImgPath()), "floor", 1);
		// FOR JAMES
		this.g_obj = obj;
	}

	public void deleteFloor() {
		this.removeActor("floor");
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
				return g_obj;
			else
				return d_obj;
		}
		return null;
	}
	
	// Overwrites current object if any

	// Setters overwrite current object if any
	/*
	 * The general object setter for ITEMS, WALLS, PLAYERS AND ENEMIES
	 */

	public void setObject(GameObject new_object) {
		this.removeActor("object");
		this.removeActor("d_object");
		g_obj = new_object;
		//GameObject object = new_object.clone();
		this.addActor(processPath(new_object.getImgPath()), "object", 2);
		if (d_obj != null) this.addActor(processPath(d_obj.getImgPath()), "d_object", 2);
	}	
	
	
	/*
	 * Object setter for dynamic objects (player, enemy)
	 * Only 1 GameObject should be allowed at once
	 */
	public void setDynamicObject(DynamicObject new_d_object) {
		this.removeActor("d_object");
		this.removeActor("object");
		d_obj = new_d_object;
		if (g_obj != null) this.addActor(processPath(g_obj.getImgPath()), "object", 2);
		this.addActor(processPath(new_d_object.getImgPath()), "d_object", 2);		
	}
	
//	private TextureRegion processPath(String path) {
//		return new TextureRegion(new Texture(Gdx.files.internal(path)));
//	}
	
	private Image processPath(String path) {
		return new Image(new TextureRegion(new Texture(Gdx.files.internal(path))));
	}
	
	public ObjectType getObjectType() {
		if (this.hasObject()) {
			return this.getObject().getType();
		}
		return null;
	}
	
	public void deleteObject() {
		this.removeActor("d_object");
		this.d_obj = null;
	}
	
/*	public void deleteObject() {
		this.removeActor("object");
		this.removeActor("d_object");
		// FOR JAMES
		this.d_obj = null;
		this.g_obj = null;
	}*/
	


	// FOR JAMES
//	public String getObjectPath() {
//		if (object_texture != null) {
//			String k = ((FileTextureData)object_texture.getTexture().getTextureData()).getFileHandle().path();
//			return k;
//		}
//		return null;
//	}
}