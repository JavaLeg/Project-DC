package Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;

/* class Grid
 * 
 */

public class Grid {

	int rows;
	int columns;
	int size_x;
	int size_y;
	private Array <ImageID> images;		// The grids
	private Texture cur_texture;
	
	public Grid(int x, int y, int height, int width, String path) {
		
		//
		images = new Array <ImageID>();
		cur_texture = new Texture(Gdx.files.internal("empty.png"));
		
		int col = 0;
		int row = 0;
		
		for (int i = 40; i <= width; i = i + y) {
			for (int j = 40; j <= height; j = j + x) {
				// System.out.println("I = " + i + ", J = " + j);
				final ImageID cur_image = new ImageID(cur_texture, 0, col, row);
	            cur_image.setPosition(i, j);
	            cur_image.setSize(x, y);
	            cur_image.setWidth(x);
	            cur_image.setHeight(y);
	            images.add(cur_image);
	            col++;
			}
			row++;
			col = 0;
		}
	}
	
	public void setValue(int value, Texture nxt) {
		for (int i = 0; i < images.size; i++) {
			images.get(i).setStatus(value);
			images.get(i).setDrawable(new SpriteDrawable(new Sprite(nxt)));
		}
	}
	
	public void printValue() {
		for (int i = 0; i < images.size; i++) {
			System.out.println(images.get(i).getStatus());
		}
	}
	
	// Return the Array of images
	public Array <ImageID> getGrid() {
		return this.images;
	}
}
