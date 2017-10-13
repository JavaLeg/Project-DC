package Interface.Stages;


import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.ZZZZZZZZZZ;
import com.engine.desktop.SaveSys;

import Interface.EditorModel;
import Interface.ImageID;
import Interface.PreviewCell;
import Interface.Stages.Selections.ToolbarSelection;
import State.Coordinates;
import Tileset.Player;

/*
 * Stage for the editor UI (Tools on the left of the screen)
 */
public class Editor extends Stage implements Serializable {
	
	private Skin skin;
	
	// ArrayList of tables
	private HashMap<ToolbarSelection, Table> tableMap;
	
	private TableTuple titlePos;
	private TableTuple tablePos;
	
	//private TerrainTable t;
	//private CreatureTable c;
	
	private HashMap<Enum<?>, Class<?>> classMap;
	
	//private <E> currTable;
	private ToolbarSelection current;
	
	//private Stage related;
	private Preview related;
	private String path;
	private SaveSys saver;
		
	/*
	 * Dimensions: 280 x 480
	 */
	public Editor(Viewport v, TextureAtlas atlas, Skin skin) throws IOException {
		super(v);
		// this.atlas = atlas;
		this.skin = skin;
		this.titlePos = new TableTuple(50, 450);		
		this.tablePos = new TableTuple(v.getScreenWidth()*7/40, v.getScreenHeight());
		this.path = "SpriteFamily/";
		this.tableMap = new HashMap<ToolbarSelection, Table>();
		this.saver = new SaveSys();
		//this.
		initialise();
		update(ToolbarSelection.TERRAIN);
	}
	
	private void initialise() {
		
		Table titleTable = new Table();	
        Label HUDlabel = new Label("Editor Mode", 
        		new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        titleTable.add(HUDlabel);
		titleTable.setPosition(titlePos.getX(), titlePos.getY(), 0);
		
		// Add background and title
		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/midwall_background_side.png")))));
		super.addActor(titleTable);
	}
	
	private void display(Table newTable) {
		newTable.setPosition(tablePos.getX(), tablePos.getY());
		newTable.top();

		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/midwall_background_side.png")))));
		super.addActor(newTable);
	}

	public void update(ToolbarSelection s) throws IOException {
		
		if (s == ToolbarSelection.SAVE) {
			Json js = new Json();

			EditorModel toSave = related.getModel();
			saver.Save(toSave, "editor_model_test.txt");
			
			
			/*
			System.out.println(js.toJson(save));
			if (related.checkValidMap() == true) {
				System.out.println("Game successfully saved");
			} else {
				System.out.println("Failed to save game, errors above");
			}
			return;
			*/
		}
		
		if(current == s) return;
		
		this.clear();
		
		// Check if table already exists
		// If so, pass that table to display fn instead of generating
		
		if(!tableMap.containsKey(s)) {
			Table newTable = generateTable(s);
			tableMap.put(s, newTable);
		}
		display(tableMap.get(s));
	}
	
	private TextButton generateButton(String s) {
		TextButton button = new TextButton(s, skin);
		return button;
	}
	
	private Table generateTable(final ToolbarSelection s) {
		
		Table newTable = new Table();
		
		/*
		 * Make a custom image icon class later
		 * Includes image and name
		 */
		
		FileHandle[] files = Gdx.files.internal(path + s.toString().toLowerCase()).list();
		
		Label title = new Label(s.toString(), 
        		new Label.LabelStyle(new BitmapFont(), Color.BLACK));

		newTable.add(title);
		newTable.row();
		int i = 0;
		
		// Custom buttons should go here
		// Hardcoded for now
		switch(s) {
		case TERRAIN:
			TextButton fillButton = generateButton("Fill");
			newTable.add(fillButton);
			fillButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					related.fillGrid();
		        }
			});
			
			TextButton clearButton = generateButton("Clear");
			newTable.add(clearButton);
			clearButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					related.clearGrid();
		        }
			});
			newTable.row();
		default:
			break;
		}
		
		
		
		for(FileHandle file: files) {

			final Texture t = new Texture(file);
			
			// related.setSelection(t, s);		// Set initial selection
			
			Image icon = new Image(new TextureRegion(t));
			final String fileName = file.name().split("\\.", 2)[0];
			Label icon_name = new Label(fileName, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
			newTable.add(icon).size(40, 40);
			newTable.add(icon_name).pad(5);
			
			icon.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					System.out.println("selected " + fileName);
					related.setSelection(t, s);
		        }
			});
			
			if (i % 2 == 1 && i != 0) newTable.row();
			if (i > 20) break;					// Don't let it go over the edge
			i++;
			
		}
		return newTable;
	}
	
	/*
	public void setDependence(Stage s) {
		this.related = s;
	}
	*/
	
	
	
	public void setDependence(Preview s) {
		this.related = s;
	}
}
