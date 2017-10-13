package Tileset;

import Tileset.PlayerSubclass.*;

import com.badlogic.gdx.graphics.Texture;

import State.Coord;

public class PlayerFactory {
	public static enum PlayerType {
		WARRIOR//, ARCHER, TANK, ROGUE
	}
	
	public Player getPlayer(PlayerType type, Coord coord, Texture texture) {
		switch(type) {
		case WARRIOR:
			return new PlayerWarrior(coord, texture);
		/*case SKELETON:
		case ZOMBIE:
		case WOLF:*/
		default:
			return null;
		}
	}
}
