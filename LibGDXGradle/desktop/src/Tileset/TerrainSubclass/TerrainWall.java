package Tileset.TerrainSubclass;

import State.Coordinates;
import Tileset.Terrain;

public class TerrainWall extends Terrain {
	public TerrainWall(Coordinates coords) {
		super(30,10,coords, false); // width, height, coords, passable
		String imageName = "72_16x16_Tileset.png"; // Reusing the enemy sprite sorry!!
		this.setImage(imageName, 0, 144, this.getWidth(), this.getHeight());	}
}
