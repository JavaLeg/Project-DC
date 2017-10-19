package State;

import com.badlogic.gdx.InputProcessor;

import Interface.GameInputProcessor;
import Tileset.DynamicObject;
import Tileset.DynamicObject.ActionState;
import Tileset.GameObject;
import Tileset.GameObject.ObjectType;
import Tileset.Player;
import Tileset.Behaviour.Direction;

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
	
	
	
	// Player Actions:
	// 	Movement and attack
	// false means action can not be made and no changes are made to the state
	
	public boolean makeAction(Action a) {
		Coord curr = activeState.findPlayer();
		Player p = activeState.getPlayer();
		switch (a) {
		case ATTACK:
			p.selectLight();
			p.setActionState(ActionState.ATTACK);
			System.out.print("USER INPUT: LIGHT ATTACK\n");
			break;
		case SPECIAL:
			p.selectSpecial();
			p.setActionState(ActionState.ATTACK);
			System.out.print("USER INPUT: HEAVY ATTACK\n");
			break;
		case MOVE_SOUTH:
			if (p.canChangePosition()) {
				activeState.movePlayer((Direction.SOUTH).moveInDirection(curr));
			}
			System.out.print("USER INPUT: DOWN\n");
			break;
		case MOVE_WEST:
			if (p.canChangePosition()) {
				activeState.movePlayer((Direction.WEST).moveInDirection(curr));
			}
			System.out.print("USER INPUT: LEFT\n");
			break;
		case MOVE_EAST:
			if (p.canChangePosition()) {
				activeState.movePlayer((Direction.EAST).moveInDirection(curr));
			}
			System.out.print("USER INPUT: RIGHT\n");
			break;
		case MOVE_NORTH:
			if (p.canChangePosition()) {
				activeState.movePlayer((Direction.NORTH).moveInDirection(curr));
			}
			System.out.print("USER INPUT: UP\n");
			break;
		default:
			break;
		
		}
		return false;
	}
	
	
	
}
