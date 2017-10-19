package Interface.Screens;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.omg.PortableServer.POAManagerPackage.State;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.DCGame;
//import com.engine.desktop.EditorController;
import com.engine.desktop.SaveSys;

import Interface.Stages.Selections.LibrarySelection;
import Interface.Stages.Selections.ToolbarSelection;
import State.*;

public class LibraryScreen implements Screen{
	
    private TextureAtlas atlas;
    protected Skin skin;
    private Game game;
    private SaveSys fileHandle;
    
    private Viewport viewport;
    private Camera camera;
    private Stage mainStage;
    
    
    private LibrarySelection selection;
    private String selected_map;
    //private ArrayList<Stage> UI;
    
    private static final int WORLD_WIDTH  = 800;
    private static final int WORLD_HEIGHT = 480;

    public LibraryScreen(Game game) throws IOException {
    	this.game = game;
    	this.fileHandle = new SaveSys();
    }
    
	@Override
	public void show() {
				
        atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);

        camera = new OrthographicCamera();
        viewport = new ScreenViewport();
        mainStage = new Stage(viewport);
        
        //Camera sideCamera = new OrthographicCamera();
        //Viewport sideViewport = new ScreenViewport();
        //Stage sideStage = new Stage(sideViewport);
 
        //Create Table
        Table mainTable = new Table();
        Table sideTable = new Table();
        
        
        // SIDE TABLE
        // Edit / Run functionality
		for(final LibrarySelection selection: LibrarySelection.values()) {
			TextButton button = generateButton(selection.name());
			sideTable.add(button);
			button.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					Screen s = null;
						try {
							switch(selection) {
							case EDIT:
								s = new EditorScreen(game);
								System.out.println("Loading in editor " + selected_map + "...");
								((Game)Gdx.app.getApplicationListener()).setScreen(s);
								((EditorScreen) s).loadModel(fileHandle.Load(selected_map));
								break;
							case RUN:
								s = new GameScreen(game);
								System.out.println("Loading in editor " + selected_map + "...");
								((Game)Gdx.app.getApplicationListener()).setScreen(s);
								((GameScreen) s).loadModel(fileHandle.Load(selected_map));
								break;
							default:
								break;	
							}
						} catch (IOException e) {
							System.out.println("Cannot load map");
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							System.out.println("No EditorModel class!");
							e.printStackTrace();
						}
		        }
			});
		}
        
        //Set table to fill stage
        sideTable.setFillParent(true);
        mainTable.setFillParent(true);
       
        
        // Main Table
        // FONTS
        BitmapFont titleFont = new BitmapFont();
        titleFont.getData().setScale(4, 4);
        
        BitmapFont itemFont = new BitmapFont();
        itemFont.getData().setScale(2, 2);
        
        //Set alignment of contents in the table.
        Label title = new Label("Library", 
        		new Label.LabelStyle(titleFont, Color.WHITE));
        
        mainTable.top();
        mainTable.add(title);
        

        
        File[] list = fileHandle.getLibrary();
        
        for (final File f : list) {
        	final String fileName = f.getName().split("\\.", 2)[0];
        	final Label fileLabel = new Label(fileName, new LabelStyle(itemFont, Color.WHITE));
        	fileLabel.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                	selected_map =  fileName;     
                	System.out.println("Selected map - " + selected_map);
                }
        	});	
        	mainTable.row();
        	mainTable.add(fileLabel);
        }
        mainStage.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("LibScreen/bg2.jpg")))));
        mainStage.addActor(mainTable);
        mainStage.addActor(sideTable);
        
        
        
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
		TextButton button = new TextButton(s, skin);
		return button;
	}

}
