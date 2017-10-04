package Interface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ImageID extends Image {
	
	private int status;		// 0 for empty, 1 for ground, 2 for wall, 3, 4, 5, etc.
	private int column;
	private int row;

	public ImageID(Texture cur_texture, int status, int row, int column) {
		// TODO Auto-generated constructor stub
		super(cur_texture);
		this.status = status;
		this.row = row;
		this.column = column;
	}

	public void setPosition(int x, int y) {
		super.setPosition(x, y);
	}
	
	public void setSize(int x, int y) {
		super.setSize(x, y);
	}
	
	public void setWidth(int x) {
		super.setWidth(x);
	}
	
	public void setHeight(int y) {
		super.setHeight(y);
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public void setStatus(int start) {
		this.status = start;
	}
	
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
