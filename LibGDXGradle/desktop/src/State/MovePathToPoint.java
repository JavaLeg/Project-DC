package State;

import java.util.List;

import Tileset.GameObject.ObjectType;

// Move directly in the best route towards player
public class MovePathToPoint extends MoveBehaviour {
	 private ObjectType type;
	private boolean focus;
	 
	public MovePathToPoint(ObjectType type, boolean focus) {
		this.type = type;
		this.focus = focus;
	}
	
	
	
	
	@Override
	public Coordinates nextStep(State s, Coordinates currentPos) {
		// TODO Auto-generated method stub
		
		
		List<Coordinates> path = findRoute(s, currentPos, s.findPlayer());
		if (path == null || path.size() == 0 || s.isBlocked(path.get(0), type)) {
			return path.get(0);
		} 
		 
		// non path behaviour
		if (focus) {
			return currentPos;
		} else {
			return new MoveRandom(type).nextStep(s, currentPos);
		}		
	}
	
}
