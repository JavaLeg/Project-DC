package Tileset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileSetFactoryProducer {
	// Global reference to tileSet so we can get sub images in the specific classes
	public static BufferedImage tileSet;
	
	enum ObjectType {
		ENEMY, GROUND, MAIN_CHARACTER, WALL
	}
	
	public TileSetFactoryProducer() throws IOException {
		tileSet = ImageIO.read(new File("72_16x16_Tileset.png"));
	}
	
	public TilesetAbstractFactory getFactory(ObjectType type) {
		// TODO: finish other implementations
		switch (type) {
		case ENEMY:
			return new EnemyFactory();
//		case GROUND:
//			return new GroundFactory();
//		case MAIN_CHARACTER:
//			return new MainCharacterFactory();
//		case WALL:
//			return new WallFactory();
			
		}
		return null;
	}
}
