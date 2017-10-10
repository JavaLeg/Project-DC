package Tileset.TerrainSubclass;

import com.badlogic.gdx.graphics.Texture;

import State.Coordinates;
import Tileset.GameObject;
import Tileset.Terrain;

public class TerrainWall extends Terrain {
	
	private static String imageName = "72_16x16_Tileset.png"; // Reusing the enemy sprite sorry!!
	private static Texture texture = GameObject.getTexture(imageName, 0, 144, 16, 16).getTexture();
	
	public TerrainWall(Coordinates coords) {
		super(30,10, coords, texture, false); // width, height, coords, passable
	}
}
