package Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.DCGame;

public class EditorScreen implements Screen {
	
	private OrthographicCamera cam;
	public ShapeRenderer shapeRenderer;
	private ClickListener click;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
	
    private static final int WORLD_WIDTH  = 250;
    private static final int WORLD_HEIGHT = 250;
    
    public EditorScreen()
    {	
        atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_HEIGHT, WORLD_WIDTH, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport);   

    }
	
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
