package Interface.Screens;

import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.DCGame;

public class HelpScreen implements Screen {

	protected Stage stage;
	private Viewport viewport;
	private OrthographicCamera camera;
	private TextureAtlas atlas;
	protected Skin skin;
	final DCGame game;
	    
	private static final int WORLD_WIDTH  = 800;
	private static final int WORLD_HEIGHT = 450;
	
    public HelpScreen(DCGame game) throws IOException {
    	this.game = game;
    	atlas = new TextureAtlas(Gdx.files.internal("cloud-form-ui.atlas"));
    	skin = new Skin(Gdx.files.internal("cloud-form-ui.json"), atlas);
    	camera = new OrthographicCamera();
    	viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT,  camera);
    	viewport.apply();
    	stage = new Stage(viewport);
    }
	
	@Override
	public void show() {
		// Main Table
		Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setPosition(0, WORLD_HEIGHT/6);
             
        
        // FONTS      
        BitmapFont itemFont = new BitmapFont();
        itemFont.getData().setScale(1, 1);
        
        
        // Title
        Image titleImage = new Image(new TextureRegion(new Texture(Gdx.files.internal("HelpScreen/helpheader.png"))));
        mainTable.add(titleImage);               
        
        Label blank = new Label("", new Label.LabelStyle(itemFont, Color.BLACK));
        mainTable.row();
        mainTable.add(blank);
        
        
    	final Label fileLabel3 = new Label("Find it at:", new LabelStyle(itemFont, Color.BLACK));
        mainTable.row();
    	mainTable.add(fileLabel3);
    	
        final String fileName2 = "https://github.com/JavaLeg/Project-DC";
    	final Label fileLabel2 = new Label(fileName2, new LabelStyle(itemFont, Color.BLACK));
        mainTable.row();
    	mainTable.add(fileLabel2);
    	
        
        stage.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("HelpScreen/helpbg.jpg")))));
        stage.addActor(mainTable);
        
        
        // Add back button
        Table backTable = new Table();
        TextButton backButton = generateButton("Back");
        backButton.addListener(new ClickListener(){
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				stage.dispose();
            	((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
			}
		});
        backTable.add(backButton);
        backTable.bottom();
        backTable.left();
        backTable.padLeft(10);
        backTable.padBottom(10);        
        stage.addActor(backTable);
        
        
        // ESC key to return to main menu
		InputProcessor backProcessor = new InputAdapter() {
	         @Override
	         public boolean keyDown(int keycode) {
	
	             if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) {
	             	stage.dispose();
	             	((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
	             }
	             return false;
	         }
	     };
	     
		//Stage should control input:
		InputMultiplexer multiplexer = new InputMultiplexer(stage, backProcessor);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
		
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
		skin.dispose();
		atlas.dispose();
	}
	
	
	private TextButton generateButton(String s) {
		String newString = " " + s + " ";
		TextButton button = new TextButton(newString, skin);
		return button;
	}

}
