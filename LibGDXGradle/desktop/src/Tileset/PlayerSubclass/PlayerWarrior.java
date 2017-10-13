package Tileset.PlayerSubclass;

import com.badlogic.gdx.graphics.Texture;

import State.Coord;
import Tileset.GameObject;
import Tileset.Player;

public class PlayerWarrior extends Player {
	
	//private static String imageName = "72_16x16_Tileset.png"; // Reusing the enemy sprite sorry!!
	//private static Texture texture = GameObject.getTexture(imageName, 0, 144, 16, 16).getTexture();
	
	public PlayerWarrior(Coord coords, Texture texture) {
		super(coords, 100, 25, texture); // coords, hp, damage
		
	}
}
