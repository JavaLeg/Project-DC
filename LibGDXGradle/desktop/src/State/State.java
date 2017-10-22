package State;


import java.util.ArrayList;
import java.util.List;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import Tileset.*;
import Tileset.GameObject.ObjectType;
import Interface.EditorModel;
import Interface.TileTuple;
import Interface.Stages.TableTuple;


public class State extends Stage {
	
	private static final int DEFAULT_MAP_WIDTH = 25; 
	private static final int DEFAULT_MAP_HEIGHT = 25;
	
	private int rowActors;
	private int colActors;


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

	private DynamicObject win;			// The win condition 


	private Player player;
	private ArrayList<DynamicObject> dynamicList;
	private ArrayList<GameObject> staticList;
	
	//************************//
	//****** CONSTRUCTOR *****//
	//************************//
	
	// default create an empty State
	public State(Viewport v){
		super(v);
		this.rowActors = DEFAULT_MAP_HEIGHT;
		this.colActors = DEFAULT_MAP_WIDTH;
		this.tileList = new ArrayList<Tile>();
		this.dynamicList = new ArrayList<DynamicObject>();
		this.staticList = new ArrayList<GameObject>();
		this.player = null;
		this.win = null;
		initialise();
		
		// assumes no player initially
		this.player = null;
	}
	
//	public State(Viewport v, int width, int height) {
//		this(v);
//		this.rowActors = height;
//		this.colActors = width;
//			
//	}
		
		
	private void initialise() {
				
		for(int i = 0; i < this.rowActors; i++) {
			for(int j = 0; j < this.colActors; j++) {
								
				final Tile tile = new Tile(new Coord(i,j));
				tileList.add(tile);
				this.addActor(tile);
				
				tile.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {	
						setTile(tile, selection);	
			        }
				});
			}
		}
	}

	//************************//
	//******** EDITOR ********//
	//************************//

	public void setDynamicSelection(DynamicObject obj) {
		this.selection = obj.getType();
		this.cur_d_object = obj;
	}
	
	public void setStaticSelection(GameObject obj) {
		this.selection = obj.getType();
		this.cur_object = obj;
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
		this.dynamicList.clear();
		this.staticList.clear();
	}
	
	
	/*
	 * Setting the tile texture
	 */
	private void setTile(Tile tile, ObjectType type) {
		if (type == null) return;
		
		GameObject obj = null;

		if(type == ObjectType.ENEMY || type == ObjectType.ITEM || type == ObjectType.PLAYER) {
			obj = cur_d_object.clone();
			obj.setCoord(tile.getCoord());
			// dynamicList.add((DynamicObject) obj);
			obj.setName(cur_d_object.getName());
		}else {
			obj = cur_object.clone();
			obj.setCoord(tile.getCoord());
			// staticList.add(obj);
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
	

//	//WHY is this needed?
//	public List<Coord> getAttackedLocations() {
//		//Set<Coord> s = new HashSet<Coord>();
//		for (DynamicObject d : dynamicList) {
//			if (d.getActionState() == ActionState.ATTACK) {
//				
//			}
//		}
//		//return new LinkedList<Coord>(s);
//	}

	public GameObject getObject(Coord coord) {
		return this.tileList.get(coord.getX()* colActors  + coord.getY()).getObject();
	}
	
	
	public DynamicObject getDynamicObject(Coord c) {
		GameObject g = getObject(c);
		if (g.isDynamic()) {
			return (DynamicObject) g;
		} else {
			return null;
		}
	}
	
	/*
	 * Sets a tile to a GameObject
	 */
	public void setObject(GameObject newObject, Coord coord) {
		
		// Keep track of objects
		ObjectType type = newObject.getType();
		Tile cur = tileList.get(coord.getX() * colActors  + coord.getY());
		
		switch(type) {
		case PLAYER:
			// Delete the old player and the flag if we're overriding it
			if (this.hasPlayer() == true) this.deletePlayer(player.getCoord());
			if (cur.getObjectType() == ObjectType.ITEM
					&& cur.getObject().getName() == "win") win = null;
			
			newObject.setCoord(coord);
			player = (Player) newObject;
			
			cur.setObject(player);
			break;
		case ENEMY:
		case ITEM:
			if (cur.getObjectType() == ObjectType.PLAYER) player = null;
			newObject.setCoord(coord);
			String name = newObject.getName();
			
			// Win condition handling
			if (name != null && name.equals("win")) {						
				if (win != null) this.deleteWin(win.getCoord());
				win = (DynamicObject) newObject;
			} 
			dynamicList.add((DynamicObject) newObject);
			cur.setDynamicObject((DynamicObject) newObject);
			break;
		case WALL:
			staticList.add(newObject);
			newObject.setCoord(coord);
			cur.setObject(newObject);
			break;
		case FLOOR:
			staticList.add(newObject);
			newObject.setCoord(coord);
			cur.setFloor(newObject);
			break;
		case WAYPOINT:
			staticList.add(newObject);
			newObject.setCoord(coord);
			cur.setObject(newObject);
			break;
		default:
			break;

		}
	}
	
	/*
	 * Gets the co-ordinates of the finish line
	 * returns null if no finish line (allow players to roam freely)
	 * OSCAR
	 */
	public Coord getFinish() {
		if (win == null) return null;
		return win.getCoord();
	}
	
	/*
	 * Same as deletePlayer but didn't want to cause any conflicts right now
	 * Up for refactoring
	 */
	public void deleteWin(Coord coord) {
		Tile tile = tileList.get(coord.getX() * colActors + coord.getY());
		tile.deleteObject();
	}
	

	/*
	 * Deletes the object at that tile entirely, including getting rid of the texture
	 */
	public void deletePlayer(Coord coord) {
		Tile tile = tileList.get(coord.getX()* colActors  + coord.getY());
		tile.deleteObject();
	}
	
	public void fillObject(GameObject g) {
		for (int x = 0; x < rowActors; x++) {
			for (int y = 0; y < colActors; y++) {
				setObject(g.clone(), new Coord(x,y));
			}
		}
	}
	
	/*
	 * Attack the object that we're facing
	 */
	public void attackObject(Coord coord) {
		
		// Check if the position is in bounds
		if (!this.isValid(coord)) return;
		Tile tile = tileList.get(coord.getX()* colActors  + coord.getY());
		if (tile.getObject() != null && tile.getObject().getType().equals(ObjectType.ENEMY)) {
			deleteObject(coord);
		}
	}
	
	public void deleteObject(Coord coord) {

		Tile tile = tileList.get(coord.getX()* colActors  + coord.getY());
		GameObject obj = tile.getObject();
		
		ObjectType type = obj.getType();
		
		if(type == ObjectType.PLAYER || type == ObjectType.ITEM || type == ObjectType.ENEMY) {
			dynamicList.remove(obj);
		}else {
			staticList.remove(obj);
		}	
		this.tileList.get(coord.getX()* colActors  + coord.getY()).deleteObject();
	}
	

	public void moveObject(Coord from, Coord to) {
		Tile t = getTile(to);
		t.moveObjectTo(getTile(from));
	}
	
	
	public void swapObject(Coord from, Coord to) {
		GameObject fromObject = this.getObject(from);
		GameObject toObject = this.getObject(to);
		this.setObject(fromObject, to);
		this.setObject(toObject, from);
	}
	
	
	public List<GameObject> getAllObjects() {
		List<GameObject> newList = new ArrayList<GameObject>(staticList.size() + dynamicList.size());
		newList.addAll(staticList);
		newList.addAll(dynamicList);
		return newList;
	}
	
	
	public List<DynamicObject> getAllDynamicObjects() {
		return dynamicList;
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
	public DynamicObject getPlayer(){
		return this.player;
	}
	
	public void deletePlayer(){
		this.deleteObject(player.getCoord());
		this.player = null;
	}
	
	
	/*
	 * When moving player, don't actually delete the object
	 * Just delete the tile object
	 */
	public void movePlayer(Coord to) {
		if (player == null) return;		
		moveObject(player.getCoord(), to);
		
		Tile new_tile = this.tileList.get(to.getX() * colActors + to.getY());
		new_tile.flipObject(player.facingRight(), player.getImgPath());
	}
	//************************//
	//******* TERRAIN ********//
	//************************//
	
	public boolean hasWall(Coord pos) {
		GameObject g = getTile(pos).getObject();
		return (g != null) && (g.getType() == ObjectType.WALL);
	}
	
	/*
	 * Returns if the next position will block
	 * Also checks win condition
	 */
	public boolean isBlocked(Coord pos) {
		if (win != null && pos.equals(win.getCoord())) {		// If winnable
			System.out.println("Win!");
			return false;
		}
		return (getTile(pos).hasObject());
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
		
		if(player == null) {
			System.out.println("No player object set!");
			satisfied = false;
		}

		if(!satisfied)
			return null;
		
		
		/*
		 * ORDER MATTERS IN WHICH YOU PUT ONTO THE TABLE
		 * Ensure static objects iterated over first
		 */
		EditorModel model = new EditorModel(this.rowActors, this.colActors);
		TileTuple[][] encodedTable = model.getEncodedTable();
		
		// Static Objects
		for(GameObject obj : staticList) {
			System.out.println("hi");
			Coord c = obj.getCoord();
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
		//model.display();
		return model;
	}


	/*
	 * Retrieve the objects from encoded table
	 * Restore the state 
	 * Directly sets the objects
	 */
	public void restoreModel(EditorModel m) {
		TileTuple[][] encodedTable = m.getEncodedTable();	
		resize(m.getRows(), m.getCols());

		for(int i = 0; i < this.rowActors; i++) {
			for(int j = 0; j < this.colActors; j++) {
				TileTuple enc_tile = encodedTable[i][j];
				
				if(enc_tile == null || enc_tile.isEmpty())
					continue;

				ObjectType type = enc_tile.getID();
				
				GameObject base = null;
				DynamicObject d_obj = null;
				
				// type will only show the top-most layer
				if(type == ObjectType.ENEMY || type == ObjectType.PLAYER || type == ObjectType.ITEM) {
					setObject(enc_tile.getDynamic(), new Coord(i, j));
				} 
				
				base = enc_tile.getBase();
				if(base != null)
					setObject(base, new Coord(i, j));
									
			}
		}
	}
	
	public boolean isValid(Coord next) {
		// TODO Auto-generated method stub
		return (next.getX() >= 0 && next.getX() < colActors) &&
				(next.getY() >= 0 && next.getY() < rowActors);
	}
		
	public TableTuple getDim() {
		return new TableTuple(rowActors, colActors);
	}
	public int getRow() {
		// TODO Auto-generated method stub
		return this.rowActors;
	}
	
	public int getColumn() {
		// TODO Auto-generated method stub
		return this.colActors;
	}
	
	public void resize(int rows, int cols) {
		// TODO Auto-generated method stub
		this.clear();
		this.rowActors = rows;
		this.colActors = cols;
		
		this.tileList = null;
		this.dynamicList = null;
		this.staticList = null;
		
		this.tileList = new ArrayList<Tile>();
		this.dynamicList = new ArrayList<DynamicObject>();
		this.staticList = new ArrayList<GameObject>();
		this.player = null;
		this.win = null;
		initialise();
	}
}
