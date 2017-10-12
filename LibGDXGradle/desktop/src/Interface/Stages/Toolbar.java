package Interface.Stages;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import Interface.Stages.Selections.ToolbarSelection;

public class Toolbar extends Stage{
	private TextureAtlas atlas;
	private Skin skin;
	private Editor related;
	private TableTuple toolbarPos;
	private final int PAD = 5;

	/*
	 * Dimensions: 280 x 40
	 */
	public Toolbar(Viewport v, TextureAtlas atlas, Skin skin) {
		super(v);
		this.atlas = atlas;
		this.skin = skin;
		this.toolbarPos = new TableTuple(v.getScreenX()*3/4, 20);
		initialise();
	}
	
	private void initialise() {		
		Table mainTable = new Table();
		int pad = 0;
		
		for(final ToolbarSelection selection: ToolbarSelection.values()) {
			TextButton button = generateButton(selection.name());
			mainTable.add(button).pad(PAD);	
			pad += PAD;
			button.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					related.update(selection);

		        }
			});
		}
		mainTable.setPosition(toolbarPos.getX() + pad, toolbarPos.getY(), 0);
		
		// Add this actor
		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/tb_bg.png")))));
		super.addActor(mainTable);
	}
	
	private TextButton generateButton(String s) {
		TextButton button = new TextButton(s, skin);
		return button;
	}
	
	public void setDependence(Stage s) {
		this.related = s;
	}
	
	public void setDependence(Editor s) {
		this.related = s;
	}
}
