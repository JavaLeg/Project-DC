package Interface.Screens;

import java.util.Arrays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Interface.GameInputProcessor;
import State.Coord;
import State.DynamicGame;
import State.RunGame;
import State.State;
import State.Tile;
import Tileset.DynamicObject;
import Tileset.Enemy;
import Tileset.GameObject;
import Tileset.Behaviour.Attack;
import Tileset.Behaviour.MoveBehaviour;
import Tileset.Behaviour.MovePathToPoint;
import Tileset.Behaviour.MoveRandom;
import Tileset.GameObject.ObjectType;
import Tileset.Player;

public class GameScreen2 implements Screen {
	
	private SpriteBatch batch;
	private State currentState;
	private Game game;
	private RunGame activeGame;
	Texture floor = new Texture(Gdx.files.internal("SpriteFamily/floor/sprite_002.png"));
	
	public GameScreen2(Game g) {
		game = g;
	}
	
	
	@Override
	public void show() {
		
		batch = new SpriteBatch();
		
		int height = Gdx.graphics.getHeight();
		int width = Gdx.graphics.getWidth();
		
		//Camera gameCamera = new OrthographicCamera();
		Viewport gameViewport = new FitViewport(width, height);
		
		
		// setting up custom state
		
		
        currentState = new State(gameViewport, 10, 10);
		//currentState.fillGrid();
		currentState.fillObject(new GameObject(ObjectType.WALL, new TextureRegion(new Texture(Gdx.files.internal("SpriteFamily/wall/sprite_021.png")))));
		for (int x = 1; x < currentState.getMapWidth() - 1; x++) {
			for (int y = 1; y < currentState.getMapHeight() - 1; y++) {
				currentState.deleteObject(new Coord(x,y));
			}
		}

			
		Enemy e1 = new Enemy(null, 100, 10, 15, new MoveRandom(), null, 
				new Texture(Gdx.files.internal("SpriteFamily/enemy/sprite_091.png")));
		Enemy e2 = new Enemy(null, 100, 10, 45, new MovePathToPoint(true), 
				new Attack(Arrays.asList(new Coord(0,1)), 10, Arrays.asList(ObjectType.PLAYER), 20, 60), 
				new Texture(Gdx.files.internal("SpriteFamily/enemy/sprite_092.png")));
		Player p =  new Player(null, 100, 10, null, null, new Texture(Gdx.files.internal("SpriteFamily/player/sprite_081.png")));
		currentState.setObject(e1, new Coord(3,3));
		currentState.setObject(e2, new Coord(4,4));
		currentState.setObject(p, new Coord(6,6));
		DynamicGame g = new DynamicGame();
		GameInputProcessor inputProcessor = new GameInputProcessor(g);
		g.initialise(currentState); 
		
		activeGame = new RunGame(g, inputProcessor, 30);
		
		
		InputProcessor pause = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Keys.SPACE)) {
                	if (activeGame.isRunning()) {
                		activeGame.stop();
                	} else {
                		activeGame.run();
                	}
                }
                return false;
            }
        };
        
        InputMultiplexer multiplexer = new InputMultiplexer(inputProcessor, pause);
        Gdx.input.setInputProcessor(multiplexer);
		
		activeGame.run();
		
		//currentState.setObject(new DynamicObject(ObjectType.ENEMY, new Coord(1,1), 20, 10, 
		//		new Texture(Gdx.files.internal("SpriteFamily/enemy/sprite_091.png"))), new Coord(1,1));
		
		
	}

	@Override
	public void render(float delta) {
		
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
		batch.begin();
		for (int x = 0; x < currentState.getMapWidth(); x++) {
			for (int y = 0; y < currentState.getMapHeight(); y++) {
				Tile t = currentState.getTile(new Coord(x,y));
				
				if (t != null) {
					// little naive just grabbing the texture size
					
					batch.draw(floor, x * floor.getWidth(), y * floor.getHeight());
				if (t.getFloorTexture() != null)
					batch.draw(t.getFloorTexture(), x * t.getFloorTexture().getWidth(), y * t.getFloorTexture().getHeight());
				if (t.getObjectTexture() != null) 
					batch.draw(t.getObjectTexture(), x * t.getObjectTexture().getWidth(), y * t.getObjectTexture().getHeight());
				}
						
			}
		}
			
		batch.end();
		
		
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
		batch.dispose();
		activeGame.stop();
	}

}
