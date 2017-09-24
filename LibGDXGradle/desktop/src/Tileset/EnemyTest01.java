package Tileset;

import java.awt.image.BufferedImage;

public class EnemyTest01 extends EnemyObject implements Enemy {
	
	public EnemyTest01() {
		// set hp to 100, option size is set to 16x16 pixels
		super(100);
		// get the skull image
		BufferedImage image = TileSetFactoryProducer.tileSet.getSubimage(0, 112, this.getWidth(), this.getHeight());
		this.setImage(image);
	}
}
