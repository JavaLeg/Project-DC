package Tileset;

public class WallFactory extends TilesetAbstractFactory {
	@Override
	public EnemyObject getEnemy(EnemyType type) {
		return null;
	}

	@Override
	public GroundObject getGround(GroundType type) {
		return null;
	}

	@Override
	public PlayerObject getMainCharacter(PlayerType type) {
		return null;
	}

	@Override
	public WallObject getWall(WallType type) {
		// TODO: return specific type of wall object
		return null;
	}
}
