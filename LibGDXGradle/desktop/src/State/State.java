package State;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import Tileset.*;
import Tileset.GameObject.ObjectType;
import Interface.EditorModel;
import Interface.TileTuple;
import Interface.Stages.TableTuple;
import Interface.Stages.Selections.ToolbarSelection;


public class State extends Stage {
	
	private static final int DEFAULT_MAP_WIDTH = 50; 
	private static final int DEFAULT_MAP_HEIGHT = 50;
	
	private int rowActors;
	private int colActors;
<<<<<<< HEAD

	//private DynamicObject cur_d_object;
	private ObjectType selection;

	private ArrayList<Tile> tileList;
	private Player player;
	private ArrayList<GameObject> staticList;
	private ArrayList<Item> itemList;
	private ArrayList<Enemy> enemyList;
	
	private Enemy enemySelection;
	private Player playerSelection;
	private GameObject staticSelection;
	private Item itemSelection;

	/**			
	 * 			{PLAYER}{ENEMY}{ITEM} ------------ Item should have their own list??
	 * 				{FLOOR} {WALL}    ------------ Static objects
	 * 			
	 */		
	
=======

	private TextureRegion selected_tr;

	private GameObject cur_object;
	private DynamicObject cur_d_object;
	private ToolbarSelection selectedToolBar;
	
	// private Coord playerCoord;
	private ArrayList<Tile> tileList;

	private DynamicObject player;
	private ArrayList<DynamicObject> enemyList;
	private ArrayList<GameObject> itemList;
	private ArrayList<GameObject> wallList;
>>>>>>> refs/remotes/origin/state-remodel
	
	//************************//
	//****** CONSTRUCTOR *****//
	//************************//
	
<<<<<<< HEAD
	// default create an empty State
	public State(Viewport v){
		super(v);
		this.rowActors = DEFAULT_MAP_HEIGHT;
		this.colActors = DEFAULT_MAP_WIDTH;
		this.tileList = new ArrayList<Tile>();
		this.enemyList = new ArrayList<Enemy>();
		this.staticList = new ArrayList <GameObject>();
		this.itemList = new ArrayList<Item>();
		this.player = null;
		initialise();

=======

	// default create an empty State
		public State(Viewport v){
			super(v);
			this.rowActors = DEFAULT_MAP_HEIGHT;
			this.colActors = DEFAULT_MAP_WIDTH;
			this.tileList = new ArrayList<Tile>();
			this.enemyList = new ArrayList<DynamicObject>();
			this.wallList = new ArrayList<GameObject>();
			this.itemList = new ArrayList<GameObject>();
			this.player = null;
			initialise();
>>>>>>> refs/remotes/origin/state-remodel
			
<<<<<<< HEAD
		// assumes no player initially
		this.player = null;
	}
=======
		}
>>>>>>> refs/remotes/origin/state-remodel

	private void initialise() {
//		Table gridTable = new Table();
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
								
				final Tile tile = new Tile(new Coord(i,j));
				tileList.add(tile);
				this.addActor(tile);
				
				tile.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						try {
							setTile(tile, selection);
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
				});
//				gridTable.add(tile).size(40, 40);
			}
//			gridTable.row();
		}
//		gridTable.top();
//		gridTable.setFillParent(true);
//		super.addActor(gridTable);
		
	}
	
	//************************//
	//******** EDITOR ********//
	//************************//
	
	public void setEnemySelection(Enemy e) {
		this.enemySelection = e;		
		this.selection = ObjectType.ENEMY;
	}

	
	public void setPlayerSelection(Player p) {
		this.playerSelection = p;
		this.selection = ObjectType.PLAYER;
	}
	
	public void setItemSelection(Item i) {
		this.itemSelection = i;
		this.selection = ObjectType.ITEM;
	}
	

	public void setStaticSelection(GameObject obj) {
		this.staticSelection = obj;
		this.selection = obj.getType();
	}
	
	// Fill grid with selected floor
	// TODO
	public void fillGrid() {
		for(Tile tile : tileList) {
			setTile(tile, selection);
		}
	}
	
	
	
	public void clearGrid() {		
		this.player = null;
		for(Tile tile : tileList) {
			tile.clear();
		}
		//this.tileList.clear();
		this.enemyList.clear();
		this.staticList.clear();
	}
	
	
	/*
	 * Setting the tile texture
	 */
<<<<<<< HEAD

	public void moveObject(Coord from, Coord to) {
		GameObject temp = this.getObject(from);
		this.deleteObject(from);
		this.setObject(temp, to);
	}
	
	public void movePlayerTo(Coord c) {
		Player temp = playerSelection;
		
	}
	
	/*
	 * Splits to different object types and
	 * their respective tile function
	 */
	private void setTile(Tile tile, ObjectType type) throws CloneNotSupportedException {
		if (type == null) return;
		switch (type){
=======
	private void setTileTexture(Tile tile, ToolbarSelection ts) {
		// If tile already has an object, remove it and return
		if(tile.hasObject() && ts != ToolbarSelection.FLOOR) {
			this.deleteObject(tile.getCoord());
			return;
		}
		if (ts == null) return;
		
		switch (ts){
>>>>>>> refs/remotes/origin/state-remodel
		case FLOOR:
<<<<<<< HEAD
			GameObject clonedFloor = null;
			try {
				clonedFloor = staticSelection.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			clonedFloor.setCoord(tile.getCoord());
			staticList.add(clonedFloor);
			tile.setFloor(clonedFloor);
			break;
		case WALL:
			GameObject clonedWall = null;
			try {
				clonedWall = staticSelection.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			if (tile.getPlayerObj() != null) player = null;
			clonedWall.setCoord(tile.getCoord());
			staticList.add(clonedWall);
			tile.setWall(clonedWall);
=======
			tile.setFloor(selected_tr);
			break;
		case ENEMY:
			setObject(cur_d_object, tile.getCoord());
>>>>>>> refs/remotes/origin/state-remodel
			break;
<<<<<<< HEAD
		case ITEM:
			Item clonedItem = null;
			try {
				clonedItem = (Item) itemSelection.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			if (tile.getPlayerObj() != null) player = null;
			clonedItem.setCoord(tile.getCoord());
			itemList.add(clonedItem);
			tile.setItem(clonedItem);
			break;
		case ENEMY:
			Enemy clonedEnemy = null;
			try {
				clonedEnemy = enemySelection.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			if (tile.getPlayerObj() != null) player = null;
			clonedEnemy.setCoord(tile.getCoord());
			enemyList.add(clonedEnemy);
			tile.setEnemy(clonedEnemy);
			break;
		case PLAYER:
			Player clonedPlayer = null;
			try {
				clonedPlayer = playerSelection.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			deletePlayer();
			clonedPlayer.setCoord(tile.getCoord());
			player = clonedPlayer;
			tile.setPlayer(clonedPlayer);
=======
		case ITEM:
		case WALL:
			cur_object.setCoord(tile.getCoord());
			if (tile.getObjectType() == ObjectType.PLAYER) {
				this.player = null; 
			}
			tile.setObject(cur_object);
			break;
		case PLAYER:
			if (this.player != null) deletePlayer();
			setObject(cur_d_object, tile.getCoord());
			player = cur_d_object;
>>>>>>> refs/remotes/origin/state-remodel
			break;
		default:
			break;
		}
		//setObject(cur_object, tile.getCoord());
	}


	/*
	 * Check the map is valid before saving (e.g, at least one player)
	 * At least one tile, (check creatures on tile, etc. etc.)
	 * UNUSED
	 */
	public boolean checkValidMap() {
		boolean no_err = true;
		
		if (!this.hasPlayer()) {
			System.out.println("No player present, insert Player to fix");
			no_err = false;
		}
		
		for (Tile tile: tileList) {
			if (tile.isValid() == false) {
				System.out.println("Invalid object on empty tile");
				no_err = false;
			}
		}	
		return no_err;
	}
	
<<<<<<< HEAD
		
=======
	
	/* 
	 * Called by Editor.java when attempting to edit an enemy
	 * or player attribute
	 */
	public void isEditable() {
		System.out.println(selectedToolBar);
	}
	
	
>>>>>>> refs/remotes/origin/state-remodel
	//************************//
	//******** OBJECT ********//
	//************************//
	
	public GameObject getObject(Coord coord) {
		return this.tileList.get(coord.getX()* colActors  + coord.getY()).getObject();
	}
	
<<<<<<< HEAD
	
//	public void setObject(GameObject newObject, Coord coord) {
//		ObjectType type = newObject.getType();
//		
//		switch(type) {
//		case PLAYER:
//			deletePlayer();
//			this.player = (DynamicObject) newObject;
//			break;
//		case ENEMY:
//			enemyList.add((DynamicObject) newObject);
//			break;
//		case ITEM:
//			itemList.add(newObject);
//			break;
//		case WALL:
//			wallList.add(newObject);
//			break;
//		default:
//			return;
//		}
//		
//		newObject.setCoord(coord);
//		this.tileList.get(coord.getX()* colActors  + coord.getY()).setObject(newObject);
//	}
	
	
	public void deleteObject(Coord coord, ObjectType type) {
		
		Tile tile = tileList.get(coord.getX()*colActors + coord.getY());
		
		// Removal of image from tile
		
		switch(type) {
		case PLAYER:
			// Already done no?
			player = null;
			break;
		case ENEMY:
			enemyList.remove(tile.getEnemyObj());
			break;
		default:
			staticList.remove(tile.getStaticObj(type));
=======
	/*
	 * Sets a tile to a GameObject
	 */
	public void setObject(GameObject newObject, Coord coord) {

		newObject.setCoord(coord);
		this.tileList.get(coord.getX() * colActors  + coord.getY() ).setObject(newObject);
	}
	
	/*
	 * Deletes the object at that tile entirely, including getting rid of the texture
	 */
	public void deleteObject(Coord coord) {

		ObjectType type = this.tileList.get(coord.getX()* colActors  + coord.getY() ).getObjectType();
		switch(type) {
		case PLAYER:
			this.player = null;
			System.out.println("Deleting player");
			break;
		case ENEMY:
			Iterator<DynamicObject> iterE = enemyList.iterator();
			while(iterE.hasNext()) {
				DynamicObject obj = iterE.next();
				if(obj.getCoord() == coord) iterE.remove();
			}
			break;
		case ITEM:
			Iterator<GameObject> iterI = itemList.iterator();
			while(iterI.hasNext()) {
				GameObject obj = iterI.next();
				if(obj.getCoord() == coord) iterI.remove();
			}
			break;
		case WALL:
			Iterator<GameObject> iterW = wallList.iterator();
			while(iterW.hasNext()) {
				GameObject obj = iterW.next();
				if(obj.getCoord() == coord) iterW.remove();
			}
			break;
		default:
>>>>>>> refs/remotes/origin/state-remodel
			break;
		}
<<<<<<< HEAD
		tile.deleteTileElement(type);
=======
		
		this.tileList.get(coord.getX()* colActors  + coord.getY()).deleteObject();
>>>>>>> refs/remotes/origin/state-remodel
	}
	
<<<<<<< HEAD
	

=======
	/*
	 * TODO, does this need a clone?
	 */
	public void moveObject(Coord from, Coord to) {
		GameObject temp = this.getObject(from);
		this.deleteObject(from);
		this.setObject(temp, to);
	}
>>>>>>> refs/remotes/origin/state-remodel
	
	
	public void swapObject(Coord from, Coord to) {
		GameObject fromObject = this.getObject(from);
		GameObject toObject = this.getObject(to);
		this.setObject(fromObject, to);
		this.setObject(toObject, from);
	}
	
	
	//************************//
	//******* PLAYER *********//
	//************************//
	
	public boolean hasPlayer() {
		return this.player != null;
	}
	
	
	// Return coord of player
	public Coord findPlayer(){
		return this.player.getCoord();
	}
	
	// Get player object

	public Player getPlayer(){
		return (Player) this.player;
	}
	
	public void deletePlayer(){
<<<<<<< HEAD
		if(this.player == null) return;
		this.deleteObject(player.getCoord(), ObjectType.PLAYER);
=======
		this.deleteObject(player.getCoord());
		this.player = null;
>>>>>>> refs/remotes/origin/state-remodel
	}
	
	
	/*
	 * When moving player, don't actually delete the object
	 * Just delete the tile object
	 */
	public void movePlayer(Coord to) {
		if (player == null) return;				// Only move existent players	
		Coord pos = player.getCoord();
		this.tileList.get(pos.getX() * colActors + pos.getY()).deleteObject();
		this.setObject(player, to);
	}
	
	
	//************************//
	//******* TERRAIN ********//
	//************************//
	
	public boolean isBlocked(Coord pos) {
		return !(this.tileList.get(pos.getX()* colActors  + pos.getY() ).getObject()).isPassable();
	}
	
	
	
	//************************//
	//******** TILES *********//
	//************************//
	
	public Tile getTile(Coord coord) {
		return this.tileList.get(coord.getX()* colActors  + coord.getY() ); 
	}
	
	
	// Deletes floor and object, replaces with empty tile texture
	public void clearTile(Coord coord) {
		this.tileList.get(coord.getX()* colActors  + coord.getY() ).clear(); 
	}
	
	
	public int getMapWidth() {
		return colActors;
	}

	public int getMapHeight() {
		return rowActors;
	}
	
	
	
	//************************//
	//******** SAVE/LOAD *********//
	//************************//
	

	/*
	 * Loop through each object list and encode tile information into the editor model
	 */
	public EditorModel getModel() {
		
		// Sanity checks that the map is worth saving
		// Add more conditionals later
		boolean satisfied = true;
		
//		if(player == null) {
//			System.out.println("No player object set!");
//			satisfied = false;
//		}

		if(!satisfied)
			return null;
		
		
		/*
		 * ORDER MATTERS IN WHICH YOU PUT ONTO THE TABLE
		 * Ensure static objects iterated over first
		 */
		EditorModel model = new EditorModel(rowActors, colActors);
		TileTuple[][] encodedTable = model.getEncodedTable();
		
		// Static Objects
		for(GameObject obj : staticList) {
			Coord c = obj.getCoord();
			System.out.println(c.getX() + " " + c.getY());
			encodedTable[c.getX()][c.getY()].setBase(obj);
		}

		if(player != null) {
			Coord pc = player.getCoord();
			encodedTable[pc.getX()][pc.getY()].setPlayer(player);
		}

		
		// Enemy Objects
		for(Enemy obj : enemyList) {

			Coord c = obj.getCoord();
			encodedTable[c.getX()][c.getY()].setEnemy(obj);
		}
		
		// Item Objects
		for(Item obj : itemList) {
			Coord c = obj.getCoord();
			encodedTable[c.getX()][c.getY()].setItem(obj);
		}
		
		//model.display();
		
		return model;
	}
		
		// Conversion should not take place inside the object
//		
//		for(int i = 0; i < tileList.size(); i++) {
//			int row_val = i/colActors;
//			int col_val = i % colActors;
//			
//			Tile tile = tileList.get(i);
//			ObjectType ID = tile.getObjectType();
//			TileTuple t = new TileTuple(tile.getObjectPath(), tile.getFloorPath(), ID);
//			model.setTile(t, row_val, col_val);
//		}
//		return model;
//}
	

	/*
	 * Retrieve the objects from encoded table
	 * Restore the state 
	 */
	public void restoreModel(EditorModel m) {
		TileTuple[][] encodedTable = m.getEncodedTable();
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
				TileTuple enc_tile = encodedTable[i][j];
				
				if(enc_tile == null || enc_tile.isEmpty())
					continue;

<<<<<<< HEAD
				ObjectType type = enc_tile.getID();
				Tile tile = getTile(new Coord(i, j));
				
				GameObject base = null;
				
				switch(type) {
				case FLOOR:
					tile.setFloor(enc_tile.getBase());
					break;
				case WALL:
					tile.setWall(enc_tile.getBase());
					break;
				case PLAYER:
					base = enc_tile.getBase();
					
					if(base != null)
						tile.setFloor(base);
					
					tile.setPlayer(enc_tile.getPlayer());
					break;
				case ENEMY:
					base = enc_tile.getBase();
					
					if(base != null)
						tile.setFloor(base);
					
					tile.setEnemy(enc_tile.getEnemy());
					break;
				case ITEM:
					base = enc_tile.getBase();
					
					if(base != null)
						tile.setFloor(base);
					
					tile.setItem(enc_tile.getItem());
					break;
				default:
					break;
=======
				// Set terrain
				if(t_tuple.getFloor() != null)
					tile.setFloor(new TextureRegion(new Texture(Gdx.files.internal(t_tuple.getFloor()))));
					
				// Set object
				if(t_tuple.getObject() != null) {
					ObjectType type = t_tuple.getID();
					Coord tile_pos = tile.getCoord();
					TextureRegion cur_texture = new TextureRegion(new Texture(Gdx.files.internal(t_tuple.getObject())));
					GameObject new_obj = new GameObject(type, cur_texture);
					
					if (type == ObjectType.PLAYER) {
						player = new Player(tile_pos, 1, 1, new Texture(Gdx.files.internal(t_tuple.getObject())));
					} else {			
						new_obj.setCoord(tile_pos);
					}
					tile.setObject(new_obj);
>>>>>>> refs/remotes/origin/state-remodel
				}
			}
		}
	}
<<<<<<< HEAD
	
//	public void restoreModel(EditorModel m) {
//		TileTuple[][] map = m.getmodelPaths();
//		
//		for(int i = 0; i < rowActors; i++) {
//			for(int j = 0; j < colActors; j++) {
//				int index = colActors*i + j;
//				
//				TileTuple t_tuple = map[i][j];
//				Tile tile = tileList.get(index);
//
//				// Set terrain
//				if(t_tuple.getFloor() != null)
//					tile.setFloor(new TextureRegion(new Texture(Gdx.files.internal(t_tuple.getFloor()))));
//				
//				
//				// Set object
//				if(t_tuple.getObject() != null) {
//					TextureRegion cur_texture = new TextureRegion(new Texture(Gdx.files.internal(t_tuple.getObject())));
//					GameObject new_obj = new GameObject(t_tuple.getID(), cur_texture);
//					tile.setObject(new_obj);
//				}
//			}
//		}
//	}

	
	/*
	 * Not related to tables
	 * Just using as a 2 variable tuple
	 */
=======

	/* 
	 * Determines if the next position is valid (for player)
	 */
	public boolean isValid(Coord next) {
		// TODO Auto-generated method stub
		return (next.getX() >= 0 && next.getX() < colActors) && (next.getY() >= 0 && next.getY() < rowActors);
	}
		
>>>>>>> refs/remotes/origin/state-remodel
	public TableTuple getDim() {
		return new TableTuple(rowActors, colActors);
	}
	
	
	// String splitting
	private ObjectType getType(String path) {
		String[] parts = path.split("/");
		
		for(ObjectType t : ObjectType.values()) {
			if(t.toString().toLowerCase().equals(parts[1]))
					return t;
		}
		return null;
	}

}
