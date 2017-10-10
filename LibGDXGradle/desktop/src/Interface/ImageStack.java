package Interface;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

public class ImageStack extends Stack {
	
	//private int status;		// 0 for empty, 1 for ground, 2 for wall, 3, 4, 5, etc.
	//private int column;
	//private int row;
	private Image terrain;
	private Image object;
	
	private boolean object_exists;
	
	public ImageStack(TextureRegion region) {
		super();
		terrain = new Image(region);
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
