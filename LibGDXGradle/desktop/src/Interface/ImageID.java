package Interface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/* Class ImageID
 * 
 * 
 */

public class ImageID extends Image {
	
	private int status;		// 0 for empty, 1 for ground, 2 for wall, 3, 4, 5, etc.
	private int column;
	private int row;

	public ImageID(TextureRegion region, int status, int row, int column) {
		super(region);
		this.status = status;
		this.row = row;
		this.column = column;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	// Status holds the image type (ground / wall / empty)
	public void setStatus(int start) {
		this.status = start;
	}
	
	// Increments the status (UNUSED)
	public void changeStatus() {
		this.status++;
		if (this.status > 2) this.status = 0;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public int getRow() {
		return this.row;
	}
}
