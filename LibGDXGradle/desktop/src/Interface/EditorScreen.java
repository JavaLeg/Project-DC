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
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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
    
    private int[][] status;
    
    private Grid grid;
    private DCGame game;
	private TextButton exitButton;
    
	private Texture ground_texture;
	private Texture wall_texture;
	private Texture empty_texture;
	private int current_click = 1;
	
	private Array<ImageID> draw;
	
    public EditorScreen(DCGame g) {
    	this.game = g;
    }
    
    /*public void setModel(EditorModel m) {
    	this.model = m;
    }*/

	// Show only operates once, after it will render
	@Override
	public void show() {
        
		grid = new Grid(40, 40, 480, 480, "tmp.png");
		draw = grid.getGrid();								// Creates the grid
			
		ground_texture = new Texture(Gdx.files.internal("ground.jpg"));
		wall_texture = new Texture(Gdx.files.internal("wall.jpg"));
		empty_texture = new Texture(Gdx.files.internal("empty.png"));
		
        camera = new OrthographicCamera();
        camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);
        camera.update();
        
        InputMultiplexer multiplexer = new InputMultiplexer();
        
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
        
		// Configure the RHS of screen (grid preview)
        viewport_right = new ScreenViewport(camera);
        viewport_right.setScreenX(200);					// Sets viewport's position
        viewport_right.update(400, 300, false);			// Updates the right pos and sets size
        stage_right = new Stage(viewport_right); 
        
        /*
        viewport_left = new ScreenViewport(camera);
        viewport_left.setScreenPosition(100, 100);
        viewport_left.update(200, 200, true);
        stage_left = new Stage(viewport_left);        
        */
        
        touchPos = new Vector3();
         
        ////////////////////////////////
        // MAINTABLE creation started //
        ////////////////////////////////
        
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
        
		TextButton wallButton = new TextButton("Wall", skin);
		TextButton groundButton = new TextButton("Ground", skin);
		TextButton emptyButton = new TextButton("Empty", skin);
		TextButton setGround = new TextButton("Fill ground", skin);
		TextButton reset = new TextButton("Reset", skin);
		
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
        
        mainTable.row();
        mainTable.add(wallButton);
        wallButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	current_click = 2;
            }
        });
        mainTable.row();
        mainTable.add(groundButton);
        groundButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	current_click = 1;
            }
        }); 
        
        mainTable.row();
        mainTable.add(emptyButton);
        emptyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                current_click = 0;
            }
        }); 
        
        mainTable.row();
        mainTable.add(setGround);
        setGround.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                grid.setValue(1, ground_texture);
                // For James
            }
        }); 
        
        mainTable.row();
        mainTable.add(reset);
        reset.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	grid.setValue(0, empty_texture);
            	// For James
            }
        }); 
        
        /////////////////////////////////
        // MAINTABLE creation finished //
        /////////////////////////////////
        
        Gdx.input.setInputProcessor(multiplexer);
        // multiplexer.addProcessor(stage_left);
        multiplexer.addProcessor(stage_right);
        mainTable.setFillParent(true);
        // mainTable.padRight(100);
        
        // Creates the grid of images
        
        for (final ImageID cur: draw) {
            cur.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        // Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                     
                        cur.setStatus(current_click);
                        System.out.println("Pos = (" + cur.getRow() + ", " + cur.getColumn() 
                        + "), status = " + cur.getStatus());
                        
                        // For James
                        if (cur.getStatus() == 0) {		// If currently empty, make it a ground
                        	cur.setDrawable(new SpriteDrawable(new Sprite(empty_texture)));
                        } else if (cur.getStatus() == 1) {		// If currently ground, make wall
                        	cur.setDrawable(new SpriteDrawable(new Sprite(ground_texture)));
                        } else if (cur.getStatus() == 2) {
                        	cur.setDrawable(new SpriteDrawable(new Sprite(wall_texture)));
                        }                       
                        
                        return false;
                }
            });
            stage_right.addActor(cur);
        }  
        mainTable.setPosition(-60, -40, 0);			// Set position
        stage_right.addActor(mainTable);
	}

	
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw both stage right and stage left
        // If window resize (update TODO)
        stage_right.getViewport().update(500, 800, true);		// First value is X from right of screen
        viewport_right.update(800, 800, false);					// Second is Y from top of screen
        
        //stage_left.getViewport().update(300, 1000, true);
        //viewport_left.update(200, 200, false);			// Updates the right pos and sets size
        
        stage_right.act();
        stage_right.draw();
        
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
