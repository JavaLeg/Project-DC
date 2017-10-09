package State;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Tileset.*;
import Tileset.DynamicObject.DynamicObjectType;
import Tileset.GameObject.ObjectType;

public class State implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_MAP_WIDTH = 50; // 50 tiles 
	private static final int DEFAULT_MAP_HEIGHT = 50;
	
	// Coords of player
	private Coordinates playerCoord;
	
	// Map is hashmap of hashmap of tiles
	private Tile[][] map;
	// The first index is x, the second index is y
	
	
	//************************//
	//******* CREATORS *******//
	//************************//
	
	// default create an empty State
	public State(){
		// Default player position is outside the map -1,-1
		this.playerCoord = new Coordinates();
		
		this.map = new Tile[DEFAULT_MAP_WIDTH][DEFAULT_MAP_HEIGHT];
		
		// Initialise Tile objects 
		Coordinates tempCoord = new Coordinates();
		for(int i = 0; i <  DEFAULT_MAP_WIDTH; i++){
			for(int j = 0; j < DEFAULT_MAP_HEIGHT; j++){
				tempCoord.setX(i);
				tempCoord.setY(j);
				map[i][j] = new Tile(tempCoord); 
			}
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
	
	public GameObject getObject(Coordinates coord) {
		return this.map[coord.getX()][coord.getY()].getObject();
	}
	
	public boolean setObject(GameObject newObject, Coordinates coord) {
		return this.map[coord.getX()][coord.getY()].setObject(newObject);
	}
	
	public boolean deleteObject(Coordinates coord) {
		return this.map[coord.getX()][coord.getY()].deleteObject();
	}
	
	public boolean moveObject(Coordinates from, Coordinates to) {
		// From tile does not have object
		if (!this.map[from.getX()][from.getY()].hasObject()) {
			return false;
		}
		// To Tile already has object
		if (this.map[to.getX()][to.getY()].hasObject()) {
			return false;
		} 
		// Do the move
		GameObject temp = this.map[from.getX()][from.getY()].getObject();
		this.map[from.getX()][from.getY()].deleteObject();
		this.map[to.getX()][to.getY()].setObject(temp);
		return true;
	}
	
	
	public List<GameObject> getAllObjects() {
		List<GameObject> ret = new LinkedList<GameObject>();
		for (Tile[] ta : map) {
			for (Tile t : ta) {		
				ret.add(t.getObject());
			}
		}
		return ret;
	}
	
	public List<DynamicObject> getAllDynamicObjects() {
		List<DynamicObject> ret = new LinkedList<DynamicObject>();
		for (Tile[] ta : map) {
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
		return (Player) this.map[playerCoord.getX()][playerCoord.getY()].getObject();
	}
	
	// Remove player from current coords and set player coords to -1,-1
	// Returns false if player is already deleted/not on the map
	public boolean deletePlayer(){
		// Not on map
		if (this.playerCoord.getX() ==  -1 && this.playerCoord.getY() == -1){
			return false;
		}
		// Try to delete, return false if fail
		if(!this.map[this.playerCoord.getX()][this.playerCoord.getY()].deleteObject()) {
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
	//******* TERRAIN ********//
	//************************//
	
	public boolean isBlocked(Coordinates pos) {
		if(!((Terrain) this.map[pos.getX()][pos.getY()].getObject()).isPassable()) {
			return true;
		} else if (this.map[pos.getX()][pos.getY()].checkObject() == ObjectType.ENEMY) {
			return true;
		} else {
			return false;
		}
	}
	
	
	//************************//
	//******** TILES *********//
	//************************//
	
	public Tile getTile(Coordinates coords) {
		return this.map[coords.getX()][coords.getY()]; 
	}
	
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
}
