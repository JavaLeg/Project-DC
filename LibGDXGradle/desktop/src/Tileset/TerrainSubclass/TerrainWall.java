package Tileset.TerrainSubclass;

import com.badlogic.gdx.graphics.Texture;

import State.Coord;
import Tileset.GameObject;
import Tileset.Terrain;

public class TerrainWall extends Terrain {
	
	//private static String imageName = "72_16x16_Tileset.png"; // Reusing the enemy sprite sorry!!
	//private static Texture texture = GameObject.getTexture(imageName, 0, 144, 16, 16).getTexture();
	
	public TerrainWall(Coord coords, Texture texture) {
		super(coords,  false, texture); // width, height, coords, passable
	}
}
