package State;

import java.util.List;
import java.util.Random;

import Tileset.GameObject.ObjectType;

public class MoveRandom extends MoveBehaviour {
	private ObjectType type;
	
	
	public MoveRandom(ObjectType type) {
		this.type = type;
	}
	
	
	@Override
	public Coordinates nextStep(State s, Coordinates currentPos) {
		List<Coordinates> options = this.getAdjacent(currentPos, s, type);
		if (options.size() == 0) {
			return currentPos;
		}
		Random r = new Random();
		return options.get(r.nextInt(options.size()));
	}
	
	
}
