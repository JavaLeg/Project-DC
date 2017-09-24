package Tileset;

public class EnemyFactory extends TilesetAbstractFactory {

	@Override
	Enemy getEnemy(EnemyType type) {
		
		// For a specific type of enemy, return that object
		switch (type) {
		case TEST_01:
			return new EnemyTest01();
		}
		return null;
	}

	@Override
	Ground getGround(GroundType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	MainCharacter getMainCharacter(MainCharacterType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	Wall getWall(WallType type) {
		// TODO Auto-generated method stub
		return null;
	}

}
