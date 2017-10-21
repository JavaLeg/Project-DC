package Interface.Screens;

import java.awt.DisplayMode;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
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
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.DCGame;

public class OptionsScreen implements Screen {

	private TextureAtlas atlas;
    protected Skin skin;
    private DCGame game;
    private Viewport viewport;
    private Camera camera;
    private Stage mainStage;
	
    public OptionsScreen(DCGame game) throws IOException {
    	this.game = game;
    }
	
	@Override
	public void show() {
		atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);

        camera = new OrthographicCamera();
        viewport = new ScreenViewport();
        mainStage = new Stage(viewport);
		
        
        //Create Table
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        
        // Main Table
        // FONTS
        BitmapFont titleFont = new BitmapFont();
        titleFont.getData().setScale(4, 4);
        
        BitmapFont itemFont = new BitmapFont();
        itemFont.getData().setScale(2, 2);
        
        //Set alignment of contents in the table.
        Label title = new Label("Options", 
        		new Label.LabelStyle(titleFont, Color.WHITE));
        
        mainTable.top();
        mainTable.add(title);
        
        
        /* Add things to page here */	
        Label displayMode = new Label("Display mode:", 
        		new Label.LabelStyle(itemFont, Color.WHITE));
       mainTable.row();
       mainTable.add(displayMode);
       
       if(Gdx.graphics.isFullscreen()) {
    	   TextButton windowedButton = generateButton("Windowed");
    	   windowedButton.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
            	   Gdx.graphics.setWindowedMode(1600, 960);
            	   // Refresh screen
            	   try {
	   					((Game)Gdx.app.getApplicationListener()).setScreen(new OptionsScreen(game));
	   				} catch (IOException e) {
	   					e.printStackTrace();
	   				}
               }
           });
           mainTable.row();
           mainTable.add(windowedButton);
       } else {
    	   TextButton fullscreenButton = generateButton("Fullscreen");
           fullscreenButton.addListener(new ClickListener(){
               @Override
               public void clicked(InputEvent event, float x, float y) {
            	   Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            	   // Refresh screen
            	   try {
	   					((Game)Gdx.app.getApplicationListener()).setScreen(new OptionsScreen(game));
	   				} catch (IOException e) {
	   					e.printStackTrace();
	   				}
               }
           });
           mainTable.row();
           mainTable.add(fullscreenButton);
       }
       
      
       
       // TextButton button = generateButton("Windowed");
        SelectBox<String> test = new SelectBox(skin);
        String[] selection = new String[1];
        String Res1 = "1355x768";
        selection[0] = Res1;
       
        
        test.setItems(selection);
        mainTable.row();
        mainTable.add(test);
        /*
        
		config.width = 800;
		config.height = 480;
		//config.fullscreen = true;
		config.title = "Project-DC";
		new LwjglApplication(new DCGame(), config);
		*/
        
    	
        
        mainStage.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("LibScreen/bg2.jpg")))));
        mainStage.addActor(mainTable);
        
        
        // Add back button
        Table backTable = new Table();
        TextButton backButton = generateButton("Back");
        backButton.addListener(new ClickListener(){
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				mainStage.dispose();
            	((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
			}
		});
        backTable.add(backButton);
        backTable.bottom();
        backTable.left();
        backTable.padLeft(10);
        backTable.padBottom(10);        
        mainStage.addActor(backTable);
        
        
        // ESC key to return to main menu
		InputProcessor backProcessor = new InputAdapter() {
	         @Override
	         public boolean keyDown(int keycode) {
	
	             if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) {
	             	mainStage.dispose();
	             	((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
	             }
	             return false;
	         }
	     };
	     
		//Stage should control input:
		InputMultiplexer multiplexer = new InputMultiplexer(mainStage, backProcessor);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.act();
        mainStage.draw();
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
