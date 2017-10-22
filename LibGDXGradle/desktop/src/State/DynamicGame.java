package State;

import java.util.Iterator;

import com.badlogic.gdx.InputProcessor;

import Interface.GameInputProcessor;
import Tileset.DynamicObject;
import Tileset.DynamicObject.ActionState;
import Tileset.GameObject;
import Tileset.GameObject.ObjectType;
import Tileset.Player;
import Tileset.Enemy;
import Tileset.Behaviour.Attack;
import Tileset.Behaviour.AttackAnimation;
import Tileset.Behaviour.Direction;

//temporary class for Game, 
// showing interface necessary for higher level input and step processing
// modifies GameState in a dynamic way, ALL GAME LOGIC HERE
// Probably needs a better name 
public class DynamicGame {
	private int steps;
	private State activeState;
	
	// private int last_move;					// Only move in the direction you face, otherwise turn. 

	AttackAnimation playerAttack;
	
	
	public DynamicGame() {
		steps = 0;
	}
	
	// GENERAL FUNCTIONALITY
	public void initialise(State startState) {
		// take in a new GameState, and execute any other preamble
		this.activeState = startState;
		
		playerAttack = new AttackAnimation();
	}
	
	public void step() {
		// execute each game step (for any realtime objects)
		
		// get all gameworld objects
		// iterate upon these objects running their step()

		//System.out.print(activeState.getAllDynamicObjects().size());
		Iterator<DynamicObject> iterator = activeState.getAllDynamicObjects().iterator();
		while(iterator.hasNext()) {
			DynamicObject o = iterator.next();
			o.step(activeState);
		}
		
		
		// Conflict Resolution
		activeState.resolveConflicts();
		
		// game over check
		if (activeState.getPlayer() == null) {
			// GAME OVER HANDLING
		}
	}
	
	public State getState() {
		return activeState;
	}
	
	/*
	 * Sets up the player direction initially
	 */
/*	public void playerSetup() {
		// Start the game with orientation
		System.out.println("Has player = " + activeState.hasPlayer());
		if (activeState.getPlayer() != null) {
			Player cur = (Player) activeState.getPlayer();
			cur.setFacingRight();
			cur.setDirection(Direction.EAST);
			System.out.println("Initialised");
		}	
	}*/
	// Player Actions:
	// 	Movement and attack
	// false means action can not be made and no changes are made to the state
	
	/* Last move:
	 * 1 for up, 2 for right, 3 for down, 4 for left
	 */
	
	
	public boolean makeAction(Action a) {
		Coord curr = activeState.findPlayer();
		Player p = (Player) activeState.getPlayer();
		
		if (p.getFacing() == null) p.setFacing(Direction.EAST);		
		// Set starting direction to EAST when starting the game
		
		Coord toMove = null;
		//System.out.println("coord = " + curr);

		switch (a) {
		case ATTACK:
			//assert(p.getDirection() != null);
			if (p.canAttack()) {
				Coord coord = p.getFacing().moveInDirection(curr);
				playerAttack.add(coord,  p.getFacing());
				//AttackAnimation att = new AttackAnimation(p.getDirection() , coord);
				activeState.addActor(playerAttack);
				p.selectLight();
				p.setActionState(ActionState.ATTACK);
				//activeState.attackObject(coord, p.getAttack());
			}
			
			
//			
			System.out.print("USER INPUT: LIGHT ATTACK\n");
			break;
		case SPECIAL:
//			p.selectSpecial();
//			p.setActionState(ActionState.ATTACK);
			System.out.print("USER INPUT: HEAVY ATTACK\n");
			break;
		case MOVE_SOUTH:
			// Up and down are uncontested, however they preserve original
			// direction player faces (either left or right)
			toMove = (Direction.SOUTH).moveInDirection(curr);
			if (p.canChangePosition() && !activeState.isBlocked(toMove)) {
				activeState.movePlayer(toMove);
			}
			p.setFacing(Direction.SOUTH);
			System.out.print("USER INPUT: DOWN\n");
			break;
		case MOVE_WEST:
			// If looking left, we can move left. Otherwise turn left
			if (p.getFacing() == Direction.WEST) {
				toMove = (Direction.WEST).moveInDirection(curr);
				if (p.canChangePosition() && !activeState.isBlocked(toMove)) {
					activeState.movePlayer(toMove);
				}
			} else {
				p.setFacing(Direction.WEST);
			}
			System.out.print("USER INPUT: LEFT\n");
			break;
		case MOVE_EAST:
			// If looking right, we can move right. Otherwise turn right
			if (p.getFacing() == Direction.EAST) {
				toMove = (Direction.EAST).moveInDirection(curr);
				if (p.canChangePosition() && !activeState.isBlocked(toMove)) {
					activeState.movePlayer(toMove);
				}
			} else {
				p.setFacing(Direction.EAST);
			}
			System.out.print("USER INPUT: RIGHT\n");
			break;
		case MOVE_NORTH:
			// Up and down are uncontested, however they preserve original
			// direction player faces (either left or right)
			toMove = (Direction.NORTH).moveInDirection(curr);
			if (p.canChangePosition() && !activeState.isBlocked(toMove)) {
				activeState.movePlayer(toMove);
			}
			p.setFacing(Direction.NORTH);
			System.out.print("USER INPUT: UP\n");

			break;
		default:
			break;
		}
		activeState.getTile(curr).flipObject(p.facingRight(), p.getImgPath());
		return false;
	}
	
	
	
}
