package Tileset;

import State.Coordinates;
import Tileset.EnemySubclass.*;

public class EnemyFactory {
	
	public static enum EnemyType {
		SLIME//, SKELETON, ZOMBIE, WOLF
	}
	
	public Enemy getEnemy(EnemyType type, Coordinates coords) {
		switch(type) {
		case SLIME:
			return new EnemySlime(coords);
		/*case SKELETON:
		case ZOMBIE:
		case WOLF:*/
		default:
			return null;
		}
	}
}
