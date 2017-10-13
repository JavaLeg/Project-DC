package State;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import Tileset.GameObject.ObjectType;

public class MoveBehaviour {
	// Default behaviour returns current position
	// 		Behaviours should override this
	public Coordinates nextStep(State s, Coordinates currentPos) {
		return currentPos;
	}
	
	
	
	// Path finding helper functions:
	// 		Assume no diagonal movement
	
	
	// gets adjacent coordinates, regardless of of a State
	public List<Coordinates> getAdjacent(Coordinates c) {
		List<Coordinates> adj = new LinkedList<Coordinates>();
		adj.add(new Coordinates(c.getX() + 1, c.getY()));
		adj.add(new Coordinates(c.getX() - 1, c.getY()));
		adj.add(new Coordinates(c.getX(), c.getY() + 1));
		adj.add(new Coordinates(c.getX(), c.getY() - 1));
		return adj;
	}
	
	
	// gets adjacent coordinates restricted by walls
	public List<Coordinates> getAdjacent(Coordinates c, State s, ObjectType type) {
		List<Coordinates> adj = new LinkedList<Coordinates>();
		if (!s.isBlocked(new Coordinates(c.getX() + 1, c.getY()), type))
			adj.add(new Coordinates(c.getX() + 1, c.getY()));
		if (!s.isBlocked(new Coordinates(c.getX() - 1, c.getY()), type))
			adj.add(new Coordinates(c.getX() - 1, c.getY()));
		if (!s.isBlocked(new Coordinates(c.getX(), c.getY() + 1), type))
			adj.add(new Coordinates(c.getX(), c.getY() + 1));
		if (!s.isBlocked(new Coordinates(c.getX(), c.getY() - 1), type))
			adj.add(new Coordinates(c.getX(), c.getY() - 1));
		return adj;
	}
	
	
	// Astar search finding shortest path from one location to other
	//		Does not take into account active objects, only whether terrain is passable
	public List<Coordinates> findRoute(State s, Coordinates src, Coordinates dst) {
		// SEARCH STATE
		class SearchState implements Comparable<SearchState> {
			Coordinates c;
			int f;
			double h;
			
			public SearchState(Coordinates c, int f, double h) {
				this.c = c;
				this.f = f;
				this.h = h;
			}

			@Override
			public int compareTo(SearchState s) {
				return (int)((this.f + this.h) - (s.f + s.h));
			}	
			
			public SearchState next(Coordinates to, Coordinates goal) {
				return new SearchState(to.clone(), f + 1, heuristic(to, goal));
			}
		}
		
		// ALGORITHM
		HashMap<Coordinates, Coordinates> pred = new HashMap<Coordinates, Coordinates>();
		HashMap<Coordinates, Boolean> seen = new HashMap<Coordinates, Boolean>();
		PriorityQueue<SearchState> searchQueue = new PriorityQueue<SearchState>();
		searchQueue.add( new SearchState(src, 0, heuristic(src, dst)));
		
		while (!searchQueue.isEmpty()) {
			
			SearchState curr = searchQueue.poll();
			if (curr.c.equals(dst)) {
				// found path, extract from predecessor array
				List<Coordinates> path = new LinkedList<Coordinates>();
				for (Coordinates c = curr.c; !c.equals(src); c = pred.get(c)) {
					path.add(0, c);
				}
				path.add(src);
				return path;
			}
			
			seen.put(curr.c, true);
			
			for (Coordinates conn : getAdjacent(curr.c, s, null)) {
				if (seen.get(curr) != null) {
					continue;
				}
				SearchState newState  = curr.next(conn, dst);
				searchQueue.add(newState);
				pred.put(conn, curr.c);
			}		
		}

		// Queue is empty, means no path
		return null;
	}
	
	
	public double heuristic(Coordinates from, Coordinates to) {
		return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()); 
	}
	
	
}
