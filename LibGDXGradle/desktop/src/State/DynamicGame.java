package State;

import com.badlogic.gdx.InputProcessor;

import Interface.GameInputProcessor;
import Tileset.DynamicObject;
import Tileset.GameObject;
import Tileset.Player;

//temporary class for Game, 
// showing interface necessary for higher level input and step processing
// modifies GameState in a dynamic way, ALL GAME LOGIC HERE
// Probably needs a better name 
public class DynamicGame {
	private int steps;
	private State activeState;
	private GameInputProcessor input;
	private int last_move;					// 0 for N/A, 1 for left, 2 for right
	
	public DynamicGame() {
		steps = 0;
	}
	
	// GENERAL FUNCTIONALITY
	public void initialise(State startState, GameInputProcessor input) {
		// take in a new GameState, and execute any other preamble
		this.activeState = startState;
		this.last_move = 0;
		System.out.print("Initiated\n");
		this.input = input;
		if (this.input == null) {
			System.out.print("shitttdsyyucjsvcblskb\n");
		}
	}
	
	public void step() {
		// execute each game step (for any realtime objects)
		
		// get all gameworld objects
		// iterate upon these objects running their step()

		// TODO: when state fixed
		for (DynamicObject o : activeState.getAllDynamicObjects()) {
			o.step(activeState);
		}
		input.step();
		
		// Conflict Resolution
		
		
		// game over check
		if (activeState.getPlayer() == null) {
			// GAME OVER HANDLING
		}
	}
	
	public State getState() {
		return activeState;
	}
	
	
	
	/* Player actions, returns false if unable to perform
	 * Does checks for valid next position
	 */
	
	public boolean makeAction(Action a) {
		GameObject curr = activeState.getPlayer();
		
		if (curr == null) return false;
		Coord pos = curr.getCoord();
		
		// Two things to consider, if the move is valid and if 
		// we need to flip the image
		switch (a) {
		case ATTACK:
			System.out.print("USER INPUT: ATTACK\n");
			break;
		case MOVE_DOWN:
			Coord down = new Coord(pos.getX(), pos.getY() - 1);
			if (activeState.isValid(down) == false) return false;
			activeState.movePlayer(down);
			System.out.print("USER INPUT: DOWN\n");
			break;
		case MOVE_UP:
			Coord up = new Coord(pos.getX(), pos.getY() + 1);
			if (activeState.isValid(up) == false) return false;
			activeState.movePlayer(up);
			System.out.print("USER INPUT: UP\n");
			break;
		case MOVE_LEFT:
			Coord left = new Coord(pos.getX() - 1, pos.getY());
			if (activeState.isValid(left) == false) return false;
			activeState.movePlayer(left);
			if (last_move == 2) curr.getTexture().flip(true, false);			// flip(boolean x-axis flip, boolean y-axis flip)
			last_move = 1;
			
			System.out.print("USER INPUT: LEFT\n");
			break;
		case MOVE_RIGHT:
			Coord right = new Coord(pos.getX() + 1, pos.getY());
			if (activeState.isValid(right) == false) return false;
			activeState.movePlayer(right);
			
			if (last_move == 1) curr.getTexture().flip(true, false);			// flip(boolean x-axis flip, boolean y-axis flip)
			last_move = 2;
			System.out.print("USER INPUT: RIGHT\n");
			break;
		default:
			break;
		}
		return false;
	}
	
	
	
}
