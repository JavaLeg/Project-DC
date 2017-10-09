package Interface;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.io.IOException;
import java.util.ArrayList;

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
import Interface.Stages.Preview;
import Interface.Stages.PreviewViewport;
import State.DynamicGame;
import State.State;


public class EditorScreen implements Screen {
	
	protected EditorController controller;
	protected EditorModel model;
	protected ArrayList<Stage> UI;
	
    private SpriteBatch batch;
    private Game game;
    
    private Editor editorStage;
    private Preview previewStage;
    
    private int APP_WIDTH = Gdx.graphics.getWidth();
    private int APP_HEIGHT = Gdx.graphics.getHeight();
	
    public EditorScreen(Game game) throws IOException {
    	this.game = game;
    	this.controller = new EditorController();
    	this.model = new EditorModel();
    }
    
    /*public EditorController getController() {
    	return controller;
    }*/

	// Show only operates once, after it will render
	@Override
	public void show() {
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
		
		Camera editor_camera = new OrthographicCamera();
		Camera preview_camera = new OrthographicCamera();

		Viewport editor_viewport = new FitViewport(APP_WIDTH, APP_HEIGHT, editor_camera);
		Viewport preview_viewport = new PreviewViewport(APP_WIDTH, APP_HEIGHT, preview_camera);
		
		editorStage = new Editor(editor_viewport, atlas, skin);
		previewStage = new Preview(preview_viewport, atlas, skin);
				
		//UI.add(editorStage);
		//UI.add(previewStage);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(editorStage);
		multiplexer.addProcessor(previewStage);

		
	}
	
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        editorStage.getViewport().apply();
        editorStage.act();
        editorStage.draw();
        
        previewStage.getViewport().apply();
        previewStage.act();
        previewStage.draw();
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