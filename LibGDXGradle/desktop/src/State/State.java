package State;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
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
	
	//************************//
	//****** CONSTRUCTOR *****//
	//************************//
	

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
						setTileTexture(tile, selectedToolBar);
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
	
	
	/*
	 * DynamicObject Selection
	 */
	public void setSelection(Texture t, ToolbarSelection s, DynamicObject obj) {
		selected_tr = new TextureRegion(t);
		selectedToolBar = s;
		if (s != ToolbarSelection.FLOOR) cur_d_object = obj;
	}
	
	
	/*
	 * GameObject selection
	 */
	public void setSelection(Texture t, ToolbarSelection s, GameObject obj) {
		selected_tr = new TextureRegion(t);
		selectedToolBar = s;
		if (s != ToolbarSelection.FLOOR) cur_object = obj;
	}
	
	
	// Fill grid with selected floor
	public void fillGrid() {
		if(selected_tr == null || selectedToolBar != ToolbarSelection.FLOOR) 
			return;
		
		Texture texture = selected_tr.getTexture();
		String path = ((FileTextureData)texture.getTextureData()).getFileHandle().name();
		System.out.println("Fill grid with : " + path);
		
		for(Tile tile : tileList) {
			setTileTexture(tile, ToolbarSelection.FLOOR);
		}
	}
	
	
	public void clearGrid() {		
		this.player = null;
		for(Tile tile : tileList) {
			tile.clear();
		}
		this.tileList.clear();
		this.enemyList.clear();
		this.wallList.clear();
		this.itemList.clear();
	}
	
	
	/*
	 * Setting the tile texture, if it's an object we pass that instead
	 */
	private void setTileTexture(Tile tile, ToolbarSelection ts) {
		// If tile already has an object, remove it and return
		if(tile.hasObject() && ts != ToolbarSelection.FLOOR) {
			this.deleteObject(tile.getCoord());
			return;
		}
		if (ts == null) return;
		
		switch (ts){
		case FLOOR:
			tile.setFloor(selected_tr);
			break;
		case ENEMY:
			setObject(cur_d_object, tile.getCoord());
			break;
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
			break;
		default:
			// SAVE, EDIT
			break;
		}
	}


	/*
	 * Check the map is valid before saving (e.g, at least one player)
	 * At least one tile, (check creatures on tile, etc. etc.)
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
	public void isEditable() {
		System.out.println(selectedToolBar);
	}
	
	
	//************************//
	//******** OBJECT ********//
	//************************//
	
	public GameObject getObject(Coord coord) {
		return this.tileList.get(coord.getX()* colActors  + coord.getY()).getObject();
	}
	
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
			break;
		}
		
		this.tileList.get(coord.getX()* colActors  + coord.getY()).deleteObject();
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
			if(ta.getObject().isDynamic()) {
				ret.add((DynamicObject) ta.getObject());
			}
		}
		return ret;
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
	//******** OTHER *********//
	//************************//
	

	public EditorModel getModel() {
		EditorModel model = new EditorModel(rowActors, colActors);
		
		// Conversion should not take place inside the object
		for(int i = 0; i < tileList.size(); i++) {
			int row_val = i/colActors;
			int col_val = i % colActors;
			
			Tile tile = tileList.get(i);
			ObjectType ID = tile.getObjectType();
			TileTuple t = new TileTuple(tile.getObjectPath(), tile.getFloorPath(), ID);
			model.setTile(t, row_val, col_val);
		}
		return model;
	}
	

	/*
	 * Regenerate the textures from string paths
	 * Place back onto grid via direct calls instead of click listeners
	 */
	public void restoreModel(EditorModel m) {
		TileTuple[][] map = m.getmodelPaths();
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
				int index = colActors*i + j;
				
				TileTuple t_tuple = map[i][j];
				Tile tile = tileList.get(index);

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
				}
			}
		}
	}

	/* 
	 * Determines if the next position is valid (for player)
	 */
	public boolean isValid(Coord next) {
		// TODO Auto-generated method stub
		return (next.getX() >= 0 && next.getX() < colActors) && (next.getY() >= 0 && next.getY() < rowActors);
	}
		
	public TableTuple getDim() {
		return new TableTuple(rowActors, colActors);
	}
	
	
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
	 */
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
	}
	*/
}
