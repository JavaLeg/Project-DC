package Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

// import Interface.Stages.Selections.CreatureSelection;
// import Interface.Stages.Selections.EditorSelection;
// import Interface.Stages.Selections.TerrainSelection;
import Interface.Stages.Selections.ToolbarSelection;


/*
 * Name change from image stack as it seems more appropriate
 */
public class PreviewCell extends Stack {
	
	//private int status;		// 0 for empty, 1 for ground, 2 for wall, 3, 4, 5, etc.
	//private int column;
	//private int row;
	private Texture currTexture;
	private Image terrain;
	private Image object;
	private Image empty;
	
	private boolean terrain_exists;
	private boolean object_exists;

	
	/*
	 * Initialises to empty
	 */
	public PreviewCell() {
		super();
		Texture cur_texture = new Texture(Gdx.files.internal("EditorScreen/empty_grid.png"));
		//TextureRegion gnd = new TextureRegion(cur_texture, 40, 40);
		// Changed, we can lock grid size. Done in the loop in preview
		TextureRegion gnd = new TextureRegion(cur_texture);
		terrain = new Image(gnd);
		empty = terrain;
		
		terrain_exists = false;
		object_exists = false;
		
		this.add(terrain);
		
		//this.status = status;
		//this.row = row;
		//this.column = column;
	}
	
	
	
	
	/*
	public int getStatus() {
		return this.status;
	}
	
	/*
	 * Holds the new block type
	 * Possible unnecessary
	
	public void setStatus(int start) {
		this.status = start;
	}
	
	// Increments the status (UNUSED)
	public void changeStatus() {
		this.status++;
		if (this.status > 2) this.status = 0;
	}
	*/
	
	
	public void setTexture(TextureRegion i, ToolbarSelection ts) {
		/*
		 * Only two cases we need to consider
		 * Terrain: Placed with logical order
		 * Other: Just place it damn man, just do it.
		 */
		if (i == null) return;			// If no current texture, don't do shit
		
		this.clearChildren();
		
		switch(ts) {
		case TERRAIN:
			terrain_exists = true;
			terrain = new Image(i);
			this.add(terrain);
			
			if (object_exists == true) {
				this.add(object);
			}
			break;
		default:
			object_exists = true;
			object = new Image(i);
			this.add(terrain);
			this.add(object);
			break;
		}
	}
	
	/*
	 * Clear the cell
	 * Keeps the empty texture 
	 */
	public void clear() {
		this.clearChildren();
		object_exists = false;
		terrain_exists = false;
		this.add(empty);
	}
	
	
	/*
	 * UNUSED function
	 */
	public void placeTerrain(Texture s) {
		this.clearChildren();
		
		// JAMES, use texture regions so you can preset the size
		TextureRegion nxt = new TextureRegion(s, 0, 0, 40, 40);
		Image n = new Image(nxt);
	
		terrain = n;
		this.add(terrain);
		
		if(object != null) {	
			this.add(object);
		}
	}
	
	/*
	 * UNUSED function
	 */
	public void placeCreature(Texture s) {
		
		this.clearChildren();
		
		// JAMES, use texture regions so you can preset the size
		// Okay sorry man dont yell at me :c 
		TextureRegion nxt = new TextureRegion(s, 40, 40);
		Image n = new Image(nxt);
		
		object = n;
		
		this.add(terrain);
		this.add(object);
	}
	
	/* UNUSED
	 * Changes the current block, there is a difference
	 * between a terrain object and an item/creature object
	 * This allows for overlapping images
	 * @param object_type: true if object is not terrain
	 */
	public void changeImage(Texture nxt_text, boolean object_type) {
		
		TextureRegion nxt = new TextureRegion(nxt_text, 100, 100);
		this.clearChildren();
		if (object_type == true) {
			object = new Image(nxt);
			object_exists = true;
		} else {
			terrain = new Image(nxt);
		}
		
		this.add(terrain);
		if (object_exists == true) {
			this.add(object);
		}
	}
	
	/*
	 * When setting all to ground / empty
	 */
	public void setToTerrain(Texture nxt) {
		this.clearChildren();
		object_exists = false;
		terrain = new Image(nxt);
		this.add(terrain);
	}
	
	/*
	 * Checks if this grid is valid (can't have object on null cell)
	 */
	public boolean isValid() {
		if (object_exists == true && terrain_exists == false) return false;
		return true;
	}
	/*
	public int getColumn() {
		return this.column;
	}
	
	public int getRow() {
		return this.row;
	}
	*/
	
}
