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
	
	public DynamicGame() {
		steps = 0;
	}
	
	// GENERAL FUNCTIONALITY
	public void initialise(State startState, GameInputProcessor input) {
		// take in a new GameState, and execute any other preamble
		this.activeState = startState;
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
	 * 
	 */
	
	public boolean makeAction(Action a) {
		GameObject curr = activeState.getPlayer();
		
		if (curr == null) return false;
		System.out.println("successful!");
		Coord pos = curr.getCoord();
		System.out.println(pos);
		
		switch (a) {
		case ATTACK:
			System.out.print("USER INPUT: ATTACK\n");
			break;
		case MOVE_DOWN:
			Coord next = new Coord(pos.getX(), pos.getY() - 1);
			if (activeState.isValid(next) == false) return false;
			activeState.movePlayer(next);
			System.out.print("USER INPUT: DOWN\n");
			break;
		case MOVE_UP:
			activeState.movePlayer(new Coord(pos.getX(), pos.getY() + 1));
			System.out.print("USER INPUT: UP\n");
			break;
		case MOVE_LEFT:
			activeState.movePlayer(new Coord(pos.getX() - 1, pos.getY()));
			System.out.print("USER INPUT: LEFT\n");
			break;
		case MOVE_RIGHT:
			activeState.movePlayer(new Coord(pos.getX() + 1, pos.getY()));
			System.out.print("USER INPUT: RIGHT\n");
			break;
		default:
			break;
		}
		return false;
	}
	
	
	
}
