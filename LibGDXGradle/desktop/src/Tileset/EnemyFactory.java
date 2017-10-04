package Tileset;

import Tileset.EnemySubclass.*;

public class EnemyFactory {
	
	public static enum EnemyType {
		SLIME//, SKELETON, ZOMBIE, WOLF
	}
	
	public Enemy getEnemy(EnemyType type) {
		switch(type) {
		case SLIME:
			return new EnemySlime();
		/*case SKELETON:
		case ZOMBIE:
		case WOLF:*/
		default:
			return null;
		}
	}
}
