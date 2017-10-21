package Interface.Viewports;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;

public class PreviewProcessor implements InputProcessor{
	private int dragX, dragY;
	private float intensity = 450f;
	private Camera camera;
	
	public PreviewProcessor(Camera cam) {
		this.camera = cam;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
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
		dragX = screenX;
		dragY = screenY;
		return true;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
	    float dX = (float)(dragX - screenX)/(float)Gdx.graphics.getWidth();
	    float dY = (float)(screenY - dragY)/(float)Gdx.graphics.getHeight();
	    dragX = screenX;
	    dragY = screenY;
	    
	    camera.position.add(dX * intensity, dY * intensity, 0f);
	    camera.update();
	    return true;
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
