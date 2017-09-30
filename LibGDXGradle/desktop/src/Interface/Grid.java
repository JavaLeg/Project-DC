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
	private Array <Image> images;		// The grids
	private Texture cur_texture;
	
	public Grid(int x, int y, int height, int width, String path) {
		
		images = new Array <Image>();
		cur_texture = new Texture(Gdx.files.internal("tmp.png"));
		
		for (int i = 80; i <= width; i = i + y) {
			for (int j = 80; j <= height; j = j + x) {
				// System.out.println("I = " + i + ", J = " + j);
				final Image cur_image = new Image(cur_texture);
	            cur_image.setPosition(i, j);
	            cur_image.setSize(x, y);
	            cur_image.setWidth(x);
	            cur_image.setHeight(y);
	            
	            cur_image.addListener(new InputListener() {
	                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	                        Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
	                        cur_image.setVisible(false);
	                        return false;
	                }
	                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.log("Example", "touch ended at (" + x + ", " + y + ")");
                        return;
                }
	            });
	            images.add(cur_image);
			}
		}
	}
	
	// Return the Array of images
	public Array <Image> getGrid() {
		return this.images;
	}
}
