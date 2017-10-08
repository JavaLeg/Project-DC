package Tileset;

import Tileset.PlayerSubclass.*;
import State.Coordinates;

public class PlayerFactory {
	public static enum PlayerType {
		WARRIOR//, ARCHER, TANK, ROGUE
	}
	
	public Player getTerrain(PlayerType type, Coordinates coords) {
		switch(type) {
		case WARRIOR:
			return new PlayerWarrior(coords);
		/*case SKELETON:
		case ZOMBIE:
		case WOLF:*/
		default:
			return null;
		}
	}
}
