package Tileset;

import java.io.Serializable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import State.Coord;

public class Item extends DynamicObject implements Cloneable, Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7358945601985718804L;
	private int restoreValue;	// Treat as a consumable? Could be negative value? for traps
	
	public Item(Coord position, String imgPath) {
		super(ObjectType.ITEM, imgPath);
	}
	
	public Item(String imgPath) {
		super(ObjectType.ITEM, imgPath);
	}

	public int getRestoreValue() {
		return restoreValue;
	}

	public void setRestoreValue(int restoreValue) {
		this.restoreValue = restoreValue;
	}
	
	public Item clone() {
		return new Item(getImgPath());
	}

}
