package Interface.Grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GridCell extends Image{
	private static final gridWidth;
	private static final gridHeight;
	
	public final int xPos;
	public final int yPos;
	
	private Image currImage;
	
	public GridCell(int x, int y) {
		this.xPos = x;
		this.yPos = y;
		
		this.gridWidth = 40;
		this.gridHeight = 40;
		
		// Determine grid lines
		int left = xPos * gridWidth;
		int right = left + gridWidth;
		int bottom = yPos * gridHeight;
		int top = bottom + gridHeight;
		
		// Set default
		Image currImage = new Image(new TextureRegion(new Texture(
				Gdx.files.internal("empty.png"))));
		
	}
	
}
