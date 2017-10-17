package Tileset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import State.Coord;

public class Item extends GameObject {

	public Item(Coord position, double hp, double damage, Texture texture) {
		super(ObjectType.ITEM, position, new TextureRegion(texture));
	}

}
