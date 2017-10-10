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

public class Toolbar extends Stage{
	private TextureAtlas atlas;
	private Skin skin;

	/*
	 * Dimensions: 280 x 40
	 */
	public Toolbar(Viewport v, TextureAtlas atlas, Skin skin) {
		super(v);
		this.atlas = atlas;
		this.skin = skin;
		initialise();
	}
	
	private void initialise() {
		String[] temp;
		temp = new String[] {"Map", "Creatures", "Terrain"};
		
		// Hard-coded for now, can deal with it later when images replace these buttons
		final HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		Table mainTable = new Table();
		
		for (final String s : temp) {
			TextButton button = generateButton(s);
			mainTable.add(button);	
			button.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					System.out.println("Clicked: " + s);
		        }
			});
			
		}		
		mainTable.setPosition(100, 20, 0);
		
		// Add this actor
		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/tb_bg.png")))));
		super.addActor(mainTable);
	}
	
	private TextButton generateButton(String s) {
		TextButton button = new TextButton(s, skin);
		return button;
	}
}
