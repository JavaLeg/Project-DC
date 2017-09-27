package Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EditorScreen implements Screen {
	
	private OrthographicCamera cam;
	public ShapeRenderer shapeRenderer;
	private ClickListener click;
	
	// Show only operates once, after it will render
	@Override
	public void show() {
		
		// Get height, width of current screen 
		int height = Gdx.graphics.getHeight();
		int width = Gdx.graphics.getWidth();
		
		click = new ClickListener();
		cam = new OrthographicCamera(height, width);
		shapeRenderer = new ShapeRenderer();
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );	

		cam.update();
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		
		// Draw the vertical lines
		for (int i = 0; i < width; i = i + 10) {
			shapeRenderer.line(i, 0, i, height);
		}
		
		// Draw horizontal lines
		for (int i = 0; i < height; i = i + 10) {
			shapeRenderer.line(0, i, width, i);
		}

		shapeRenderer.end();
	}
	
	@Override
	public void render(float delta) {
		// Constantly render when grids are clicked 
		
		// Doesn't currently work
		if (click.isPressed()) {
			System.out.println("Click!");
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
