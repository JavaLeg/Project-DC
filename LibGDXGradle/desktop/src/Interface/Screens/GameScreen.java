package Interface.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.DCGame;

import Interface.EditorModel;
import Interface.GameInputProcessor;
import State.DynamicGame;
import State.RunGame;
import State.State;
import State.Coord;

public class GameScreen implements Screen {

    private OrthographicCamera camera;	
	private RunGame gameThread;
	private DCGame game;
	private State previewStage;
	private DynamicGame g;
	private GameInputProcessor inputProcessor;
    private float lerp;
	
	public GameScreen(DCGame g) {
		this.game = g;
	}
	
	
	@Override
	public void show() {    		
    
		int height = Gdx.graphics.getHeight();
		int width = Gdx.graphics.getWidth();
		
		camera = new OrthographicCamera();
		Viewport gameViewport = new FitViewport(width, height, camera);
		previewStage = new State(gameViewport);

		lerp = 0.1f;
		
        g = new DynamicGame();
		inputProcessor = new GameInputProcessor(g);
		g.initialise(previewStage); // input player created state here
		Gdx.input.setInputProcessor(inputProcessor);
		
		gameThread = new RunGame(g, inputProcessor, 150);
		gameThread.run();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Make sure there is actually a player object
        Coord centre = null;
        if (g.getState().getPlayer() != null) {
        	centre = g.getState().getPlayer().getCoord();
        }
        //System.out.println(centre.toString());
        // Lerp the camera so it looks smooth, so smooth, what a feature
        if (centre != null && camera != null) {
        	Vector3 position = camera.position;
        	// Can change the variable 0.4 (faster means faster camera flicks)
        	position.x += (centre.getX() * 40 - position.x) * lerp * 0.4;
        	position.y += (centre.getY() * 40 - position.y) * lerp * 0.4;
        	//System.out.println(position.toString());
	        camera.position.set(position.x, position.y, 0);
	        camera.update();
        }
        
        previewStage.act();
        previewStage.draw();


	}
	
	public void showAttack() {
		
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
	
	public void loadModel(EditorModel m) {
		System.out.println("Loaded model.");	
		this.previewStage.restoreModel(m);
	}
}
