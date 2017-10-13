package Tileset.EnemySubclass;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import State.Coord;
import Tileset.Enemy;
import Tileset.GameObject;
import Tileset.Behaviour.MovePathToPoint;
import Tileset.Behaviour.MoveRandom;

public class EnemySlime extends Enemy {
	//private static String imageName = "72_16x16_Tileset.png";
	//private static Texture texture = GameObject.getTexture(imageName, 0, 144, 16, 16).getTexture();
	
	public EnemySlime(Coord coords, Texture texture) {
		
		super(coords, 50, 10, 30, new MoveRandom(ObjectType.ENEMY), texture); // coords, hp, damage, moveRate, moveBehaviour
		
	}
}
