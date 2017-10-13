package Tileset;

import com.badlogic.gdx.graphics.Texture;

import State.Coordinates;
import Tileset.EnemySubclass.*;

public class EnemyFactory {
	
	public static enum EnemyType {
		SLIME//, SKELETON, ZOMBIE, WOLF
	}
	
	public Enemy getEnemy(EnemyType type, Coordinates coord, Texture texture) {
		switch(type) {
		case SLIME:
			return new EnemySlime(coord, texture);

		/*case SKELETON:
		case ZOMBIE:
		case WOLF:*/
		default:
			return null;
		}
	}
}
