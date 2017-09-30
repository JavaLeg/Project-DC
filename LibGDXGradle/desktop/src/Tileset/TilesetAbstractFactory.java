package Tileset;

public abstract class TilesetAbstractFactory {
	public static enum EnemyType {
		TEST_01
	}
	
	public static enum GroundType {
		TEST_01
	}
	
	public static enum PlayerType {
		TEST_01
	}
	
	public static enum WallType {
		TEST_01
	}
	
	public abstract EnemyObject getEnemy(EnemyType type);
	public abstract GroundObject getGround(GroundType type);
	public abstract PlayerObject getMainCharacter(PlayerType type);
	public abstract WallObject getWall(WallType type);
	
}
