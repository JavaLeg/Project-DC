package Tileset.Behaviour;

import java.util.List;

import State.Coord;
import State.State;

public class testMove {
	public static void main (String[] arg) {
		MoveBehaviour m = new MoveBehaviour();
		State s = new State(20, 20);
		List<Coord> p = m.findRoute(s, new Coord(0,0), new Coord(6,8));
		if (p == null) System.out.print("No path found.\n");
		else {
			System.out.print("Found path:\n");
			for (Coord c : p) {
				System.out.print(c.toString() + "\n");
			}
			
		}
	
		
	}
}
