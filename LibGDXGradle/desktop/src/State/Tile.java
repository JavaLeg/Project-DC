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
	
>>>>>>> refs/remotes/origin/state-remodel
	/*
	 * Clear the cell
	 * Keeps the empty texture 
	 */
	public void clear() {
		this.clearChildren();
<<<<<<< HEAD
		this.floorObj = null;
		this.wallObj = null;
		this.setPlayerObj(null);
		this.setEnemyObj(null);
		
		this.wall = null;
		this.floor = null;

		this.player = null;
		this.enemy = null;

		this.add(empty);
=======
		Texture cur_texture = new Texture(Gdx.files.internal("EditorScreen/empty_grid.png"));
		TextureRegion gnd = new TextureRegion(cur_texture);
		Image empty = new Image(gnd);
		addActor(empty, "empty", 0);
>>>>>>> refs/remotes/origin/state-remodel
	}
	


	
	/*
	 * Checks if this grid is valid (can't have object on null cell)
	 */
<<<<<<< HEAD
//	public boolean isValid() {
//		if ((object != null || d_object != null) && floor == null) return false;
//		return true;
//	}
=======
	public boolean isValid() {
		return !(this.hasObject() && !(this.hasActor("floor")));
	}
>>>>>>> refs/remotes/origin/state-remodel
	
	
	public Coord getCoord() {
		return this.coordinates;
	}
	
		
	//*************************//
	//********* FLOOR *********//
	//*************************//
	
<<<<<<< HEAD

		
		
=======
	public boolean hasFloor () {
		return this.hasActor("floor");
	}
	
	public void setFloor(TextureRegion txt) {
		Image floor_img = new Image(txt);
		this.addActor(floor_img, "floor", 1);
		// FOR JAMES
		this.floor_texture = txt;
	}
		
	
>>>>>>> refs/remotes/origin/state-remodel
	public void deleteFloor() {
<<<<<<< HEAD
		this.floor = null;
		this.floor_texture = null;
		this.clearChildren();
		this.add(empty);
		this.add(object);
=======
		this.removeActor("floor");
>>>>>>> refs/remotes/origin/state-remodel
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
	
<<<<<<< HEAD
//	public boolean hasObject() {
//		if(this.object != null || this.d_object != null) {
//			return true;
//		}
//		return false;
//	}
=======
	public boolean hasObject() {
		return (this.hasActor("object") || this.hasActor("d_object"));
	}
>>>>>>> refs/remotes/origin/state-remodel
	
<<<<<<< HEAD
	

=======
	public GameObject getObject() {
		if (this.hasObject()) {
			if (this.hasActor("object"))
				return ((GameObject)this.getActor("object"));
			else
				return ((GameObject)this.getActor("d_object"));
		}
		return null;
		
	}
	
>>>>>>> refs/remotes/origin/state-remodel
	// Overwrites current object if any

	// Setters overwrite current object if any
	/*
	 * The general object setter for ITEMS, WALLS, PLAYERS AND ENEMIES
	 */
<<<<<<< HEAD

=======

>>>>>>> refs/remotes/origin/state-remodel
	public void setObject(GameObject new_object) {
<<<<<<< HEAD
		if(this.d_object != null) {
			this.d_object = null;
		}
		
		this.clearChildren();
=======
		this.removeActor("d_object");
>>>>>>> refs/remotes/origin/state-remodel
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
<<<<<<< HEAD
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
	
	public void setItem(Item obj) {
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
		
		if (floor != null) {
			this.add(floor);
		} else {
			this.add(empty);
		}
		this.add(player);
=======
	public void setDynamicObject(DynamicObject new_d_object) {
		this.removeActor("object");
		object_texture = new_d_object.getTexture();
		try {
			DynamicObject d_object = (DynamicObject) new_d_object.clone();
			this.addActor(d_object, "d_object", 2);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}		
>>>>>>> refs/remotes/origin/state-remodel
	}
	
	
<<<<<<< HEAD
	// Removal of image from tile
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
		this.updateTile();
	}
	

	/* 
	 * After deletion, update the textures of the tile
	 */
	public void updateTile() {
		this.clearChildren();
		if (floor != null) {
			this.add(floor);
		} else {
			this.add(empty);
		}
=======
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
>>>>>>> refs/remotes/origin/state-remodel
	}
	
<<<<<<< HEAD
	/*
	 * Retrieve texture and returns an image
	 */
	private Image processPath(String path) {
		return new Image(new TextureRegion(new Texture(Gdx.files.internal(path))));
=======
	// FOR JAMES
	public String getObjectPath() {
		if (object_texture != null) {
			String k = ((FileTextureData)object_texture.getTexture().getTextureData()).getFileHandle().path();
			return k;
		}
		return null;
>>>>>>> refs/remotes/origin/state-remodel
	}
<<<<<<< HEAD


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
		default:
			break;
		}
		return obj;
	}

//	public void setStaticObj(GameObject wallObj) {
//		this.wallObj = wallObj;
//	}


=======
	
>>>>>>> refs/remotes/origin/state-remodel
}