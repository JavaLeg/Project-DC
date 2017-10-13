package Tileset.Behaviour;

import java.util.List;
import java.util.Random;

import State.Coord;
import State.State;
import Tileset.GameObject.ObjectType;

public class MoveRandom extends MoveBehaviour {
	private ObjectType type;
	
	
	public MoveRandom(ObjectType type) {
		this.type = type;
	}
	
	
	@Override
	public Coord nextStep(State s, Coord currentPos) {
		List<Coord> options = this.getAdjacent(currentPos, s, type);
		if (options.size() == 0) {
			return currentPos;
		}
		Random r = new Random();
		return options.get(r.nextInt(options.size()));
	}
	
	
}
