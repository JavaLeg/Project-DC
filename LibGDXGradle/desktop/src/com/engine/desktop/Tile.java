public class Tile {
	int xCoord; // probably redundant info to make things easier
	int yCoord; 
	
	Player player;
	Enemy enemy;
	Trap trap;
	Item item;
	Terrain terrain;
	
	
	//************************//
	//******* CREATORS *******//
	//************************//
	
	// Default Create a Tile
	public Tile(){
		this.player = NULL;
		this.enemy = NULL;
		this.trap = NULL;
		this.item = NULL;
		this.terrain = NULL;
		
		this.xCoord = -1;
		this.yCoord = -1;
	}
	
	// Create a Tile given x and y coords as int[2]
	public Tile(int[] coords){
		this.player = NULL;
		this.enemy = NULL;
		this.trap = NULL;
		this.item = NULL;
		this.terrain = NULL;
		
		this.xCoord = coords[0];
		this.yCoord = coords[1];
	}
	
	
	
	//************************//
	//****** COORDINATES *****//
	//************************//
	
	// Get Tile coords as int[2]
	public int[] getCoord(){
		return new int[]{this.xCoord, this.yCoord};
	}
	
	// Set Tile coords given int[2]
	public void setCoord(int[] coords){
		this.xCoord = coords[0];
		this.yCoord = coords[1];
	}
	
	
	
	//************************//
	//******* PLAYER *********//
	//************************//
	
	// Check Tile has player
	public boolean hasPlayer(){
		if (this.player){
			return true;
		} else {
			return false;
		}
	}
	
	// Get player or return null if no player
	public Player getPlayer(){
		return this.player;
	}
	
	// Set player if empty return true if successful or false if already exists
	public boolean setPlayer(Player newPlayer){
		if (this.hasPlayer()){
			return false;
		} else {
			this.player = newPlayer;
			return true;
		}
	}
	
	// Delete player, return true if successful or false if not exists
	public boolean deletePlayer(){
		if(this.hasPlayer()){
			this.player = NULL;
			return true;
		} else {
			return false;
		}
	}
	
	
	//************************//
	//******** ENEMY *********//
	//************************//
	
	// Check Tile has enemy
	public boolean hasEnemy(){
		if (this.enemy){
			return true;
		} else {
			return false;
		}
	}
	
	// Get enemy or return null if no enemy
	public Enemy getEnemy(){
		return this.enemy;
	}
	
	// Set enemy if empty return true if successful or false if already exists
	public boolean setEnemy(Enemy newEnemy){
		if (this.hasEnemy()){
			return false;
		} else {
			this.enemy = newEnemy;
			return true;
		}
	}
	
	// Delete enemy, return true if successful or false if not exists
	public boolean deleteEnemy(){
		if(this.hasEnemy()){
			this.enemy = NULL;
			return true;
		} else {
			return false;
		}
	}
	
	
	//************************//
	//******** TRAP **********//
	//************************//
	
	// Check Tile has trap
	public boolean hasTrap(){
		if (this.trap){
			return true;
		} else {
			return false;
		}
	}
	
	// Get trap or return null if no trap
	public Trap getTrap(){
		return this.trap;
	}
	
	// Set trap if empty return true if successful or false if already exists
	public boolean setTrap(Trap newTrap){
		if (this.hasTrap()){
			return false;
		} else {
			this.trap = newTrap;
			return true;
		}
	}
	
	// Delete trap, return true if successful or false if not exists
	public boolean deleteTrap(){
		if(this.hasTrap()){
			this.trap = NULL;
			return true;
		} else {
			return false;
		}
	}
	
	
	//************************//
	//******** ITEM **********//
	//************************//
	
	// Check Tile has item
	public boolean hasItem(){
		if(this.item){
			return true;
		} else {
			return false;
		}
	}
	
	// Get item or return null if no item
	public Item getItem(){
		return this.item;
	}
	
	// Set item if empty return true if successful or false if already exists
	public boolean setItem(Item newItem){
		if(this.hasItem()){
			return false;
		} else {
			this.item = newItem;
			return true;
		}
	}
	
	// Delete item, return true if successful or false if not exists
	public boolean deleteItem(){
		if(this.hasItem()){
			this.item = NULL;
			return true;
		} else {
			return false;
		}
	}
	
	
	//************************//
	//******* TERRAIN ********//
	//************************//
	
	// Check Tile has terrain
	public boolean hasTerrain(){
		if (this.terrain){
			return true;
		} else {
			return false;
		}
	}
	
	// Get terrain or return null if no terrain
	public Terrain getTerrain(){
		return this.terrain;
	}
	
	// Set terrain if empty return true if successful or false if already exists
	public boolean setTerrain(Terrain newTerrain){
		if (this.hasTerrain()){
			return false;
		} else {
			this.terrain = newTerrain;
			return true;
		}
	}
	
	// Delete terrain, return true if successful or false if not exists
	public boolean deleteTerrain(){
		if(this.hasTerrain()){
			this.terrain = NULL;
			return true;
		} else {
			return false;
		}
	}	
}