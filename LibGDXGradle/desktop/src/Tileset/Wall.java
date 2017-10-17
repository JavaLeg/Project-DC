package Tileset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import State.Coord;

public class Wall extends GameObject {
	
	public Wall(Coord position,  boolean passable, Texture texture) {
		super(ObjectType.WALL,  position, new TextureRegion(texture));
	}
}
