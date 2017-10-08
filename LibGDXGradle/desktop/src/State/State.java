package State;

import java.io.Serializable;
import java.util.HashMap;

import Tileset.*;
import Tileset.GameObject.ObjectType;

public class State implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_MAP_WIDTH = 50; // 50 tiles 
	private static final int DEFAULT_MAP_HEIGHT = 50;
	
	// Coords of player
	private Coordinates playerCoord;
	
	// Map is hashmap of hashmap of tiles
	private HashMap<Integer, HashMap<Integer, Tile>> map;
	// The first index is x, the second index is y
	
	
	//************************//
	//******* CREATORS *******//
	//************************//
	
	// default create an empty State
	public State(){
		// Initialise map
		this.map = new HashMap<Integer, HashMap<Integer, Tile>>(DEFAULT_MAP_WIDTH); 
		// Initialise Tiles
		Coordinates tempCoord = new Coordinates();
		for (int i = 0; i < DEFAULT_MAP_WIDTH; i++) {
			this.map.put(i, HashMap<Integer, Tile>);
			
		}
	}
	
	
	// Create empty state of x width and y height
	public State(int width, int height){
		// Default player position is outside the map -1,-1
		this.playerCoord = new Coordinates();
		
		this.map = new Tile[width][height];
		
		// Initialise Tile objects 
		Coordinates tempCoord = new Coordinates();
		for(int i = 0; i <  width; i++){
			for(int j = 0; j < height; j++){
				tempCoord.setX(i);
				tempCoord.setY(j);
				map[i][j] = new Tile(tempCoord); 
			}
		}
	}
	
	
	
	//************************//
	//******* GENERAL ********//
	//************************//
	
	public GameObject getObject(ObjectType type, Coordinates coord) {
		return this.map[coord.getX()][coord.getY()].getObject(type);
	}
	
	public boolean setObject(GameObject newObject, Coordinates coord) {
		return this.map[coord.getX()][coord.getY()].setObject(newObject);
	}
	
	public boolean deleteObject(ObjectType type, Coordinates coord) {
		return this.map[coord.getX()][coord.getY()].deleteObject(type);
	}
	
	public boolean moveObject(ObjectType type, Coordinates from, Coordinates to) {
		// From tile does not have object
		if (!this.map[from.getX()][from.getY()].hasObject(type)) {
			return false;
		}
		// To Tile already has object
		if (this.map[to.getX()][to.getY()].hasObject(type)) {
			return false;
		} 
		// Do the move
		GameObject temp = this.map[from.getX()][from.getY()].getObject(type);
		this.map[from.getX()][from.getY()].deleteObject(type);
		this.map[to.getX()][to.getY()].setObject(temp);
		return true;
	}
	
	
	public List<GameObject> getAllObjects() {
		List<GameObject> ret = new LinkedList<GameObject>();
		for (Tile[] ta : map) {
			for (Tile t : ta) {		
				for (ObjectType type : ObjectType.values()) {
					GameObject toAdd = getObject(type, t.getCoord());
					if (toAdd != null) {
						ret.add(toAdd);
					}
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
		return (Player) this.map[playerCoord.getX()][playerCoord.getY()].getObject(ObjectType.PLAYER);
	}
	
	// Remove player from current coords and set player coords to -1,-1
	// Returns false if player is already deleted/not on the map
	public boolean deletePlayer(){
		// Not on map
		if (this.playerCoord.getX() ==  -1 && this.playerCoord.getY() == -1){
			return false;
		}
		// Try to delete, return false if fail
		if(!this.map[this.playerCoord.getX()][this.playerCoord.getY()].deleteObject(ObjectType.PLAYER)) {
			return false;
		}
		
		this.playerCoord.setX(-1);
		this.playerCoord.setY(-1);
		return true;
	}
	
	// Moves player to different tile
	// Returns false if player is already on that Tile
	public boolean setPlayer(Coordinates to){
		// Already there
		if(this.playerCoord.getX() == to.getX() && this.playerCoord.getY() == to.getY()){
			return false;
		}
		
		Player currPlayer = this.getPlayer();
		this.deletePlayer();
		return this.map[to.getX()][to.getY()].setObject(currPlayer);
	}
	
	// Same as setPlayer, redundant 
	public boolean movePlayer(Coordinates to){
		return this.setPlayer(to);
	}
	
	
	
	//************************//
	//******** TILES *********//
	//************************//
	
	// Swaps the contents of two Tiles
	public void swapTiles(Coordinates from, Coordinates to) {
		Tile tempTile = new Tile();
		tempTile = this.map[from.getX()][from.getY()];
		this.map[from.getX()][from.getY()] = this.map[to.getX()][to.getY()];
		this.map[to.getX()][to.getY()] = tempTile;
	}
		
	/*
	
	// Use ArrayList of ArrayLists instead of 2D array to implement:
	
	public void createRow() {
		
	}
	
	public void createColoumn() {
		
	}
	
	public void deleteRow() {
		
	}
	
	public void deleteColoumn() {
		
	}
	*/
	
	// Terrain helper
	
	public boolean isBlocked(Coordinates pos) {
		return !((Terrain) this.map[pos.getX()][pos.getY()].getObject(ObjectType.TERRAIN)).checkPassable();
	}
	
	public boolean isBlocked(Coordinates pos, ObjectType type) {
		if (type == null) return isBlocked(pos);
		return (!((Terrain) this.map[pos.getX()][pos.getY()].getObject(ObjectType.TERRAIN)).checkPassable()
				&& (this.map[pos.getX()][pos.getY()].getObject(type) != null));
	}


	public Tile getTile(Coordinates coord) {
		// TODO Auto-generated method stub
		return null;
	}


	public DynamicObject[] getAllDynamicObjects() {
		// TODO Auto-generated method stub
		return null;
	}
}
