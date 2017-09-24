package Tileset;

public abstract class TilesetAbstractFactory {
	enum EnemyType {
		TEST_01
	}
	
	enum GroundType {
		TEST_01
	}
	
	enum MainCharacterType {
		TEST_01
	}
	
	enum WallType {
		TEST_01
	}
	
	abstract Enemy getEnemy(EnemyType type);
	abstract Ground getGround(GroundType type);
	abstract MainCharacter getMainCharacter(MainCharacterType type);
	abstract Wall getWall(WallType type);
	
}
