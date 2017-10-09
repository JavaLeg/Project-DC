package Tileset.EnemySubclass;

import State.Coordinates;
import State.MovePathToPoint;
import Tileset.Enemy;

public class EnemySlime extends Enemy {
	public EnemySlime(Coordinates coords) {
		super(30, 10, coords, 50, 10, 30, new MovePathToPoint()); // width, height, coords, hp, damage, moveRate, moveBehaviour
		String imageName = "72_16x16_Tileset.png";
		this.setImage(imageName, 0, 144, this.getWidth(), this.getHeight());	}
}
