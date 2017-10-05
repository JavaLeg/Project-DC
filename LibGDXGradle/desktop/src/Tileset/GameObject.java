package Tileset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import State.Coordinates;
import State.State;

public class GameObject {
	public static enum ObjectType {
		PLAYER, ENEMY, TRAP, ITEM, TERRAIN
	}
	
	ObjectType type;
	private Image image;
	private Sprite sprite;
	private int height;
	private int width;
	private Coordinates position;
	
	
	public GameObject() {
		this.height = 16;
		this.width = 16;
		this.position = new Coordinates(0,0);
	}
	
	
	public GameObject(Coordinates position) {
		this.height = 16;
		this.width = 16;
		this.position = position;
	}
	
	public GameObject(int height, int width, Coordinates position) {
		this.height = height;
		this.width = width;
		this.position = position;
	}
	
	public ObjectType getType() {
		return this.type; 
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
	
	public Coordinates getCoord() {
		return position;
	}
	
	
	
	
	// Dynamic Game Components
	//
	
	public void create(State s) {
		
	}
	
	public void step(State s) {
		
	}
	
	public void destroy(State s) {
		
	}
	
}
