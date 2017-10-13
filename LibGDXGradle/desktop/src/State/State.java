package State;

import java.io.Serializable;
import java.util.ArrayList;
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
	private Coordinates playerCoord;

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
		this.playerCoord = new Coordinates();
		
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
						
//						tile.setTexture(selected_tr, selectedLayer);
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
		
		if(selected_tr == null || selectedLayer != ToolbarSelection.TERRAIN) 
			return;
		
		
		Texture texture = selected_tr.getTexture();
		String path = ((FileTextureData)texture.getTextureData()).getFileHandle().name();
		System.out.println("Fill grid with : " + path);
		
		for(Tile tile : tileList) {
			tile.setTexture(selected_tr, selectedLayer);
		}
	}
	
	public void clearGrid() {		
		has_player = false;
		for(Tile tile : tileList) {
			tile.clear();
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
	
	// Example: state.createEnemy(EnemyType.SLIME, coord);
	public void createEnemy(EnemyType type, Coordinates coord) {
		EnemyFactory factory = new EnemyFactory();
		Enemy object = factory.getEnemy(type, coord);
		setObject(object, coord);
	}
	
	public void createPlayer(PlayerType type, Coordinates coord) {
		PlayerFactory factory = new PlayerFactory();
		Player object = factory.getPlayer(type, coord);
		setObject(object, coord);
	}
	
	public void createTerrain(TerrainType type, Coordinates coord) {
		TerrainFactory factory = new TerrainFactory();
		Terrain object = factory.getTerrain(type, coord);
		setObject(object, coord);
	}
	
	public GameObject getObject(Coordinates coord) {
		return this.map.get(coord.getX()).get(coord.getY()).getObject();
	}
	
	public void setObject(GameObject newObject, Coordinates coord) {
		this.map.get(coord.getX()).get(coord.getY()).setObject(newObject);
	}
	
	public void deleteObject(Coordinates coord) {
		this.map.get(coord.getX()).get(coord.getY()).deleteObject();
	}
	
	public void moveObject(Coordinates from, Coordinates to) {
		GameObject temp = getObject(from);
		deleteObject(from);
		setObject(temp, to);
	}
	
	public void swapObject(Coordinates from, Coordinates to) {
		GameObject fromObject = getObject(from);
		GameObject toObject = getObject(to);
		setObject(fromObject, to);
		setObject(toObject, from);
	}
	
	
	public List<GameObject> getAllObjects() {
		List<GameObject> ret = new LinkedList<GameObject>();
		for (List<Tile> ta : map) {
			for (Tile t : ta) {		
				ret.add(t.getObject());
			}
		}
		return ret;
	}
	
	public List<DynamicObject> getAllDynamicObjects() {
		List<DynamicObject> ret = new LinkedList<DynamicObject>();
		for (List<Tile> ta : map) {
			for (Tile t : ta) {		
				if(t.getObject().isDynamic()) {
					ret.add((DynamicObject) t.getObject());
				}
			}	
		}
		return ret;
	}
	
	
	//************************//
	//******* PLAYER *********//
	//************************//
	
	// Return coord of player
	public Coordinates findPlayer(){
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
	public void setPlayer(Coordinates to){
		Player currPlayer = this.getPlayer();
		this.deletePlayer();
		setObject(currPlayer, to);
	}
	
	// Same as setPlayer, redundant 
	public void movePlayer(Coordinates to){
		this.setPlayer(to);
	}
	
	
	
	//************************//
	//******* TERRAIN ********//
	//************************//
	
	public boolean isBlocked(Coordinates pos) {
		return !((Terrain) this.map.get(pos.getX()).get(pos.getY()).getObject()).isPassable();
	}
	
	public boolean isBlocked(Coordinates pos, ObjectType type) {
		if (type == null) return isBlocked(pos);
		return (!((Terrain) this.map.get(pos.getX()).get(pos.getY()).getObject()).isPassable()
				&& (this.map.get(pos.getX()).get(pos.getY()).hasObject()));
	}
	
	
	
	//************************//
	//******** TILES *********//
	//************************//
	
	public Tile getTile(Coordinates coord) {
		return this.map.get(coord.getX()).get(coord.getY()); 
	}

	public void clearTile(Coordinates coord) {
		deleteObject(coord);
	}
	
	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}
	
	public void createRow(int idx) {
		// Create the row at the top
		this.mapHeight++;
		for(int i = 0; i < this.mapWidth; i++) {
			Coordinates tempCoord = new Coordinates(i, this.mapHeight);
			this.map.get(i).add(new Tile(tempCoord));
		}
		
		// Shift objects up
		for (int j = this.mapHeight-1; j > idx; j--) {
			for(int k = 0; k < this.mapWidth; k++) {
				Coordinates toCoord = new Coordinates(k, j);
				Coordinates fromCoord = new Coordinates(k,j-1);
				moveObject(fromCoord, toCoord);
				getObject(toCoord).setCoord(toCoord);
			}
		}
	}
	
	public void createRows(int idx, int n) {
		for (int i = 0; i < n; i++) {
			createRow(idx);
		}
	}
	
	public void deleteRow(int idx) {
		// Shift objects down
		for (int i = idx; i < this.mapHeight-1; i++) {
			for(int j = 0; j < this.mapWidth; j++) {
				Coordinates toCoord = new Coordinates(j,i);
				Coordinates fromCoord = new Coordinates(j,i+1);
				moveObject(fromCoord, toCoord);
				getObject(toCoord).setCoord(toCoord);
			}
		}
		
		// Delete row
		for(int k = 0; k < this.mapWidth; k++) {
			this.map.get(k).remove(this.mapHeight);
		}
		this.mapHeight--;
	}
	
	public void deleteRows(int idx, int n) {
		for(int i = 0; i < n; i++) {
			deleteRow(idx);
		}
	}
	
	public void createColoumn(int idx) {
		// Create coloumn on right
		this.map.add(new ArrayList<Tile>());
		this.mapWidth++;
		for(int i = 0; i < this.mapHeight; i++) {
			Coordinates tempCoord = new Coordinates(this.mapWidth, i);
			this.map.get(this.mapWidth).add(new Tile(tempCoord));
		}
		
		// Shift objects right
		for(int j = this.mapWidth-1; j > idx; j--) {
			for(int k = 0; k < this.mapHeight; k++) {
				Coordinates toCoord = new Coordinates(j,k);
				Coordinates fromCoord = new Coordinates(j-1,k);
				moveObject(fromCoord, toCoord);
				getObject(toCoord).setCoord(toCoord);
			}
		}
	}
	
	public void createColoumns(int idx, int n) {
		for(int i = 0; i < n; i++) {
			createColoumn(idx);
		}
	}
	
	public void deleteColoumn(int idx) {
		// Shift objects left
		for (int i = idx; i < this.mapWidth-1; i++) {
			for(int j = 0; j < this.mapHeight; j++) {
				Coordinates toCoord = new Coordinates(i,j);
				Coordinates fromCoord = new Coordinates(i+1,j);
				moveObject(fromCoord, toCoord);
				getObject(toCoord).setCoord(toCoord);
			}
		}
		
		// Delete Coloumn on right
		this.map.remove(this.mapWidth);
		this.mapWidth--;
	}
	
	public void deleteColoumns(int idx, int n) {
		for(int i = 0; i < n; i++) {
			deleteColoumn(idx);
		}
	}
}
