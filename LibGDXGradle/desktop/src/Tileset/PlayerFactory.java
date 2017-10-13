package Tileset;

import Tileset.PlayerSubclass.*;
import State.Coordinates;

public class PlayerFactory {
	public static enum PlayerType {
		WARRIOR//, ARCHER, TANK, ROGUE
	}
	
	public Player getPlayer(PlayerType type, Coordinates coord) {
		switch(type) {
		case WARRIOR:
			return new PlayerWarrior(coord);
		/*case SKELETON:
		case ZOMBIE:
		case WOLF:*/
		default:
			return null;
		}
	}
}
