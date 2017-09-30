package State;


//temporary class for Game, 
// showing interface necessary for higher level input and step processing
// modifies GameState in a dynamic way, ALL GAME LOGIC HERE
// Probably needs a better name :/
public class DynamicGame {
	private int steps;
	
	
	public DynamicGame() {
		steps = 0;
	}
	
	// GENERAL FUNCTIONALITY
	public void initialise(State startState) {
		// take in a new GameState, and execute any other preamble
		
		
	}
	
	public void step() {
		// execute each game step (for any realtime enemies)
		System.out.print("Game step.");
		steps++;
		if (steps >= 30) {
			System.out.print("30 steps");
		}
	}
	
	public State getState() {
		
		return null;
	}
	
	
	
	// Player Actions:
	// 	Movement and attack
	// false means action can not be made and no changes are made to the state
	
	public boolean makeAction(Action a) {
		switch (a) {
		case ATTACK:
			System.out.print("USER INPUT: ATTACK\n");
			break;
		case MOVE_DOWN:
			System.out.print("USER INPUT: DOWN\n");
			break;
		case MOVE_LEFT:
			System.out.print("USER INPUT: LEFT\n");
			break;
		case MOVE_RIGHT:
			System.out.print("USER INPUT: RIGHT\n");
			break;
		case MOVE_UP:
			System.out.print("USER INPUT: UP\n");
			break;
		default:
			break;
		
		}
		return false;
	}
	
	
	
}
