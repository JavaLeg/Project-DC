package Tileset.EnemySubclass;

import State.Coordinates;
import Tileset.Enemy;

public class EnemySlime extends Enemy {
	public EnemySlime() {
		super(10, 30, new Coordinates(0, 0));
		String imageName = "72_16x16_Tileset.png";
		this.setImage(imageName, 0, 144, this.getWidth(), this.getHeight());
		this.setSprite(imageName, 0, 144, this.getWidth(), this.getHeight());
	}
	
	
	public EnemySlime(Coordinates position) {
		super(10, 30, position);
		String imageName = "72_16x16_Tileset.png";
		this.setImage(imageName, 0, 144, this.getWidth(), this.getHeight());
		this.setSprite(imageName, 0, 144, this.getWidth(), this.getHeight());
	}
}
