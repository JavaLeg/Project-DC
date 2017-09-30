package Interface;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import State.Action;
import State.DynamicGame;



// Handles direct user input and updates model
public class GameInputProcessor implements InputProcessor {
	private DynamicGame activeGame;

	
	
	
	public GameInputProcessor(DynamicGame g) {
		this.activeGame = g;
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		
		// May be continuous, change to key up or inbuilt timer
		
		//SUPPORTS:
		// 	Move: WSAD, Attack: J
		//  Move: Arrow Keys, Attack: Z
		
		switch (keycode) {
		
		// PLAYER MOVEMENT
		case Keys.LEFT:
		case Keys.A:
			activeGame.makeAction(Action.MOVE_LEFT);
			break;
		case Keys.RIGHT:
		case Keys.D:	
			activeGame.makeAction(Action.MOVE_RIGHT);
			break;
		case Keys.UP:
		case Keys.W:
			activeGame.makeAction(Action.MOVE_UP);
			break;
		case Keys.DOWN:
		case Keys.S:
			activeGame.makeAction(Action.MOVE_DOWN);
			break;
		// PLAYER SPECIAL/ATTACK
		
		case Keys.Z:
		case Keys.J:
			activeGame.makeAction(Action.ATTACK);
			break;
			
		default:
			// nothing 
		}
		return false;
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

}
