package Tileset;

import com.badlogic.gdx.graphics.Texture;

import State.Coordinates;
import Tileset.TerrainSubclass.*;

public class TerrainFactory {
	public static enum TerrainType {
		WALL//, WATER, MOUNTAIN, TREE
	}
	
	public Terrain getTerrain(TerrainType type, Coordinates coord, Texture texture) {
		switch(type) {
		case WALL:
			return new TerrainWall(coord, texture);
		/*case SKELETON:
		case ZOMBIE:
		case WOLF:*/
		default:
			return null;
		}
	}
}
