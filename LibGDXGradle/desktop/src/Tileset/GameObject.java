package Tileset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import State.Coordinates;

// Game object is in charge of: image/sprite, ObjectType, Coordinates
public class GameObject extends Image {
	public static enum ObjectType {
		PLAYER, ENEMY, TERRAIN;
	}
	
	private ObjectType type;
	private Coordinates position;
	private Sprite sprite;
	// height and width are in Actor

	// TODO: WE MAY NOT NEED POSITION
	public GameObject(ObjectType type, int width, int height, Coordinates position, Texture texture) {
		super(texture);
		this.type = type;
		this.setSize(width,height);
		this.position = position;
		// initial position on screen 0,0 is bottom left
//		this.setPosition(x, y);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch);
	}

	public boolean isDynamic() {
		if(type == ObjectType.PLAYER || type == ObjectType.ENEMY) {
			return true;
		} else {
			return false;
		}
	}
	
	public ObjectType getType() {
		return this.type; 
	}
	
	public Coordinates getCoord() {
		return position;
	}
	
	public void setCoord(Coordinates coord) {
		position = coord;
	}
	
	// use the whole image
	public static Texture getTexture(String imageName) {
//		this.sprite = new Sprite(new Texture(Gdx.files.internal(imageName)));
		return new Texture(Gdx.files.internal(imageName));
	}
	
	// use a subsection via texture region at a given position? TESTING NEEDED
	public static TextureRegion getTexture(String imageName, int x, int y, float f, float g) {
		Texture tilemap = new Texture(Gdx.files.internal(imageName));
//		this.sprite = new Sprite(new TextureRegion(tilemap, x, y, (int)f, (int)g));
		return new TextureRegion(tilemap, x, y, (int)f, (int)g);
	}
}
