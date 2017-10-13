package Tileset.Behaviour;

import java.util.List;

import State.Coord;
import State.State;
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
	public Coord nextStep(State s, Coord currentPos) {
		// TODO Auto-generated method stub
		
		
		List<Coord> path = findRoute(s, currentPos, s.findPlayer());
//		TODO: oscar!
//		if (path == null || path.size() == 0 || s.isBlocked(path.get(0), type)) {
//			return path.get(0);
//		} 
		 
		// non path behaviour
		if (focus) {
			return currentPos;
		} else {
			return new MoveRandom(type).nextStep(s, currentPos);
		}		
	}
	
}
