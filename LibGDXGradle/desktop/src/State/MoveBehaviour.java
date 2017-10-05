package State;

import java.util.LinkedList;
import java.util.List;

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
	
	
	// Astar search finding shortest 
	public List<Coordinates> findRoute(State s, Coordinates to, Coordinates from) {
		List<Coordinates> path = new LinkedList<Coordinates>();
		class searchState {
			
		}
		
		
		
		
		return path;
	}
	
	
	public double heuristic(Coordinates from, Coordinates to) {
		return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()); 
	}
	
	
}
