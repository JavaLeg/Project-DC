package Tileset;

public class PlayerFactory extends TilesetAbstractFactory {
	
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
		// TODO
		return null;
	}

	@Override
	public WallObject getWall(WallType type) {
		return null;
	}
}
