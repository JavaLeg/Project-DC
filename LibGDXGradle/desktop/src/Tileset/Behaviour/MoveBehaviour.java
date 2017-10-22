package Tileset.Behaviour;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import Interface.Stages.Selections.BehaviourSelection;
import State.Coord;
import State.State;

public class MoveBehaviour implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 595534154089190386L;
	private BehaviourSelection selection;

	// Default behaviour returns current position
	// 		Behaviours should override this
	public Coord nextStep(State s, Coord currentPos) {
		return currentPos;
	}
	
	public BehaviourSelection getSelection() {
		return selection;
	}
	
	public void setSelection(BehaviourSelection selection) {
		this.selection = selection;
	}

	
	
	// Path finding helper functions:
	// 		Assume no diagonal movement
	
	
	// gets adjacent coordinates, regardless of of a State
	public List<Coord> getAdjacent(Coord c) {
		List<Coord> adj = new LinkedList<Coord>();
		adj.add(new Coord(c.getX() + 1, c.getY()));
		adj.add(new Coord(c.getX() - 1, c.getY()));
		adj.add(new Coord(c.getX(), c.getY() + 1));
		adj.add(new Coord(c.getX(), c.getY() - 1));
		return adj;
	}
	
	
	// gets adjacent coordinates restricted by walls
	public List<Coord> getAdjacent(Coord c, State s) {
		List<Coord> adj = new LinkedList<Coord>();
		if (!s.hasWall(new Coord(c.getX() + 1, c.getY())))
			adj.add(new Coord(c.getX() + 1, c.getY()));
		if (!s.hasWall(new Coord(c.getX() - 1, c.getY())))
			adj.add(new Coord(c.getX() - 1, c.getY()));
		if (!s.hasWall(new Coord(c.getX(), c.getY() + 1)))
			adj.add(new Coord(c.getX(), c.getY() + 1));
		if (!s.hasWall(new Coord(c.getX(), c.getY() - 1)))
			adj.add(new Coord(c.getX(), c.getY() - 1));
		return adj;
	}
	
	public List<Coord> getAdjacentBlocked(Coord c, State s) {
		List<Coord> adj = new LinkedList<Coord>();
		if (!s.isBlocked(new Coord(c.getX() + 1, c.getY())))
			adj.add(new Coord(c.getX() + 1, c.getY()));
		if (!s.isBlocked(new Coord(c.getX() - 1, c.getY())))
			adj.add(new Coord(c.getX() - 1, c.getY()));
		if (!s.isBlocked(new Coord(c.getX(), c.getY() + 1)))
			adj.add(new Coord(c.getX(), c.getY() + 1));
		if (!s.isBlocked(new Coord(c.getX(), c.getY() - 1)))
			adj.add(new Coord(c.getX(), c.getY() - 1));
		return adj;
	}
	
	
	
	// Astar search finding shortest path from one location to other
	//		Does not take into account active objects, only whether terrain is passable
	public List<Coord> findRoute(State s, Coord src, Coord dst) {
		// SEARCH STATE
		//System.out.print("From " + src.toString() + " to " + dst.toString() + "\n");
		
		
		class SearchState implements Comparable<SearchState> {
			Coord c;
			Coord p;
			int f;
			double h;
			
			public SearchState(Coord c, Coord p, int f, double h) {
				this.c = c;
				this.p = p;
				this.f = f;
				this.h = h;
				
			}

			@Override
			public int compareTo(SearchState s) {
				return (int)((this.f + this.h) - (s.f + s.h));
			}	
			
			public SearchState next(Coord to, Coord prev, Coord goal) {
				return new SearchState(to.clone(), prev.clone(), this.f + 1, heuristic(to, goal));
			}
		}
		
		// ALGORITHM
		HashMap<Coord, Coord> pred = new HashMap<Coord, Coord>();
		HashMap<Coord, Boolean> seen = new HashMap<Coord, Boolean>();
		PriorityQueue<SearchState> searchQueue = new PriorityQueue<SearchState>();
		searchQueue.add( new SearchState(src, null, 0, heuristic(src, dst)));
		
		while (!searchQueue.isEmpty()) {
			
			SearchState curr = searchQueue.poll();
			pred.put(curr.c, curr.p);
			//System.out.print("added: " + curr.c + " h, f: " + curr.h + "," +  curr.f +  " from: " + pred.get(curr.c) + "\n");
			if (curr.c.equals(dst)) {
				// found path, extract from predecessor array
				List<Coord> path = new LinkedList<Coord>();
				for (Coord c = curr.c; !c.equals(src); c = pred.get(c)) {
					path.add(0, c);
				}
				//path.add(src);
				return path;
			}
			
			seen.put(curr.c, true);
			
			for (Coord conn : getAdjacent(curr.c, s)) {
				if (seen.get(conn) != null) {
					continue;
				}
				//System.out.print(conn.toString() + " from " + curr.c.toString() + "\n");
				SearchState newState  = curr.next(conn, curr.c, dst);
				
				searchQueue.add(newState);
				//if (pred.get(conn))
				//pred.put(conn, curr.c);
			}		
		}

		// Queue is empty, means no path
		return null;
	}
	
	
	public double heuristic(Coord from, Coord to) {
		return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()); 
	}
	
	
}
