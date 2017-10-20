package Interface.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.DCGame;

import Interface.EditorModel;
import Interface.GameInputProcessor;
import Interface.Viewports.PreviewViewport;
import Interface.Viewports.ToolbarViewport;
import State.DynamicGame;
import State.RunGame;
import State.State;
import State.Action;

public class GameScreen implements Screen {

    private OrthographicCamera camera;	
	private RunGame gameThread;
	private DCGame game;
	private State previewStage;
	private DynamicGame g;
	private GameInputProcessor inputProcessor;
    
	public GameScreen(DCGame g) {
		this.game = g;
	}
	
	
	@Override
	public void show() {    		
    
		int height = Gdx.graphics.getHeight();
		int width = Gdx.graphics.getWidth();
		
		//Camera gameCamera = new OrthographicCamera();
		Viewport gameViewport = new FitViewport(width, height);
		
        previewStage = new State(gameViewport);
		
        
        g = new DynamicGame();
		inputProcessor = new GameInputProcessor(g);
		g.initialise(previewStage, inputProcessor); // input player created state here
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

        g.getState().act();
        g.getState().draw();
        
        inputProcessor = new GameInputProcessor(g);
        Gdx.input.setInputProcessor(inputProcessor);
        
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
		//show();
		previewStage.restoreModel(m);
	}
}
