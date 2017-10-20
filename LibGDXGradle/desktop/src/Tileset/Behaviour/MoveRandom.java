package Tileset.Behaviour;

import java.util.List;
import java.util.Random;

import State.Coord;
import State.State;

public class MoveRandom extends MoveBehaviour {
	
	
	public MoveRandom() {
		
	}
	
	
	@Override
	public Coord nextStep(State s, Coord currentPos) {
		List<Coord> options = this.getAdjacentBlocked(currentPos, s);
		if (options.size() == 0) {
			return currentPos;
		}
		Random r = new Random();
		return options.get(r.nextInt(options.size()));
	}
	
	
}
