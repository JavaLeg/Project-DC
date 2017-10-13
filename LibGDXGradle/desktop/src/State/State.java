package State;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

import Tileset.*;
import Tileset.EnemyFactory.EnemyType;
import Tileset.GameObject.ObjectType;
import Tileset.PlayerFactory.PlayerType;
import Tileset.TerrainFactory.TerrainType;

public class State implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_MAP_WIDTH = 50; // 50 tiles 
	private static final int DEFAULT_MAP_HEIGHT = 50;
	
	// Coords of player
	private Coord playerCoord;
	
	private List<List<Tile>> map;
	// The outer index is x, the inner index is y
	private int mapWidth;
	private int mapHeight;
	
	
	//************************//
	//******* CREATORS *******//
	//************************//
	
	// default create an empty State
	public State(){
		// Default player position is outside the map -1,-1
		this.playerCoord = new Coord();
		
		this.map = new ArrayList<List<Tile>>();
		
		for(int i = 0; i < DEFAULT_MAP_WIDTH; i++) {
			this.map.add(new ArrayList<Tile>());
			for(int j = 0; j < DEFAULT_MAP_HEIGHT; j++) {
				Coord tempCoord = new Coord(i,j);
				this.map.get(i).add(new Tile(tempCoord));
			}
		}
		
		this.mapWidth = DEFAULT_MAP_WIDTH;
		this.mapHeight = DEFAULT_MAP_HEIGHT;
	}
	
	
	// Create empty state of x width and y height
	public State(int width, int height){
		// Default player position is outside the map -1,-1
		this.playerCoord = new Coord();
		
		this.map = new ArrayList<List<Tile>>();
		
		for(int i = 0; i < width; i++) {
			this.map.add(new ArrayList<Tile>());
			for(int j = 0; j < height; j++) {
				Coord tempCoord = new Coord(i,j);
				this.map.get(i).add(new Tile(tempCoord));
			}
		}
		
		this.mapWidth = width;
		this.mapHeight = height;
	}
	
	
	
	//************************//
	//******* GENERAL ********//
	//************************//
	
	// Example: state.createEnemy(EnemyType.SLIME, coord);
	public void createEnemy(EnemyType type, Coord coord, Texture texture) {
		EnemyFactory factory = new EnemyFactory();
		Enemy object = factory.getEnemy(type, coord, texture);
		setObject(object, coord);
	}
	
	public void createPlayer(PlayerType type, Coord coord, Texture texture) {
		PlayerFactory factory = new PlayerFactory();
		Player object = factory.getPlayer(type, coord, texture);
		setObject(object, coord);
	}
	
	public void createTerrain(TerrainType type, Coord coord, Texture texture) {
		TerrainFactory factory = new TerrainFactory();
		Terrain object = factory.getTerrain(type, coord, texture);
		setObject(object, coord);
	}
	
	public GameObject getObject(Coord coord) {
		return this.map.get(coord.getX()).get(coord.getY()).getObject();
	}
	
	public void setObject(GameObject newObject, Coord coord) {
		this.map.get(coord.getX()).get(coord.getY()).setObject(newObject);
	}
	
	public void deleteObject(Coord coord) {
		this.map.get(coord.getX()).get(coord.getY()).deleteObject();
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
	
	private List<Coord> l = Arrays.asList(new Coord(1,2), new Coord(2,1), new Coord(0,3), new Coord(4,1), 
			new Coord(4,2), new Coord(4,3), new Coord(4,4), new Coord(5,4), new Coord(6,2), new Coord(6,6), new Coord(3,3));
	
	public boolean isBlocked(Coord pos) {
		if (l.contains(pos)) return true;
		if (true) return false; // TEMPORARY BYPASS
		return !((Terrain) this.map.get(pos.getX()).get(pos.getY()).getObject()).isPassable();
	}
	
	
	
	public boolean isBlocked(Coord pos, ObjectType type) {
		if (l.contains(pos)) return true;
		if (true) return false; // TEMPORARY BYPASS
		if ( this.map.get(pos.getX()).get(pos.getY()).getObject()  == null) return false;
		if (type == null) return isBlocked(pos);
		return (!((Terrain) this.map.get(pos.getX()).get(pos.getY()).getObject()).isPassable()
				&& (this.map.get(pos.getX()).get(pos.getY()).hasObject()));
	}
	
	
	
	//************************//
	//******** TILES *********//
	//************************//
	
	public Tile getTile(Coord coord) {
		return this.map.get(coord.getX()).get(coord.getY()); 
	}

	public void clearTile(Coord coord) {
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
			Coord tempCoord = new Coord(i, this.mapHeight);
			this.map.get(i).add(new Tile(tempCoord));
		}
		
		// Shift objects up
		for (int j = this.mapHeight-1; j > idx; j--) {
			for(int k = 0; k < this.mapWidth; k++) {
				Coord toCoord = new Coord(k, j);
				Coord fromCoord = new Coord(k,j-1);
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
				Coord toCoord = new Coord(j,i);
				Coord fromCoord = new Coord(j,i+1);
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
			Coord tempCoord = new Coord(this.mapWidth, i);
			this.map.get(this.mapWidth).add(new Tile(tempCoord));
		}
		
		// Shift objects right
		for(int j = this.mapWidth-1; j > idx; j--) {
			for(int k = 0; k < this.mapHeight; k++) {
				Coord toCoord = new Coord(j,k);
				Coord fromCoord = new Coord(j-1,k);
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
				Coord toCoord = new Coord(i,j);
				Coord fromCoord = new Coord(i+1,j);
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
