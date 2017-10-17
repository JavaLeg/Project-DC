package Interface.Stages;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.SaveSys;

import Interface.EditorModel;
import Interface.Stages.Selections.ToolbarSelection;
import State.State;
import Tileset.DynamicObject;
import Tileset.GameObject;
import Tileset.GameObject.ObjectType;

/*
 * Stage for the editor UI (Tools on the left of the screen)
 */
public class Editor extends Stage{
	
	private Skin skin;
	private HashMap<ToolbarSelection, Table> tableMap;
	
	private TableTuple titlePos;
	private TableTuple tablePos;

	
	private HashMap<Enum<?>, Class<?>> classMap;
	private HashMap<ToolbarSelection, String[]> customButtonMap;
	
	private DynamicObject selectedObject;
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
		this.customButtonMap = new HashMap<ToolbarSelection, String[]>();
		this.saver = new SaveSys();
		//formatButtons();
		initialise();
		update(ToolbarSelection.FLOOR);
	}
	
	private void formatButtons() {
		
		String[] playerList = {"Edit"};
		String[] mapList = {"Save", "Refresh", "Clear"};
		String[] enemyList = {"Edit"};
		String[] itemList = {"Edit"};
		
		customButtonMap.put(ToolbarSelection.PLAYER, playerList);
		customButtonMap.put(ToolbarSelection.MAP, mapList);
		customButtonMap.put(ToolbarSelection.ENEMY, enemyList);
		customButtonMap.put(ToolbarSelection.ITEM, itemList);
		
		/*
		HashMap<String, TextButton> stringMap = new HashMap<String, TextButton>();
		
		TextButton saveButton = generateButton("Save Map");		
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
		*/
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
	
	private TextField generateTextField(String s) {
		TextField tf = new TextField("", skin);
		tf.setMessageText(s);
		return tf;
	}
		
	/*
	 * Generates a table according to the structure of SpriteFamily/* directory
	 * Early returns (Like in case SAVE) can be made for custom tables
	 */
	private Table generateTable(final ToolbarSelection s) {
		
		final Table newTable = new Table();
		
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
		
		// If editor tab contains other stuff
		// Hard coded for now

		TextButton editButton = null;
		TextButton customButton = null;
		
		switch(s) {
		case PLAYER:
			editButton = generateButton("Edit");
			newTable.add(editButton);
			editButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					related.newEdit(selectedObject, skin);
		        }
			});
			
//			if(t == TableType.DEFAULT) {
//				customButton = generateButton("Custom");
//				newTable.add(customButton);
//				customButton.addListener(new ClickListener(){
//					@Override
//			        public void clicked(InputEvent event, float x, float y) {
//						generateTable(s, TableType.CUSTOM);
//			        }
//				});
//			}else {
//				customButton = generateButton("DEFAULT");
//				newTable.add(customButton);
//				customButton.addListener(new ClickListener(){
//					@Override
//			        public void clicked(InputEvent event, float x, float y) {
//						generateTable(s, TableType.DEFAULT);
//			        }
//				});
//			}

			customButton = generateButton("Custom");
			newTable.add(customButton);
			customButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					System.out.println("CLICKED!");
					newTable.setVisible(false);
		        }
			});
			break;
		case ENEMY:
			editButton = generateButton("Edit");
			newTable.add(editButton);
			editButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					System.out.println("CLICKED!");
					related.newEdit(selectedObject, skin);
		        }
			});
			
//			if(t == TableType.DEFAULT) {
//				customButton = generateButton("Custom");
//				newTable.add(customButton);
//				customButton.addListener(new ClickListener(){
//					@Override
//			        public void clicked(InputEvent event, float x, float y) {
//						generateTable(s, TableType.CUSTOM);
//			        }
//				});
//			}else {
//				customButton = generateButton("DEFAULT");
//				newTable.add(customButton);
//				customButton.addListener(new ClickListener(){
//					@Override
//			        public void clicked(InputEvent event, float x, float y) {
//						generateTable(s, TableType.DEFAULT);
//			        }
//				});
//			}
			break;
		case FLOOR:
			TextButton fillButton = generateButton("Fill");
			newTable.add(fillButton);
			fillButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					related.fillGrid();
		        }
			});
			newTable.row();
			break;
			
		case MAP:
			final TextField textField = new TextField("", skin);
			textField.setMessageText("Save as...");
			
			// Map Title
	        Label map_info = new Label("Map size", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
			newTable.add(map_info);
			newTable.row();

			TableTuple dim = related.getDim();
			
			final TextField rowField = generateTextField(Integer.toString(dim.getX()));
			final TextField colField = generateTextField(Integer.toString(dim.getY()));

			newTable.add(rowField).size(40, 30);
			newTable.add(colField).size(40, 30);
			newTable.row();
			
			newTable.add(textField).size(80, 30);			
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
			newTable.row();
			TextButton refreshButton = generateButton("Refresh");
			refreshButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					System.out.println("Reload State");
		        }
			});
			newTable.add(refreshButton);
			newTable.row();
			
			TextButton clearButton = generateButton("Clear");
			newTable.add(clearButton);
			clearButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					related.clearGrid();
		        }
			});
			return newTable;
		default:
			break;
		}
		
		
		
		// DEFAULT TABLE CONTINUES
		newTable.row();
		
		for(FileHandle file: files) {

			String fileName = file.name().split("\\.", 2)[0];
			final Texture texture = new Texture(file);	
			
			// This can be done directly without switch lol
			final ObjectType cur = ObjectType.valueOf(s.toString());
						
			if(cur == ObjectType.ENEMY || cur == ObjectType.PLAYER)
				fileName = "Health: 1\nDamage: 1\nSpeed: 1";

			final Image icon = new Image(new TextureRegion(texture));
			Label icon_name = new Label(fileName, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
			
			newTable.add(icon).size(40, 40);
			newTable.add(icon_name).pad(5);
			
			if(cur == ObjectType.ENEMY || cur == ObjectType.PLAYER) {
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						DynamicObject d_obj = new DynamicObject(cur, 0, 0, texture);
						selectedObject = d_obj;
						related.setSelection(texture, s, d_obj);
			        }
				});
			}else {
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						GameObject obj = new GameObject(cur, new TextureRegion(texture));
						related.setSelection(texture, s, obj);
			        }
				});
			}
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
