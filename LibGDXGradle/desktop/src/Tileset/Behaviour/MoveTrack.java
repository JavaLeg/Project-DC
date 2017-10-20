package Tileset.Behaviour;

import java.util.LinkedList;
import java.util.List;

import State.Coord;
import State.State;

// follow players movement but not in a particularly efficient way
public class MoveTrack extends MoveBehaviour {
	private List<Coord> ongoingPath;
	private int stayedStill;
	
	public MoveTrack(boolean focus) {
		this.ongoingPath = new LinkedList<Coord>();
		this.stayedStill = 0;
	}
	
	// TODO: uncomment when Blocked implemented
	@Override
	public Coord nextStep(State s, Coord currentPos) {
		if (ongoingPath.isEmpty()) {
			ongoingPath = findRoute(s, currentPos, s.findPlayer());
			if (!(ongoingPath == null || ongoingPath.size() == 0 || s.isBlocked(ongoingPath.get(0)))) {
				return ongoingPath.remove(0);
			}
		} else {
			if (!s.isBlocked(ongoingPath.get(0))) {
				return ongoingPath.remove(0);
			}
		}
		
		if (stayedStill >= 3) {
			stayedStill = 0;
			ongoingPath.clear();
			return new MoveRandom().nextStep(s, currentPos);
		} else {
			stayedStill++;
			return currentPos;
		}
				
	}
}
