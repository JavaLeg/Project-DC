package Tileset;

public class GroundFactory extends TilesetAbstractFactory {

	@Override
	public EnemyObject getEnemy(EnemyType type) {
		return null;
	}

	@Override
	public GroundObject getGround(GroundType type) {
		// TODO: For a specific type of ground, return that object
		return null;
	}

	@Override
	public PlayerObject getMainCharacter(PlayerType type) {
		return null;
	}

	@Override
	public WallObject getWall(WallType type) {
		return null;
	}

}
