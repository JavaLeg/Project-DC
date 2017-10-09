package Tileset.PlayerSubclass;

import State.Coordinates;
import Tileset.Player;

public class PlayerWarrior extends Player {
	public PlayerWarrior(Coordinates coords) {
		super(coords, 100, 25); // coords, hp, damage
		String imageName = "72_16x16_Tileset.png"; // Reusing the enemy sprite sorry!!
		this.setImage(imageName, 0, 144, this.getWidth(), this.getHeight());
	}
}
