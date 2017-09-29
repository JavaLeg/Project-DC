package Tileset;

import Tileset.EnemySubclass.EnemyTest01;

public class EnemyFactory extends TilesetAbstractFactory {

	@Override
	public EnemyObject getEnemy(EnemyType type) {
		
		// For a specific type of enemy, return that object
		switch (type) {
		case TEST_01:
			return new EnemyTest01();
		}
		return null;
	}

	@Override
	public GroundObject getGround(GroundType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerObject getMainCharacter(PlayerType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WallObject getWall(WallType type) {
		// TODO Auto-generated method stub
		return null;
	}

}
