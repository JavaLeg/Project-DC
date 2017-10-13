package State;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.prism.image.Coords;

import Tileset.*;
import Tileset.GameObject.ObjectType;
import Interface.Stages.Editor;
import Interface.Stages.TableTuple;
import Interface.Stages.Selections.ToolbarSelection;

public class State extends Stage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_MAP_WIDTH = 50; // 50 tiles 
	private static final int DEFAULT_MAP_HEIGHT = 50;
	
	// Coords of player
	private Coord playerCoord;

	private int rowActors;
	private int colActors;
	private Stage related;

	private boolean has_player = false;			// Is this the appropriate place
	
	private TableTuple tablePos;
	private TextureRegion selected_tr;
	
	private ArrayList<Tile> tileList;
	
	// Should rename it soon, Image Stack can hold more than one "layer" of object objects.
	private ToolbarSelection selectedLayer;
	
	// private List<List<Tile>> map;
	// The outer index is x, the inner index is y
	// private int mapWidth;
	// private int mapHeight;
	
	
	//************************//
	//******* CREATORS *******//
	//************************//
	
	// default create an empty State
	public State(Viewport v, int viewWidth, int viewHeight, int tileWidth, int tileHeight){
		super(v);
		this.rowActors = viewWidth/tileWidth - 2;
		this.colActors = viewHeight/tileHeight + 1;
		this.tileList = new ArrayList<Tile>();
		initialise(tileWidth, tileHeight);
		
		// Default player position is outside the map -1,-1
		this.playerCoord = new Coord();
		
		// this.map = new ArrayList<List<Tile>>();
		
		// for(int i = 0; i < DEFAULT_MAP_WIDTH; i++) {
		// 	this.map.add(new ArrayList<Tile>());
		// 	for(int j = 0; j < DEFAULT_MAP_HEIGHT; j++) {
		// 		Coordinates tempCoord = new Coordinates(i,j);
		// 		this.map.get(i).add(new Tile(tempCoord));
		// 	}
		// }
		
		// this.mapWidth = DEFAULT_MAP_WIDTH;
		// this.mapHeight = DEFAULT_MAP_HEIGHT;
	}

	//************************//
	//***** INITIALISE *******//
	//************************//

	private void initialise(int tileWidth, int tileHeight) {
		Table gridTable = new Table();
		//ImageStack[] tiles = new ImageStack[rowActors * colActors];
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
				System.out.println("x: " + i + " y: " + j);
								
				final Tile tile = new Tile();
				tileList.add(tile);
				
				tile.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						// Allow for only one player per map (multiplayer possibly later)
						if (selectedLayer == ToolbarSelection.PLAYER) {
							if (has_player == true) return;
							has_player = true;
						}
						setTileTexture(tile, selectedLayer);
			        }
				});
				gridTable.add(tile).size(40, 40);
			}
			gridTable.row();
		}
		//gridTable.setPosition(tablePos.getX(), tablePos.getY());
		gridTable.top();
		gridTable.setFillParent(true);
		super.addActor(gridTable);
	}

	/*
	private ImageStack ImageStack(TextureRegion textureRegion) {
		// TODO Auto-generated method stub
		return null;
	}
	*/

	public void setDependence(Stage s) {
		this.related = s;
	}
	
	public void setDependence(Editor s) {
		this.related = s;
	}
	
	public void setSelection(Texture s, ToolbarSelection ts) {

		this.selected_tr = new TextureRegion(s);
		this.selectedLayer = ts;
	}

	public void fillGrid() {
		
		if(selected_tr == null || selectedLayer != ToolbarSelection.FLOOR) 
			return;
		
		Texture texture = selected_tr.getTexture();
		String path = ((FileTextureData)texture.getTextureData()).getFileHandle().name();
		System.out.println("Fill grid with : " + path);
		
		for(Tile tile : tileList) {
			setTileTexture(tile, selectedLayer);
		}
	}
	
	public void clearGrid() {		
		has_player = false;
		for(Tile tile : tileList) {
			tile.clear();
		}
	}
	
	private void setTileTexture(Tile tile, ToolbarSelection ts) {
		if (ts == null) return;
		switch (ts){
		case FLOOR:
			Image temp = new Image(selected_tr);
			tile.setFloor(temp);
			break;
		case ENEMY:
			// TODO:
			break;
		case ITEM:
			// TODO:
			break;	
		case WALL:
			// TODO:
			break;
		case PLAYER:
			// TODO:
			break;
		case SAVE:
			break;
		}
	}

	/*
	 * Check the map is valid before saving (e.g, at least one player)
	 * At least one tile, (check creatures on tile, etc. etc.)
	 */
	public boolean checkValidMap() {
		// TODO Auto-generated method stub
		boolean no_err = true;
		
		if (has_player == false) {
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
	
	//************************//
	//******* GENERAL ********//
	//************************//
	
	public GameObject getObject(Coord coord) {
		return this.tileList.get(coord.getX()  + coord.getY() * colActors).getObject();
	}
	
	public void setObject(GameObject newObject, Coord coord) {
		this.tileList.get(coord.getX()  + coord.getY() * colActors).setObject(newObject);
	}
	
	public void deleteObject(Coord coord) {
		this.tileList.get(coord.getX()  + coord.getY() * colActors).deleteObject();
	}
	
	public void moveObject(Coord from, Coord to) {
		GameObject temp = getObject(from);
		deleteObject(from);
		setObject(temp, to);
	}
	
	public void swapObject(Coord from, Coord to) {
		GameObject fromObject = getObject(from);
		GameObject toObject = getObject(to);
		setObject(fromObject, to);
		setObject(toObject, from);
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
	
	// Return coord of player
	public Coord findPlayer(){
		return this.playerCoord;
	}
	
	// Get player object
	public Player getPlayer(){
		return (Player) getObject(playerCoord);
	}
	
	// Remove player from current coords and set player coords to -1,-1
	// Returns false if player is already deleted/not on the map
	public void deletePlayer(){
		deleteObject(playerCoord);
		this.playerCoord.setX(-1);
		this.playerCoord.setY(-1);
	}
	
	// Moves player to different tile
	// Returns false if player is already on that Tile
	public void setPlayer(Coord to){
		Player currPlayer = this.getPlayer();
		this.deletePlayer();
		setObject(currPlayer, to);
	}
	
	// Same as setPlayer, redundant 
	public void movePlayer(Coord to){
		this.setPlayer(to);
	}
	
	
	
	//************************//
	//******* TERRAIN ********//
	//************************//
	
//	private List<Coord> l = Arrays.asList(new Coord(1,2), new Coord(2,1), new Coord(0,3), new Coord(4,1), 
//			new Coord(4,2), new Coord(4,3), new Coord(4,4), new Coord(5,4), new Coord(6,2), new Coord(6,6), new Coord(3,3));
	
//	public boolean isBlocked(Coord pos) {
//		if (l.contains(pos)) return true;
//		if (true) return false; // TEMPORARY BYPASS
//		return !((Wall) this.tileList.get(pos.getX()  + pos.getY() * colActors).getObject()).isPassable();
//	}
//	
//	
//	
//	public boolean isBlocked(Coord pos, ObjectType type) {
//		if (l.contains(pos)) return true;
//		if (true) return false; // TEMPORARY BYPASS
//		if ( this.tileList.get(pos.getX()  + pos.getY() * colActors).getObject()  == null) return false;
//		if (type == null) return isBlocked(pos);
//		return (!((Wall) this.tileList.get(pos.getX()  + pos.getY() * colActors).getObject()).isPassable()
//				&& (this.tileList.get(pos.getX()  + pos.getY() * colActors).hasObject()));
//	}
	
	
	
	//************************//
	//******** TILES *********//
	//************************//
	
	public Tile getTile(Coord coord) {
		return this.tileList.get(coord.getX()  + coord.getY() * colActors); 
	}

	public void clearTile(Coord coord) {
		deleteObject(coord);
	}
	
	public int getMapWidth() {
		return colActors;
	}

	public int getMapHeight() {
		return rowActors;
	}
}
