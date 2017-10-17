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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.SaveSys;

import Interface.EditorModel;
import Interface.Stages.Selections.ToolbarSelection;

import State.State;

/*
 * Stage for the editor UI (Tools on the left of the screen)
 */
public class Editor extends Stage{
	
	private Skin skin;
	private HashMap<ToolbarSelection, Table> tableMap;
	
	private TableTuple titlePos;
	private TableTuple tablePos;

	
	private HashMap<Enum<?>, Class<?>> classMap;
	

	private ToolbarSelection current;
	
	//private Stage related;
	private State related;
	private String path;
	private SaveSys saver;
		
	/*
	 * Dimensions: 280 x 480
	 * Stage takes in a viewport and a skin
	 */
	public Editor(Viewport v, Skin skin) throws IOException {
		super(v);
		this.skin = skin;
		this.titlePos = new TableTuple(50, 450);		
		this.tablePos = new TableTuple(v.getScreenWidth()*7/40, v.getScreenHeight());
		this.path = "SpriteFamily/";
		this.tableMap = new HashMap<ToolbarSelection, Table>();
		this.saver = new SaveSys();
		initialise();
		update(ToolbarSelection.FLOOR);
	}
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DISPLAYS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/*
	 * Initialise stage contents (Tables, Titles, Background etc...)
	 */
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
	
	/*
	 * Display a table on the stage
	 */
	private void display(Table newTable) {
		newTable.setPosition(tablePos.getX(), tablePos.getY());
		newTable.top();

		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/midwall_background_side.png")))));
		super.addActor(newTable);
	}

	
	
	
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GENERATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private TextButton generateButton(String s) {
		TextButton button = new TextButton(s, skin);
		return button;
	}
	
	
	/*
	 * Generates a table according to the structure of SpriteFamily/* directory
	 * Early returns (Like in case SAVE) can be made for custom tables
	 */
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
		// Hard-coded for now
		switch(s) {
		case FLOOR:
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
			break;
			
		case SAVE:
			final TextField textField = new TextField("", skin);
			textField.setMessageText("Save as...");
			newTable.add(textField);			
			TextButton saveButton = generateButton("Save");
			newTable.add(saveButton);
			saveButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					try {
						saveMap(textField.getText());
					} catch (IOException e) {
						System.out.println("Cannot save map");
						e.printStackTrace();
					}
		        }
			});
			return newTable;
		default:
			break;
		}
		
		for(FileHandle file: files) {

			String fileName = file.name().split("\\.", 2)[0];
			final Texture t = new Texture(file);			
			Image icon = new Image(new TextureRegion(t));
			
			if (s == ToolbarSelection.ENEMY) {
				fileName = "Health: 1\nDamage: 1\nSpeed: 1";
			} else if (s == ToolbarSelection.PLAYER) { 
				fileName = "Health: 1\nDamage: 1\nSpeed: 1";
			}
			Label icon_name = new Label(fileName, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
			
			
			newTable.add(icon).size(40, 40);
			newTable.add(icon_name).pad(5);
			
			icon.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					// System.out.println("selected " + fileName);
					related.setSelection(t, s);
		        }
			});
			
			if (i % 2 == 1 && i != 0) newTable.row();
			
			// Don't let it go over the edge
			if (i > 20 && s == ToolbarSelection.FLOOR) break;
			if (i > 14 && s == ToolbarSelection.ENEMY) break;
			i++;
			
		}
		return newTable;
	}
	
	
	
	
	
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INTERNALS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/*
	 * Updates the stage according to toolbar selection
	 */
	public void update(ToolbarSelection s) throws IOException {
				
		if(current == s) return;
		this.clear();
		
		// Check if table already exists
		// If so, pass that table to display of instead of generating
		if(!tableMap.containsKey(s)) {
			Table newTable = generateTable(s);
			tableMap.put(s, newTable);
		}
		display(tableMap.get(s));
	}
	
	
	/*
	 * Private method for saving
	 * Takes in custom save filename
	 * Spaces replaced with underscore and .txt appended
	 * 
	 */
	private void saveMap(String s) throws IOException {
		
		if(s.isEmpty()) {
			System.out.println("Blank Save String!");
			System.out.println("Not saved.");
			return;
		}
		
		s = s.replaceAll(" ","_");
		EditorModel toSave = related.getModel();
		s += ".txt";
		saver.Save(toSave, s);
		System.out.println("Saved as file: " + s);
	}
	
	
	
	/*
	public void setDependence(Stage s) {
		this.related = s;
	}
	*/
	
	public void setDependence(State s) {
		this.related = s;
	}
}
