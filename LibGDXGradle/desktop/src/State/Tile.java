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
	
	
	private GameObject wallObj; // can only have object or d_object
	private GameObject floorObj;
	private Player playerObj;
	private Enemy enemyObj;
	
	private Image floor;
	private Image wall;
	private Image player;
	private Image enemy;
	private Image empty;
		
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
		this.floorObj = null;
		this.wallObj = null;
		this.playerObj = null;
		this.enemyObj = null;
		
		this.wall = null;
		this.floor = null;
		this.player = null;
		this.enemy = null;

		this.add(empty);
	}
	


	
	/*
	 * Checks if this grid is valid (can't have object on null cell)
	 */
//	public boolean isValid() {
//		if ((object != null || d_object != null) && floor == null) return false;
//		return true;
//	}
	
	
	public Coord getCoord() {
		return this.coordinates;
	}
	
		
	//*************************//
	//********* FLOOR *********//
	//*************************//
	

		
		
	

	
	
	//*************************//
	//******** OBJECT *********//
	//*************************//
	
//	public boolean hasObject() {
//		if(this.object != null || this.d_object != null) {
//			return true;
//		}
//		return false;
//	}
	
	
	
	/*
	 * Object setter for dynamic objects (player, enemy)
	 * Only 1 GameObject should be allowed at once
	 */
//	public void setDynamicObject(DynamicObject new_d_object) {
//		if(this.object != null) {
//			this.object = null;
//		}
//		
//		this.clearChildren();
//		object_texture = new_d_object.getTexture();
//		try {
//			d_object = (DynamicObject) new_d_object.clone();
//		} catch (CloneNotSupportedException e) {
//			e.printStackTrace();
//		}
//		if(this.hasFloor()) {
//			this.add(floor);
//		} else {
//			this.add(empty);
//		}
//		this.add(d_object);
//	}
	
			
	public void setFloor(GameObject obj) {
		this.clearChildren();
		
		floorObj = obj;
		floor = processPath(obj.getImgPath());
		
		wallObj = null;
		wall = null;
		
		this.add(floor);
		
		if(player != null)
			this.add(player);
		else if(enemy != null) {
			this.add(enemy);
		}
	}
	
	public void setWall(GameObject obj) {
		this.clear();
		this.clearChildren();
			
		wallObj = obj;
		wall = processPath(obj.getImgPath());
		
		this.add(wall);
	}



	public void setEnemy(Enemy obj) {
		this.clearChildren();
		
		enemyObj = obj;
		enemy = processPath(obj.getImgPath());
		
		playerObj = null;
		player = null;
		
		wallObj = null;
		wall = null;
		
		if(floor != null) {
			this.add(floor);
		}else{
			this.add(empty);
		}
		this.add(enemy);
	}


	public void setPlayer(Player obj) {
		this.clearChildren();
		
		playerObj = obj;
		player = processPath(obj.getImgPath());
		
		enemyObj = null;
		enemy = null;
		
		wallObj = null;
		wall = null;
		
		if(floor != null) {
			this.add(floor);
		}else{
			this.add(empty);
		}
		this.add(player);
	}
	
	/*
	 * Retrieve texture and returns an image
	 */
	private Image processPath(String path) {
		return new Image(new TextureRegion(new Texture(Gdx.files.internal(path))));
	}

}