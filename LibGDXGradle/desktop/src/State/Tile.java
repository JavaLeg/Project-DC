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
	
	
	// Static objects
	private GameObject wallObj;
	private GameObject floorObj;
	private GameObject itemObj;
	
	// Dynamic objects
	private Player playerObj;
	private Enemy enemyObj;
	
	private Image floor;
	private Image wall;
	private Image player;
	private Image enemy;
	private Image item;
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
		this.setPlayerObj(null);
		this.setEnemyObj(null);
		
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
	
	public void setItem(GameObject obj) {
		this.clearChildren();
		
		itemObj = obj;
		item = processPath(obj.getImgPath());
		
		setEnemyObj(null);
		enemy = null;
		
		setPlayerObj(null);
		player = null;
		
		wallObj = null;
		wall = null;
		
		if(floor != null) {
			this.add(floor);
		}else{
			this.add(empty);
		}
		this.add(item);
	}



	public void setEnemy(Enemy obj) {
		this.clearChildren();
		
		setEnemyObj(obj);
		enemy = processPath(obj.getImgPath());
		
		setPlayerObj(null);
		player = null;
		
		itemObj = null;
		item = null;
		
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
		
		setPlayerObj(obj);
		player = processPath(obj.getImgPath());
		
		setEnemyObj(null);
		enemy = null;
		
		itemObj = null;
		item = null;
		
		wallObj = null;
		wall = null;
		
		if(floor != null) {
			this.add(floor);
		}else{
			this.add(empty);
		}
		this.add(player);
	}
	
	public void deleteTileElement(ObjectType t) {
		switch(t) {
		case WALL:
			wall = null;
			break;
		case FLOOR:
			floor = null;
			break;
		case ITEM:
			item = null;
			break;
		case PLAYER:
			player = null;
			break;
		case ENEMY:
			enemy = null;
			break;
		default:
			break;
		}
	}
	
	/*
	 * Retrieve texture and returns an image
	 */
	private Image processPath(String path) {
		return new Image(new TextureRegion(new Texture(Gdx.files.internal(path))));
	}


	public Enemy getEnemyObj() {
		return enemyObj;
	}


	public void setEnemyObj(Enemy enemyObj) {
		this.enemyObj = enemyObj;
	}


	public Player getPlayerObj() {
		return playerObj;
	}


	public void setPlayerObj(Player playerObj) {
		this.playerObj = playerObj;
	}


	public GameObject getStaticObj(ObjectType t) {
		
		GameObject obj = null;
		
		switch(t){
		case WALL:
			obj = wallObj;
			break;
		case FLOOR:
			obj = floorObj;
			break;
		case ITEM:
			obj = itemObj;
			break;
		default:
			break;
		}
		return obj;
	}


	public void setStaticObj(GameObject wallObj) {
		this.wallObj = wallObj;
	}

}