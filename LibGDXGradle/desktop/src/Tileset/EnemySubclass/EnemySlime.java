package Tileset.EnemySubclass;

import Tileset.Enemy;

public class EnemySlime extends Enemy {
	public EnemySlime() {
		super(10);
		String imageName = "72_16x16_Tileset.png";
		this.setImage(imageName, 0, 144, this.getWidth(), this.getHeight());
		this.setSprite(imageName, 0, 144, this.getWidth(), this.getHeight());
	}
}
