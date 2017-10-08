package Interface;

import java.io.File;
import java.io.IOException;

import org.omg.PortableServer.POAManagerPackage.State;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.DCGame;
import com.engine.desktop.EditorController;
import com.engine.desktop.SaveSys;
import State.*;

public class LibraryScreen implements Screen{
	
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    private String dirPath;
    private DCGame game;
    private SaveSys fileHandle;
    
    private static final int WORLD_WIDTH  = 250;
    private static final int WORLD_HEIGHT = 250;

    public LibraryScreen(DCGame g) throws IOException {
    	this.game = g;
    	this.fileHandle = new SaveSys();
    }
    
	@Override
	public void show() {
				
        atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_HEIGHT, WORLD_WIDTH, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport);  
        
        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        
        Label title = new Label("Library", 
        		new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        
        mainTable.top();
        mainTable.add(title);
        

        
        File[] list = fileHandle.getLibrary();
        
        for (File f : list) {
        	final Label fileLabel = new Label(f.getName(), new LabelStyle(new BitmapFont(), Color.WHITE));
        	//final TextButton fileLabel = new TextButton(f.getName(), skin);
        	fileLabel.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                	try {
                		EditorScreen es = new EditorScreen(game);
                		es.getController().loadMap(fileLabel.getText().toString());
                		
                		System.out.println("Loaded " + fileLabel.getText().toString());
    					((Game)Gdx.app.getApplicationListener()).setScreen(es);
    					
    				} catch (IOException e) {
    					System.out.println("Error: could not load file " + fileLabel.getText().toString());
    					e.printStackTrace();
    				} catch (ClassNotFoundException e) {
    					System.out.println("Error: could not find file " + fileLabel.getText().toString());
						e.printStackTrace();
					}
                }
        	});
        	
        	mainTable.row();
        	mainTable.add(fileLabel);
        }
        	
        stage.addActor(mainTable);
		//Stage should control input:
        Gdx.input.setInputProcessor(stage);
				
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
		// TODO Auto-generated method stub
		
	}

}
