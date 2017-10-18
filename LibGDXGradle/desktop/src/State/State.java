package State;

import java.util.ArrayList;
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
import Interface.Stages.Selections.ToolbarSelection;


public class State extends Stage{
	
	private static final int DEFAULT_MAP_WIDTH = 50; 
	private static final int DEFAULT_MAP_HEIGHT = 50;
	
	private int rowActors;
	private int colActors;

	private TextureRegion selected_tr;
	private GameObject cur_object;
	private ToolbarSelection selectedToolBar;
	
	private ArrayList<Tile> tileList;
	
	private Player player;
	private ArrayList<Tile> enemy;
	
	//************************//
	//****** CONSTRUCTOR *****//
	//************************//
	
	// default create an empty State
	public State(Viewport v){
		super(v);
		this.rowActors = DEFAULT_MAP_HEIGHT;
		this.colActors = DEFAULT_MAP_WIDTH;
		this.tileList = new ArrayList<Tile>();
		initialise();
	}

	private void initialise() {
		Table gridTable = new Table();
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
								
				final Tile tile = new Tile(new Coord(i,j));
				tileList.add(tile);
				
				tile.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						setTileTexture(tile, selectedToolBar);
			        }
				});
				gridTable.add(tile).size(40, 40);
			}
			gridTable.row();
		}
		gridTable.top();
		gridTable.setFillParent(true);
		super.addActor(gridTable);
	}
	
	
	
	//************************//
	//******** EDITOR ********//
	//************************//
		
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
	}


	public void setSelection(Texture t, ToolbarSelection s, GameObject icon) {
		selected_tr = new TextureRegion(t);
		selectedToolBar = s;
		if (s != ToolbarSelection.FLOOR) cur_object = icon;
	}
	
	
	/*
	 * Setting the tile texture, if it's an object we pass that instead
	 */
	private void setTileTexture(Tile tile, ToolbarSelection ts) {
		if (ts == null) return;
		switch (ts){
		case FLOOR:
			tile.setFloor(selected_tr);
			break;
		case ENEMY:
		case ITEM:
		case WALL:
			cur_object.setCoord(tile.getCoord());
			tile.setObject(cur_object);
			// Overwrite player if same tile as player
			if (tile.getObjectType() == ObjectType.PLAYER) {
				this.player = null; 
			}
			break;
		case PLAYER:
			// If player already exists, move it
			if(this.hasPlayer()) {
				this.deletePlayer();
			}
			cur_object.setCoord(tile.getCoord());
			tile.setObject(cur_object);
			this.playerCoord = tile.getCoord();
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
		return this.tileList.get(coord.getX()  + coord.getY() * colActors).getObject();
	}
	
	public void setObject(GameObject newObject, Coord coord) {
		// Overwriting player?
		if(this.tileList.get(coord.getX()  + coord.getY() * colActors).getObjectType() == ObjectType.PLAYER) {
			this.playerCoord = null;
		}
		
		// Setting player?
		if(newObject.getType() == ObjectType.PLAYER) {
			this.playerCoord = coord;
		}
		
		newObject.setCoord(coord);;
		this.tileList.get(coord.getX()  + coord.getY() * colActors).setObject(newObject);
	}
	
	public void deleteObject(Coord coord) {
		if(this.tileList.get(coord.getX()  + coord.getY() * colActors).getObjectType() == ObjectType.PLAYER) {
			this.playerCoord = null;
		}
		this.tileList.get(coord.getX()  + coord.getY() * colActors).deleteObject();
	}
	
	
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
		return this.playerCoord != null;
	}
	
	
	// Return coord of player
	public Coord findPlayer(){
		return this.playerCoord;
	}
	
	// Get player object
	public Player getPlayer(){
		if (!this.hasPlayer()) return null;
		return (Player) this.getObject(this.playerCoord);
	}
	
	
	public void deletePlayer(){
		this.deleteObject(playerCoord);
		this.playerCoord = null;
	}
	
	
	public void movePlayer(Coord to) {
		Player temp = this.getPlayer();
		this.deletePlayer();
		this.setObject(temp, to);
		this.playerCoord = to;
	}
	
	
	//************************//
	//******* TERRAIN ********//
	//************************//
	
	public boolean isBlocked(Coord pos) {
		return !(this.tileList.get(pos.getX()  + pos.getY() * colActors).getObject()).isPassable();
	}
	
	
	
	//************************//
	//******** TILES *********//
	//************************//
	
	public Tile getTile(Coord coord) {
		return this.tileList.get(coord.getX()  + coord.getY() * colActors); 
	}
	
	
	// Deletes floor and object, replaces with empty tile texture
	public void clearTile(Coord coord) {
		this.tileList.get(coord.getX()  + coord.getY() * colActors).clear(); 
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
					TextureRegion cur_texture = new TextureRegion(new Texture(Gdx.files.internal(t_tuple.getObject())));
					GameObject new_obj = new GameObject(t_tuple.getID(), cur_texture);
					tile.setObject(new_obj);
				}
			}
		}
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
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
	    float dX = (float)(dragX - screenX)/(float)Gdx.graphics.getWidth();
	    float dY = (float)(screenY - dragY)/(float)Gdx.graphics.getHeight();
	    dragX = screenX;
	    dragY = screenY;
	    
	    this.getCamera().position.add(dX * intensity, dY * intensity, 0f);
	    this.getCamera().update();
	    return true;
	}
	*/
}
