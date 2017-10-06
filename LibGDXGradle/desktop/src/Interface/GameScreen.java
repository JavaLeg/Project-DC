package Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import State.DynamicGame;
import State.RunGame;
import State.State;

public class GameScreen implements Screen {

	
	
	protected Stage stage_left;
    protected Stage stage_right;
    private Viewport viewport_left;		// Holds the menu buttons
    private Viewport viewport_right;	// Holds the grid preview
    
    private OrthographicCamera camera;
    private int height;			
    private int width;
    private Table mainTable;
    public Vector3 touchPos;	
	
	private RunGame gameThread;
    
    
	public GameScreen() {
		
	}
	
	
	@Override
	public void show() {
		camera = new OrthographicCamera();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
        
		// Configure the RHS of screen (grid preview)
        viewport_right = new FitViewport(width, height, camera);
        viewport_right.apply();
        viewport_right.setScreenPosition(200, 200);		// Sets viewport's position
        viewport_right.update(720, 720, true);			// Updates the right pos and sets size
        stage_right = new Stage(viewport_right); 
        Gdx.input.setInputProcessor(stage_right);
        
        viewport_left = new FitViewport(width, height, camera);
        viewport_left.apply();
        viewport_left.setScreenPosition(100, 100);
        viewport_left.update(200, 200, true);
        stage_left = new Stage(viewport_left);        
        Gdx.input.setInputProcessor(stage_left);
		
        
        DynamicGame g = new DynamicGame();
		g.initialise(new State()); // input player created state here
		GameInputProcessor inputProcessor = new GameInputProcessor(g);
		Gdx.input.setInputProcessor(inputProcessor);
        
		gameThread = new RunGame(g, 30);
		gameThread.run();
		
        
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw both stage right and stage left
        // If window resize (update TODO)
        stage_right.act();
        stage_right.draw();
        
        stage_left.act();
        stage_left.draw();
		
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
		// terminate game
		gameThread.stop();
	}

}
