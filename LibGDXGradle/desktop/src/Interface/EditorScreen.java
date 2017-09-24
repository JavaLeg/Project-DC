package Interface;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
		
		homeButton = new TextButton("Home", skin);
        mainTable = new Table();
        mainTable.left();
        mainTable.add(homeButton);
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.

        
        homeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(null));
            }
        });       
        
        camera = new OrthographicCamera();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        
        
        
        viewport = new FitViewport(WORLD_HEIGHT, WORLD_WIDTH, camera);
        viewport.apply();
        stage = new Stage(viewport); 
        
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		
		sprites = new Array<Sprite>();
        batch = new SpriteBatch();
        touchPos = new Vector3();
        
        Texture cur_texture = new Texture("tmp.jpg");
        
        // Create grid system and put into sprites to be drawn
        for (int j = 0; j < width; j = j + 25) {
	        for (int i = 0; i < height; i = i + 20) {
	            Sprite cur_sprite = new Sprite(cur_texture);
	            cur_sprite.setSize(25, 20);
	            cur_sprite.setCenter(j, i);
	            sprites.add(cur_sprite);
	        }
        } 
        stage.addActor(mainTable);
	}

	
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        batch.begin();
        // Draw sprites and listen for inputs.
		for (Sprite cur_sprite : sprites) {
        	cur_sprite.draw(batch);
        }
		
		// X range is 160->635
		// Y range is 0->480
		if (Gdx.input.isTouched()) {
			// Can reset texture using sprite.setTexture(...);
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			int col  = (int) ((touchPos.x - 160) / 25);
			int row = (int) (touchPos.y / 20);
			System.out.println("Row = " + row + ", Col = " + col);
			
			//model.select(row, col);
		}
        batch.end();
        
        stage.act();
        stage.draw();
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
        texture.dispose();
		// TODO Auto-generated method stub
		
	}

}
