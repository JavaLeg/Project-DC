package Interface.Stages;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Editor extends Stage{
	private TextureAtlas atlas;
	private Skin skin;
	
	public Editor(Viewport v, TextureAtlas atlas, Skin skin) {
		super(v);
		this.atlas = atlas;
		this.skin = skin;
		initialise();
	}
	
	private void initialise() {

		String[] temp;
		temp = new String[] {"Wall", "Ground", "Empty", "Fill ground", "Reset", "Exit"};
		
		// Hard-coded for now, can deal with it later when images replace these buttons
		final HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("Wall", 2);
		map.put("Ground", 1);
		map.put("Empty", 0);
		
		Table mainTable = new Table();
		
        Label HUDlabel = new Label("Editor Mode", 
        		new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        

        mainTable.add(HUDlabel);
		
		for (final String s : temp) {
			TextButton button = generateButton(s);
			
			button.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					System.out.println("Clicked: " + s);
		        }
			});
			mainTable.row();
			mainTable.add(button);
			
		}
		
		// Add this actor
		super.addActor(mainTable);
	}
	
	/*
	 * Temporary, will use images later
	 */
	private TextButton generateButton(String s) {
		TextButton button = new TextButton(s, skin);
		return button;
	}
}
