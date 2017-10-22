package Interface;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import State.Action;
import State.DynamicGame;



// Handles direct user input and updates model
public class GameInputProcessor implements InputProcessor {
	private DynamicGame activeGame;
	private Action queuedAction;
	private Action nextAction; 
	private final int actionSpeed = 5;
	private int sinceLastAction;
	private boolean enabled;
	
	
	public GameInputProcessor(DynamicGame g) {
		this.activeGame = g;
		sinceLastAction = 0;
		enabled = true;
	}
	
	
	@Override
	public boolean keyDown(int keycode) {		
		if (!enabled) return false;
		//SUPPORTS:
		// 	Move: WSAD, Attack/Special: J/K
		//  Move: Arrow Keys, Attack/Special: Z/X
		
		Action toMake = null;
		
		switch (keycode) {
		
		// PLAYER MOVEMENT
		case Keys.LEFT:
			toMake = Action.MOVE_WEST;
			break;
		case Keys.RIGHT:
			
			toMake = Action.MOVE_EAST;
			break;
		case Keys.UP:
		
			toMake = Action.MOVE_NORTH;
			break;
		case Keys.DOWN:
		
			toMake = Action.MOVE_SOUTH;
			break;
			
		case Keys.A:	
			toMake = Action.ATTACK_WEST;
			break;
		case Keys.D:
			toMake = Action.ATTACK_EAST;
			break;
		case Keys.W:
			toMake = Action.ATTACK_NORTH;
			break;
		case Keys.S:
			toMake = Action.ATTACK_SOUTH;
			break;
			
		// PLAYER SPECIAL/ATTACK
		case Keys.Z:
		case Keys.J:
			toMake = Action.ATTACK;
			break;
		
		case Keys.X:
		case Keys.K:
			toMake = Action.SPECIAL;
			break;
			
		default:
			// nothing 
		}		
		System.out.println("make Action");
		if (toMake != null) {
			if (sinceLastAction > 0) {
				queuedAction = toMake;
			} else {
				sinceLastAction = actionSpeed;
				activeGame.makeAction(toMake); // TODO: move to step to syncronise all actions
				//nextAction = toMake;
			}
		}
		
		return false;
	}

	
	
	public void step() {
		//System.out.println("sup");
		//if (nextAction != null) {
		//	activeGame.makeAction(nextAction);
		//	nextAction = null;
		//	queuedAction = null;
		//} else {
			if (sinceLastAction > 0) {
				//System.out.println(sinceLastAction);
				sinceLastAction--;
			} else {
				if (queuedAction != null) {
					sinceLastAction = actionSpeed;
					activeGame.makeAction(queuedAction);
					queuedAction = null;
				}
			}
		//}
	}
	
	
	public void setEnabled(boolean enabled) {
		if (!enabled) queuedAction = null; // remove queued actions on pause
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return "USER INPUT PROCESSOR";
	}

}
