package Interface;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.engine.desktop.EditorController;

import Interface.Stages.Editor;
//import Interface.Stages.GridPreview;
import Interface.Stages.Preview;
import Interface.Stages.Toolbar;
import Interface.Viewports.PreviewViewport;
import Interface.Viewports.ToolbarViewport;
import State.DynamicGame;
import State.State;


public class EditorScreen implements Screen {
	
	public EditorController controller;
	public EditorModel model;
	protected ArrayList<Stage> UI;
	
    private SpriteBatch batch;
    private Game game;
        
    private int APP_WIDTH = Gdx.graphics.getWidth();
    private int APP_HEIGHT = Gdx.graphics.getHeight();
	
    public EditorScreen(Game game) throws IOException {
    	this.game = game;
    	this.controller = new EditorController();
    	//this.model = new EditorModel();
    }
    
	// Show only operates once, after it will render
	@Override
	public void show() {
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
		
		Camera editor_camera = new OrthographicCamera();
		Camera preview_camera = new OrthographicCamera();
		
		Camera toolbar_camera = new OrthographicCamera();
		
		// Every viewport initializes with (0, 0) at bottom left of the stage
		Viewport editor_viewport = new FitViewport(APP_WIDTH, APP_HEIGHT, editor_camera);
		Viewport preview_viewport = new PreviewViewport(APP_WIDTH, APP_HEIGHT, preview_camera);
		Viewport toolbar_viewport = new ToolbarViewport(APP_WIDTH, APP_HEIGHT, toolbar_camera);
		
		UI = new ArrayList<Stage>();
		HashMap<Stage, Stage> map = new HashMap<Stage, Stage>();
		
		Editor editorStage;
		Preview previewStage = new Preview(preview_viewport, 520, 480, 40, 40);
		Toolbar toolbarStage = new Toolbar(toolbar_viewport, atlas, skin);

		UI.add(previewStage);
		UI.add(toolbarStage);
		
		try {
			editorStage = new Editor(editor_viewport, atlas, skin);
			UI.add(editorStage);
			map.put(toolbarStage, editorStage);
			map.put(previewStage, editorStage);
			map.put(editorStage, previewStage);
			// previewStage.setDependence(editorStage);
			toolbarStage.setDependence(editorStage);
			editorStage.setDependence(previewStage);

			
			InputMultiplexer multiplexer = new InputMultiplexer(editorStage, previewStage, toolbarStage);
			Gdx.input.setInputProcessor(multiplexer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadModel(EditorModel m) {
		System.out.println("Loading model...");
		model = m;
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

	/*
	private void setDependencies(ArrayList<Stage> UI, HashMap<Stage, Stage> map) {
		
		for(Stage s: UI) {
			((Stage) s).setDependence((Stage)map.get(s));
		}
	}
	*/

}