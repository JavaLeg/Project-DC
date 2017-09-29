package Tileset;

public class EnemyTest01 extends EnemyObject implements Enemy {
	
	public EnemyTest01() {
		// set hp to 100, option size is set to 16x16 pixels
		super(100);
		// get the skull image
		String imageName = "72_16x16_Tileset.png";
		this.setImage(imageName, 0, 144, this.getWidth(), this.getHeight());
		this.setSprite(imageName, 0, 144, this.getWidth(), this.getHeight());
	}
}


