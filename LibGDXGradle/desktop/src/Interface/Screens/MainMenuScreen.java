package Interface.Screens;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.engine.desktop.DCGame;

public class MainMenuScreen implements Screen {

    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    final DCGame game;
    
    private static final int WORLD_WIDTH  = 800;
    private static final int WORLD_HEIGHT = 480;
	
    public MainMenuScreen(final DCGame game)
    {
    	this.game = game;
    	
        atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT,  camera);
        viewport.apply();

//        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
//        camera.update();

        stage = new Stage(viewport);    
    }
	
	@Override
	public void show() {
		//Stage should control input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        //mainTable.top();
        
        // Place background
        stage.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("LibScreen/bg_resize2.jpg")))));
        
        
        /*
         * Create table object
         * Add buttons to table
         * Add table to stage
         */
        //Create buttons
        TextButton editorButton = new TextButton(" Editor ", skin);
        TextButton LibButton = new TextButton(" Library ", skin);
        TextButton helpButton = new TextButton(" Help ", skin);
        TextButton optionsButton = new TextButton(" Options ", skin);
        TextButton exitButton = new TextButton(" Exit ", skin);

        //Add listeners to buttons        
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        
        editorButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	try {
					((Game)Gdx.app.getApplicationListener()).setScreen(new EditorScreen(game));
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });

        LibButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	try {
					((Game)Gdx.app.getApplicationListener()).setScreen(new LibraryScreen(game));
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        helpButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	try {
					((Game)Gdx.app.getApplicationListener()).setScreen(new HelpScreen(game));
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	try {
					((Game)Gdx.app.getApplicationListener()).setScreen(new OptionsScreen(game, 1));
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });

        
        // Main Table
        // Add title
        BitmapFont titleFont = new BitmapFont();
        titleFont.getData().setScale(2, 2);        
        Label title = new Label("Dungeon Creator", 
        		new Label.LabelStyle(titleFont, Color.WHITE));
        mainTable.add(title);
        
        // Add blank line after title
        BitmapFont itemFont = new BitmapFont();
        itemFont.getData().setScale(1, 1);  
        mainTable.row();
        Label blank = new Label("", new Label.LabelStyle(itemFont, Color.WHITE));
        mainTable.add(blank);
        
        //Add buttons to table
        mainTable.row();
        mainTable.add(editorButton);
        mainTable.row();
        mainTable.add(LibButton);
        mainTable.row();
        mainTable.add(helpButton);
        mainTable.row();
        mainTable.add(optionsButton);
        mainTable.setPosition(0, WORLD_HEIGHT/6);
        
        //Add table to stage
        stage.addActor(mainTable);
        
        // Add exit button on bottom right
        Table backTable = new Table();
        backTable.add(exitButton);		
        backTable.bottom();
        backTable.left();
        backTable.padBottom(10);
        backTable.padLeft(10);
        
        stage.addActor(backTable);
        
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
	
}
