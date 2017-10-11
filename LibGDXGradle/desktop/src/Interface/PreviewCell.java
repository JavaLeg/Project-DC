package Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import Interface.Stages.Selections.CreatureSelection;
import Interface.Stages.Selections.EditorSelection;
import Interface.Stages.Selections.TerrainSelection;


/*
 * Name change from image stack as it seems more appropriate
 */
public class PreviewCell extends Stack {
	
	//private int status;		// 0 for empty, 1 for ground, 2 for wall, 3, 4, 5, etc.
	//private int column;
	//private int row;
	private Image terrain;
	private Image object;

	
	/*
	 * Initialises to empty
	 */
	public PreviewCell() {
		super();
		
		terrain = new Image(new Texture(Gdx.files.internal("EditorScreen/empty_grid.png")));
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
	public void placeTerrain(Image s) {
		this.clearChildren();
		
		terrain = s;
		this.add(terrain);
		
		if(object != null) {	
			this.add(object);
		}
	}
	
	public void placeCreature(Image s) {
		this.clearChildren();
		
		object = s;
		
		this.add(terrain);
		this.add(object);
	}
	
	/*
	 * Changes the current block, there is a difference
	 * between a terrain object and an item/creature object
	 * This allows for overlapping images
	 * @param object_type: true if object is not terrain
	 */
	public void changeImage(Texture nxt, boolean object_type) {
		
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
	public int getColumn() {
		return this.column;
	}
	
	public int getRow() {
		return this.row;
	}
	*/
	
}
