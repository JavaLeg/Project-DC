package Tileset;

import State.Coordinates;
import Tileset.EnemySubclass.*;

public class EnemyFactory {
	
	public static enum EnemyType {
		SLIME//, SKELETON, ZOMBIE, WOLF
	}
	
	public Enemy getEnemy(EnemyType type, Coordinates coord) {
		switch(type) {
		case SLIME:
			return new EnemySlime(coord);
		/*case SKELETON:
		case ZOMBIE:
		case WOLF:*/
		default:
			return null;
		}
	}
}
