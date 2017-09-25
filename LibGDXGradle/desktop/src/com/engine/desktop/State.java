package com.engine.desktop;
private static final int DEFAULT_MAP_WIDTH = 50; 
private static final int DEFAULT_MAP_HEIGHT = 50;

public class State{
	// COORD 0,0 is the top left of the map
	
	// Coords of player
	int[2] playerCoord;
	
	// Matrix of Tiles
	Tile[][] map;
	// The first index is x, the second index is y
	
	
	
	//************************//
	//******* CREATORS *******//
	//************************//
	
	// default create an empty State
	public State(){
		// Default player position is outside the map -1,-1
		this.playerCoord = new int[]{-1,-1};
		
		this.map = new Tile[DEFAULT_MAP_WIDTH][DEFAULT_MAP_HEIGHT];
		
		// Initialise Tile objects 
		for(int i = 0; i <  DEFAULT_MAP_WIDTH; i++){
			for(int j = 0; j < DEFAULT_MAP_HEIGHT; j++){
				int[] coord = {i,j};
				Tile[i][j] = new Tile(coord); 
			}
		}
	}
	
	
	// Create empty state of x width and y height
	public State(int width, int height){
		// Default player position is outside the map -1,-1
		this.playerCoord = new int[]{-1,-1};
		
		this.map = new Tile[width][height];
		
		// Initialise Tile objects 
		for(int i = 0; i <  width; i++){
			for(int j = 0; j < height; j++){
				int[] coord = {i,j};
				Tile[i][j] = new Tile(coord); 
			}
		}
	}
	
	
	//************************//
	//******* PLAYER *********//
	//************************//
	
	// Return coord of player
	public int[] findPlayer(){
		return this.playerCoord;
	}
	
	// Get player object
	public Player getPlayer(){
		return this.map[this.playerCoord[0]][this.playerCoord[1]].getPlayer();
	}
	
	// Remove player from current coords and set player coords to -1,-1
	// Returns false if player is already deleted/not on the map
	public boolean deletePlayer(){
		if (this.playerCoord[0] == -1 && this.playerCoord[1] == -1){
			return false;
		}
		
		if(!this.map[this.playerCoord[0]][this.playerCoord[1]].deletePlayer(){
			return false;
		}
		
		this.playerCoord[0] = -1;
		this.playerCoord[1] = -1;
		return true;
	}
	
	// Moves player to different tile
	// Returns false if player is already on that Tile
	public boolean setPlayer(int[] coord){
		if(this.playerCoord[0] == coord[0] && this.playerCoord[1] == coord[1]){
			return false;
		}
		
		Player currPlayer = this.getPlayer();
		boolean redundantVariable = this.deletePlayer();
		return this.map[coord[0]][coord[1]].setPlayer(currPlayer);
	}
	
	// Same as setPlayer, redundant 
	public boolean movePlayer(int[] toCoord){
		return this.setPlayer(toCoord);
	}
	
	
	
	//************************//
	//******** ENEMY *********//
	//************************//
		
	// get enemy given coord of Tile
	public Enemy getEnemy(int[] coord){
		return this.map[coord[0][coord1].getEnemy();
	}
	
	// Set Tile at given coord with enemy
	// Returns false if there was already an enemy there
	public boolean setEnemy(int[] coord, Enemy newEnemy){
		return this.map[coord[0]][coord[1]].setEnemy(newEnemy);
	}
	
	// Delete enemy at Tile with given coord
	// Returns false if there was no enemy there
	public boolean deleteEnemy(int[] coord){
		return this.map[coord[0]][coord[1]].deleteEnemy() = NULL;
	}
	
	// Move enemy from one Tile to another, given coords of both
	// Returns false and does not do anything if no enemy at original Tile
	// Returns false and does not do anything if already enemy at destination Tile
	public boolean moveEnemy(int[] fromCoord, int[] toCoord){
		if(!this.map[fromCoord[0]][fromCoord[1].hasEnemy()){
			return false;
		}
		
		if(this.map[toCoord[0]][toCoord[1]].hasEnemy()){
			return false;
		}
		
		Enemy currEnemy = this.getEnemy(fromCoord);
		boolean redundantVariable = this.deleteEnemy(fromCoord);
		return this.setEnemy(toCoord, currEnemy);
	}
	
	
	
	//************************//
	//******** TRAP **********//
	//************************//
	
	// get trap given coord of Tile
	public Trap getTrap(int[] coord){
		return this.map[coord[0][coord1].getTrap();
	}
	
	// Set Tile at given coord with trap
	// Returns false if there was already an trap there
	public boolean setTrap(int[] coord, Trap newTrap){
		return this.map[coord[0]][coord[1]].setTrap(newTrap);
	}
	
	// Delete trap at Tile with given coord
	// Returns false if there was no trap there
	public boolean deleteTrap(int[] coord){
		return this.map[coord[0]][coord[1]].deleteTrap() = NULL;
	}
	
	// Move trap from one Tile to another, given coords of both
	// Returns false and does not do anything if no trap at original Tile
	// Returns false and does not do anything if already trap at destination Tile
	public boolean moveTrap(int[] fromCoord, int[] toCoord){
		if(!this.map[fromCoord[0]][fromCoord[1].hasTrap()){
			return false;
		}
		
		if(this.map[toCoord[0]][toCoord[1]].hasTrap()){
			return false;
		}
		
		Trap currTrap = this.getTrap(fromCoord);
		boolean redundantVariable = this.deleteTrap(fromCoord);
		return this.setTrap(toCoord, currTrap);
	}
	
	
	
	//************************//
	//******** ITEM **********//
	//************************//
	
	// get item given coord of Tile
	public Item getItem(int[] coord){
		return this.map[coord[0][coord1].getItem();
	}
	
	// Set Tile at given coord with item
	// Returns false if there was already an item there
	public boolean setItem(int[] coord, Item newItem){
		return this.map[coord[0]][coord[1]].setItem(newItem);
	}
	
	// Delete item at Tile with given coord
	// Returns false if there was no item there
	public boolean deleteItem(int[] coord){
		return this.map[coord[0]][coord[1]].deleteItem() = NULL;
	}
	
	// Move item from one Tile to another, given coords of both
	// Returns false and does not do anything if no item at original Tile
	// Returns false and does not do anything if already item at destination Tile
	public boolean moveItem(int[] fromCoord, int[] toCoord){
		if(!this.map[fromCoord[0]][fromCoord[1].hasItem()){
			return false;
		}
		
		if(this.map[toCoord[0]][toCoord[1]].hasItem()){
			return false;
		}
		
		Item currItem = this.getItem(fromCoord);
		boolean redundantVariable = this.deleteItem(fromCoord);
		return this.setItem(toCoord, currItem);
	}
	
	
	
	//************************//
	//******* TERRAIN ********//
	//************************//
	
	// get terrain given coord of Tile
	public Terrain getTerrain(int[] coord){
		return this.map[coord[0][coord1].getTerrain();
	}
	
	// Set Tile at given coord with terrain
	// Returns false if there was already an terrain there
	public boolean setTerrain(int[] coord, Terrain newTerrain){
		return this.map[coord[0]][coord[1]].setTerrain(newTerrain);
	}
	
	// Delete terrain at Tile with given coord
	// Returns false if there was no terrain there
	public boolean deleteTerrain(int[] coord){
		return this.map[coord[0]][coord[1]].deleteTerrain();
	}
	
	// Move terrain from one Tile to another, given coords of both
	// Returns false and does not do anything if no terrain at original Tile
	// Returns false and does not do anything if already terrain at destination Tile
	public boolean moveTerrain(int[] fromCoord, int[] toCoord){
		if(!this.map[fromCoord[0]][fromCoord[1]].hasTerrain()){
			return false;
		}
		
		if(this.map[toCoord[0]][toCoord[1]].hasTerrain()){
			return false;
		}
		
		Terrain currTerrain = this.getTerrain(fromCoord);
		boolean redundantVariable = this.deleteTerrain(fromCoord);
		return this.setTerrain(toCoord, currTerrain);
	}
}