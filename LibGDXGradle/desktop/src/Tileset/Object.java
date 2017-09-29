package Tileset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Object {
	private Image image;
	private Sprite sprite;
	private int height;
	private int width;
	
	public Object() {
		this.height = 16;
		this.width = 16;
	}
	
	public Object(int height, int width) {
		this.height = height;
		this.width = width;
	}
	
	// use the whole image
	public void setImage(String imageName) {
		Texture tilemap = new Texture(Gdx.files.internal(imageName));
		Image image = new Image(tilemap);
		this.image = image;
	}
	
	// use a subsection via texture region at a given position? TESTING NEEDED
	public void setImage(String imageName, int x, int y, int width, int height) {
		Texture tilemap = new Texture(Gdx.files.internal(imageName));
		TextureRegion region = new TextureRegion(tilemap, x, y, width, height);
		Image image = new Image(region);
		this.image = image;
	}
	
	public void setSprite(String imageName, int x, int y, int width, int height) {
		Texture texture = new Texture(Gdx.files.internal(imageName));
		this.sprite = new Sprite(texture, x, y, width, height);
	}

	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
}
