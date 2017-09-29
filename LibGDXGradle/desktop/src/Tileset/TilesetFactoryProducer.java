package Tileset;

public class TilesetFactoryProducer {
	
	public static enum ObjectType {
		ENEMY, GROUND, MAIN_CHARACTER, WALL
	}
	
	public static TilesetAbstractFactory getFactory(ObjectType type) {
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
		default:
			break;
		}
		return null;
	}
}
