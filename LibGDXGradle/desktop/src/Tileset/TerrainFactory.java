package Tileset;

import State.Coordinates;
import Tileset.TerrainSubclass.*;

public class TerrainFactory {
	public static enum TerrainType {
		WALL//, WATER, MOUNTAIN, TREE
	}
	
	public Terrain getTerrain(TerrainType type, Coordinates coords) {
		switch(type) {
		case WALL:
			return new TerrainWall(coords);
		/*case SKELETON:
		case ZOMBIE:
		case WOLF:*/
		default:
			return null;
		}
	}
}
