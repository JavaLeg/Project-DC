package Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD extends Stage{
	private Stage stage;
	private Viewport viewport;
	
	protected Skin skin;
	private TextureAtlas atlas;
	
	private Table mainTable;
	private TextButton exitButton;
	
    private static final int WORLD_WIDTH  = 400;
    private static final int WORLD_HEIGHT = 400;
	
	public HUD() {
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
		skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
		
		viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport);
		
		// Implement list of buttons later
		TextButton button = new TextButton("Hey nice", skin);
		exitButton = new TextButton("Exit", skin);
        Label HUDlabel = new Label("Editor Mode", 
        		new Label.LabelStyle(new BitmapFont(), Color.CYAN));
		
		//buttonHandler(button);
        
        
        mainTable = new Table();
        mainTable.left();
        
        mainTable.add(HUDlabel);
        mainTable.row();
        mainTable.add(button);
        mainTable.row();
        mainTable.add(exitButton);
        mainTable.setFillParent(true);
        
        stage.addActor(mainTable);
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public TextButton getExit() {
		return exitButton;
	}
	
	public void buttonHandler(TextButton button) {
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button Pressed!");
            }
        });
	}
}
