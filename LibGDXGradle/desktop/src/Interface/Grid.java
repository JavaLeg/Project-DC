package Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

// Main class that holds the grid (creates the GRID using Image)

public class Grid {

	int rows;
	int columns;
	int size_x;
	int size_y;
	private Array <ImageID> images;		// The grids
	private Texture cur_texture;
	
	public Grid(int x, int y, int height, int width, String path) {
		
		images = new Array <ImageID>();
		cur_texture = new Texture(Gdx.files.internal("empty.png"));
		
		for (int i = 40; i <= width; i = i + y) {
			for (int j = 40; j <= height; j = j + x) {
				// System.out.println("I = " + i + ", J = " + j);
				final ImageID cur_image = new ImageID(cur_texture, 0);
	            cur_image.setPosition(i, j);
	            cur_image.setSize(x, y);
	            cur_image.setWidth(x);
	            cur_image.setHeight(y);
	            cur_image.setColor(Color.WHITE);
	            images.add(cur_image);
			}
		}
	}
	
	// Return the Array of images
	public Array <ImageID> getGrid() {
		return this.images;
	}
}
