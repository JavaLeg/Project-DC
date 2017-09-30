package Interface;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.DCGame;

public class EditorScreen implements Screen {
	
    private SpriteBatch batch;
    
    protected Stage stage_left;
    protected Stage stage_right;
    private Viewport viewport_left;		// Holds the menu buttons
    private Viewport viewport_right;	// Holds the grid preview
    
    private OrthographicCamera camera;
    private int height;			
    private int width;
    private Table mainTable;
    public Vector3 touchPos;			
 
    private static final int WORLD_WIDTH  = 250;
    private static final int WORLD_HEIGHT = 250;
    
    private Grid grid;
    private DCGame game;
	private TextButton exitButton;
    
    public EditorScreen(DCGame g) {
    	this.game = g;
    }
    
    /*public void setModel(EditorModel m) {
    	this.model = m;
    }*/

	// Show only operates once, after it will render
	@Override
	public void show() {
        
        camera = new OrthographicCamera();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        
        InputMultiplexer multiplexer = new InputMultiplexer();
        
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
        
		// Configure the RHS of screen (grid preview)
        viewport_right = new ScreenViewport(camera);
        viewport_right.setScreenX(400);		// Sets viewport's position
        viewport_right.update(720, 0, false);			// Updates the right pos and sets size
        stage_right = new Stage(viewport_right); 
        
        viewport_left = new ScreenViewport(camera);
        viewport_left.setScreenPosition(100, 100);
        viewport_left.update(200, 200, true);
        stage_left = new Stage(viewport_left);        
        
        touchPos = new Vector3();
         
        // Creating the table
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
        
		TextButton wallButton = new TextButton("Wall", skin);
		TextButton groundButton = new TextButton("Ground", skin);
		exitButton = new TextButton("Exit", skin);
        Label HUDlabel = new Label("Editor Mode", 
        		new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        mainTable = new Table();
        mainTable.left();
        
        mainTable.add(HUDlabel);
        mainTable.row();
        mainTable.add(exitButton);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("EXIT!");
            }
        });
        
        mainTable.row();;
        mainTable.add(wallButton);
        wallButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Wall!");
            }
        });
        mainTable.row();
        mainTable.add(groundButton);
        groundButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Ground!");
            }
        }); 
        
        // Finish creating the table
        Gdx.input.setInputProcessor(multiplexer);
        multiplexer.addProcessor(stage_left);
        multiplexer.addProcessor(stage_right);
        mainTable.setFillParent(true);
        mainTable.padRight(100);
        stage_right.addActor(mainTable);
        
        // Creates the grid of images
        grid = new Grid(40, 40, 480, 480, "tmp.png");
        Array<Image> to_draw = grid.getGrid();
        
        for (Image cur: to_draw) {
        	stage_right.addActor(cur);
        }      
	}

	
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw both stage right and stage left
        // If window resize (update TODO)
        stage_right.getViewport().update(300, 800, true);		// First value is X from right of screen
        viewport_right.update(800, 800, false);					// Second is Y from top of screen
        
        //stage_left.getViewport().update(300, 1000, true);
        //viewport_left.update(200, 200, false);			// Updates the right pos and sets size
        
        stage_right.act();
        stage_right.draw();
        
        //stage_left.act();
        //stage_left.draw();
        
        // This will become redundant once we add listeners to each Image
        /*
		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			System.out.println("[X = " + 
			touchPos.x + ", Y = " + touchPos.y + "]");
		}
		*/
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
		// TODO Auto-generated method stub	
	}

}
