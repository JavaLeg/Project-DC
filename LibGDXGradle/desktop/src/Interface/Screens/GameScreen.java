package Interface.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.DCGame;

import Interface.EditorModel;
import Interface.GameInputProcessor;
import Interface.Viewports.PreviewProcessor;
import State.DynamicGame;
import State.RunGame;
import State.RunGame2;
import State.State;
import Tileset.DynamicObject;
import State.Coord;

public class GameScreen implements Screen {

    private OrthographicCamera camera;	
	private RunGame2 gameThread;
	private DCGame game;
	private State previewStage;
	private DynamicGame g;
	private GameInputProcessor inputProcessor;
    private float lerp;
    private Image green_bar;
    private Image red_bar;
/*    private ProgressBar bar;
    private ProgressBarStyle barStyle;*/
	
    private int stepRate;
	private boolean isRunning;
	private double timeOfLastUpdate;
    
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
		
		
		InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) {
                	((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
                }
                return false;
            }
        };
		InputMultiplexer multiplexer = new InputMultiplexer(previewStage, backProcessor);
		Gdx.input.setInputProcessor(multiplexer);
		isRunning = true;
		stepRate = 60;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(8/255f, 23/255f, 30/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Make sure there is actually a player object
        Coord centre = null;
        if (g.getState().getPlayer() != null) {
        	centre = g.getState().getPlayer().getCoord();
        	if (green_bar == null) HealthBarSetup();
        }
        //System.out.println(centre.toString());
        // Lerp the camera so it looks smooth, so smooth, what a feature
        if (centre != null && camera != null) {
        	Vector3 position = camera.position;
        	// Can change the variable 0.4 (faster means faster camera flicks)
        	position.x += (centre.getX() * 40 - position.x) * lerp * 0.4;
        	position.y += (centre.getY() * 40 - position.y) * lerp * 0.4;
	        camera.position.set(position.x, position.y, 0);
	        updateBar(centre);
	        camera.update();
        }
        previewStage.act();
        previewStage.draw();

        // handle game running : this is a quick fix 
        if (isRunning) {
			double newTime = TimeUtils.millis() / 1000;
			double diff = newTime - timeOfLastUpdate;
			
			if (diff >= 1/stepRate) {
				timeOfLastUpdate -= stepRate/1000;
				g.step();
				inputProcessor.step();
			}
        }
	}


	/* 
	 * Also the end screen 
	 */
	public void updateBar(Coord centre) {
        green_bar.setPosition(centre.getX() * 40, centre.getY() * 40 + 35);		// Update position of hp bar
        red_bar.setPosition(centre.getX() * 40, centre.getY() * 40 + 35);		// Update position of hp bar
        
        DynamicObject play = g.getState().getPlayer();
        if (play != null && play.getHp() > 0) {
        	int width = (int)(play.getHp() / play.getMaxHp() * 40);
        	green_bar.setWidth(width);
        } else if (play != null && play.getHp() <= 0) {
        	((Game)Gdx.app.getApplicationListener()).setScreen(new EndScreen(game));
        	//this.dispose();
	    }
        
        
	}
	
	
	public void HealthBarSetup() {
		if (g.getState().hasPlayer()) {
			Coord play = g.getState().getPlayer().getCoord();
			TextureRegion green = new TextureRegion(new Texture(Gdx.files.internal("green_bar.jpg")));
			green_bar = new Image(green);
			green_bar.setSize(40, 5);
			green_bar.setPosition(play.getX() * 40, play.getY() * 40 + 35);
			
			TextureRegion red = new TextureRegion(new Texture(Gdx.files.internal("red_bar.jpg")));
			red_bar = new Image(red);
			red_bar.setSize(40, 5);
			red_bar.setPosition(play.getX() * 40, play.getY() * 40 + 35);
			
			previewStage.addActor(red_bar);
			previewStage.addActor(green_bar);
		}
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
