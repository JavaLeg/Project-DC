package Interface.Grid;

import com.badlogic.gdx.Gdx;
import Tileset.GameObject;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;

import Interface.ImageStack;

/* class Grid
 * 
 */

public class Grid {

	int rows;
	int columns;
	int size_x;
	int size_y;
	private Array <ImageStack> images;		// The grids
	private Texture cur_texture;
	
	public Grid(int x, int y, int height, int width, String path) {
		//
		images = new Array <ImageStack>();
		cur_texture = new Texture(Gdx.files.internal("empty.png"));
		// TextureRegion region = new TextureRegion(cur_texture, 0, 112, 16, 16);
		TextureRegion region = new TextureRegion(cur_texture);
		int col = 0;
		int row = 0;
		
		for (int i = 40; i <= width; i = i + y) {
			for (int j = 40; j <= height; j = j + x) {
				System.out.println("I = " + i + ", J = " + j);
				ImageStack cur = new ImageStack(region, 0, col, row);
	            cur.setPosition(i, j);
	            cur.setSize(x, y);
	            cur.setWidth(x);
	            cur.setHeight(y);
	            images.add(cur);
	            col++;
			}
			row++;
			col = 0;
		}
	}
	
	/*
	 * For setting the entire grid to one texture
	 */
	public void setValue(int value, Texture nxt) {
		for (int i = 0; i < images.size; i++) {
			images.get(i).setStatus(value);
			images.get(i).setToTerrain(nxt);
		}
	}
	
	public void printValue() {
		for (int i = 0; i < images.size; i++) {
			System.out.println(images.get(i).getStatus());
		}
	}
	
	public Array <ImageStack> getGrid() {
		return this.images;
	}
}
