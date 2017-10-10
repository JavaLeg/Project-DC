package Interface.Grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import Interface.ImageStack;

public class GridCell extends Image {
	private static final int gridWidth = 40;
	private static int gridHeight = 40;
	
	public final int xPos;
	public final int yPos;
	
	//private Image cellContents;
	
	public GridCell(int x, int y) {
		super(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/empty_grid.png"))));
		//super();
		//this.cellContents = new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/empty.png"))));
		
		this.xPos = x;
		this.yPos = y;
		
		// Determine grid lines
		int left = xPos * gridWidth;
		int right = left + gridWidth;
		int bottom = yPos * gridHeight;
		int top = bottom + gridHeight;
	}
	/*
	public ImageStack getCellContents() {
		return cellContents;
	}
	*/
}
