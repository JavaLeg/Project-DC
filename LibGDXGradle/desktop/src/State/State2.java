package State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
import Tileset.DynamicObject.ActionState;
import Tileset.GameObject.ObjectType;
import Interface.EditorModel;
import Interface.TileTuple;
import Interface.Stages.TableTuple;
import Interface.Stages.Selections.ToolbarSelection;

public class State2 extends Stage {
	
	private static final int DEFAULT_MAP_WIDTH = 50; 
	private static final int DEFAULT_MAP_HEIGHT = 50;
	
	private int rowActors;
	private int colActors;

<<<<<<< HEAD
	private TextureRegion selected_tr;
	private GameObject cur_object;
	private DynamicObject cur_d_object;
	//private DynamicObject cur_d_object;
	private ToolbarSelection selectedToolBar;
=======
>>>>>>> refs/heads/master

/**			
 * 			{PLAYER}{ENEMY}{ITEM} ------------ Uppermost layer
 * 				{FLOOR} {WALL}    ------------ Static objects
 * 			
 */		

	private GameObject cur_object;
	private DynamicObject cur_d_object;
	private ObjectType selection;
	
	// private Coord playerCoord;
	private ArrayList<Tile> tileList;

	private Player player;
	private ArrayList<DynamicObject> dynamicList;
	private ArrayList<GameObject> staticList;
	
	//************************//
	//****** CONSTRUCTOR *****//
	//************************//
	
	// default create an empty State
<<<<<<< HEAD
		public State2(Viewport v){
			super(v);
			this.rowActors = DEFAULT_MAP_HEIGHT;
			this.colActors = DEFAULT_MAP_WIDTH;
			this.tileList = new ArrayList<Tile>();
			this.enemyList = new ArrayList<DynamicObject>();
			this.wallList = new ArrayList<GameObject>();
			this.itemList = new ArrayList<GameObject>();
			initialise();
			
			// assumes no player initially
			this.player = null;
		}
=======
	public State(Viewport v){
		super(v);
		this.rowActors = DEFAULT_MAP_HEIGHT;
		this.colActors = DEFAULT_MAP_WIDTH;
		this.tileList = new ArrayList<Tile>();
		this.dynamicList = new ArrayList<DynamicObject>();
		this.staticList = new ArrayList<GameObject>();
		this.player = null;
		initialise();
		
		// assumes no player initially
		this.player = null;
	}
>>>>>>> refs/heads/master

		public State2(Viewport v, int height, int width) {
			this(v);
			this.rowActors = height;
			this.colActors = width;
			
		}
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
					
					setTile(tile, selection);
						
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
<<<<<<< HEAD
	
	
	
	// TEMP THINGS AS I TRY SOME RENDERING
	
	
	
	
=======
	
>>>>>>> refs/heads/master
	//************************//
	//******** EDITOR ********//
	//************************//
	
<<<<<<< HEAD
=======
	public void setDynamicSelection(DynamicObject obj) {
		this.selection = obj.getType();
		this.cur_d_object = obj;
	}
>>>>>>> refs/heads/master
	
	public void setStaticSelection(GameObject obj) {
		this.selection = obj.getType();
		this.cur_object = obj;
	}
	
<<<<<<< HEAD
	/*
	 * GameObject selection
	 */
	public void setSelection(Texture t, ToolbarSelection s, GameObject obj) {
		selected_tr = new TextureRegion(t);
		selectedToolBar = s;
		if (s != ToolbarSelection.FLOOR) cur_object = obj;
	}
	
	
=======
	
>>>>>>> refs/heads/master
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
		this.dynamicList.clear();
		this.staticList.clear();
	}
	
	
	/*
<<<<<<< HEAD
	 * Setting the tile texture, if it's an object we pass that instead
	 */
	private void setTileTexture(Tile tile, ToolbarSelection ts) {
		// If tile already has an object, remove it and return
		if(tile.hasObject() && ts != ToolbarSelection.FLOOR) {
			this.deleteObject(tile.getCoord());
			return;
		}
		
		// No texture selected
		if (ts == null) return;
		
		switch (ts){
		case FLOOR:
			tile.setFloor(selected_tr);
			break;
		case PLAYER:
		case ENEMY:
			setObject(cur_d_object, tile.getCoord());
			break;
		case ITEM:
		case WALL:
			setObject(cur_object, tile.getCoord());
			break;
		default:
			// SAVE, EDIT
			break;
=======
	 * Setting the tile texture
	 */
//	private void setTile(Tile tile, ObjectType type) {
//		// If tile already has an object, remove it and return
//		if(tile.hasObject() && type != ObjectType.FLOOR) {
//			this.deleteObject(tile.getCoord());
//			return;
//		}
//		if (type == null) return;
//		
//		Coord c = null;
//		
//		try {
//			c = tile.getCoord().clone();
//		} catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		switch (type){
//		case FLOOR:
//			//tile.setFloor(cur_object);
//			setObject(cur_object, c);
//			break;
//		case ENEMY:
//			setObject(cur_d_object, c);
//			break;
//		case ITEM:
//			setObject(cur_d_object, c);
//			break;
//		case WALL:
//			cur_object.setCoord(tile.getCoord());
//			if (tile.getObjectType() == ObjectType.PLAYER) {
//				this.player = null; 
//			}
//			setObject(cur_object, c);
//			break;
//		case PLAYER:
//			if (this.player != null) deletePlayer();
//			setObject(cur_d_object, c);
//			player = cur_d_object;
//			break;
//		default:
//			break;
//		}
//	}
	
	private void setTile(Tile tile, ObjectType type) {
		if (type == null) return;
		
		GameObject obj = null;
		
		System.out.println("HEI");
		if(type == ObjectType.ENEMY || type == ObjectType.ITEM || type == ObjectType.PLAYER) {
			obj = cur_d_object.clone();
			obj.setCoord(tile.getCoord());
			dynamicList.add((DynamicObject) obj);
		}else {
			obj = cur_object.clone();
			obj.setCoord(tile.getCoord());
			staticList.add(obj);
>>>>>>> refs/heads/master
		}
		// tile.setObject(obj);
		this.setObject(obj, obj.getCoord());
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
	
	/* 
	 * Called by Editor.java when attempting to edit an enemy
	 * or player attribute
	 */
//	public void isEditable() {
//		System.out.println(selectedToolBar);
//	}

	//************************//
	//******** OBJECT ********//
	//************************//
	
<<<<<<< HEAD
	public List<Coord> getAttackedLocations() {
		Set<Coord> s = new HashSet<Coord>();
		for (DynamicObject d : getAllDynamicObjects()) {
			if (d.getActionState() == ActionState.ATTACK) {
				
			}
		}
		return s;
=======
	public GameObject getObject(Coord coord) {
		return this.tileList.get(coord.getX()* colActors  + coord.getY()).getObject();
	}
	
	/*
	 * Sets a tile to a GameObject
	 */
	public void setObject(GameObject newObject, Coord coord) {
		
		// Keep track of objects
		ObjectType type = newObject.getType();
		Tile cur = tileList.get(coord.getX() * colActors  + coord.getY());
		
		if (type == ObjectType.PLAYER) {
			if (this.hasPlayer() == true) this.deletePlayer(player.getCoord());
			newObject.setCoord(coord);
			player = (Player) newObject;
			//player = (Player) newObject;
			cur.setDynamicObject((DynamicObject) newObject);
		} else if(type == ObjectType.ENEMY || type == ObjectType.ITEM) {
			if (cur.getObjectType() == ObjectType.PLAYER) player = null;
			dynamicList.add((DynamicObject) newObject);
			newObject.setCoord(coord);
			cur.setDynamicObject((DynamicObject) newObject);
		} else if(type == ObjectType.FLOOR || type == ObjectType.WALL) {
			staticList.add(newObject);
			newObject.setCoord(coord);
			cur.setFloor(newObject);
		}
		
		
		//newObject.setCoord(coord);
		//this.tileList.get(coord.getX() * colActors  + coord.getY() ).setObject(newObject);
>>>>>>> refs/heads/master
	}
	
<<<<<<< HEAD
	
	public GameObject getObject(Coord coord) {
		return this.tileList.get(coord.getX()* colActors  + coord.getY()).getObject();
	}
	
	public DynamicObject getDynamicObject(Coord coord) {
		GameObject o = getObject(coord);
		if (o != null && o.isDynamic()) {
			return (DynamicObject) o;
		} else {
			return null;
		}
	}
	
	public void setObject(GameObject newObject, Coord coord) {
		ObjectType type = newObject.getType();
		
		switch(type) {
		case PLAYER:
			deletePlayer();
			this.player = (DynamicObject) newObject;
			break;
		case ENEMY:
			enemyList.add((DynamicObject) newObject);
			break;
		case ITEM:
			itemList.add(newObject);
			break;
		case WALL:
			wallList.add(newObject);
			break;
		default:
			return;
		}
		
		newObject.setCoord(coord);
		this.tileList.get(coord.getX()* colActors  + coord.getY() ).setObject(newObject);
=======
	/*
	 * Deletes the object at that tile entirely, including getting rid of the texture
	 */
	public void deletePlayer(Coord coord) {
		Tile tile = tileList.get(coord.getX()* colActors  + coord.getY());
		tile.deleteObject();
>>>>>>> refs/heads/master
	}
	
	public void fillObject(GameObject g) {
		for (int x = 0; x < rowActors; x++) {
			for (int y = 0; y < colActors; y++) {
				setObject(g.clone(), new Coord(x,y));
			}
		}
	}
	
	
	
	public void deleteObject(Coord coord) {
<<<<<<< HEAD
		ObjectType type = this.tileList.get(coord.getX()* colActors  + coord.getY() ).getObjectType();
		switch(type) {
		case PLAYER:
			this.player = null;
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
			break;
		}
		
		this.tileList.get(coord.getX()* colActors  + coord.getY()).deleteObject();
=======

		Tile tile = tileList.get(coord.getX()* colActors  + coord.getY());
		GameObject obj = tile.getObject();
		
		ObjectType type = obj.getType();
		
		if(type == ObjectType.PLAYER || type == ObjectType.ITEM || type == ObjectType.ENEMY) {
			dynamicList.remove(obj);
		}else {
			staticList.remove(obj);
		}	
		this.tileList.get(coord.getX()* colActors  + coord.getY()).deleteObject();
>>>>>>> refs/heads/master
	}
	
	/*
	 * TODO, does this need a clone?
	 */
	public void moveObject(Coord from, Coord to) {
		GameObject temp = this.getObject(from);
		this.deleteObject(from);
		this.setObject(temp, to);
	}
	
	
	public void swapObject(Coord from, Coord to) {
		GameObject fromObject = this.getObject(from);
		GameObject toObject = this.getObject(to);
		this.setObject(fromObject, to);
		this.setObject(toObject, from);
<<<<<<< HEAD
	}
	
	
	public List<GameObject> getAllObjects() {
		List<GameObject> ret = new LinkedList<GameObject>();
		for (Tile ta : tileList) {	
			ret.add(ta.getObject());
		}
		return ret;
	}
	
	
	public List<DynamicObject> getAllDynamicObjects() {
		List<DynamicObject> ret = new LinkedList<DynamicObject>();
		for (Tile ta : tileList) {
			if (ta.getObject() == null) continue;
			if(ta.getObject().isDynamic()) {
				ret.add((DynamicObject) ta.getObject());
			}
		}
		return ret;
=======
>>>>>>> refs/heads/master
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
<<<<<<< HEAD
	public Player getPlayer(){
		return (Player) this.player;
=======

	public DynamicObject getPlayer(){
		return this.player;
>>>>>>> refs/heads/master
	}
<<<<<<< HEAD
	
	
	public void deletePlayer(){
		if(this.player == null) return;
=======
	
	public void deletePlayer(){
>>>>>>> refs/heads/master
		this.deleteObject(player.getCoord());
		this.player = null;
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
	
<<<<<<< HEAD
	public boolean hasWall(Coord pos) {
		GameObject g = getTile(pos).getObject();
		return (g != null) && (g.getType() == ObjectType.WALL);
=======
	public boolean isBlocked(Coord pos) {
		return !(this.tileList.get(pos.getX()* colActors  + pos.getY() ).getObject()).isPassable();
>>>>>>> refs/heads/master
	}
	
	public boolean isBlocked(Coord pos) {
		return getTile(pos).hasObject();
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
			encodedTable[pc.getX()][pc.getY()].setDynamic(player);
		}

		
		for(DynamicObject obj : dynamicList) {
			Coord c= obj.getCoord();
			System.out.println(c.getX() + " " + c.getY());
			encodedTable[c.getX()][c.getY()].setDynamic(obj);
		}
		
		model.display();
		
		return model;
	}


	/*
	 * Retrieve the objects from encoded table
	 * Restore the state 
	 * Directly sets the objects
	 */
	public void restoreModel(EditorModel m) {
		TileTuple[][] encodedTable = m.getEncodedTable();
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
				TileTuple enc_tile = encodedTable[i][j];
				
				if(enc_tile == null || enc_tile.isEmpty())
					continue;

				ObjectType type = enc_tile.getID();
				Tile tile = getTile(new Coord(i, j));
				
				GameObject base = null;
				DynamicObject d_obj = null;
				
				// type will only show the top-most layer
				if(type == ObjectType.ENEMY || type == ObjectType.PLAYER || type == ObjectType.ITEM) {
					setObject(enc_tile.getDynamic(), new Coord(i, j));
				}
				
				// If the tile only contains GameObjects
				if(type == ObjectType.FLOOR || type == ObjectType.WALL) {
					base = enc_tile.getBase();
				}
				
				if(base != null)
					setObject(base, new Coord(i, j));
									
			}
		}
	}
	
<<<<<<< HEAD
	
	/*
	// String splitting
	private ObjectType getType(String path) {
		String[] parts = path.split("/");
		
		for(ObjectType t : ObjectType.values()) {
			if(t.toString().toLowerCase().equals(parts[1]))
					return t;
		}
		return null;
	}
<<<<<<< HEAD
	*/
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FOR CAMERA MOVEMENT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/*
	 * Movement involves left click followed by dragging motion
	 * Degree of movement by variable intensity
=======

	/* 
	 * Determines if the next position is valid (for player)
>>>>>>> refs/heads/master
	 */
<<<<<<< HEAD
	/*
	private int dragX, dragY;
	private float intensity = 150f;
	
	
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		dragX = screenX;
		dragY = screenY;
		return true;
=======
	
	public TableTuple getDim() {
		TableTuple t = new TableTuple(rowActors, colActors);
		return t;
>>>>>>> branch 'EditorAttributes' of https://github.com/JavaLeg/Project-DC
=======
	public boolean isValid(Coord next) {
		// TODO Auto-generated method stub
		return (next.getX() >= 0 && next.getX() < colActors) &&
				(next.getY() >= 0 && next.getY() < rowActors) &&
				(this.getObject(next) == null);
>>>>>>> refs/heads/master
	}
<<<<<<< HEAD
	*/
}
=======
		
	public TableTuple getDim() {
		return new TableTuple(rowActors, colActors);
	}
}
>>>>>>> refs/heads/master
