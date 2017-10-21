package Interface.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.DCGame;

import Interface.EditorModel;
import Interface.Stages.Editor;
import Interface.Stages.Toolbar;
import Interface.Viewports.EditorViewport;
import Interface.Viewports.PreviewProcessor;
import Interface.Viewports.PreviewViewport;
import Interface.Viewports.ToolbarViewport;
import State.State;


public class EditorScreen implements Screen {
	
    protected ArrayList<Stage> UI;
	
    private SpriteBatch batch;
    private DCGame game;
        
    private int APP_WIDTH = Gdx.graphics.getWidth();
    private int APP_HEIGHT = Gdx.graphics.getHeight();
    
    // Quick reference
    State previewStage = null;
	
    public EditorScreen(DCGame game) throws IOException {
    	this.game = game;
    }
    
	// Show only operates once, after it will render
	@Override
	public void show() {
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);

		// Every viewport initializes with (0, 0) at bottom left of the stage
		Viewport editor_viewport = new EditorViewport(APP_WIDTH*7/40, APP_HEIGHT);
		Viewport preview_viewport = new PreviewViewport(APP_WIDTH, APP_HEIGHT);
		Viewport toolbar_viewport = new ToolbarViewport(APP_WIDTH, APP_HEIGHT);
		
		UI = new ArrayList<Stage>();
		

		Editor editorStage;
		try {
			editorStage = new Editor(editor_viewport, skin);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		previewStage = new State(preview_viewport);
		Toolbar toolbarStage = new Toolbar(toolbar_viewport, skin);
		
		try {
			editorStage = new Editor(editor_viewport, skin);
			UI.add(previewStage);
			UI.add(editorStage);

			UI.add(toolbarStage);
			toolbarStage.setDependence(editorStage);
			editorStage.setDependence(previewStage);
			
			// ESC key to return to main menu
			InputProcessor backProcessor = new InputAdapter() {
	            @Override
	            public boolean keyDown(int keycode) {

	                if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK)) {
	                	for(Stage s : UI) {
	                		s.dispose();
	                	}
	                	((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
	                }
	                return false;
	            }
	        };
	        
	        
			PreviewProcessor pp = new PreviewProcessor(preview_viewport.getCamera());


			
			InputMultiplexer multiplexer = new InputMultiplexer(editorStage, toolbarStage, previewStage, backProcessor, pp);
			//InputMultiplexer multiplexer = new InputMultiplexer(editorStage, previewStage, toolbarStage, backProcessor, pp);
			Gdx.input.setInputProcessor(multiplexer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadModel(EditorModel m) {
		System.out.println("Loaded model.");	
		previewStage.restoreModel(m);
	}
	
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for(Stage s: UI) {
        	s.getViewport().apply();
        	s.act();
        	s.draw();
        }
	}

	@Override
	public void resize(int width, int height) {
		for(Stage s: UI) {
			s.getViewport().update(width, height, true);
		}
		
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
