package Tileset.EnemySubclass;

import Tileset.Enemy;
import Tileset.EnemyObject;

public class EnemyTest01 extends EnemyObject implements Enemy {
	
	public EnemyTest01() {
		// set hp to 100, option size is set to 16x16 pixels
		super(100);
		// get the skull image
		String imageName = "72_16x16_Tileset.png";
		// TODO: Not sure if we want to use Image or Sprite...
		this.setImage(imageName, 0, 144, this.getWidth(), this.getHeight());
		this.setSprite(imageName, 0, 144, this.getWidth(), this.getHeight());
	}
}

/* USAGE:
 * To create this object....
 * 
 * 1. create an Enemy Abstract Factory:
 * TilesetAbstractFactory enemyAbstractFactory = TilesetFactoryProducer.getFactory(ObjectType.ENEMY);
 * 
 * 2. create the object:
 * EnemyObject enemyObject = enemyAbstractFactory.getEnemy(EnemyType.TEST_01);
 * 
 * 3. use the object (e.g. get the sprite image):
 * Sprite enemyObjectSprite = enemyObject.getSprite();
 */