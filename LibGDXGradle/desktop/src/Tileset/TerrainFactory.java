package Tileset;

import Tileset.TerrainSubclass.*;

public class TerrainFactory {
	public static enum TerrainType {
		PATH, WALL//, WATER, MOUNTAIN, TREE
	}
	
	public Terrain getTerrain(TerrainType type) {
		switch(type) {
		case PATH:
			return new TerrainPath();
		case WALL:
			return new TerrainWall();
		/*case SKELETON:
		case ZOMBIE:
		case WOLF:*/
		default:
			return null;
		}
	}
}
