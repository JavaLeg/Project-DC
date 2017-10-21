package Interface.Stages;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.desktop.SaveSys;

import Interface.EditorModel;
import Interface.Stages.Selections.ToolbarSelection;
import State.Coord;
import State.State;
import Tileset.DynamicObject;
import Tileset.Enemy;
import Tileset.GameObject;
import Tileset.GameObject.ObjectType;
import Tileset.Item;
import Tileset.Player;
import Tileset.Behaviour.Attack;
import Tileset.Behaviour.MoveBehaviour;
import Tileset.Behaviour.MoveRandom;

/*
 * Stage for the editor UI (Tools on the left of the screen)
 */
public class Editor extends Stage {
	
	private Skin skin;
	private HashMap<ToolbarSelection, Table> tableMap;
	
	private TableTuple titlePos;
	private TableTuple tablePos;
	
	// Used purely for edits
	private DynamicObject selected_Dyn;
	
	private ToolbarSelection current;
	private int PAD;
		
	//private Stage related;
	private State related;
	private String path;
	private SaveSys saver;
	
	// Map size constraints
	private final static int MAP_MIN = 10;
	private final static int MAP_MAX = 50;
		
	/*
	 * Dimensions: 280 x 480
	 * Stage takes in a viewport and a skin
	 */
	public Editor(Viewport v, Skin skin) throws IOException {
		super(v);
		this.skin = skin;
		this.titlePos = new TableTuple(50, 450);
		
		//v.getScreenWidth()*17/40 this value is 0???
		this.tablePos = new TableTuple(10, v.getScreenHeight());
		this.path = "SpriteFamily/";
		this.tableMap = new HashMap<ToolbarSelection, Table>();
		this.saver = new SaveSys();
		this.PAD = 6;
		
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
		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/Inventory_tab_resize.png")))));
		super.addActor(titleTable);
	}
	
	/*
	 * Display a table on the stage
	 * After updating
	 */
	private void display(Table newTable) {
		newTable.setPosition(tablePos.getX(), tablePos.getY());
		newTable.top();

		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/Inventory_tab_resize.png")))));
		ScrollPane scroll = new ScrollPane(newTable);
		scroll.setSize(220,470);
		scroll.moveBy(10, 0);
		super.addActor(scroll);
	}

	
	
	
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GENERATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private TextButton generateButton(String s) {
		String newString = " " + s + " ";
		TextButton button = new TextButton(newString, skin);
		return button;
	}
	
	private TextField generateTextField(String s) {
		TextField tf = new TextField("", skin);
		tf.setMessageText(s);
		return tf;
	}
	
	
	/*
	 * Everything in here is for when you press the custom button
	 * Loads in all saved objects from corresponding "_custom" directory
	 * Creates an icon from each loaded object
	 * Attaches a listener to each icon, and if pressed sends the appropriate loaded object to the state class
	 * 
	 * PRESERVES ATTRIBUTES
	 */
	private Table generateCustomTable(final ToolbarSelection s) throws ClassNotFoundException, IOException {
		
		String quick_path = path + s.toString().toLowerCase() + "_custom/";
		FileHandle[] files = Gdx.files.internal(quick_path).list();
		
		final Table newTable = new Table();
		
		Label title = new Label(s.toString() + " - Custom", 
        		new Label.LabelStyle(new BitmapFont(), Color.BLACK));
		
		newTable.add(title);
		newTable.row();
		
		TextButton editButton = null;
		TextButton customButton = null;
		
		if(s == ToolbarSelection.PLAYER || s == ToolbarSelection.ENEMY || s == ToolbarSelection.ITEM) {
			editButton = generateButton("Edit");
			newTable.add(editButton);
			editButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					
					if(selected_Dyn == null) {
						System.out.println("No object selected!");
						return;
					}

					System.out.println("Editing - " + selected_Dyn);
					newEdit(selected_Dyn);
		        }
			});
			
			customButton = generateButton("default");
			newTable.add(customButton);
			customButton.addListener(new ClickListener(){
				@Override
			    public void clicked(InputEvent event, float x, float y) {
					try {
						update(s);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		newTable.row();
		
		int i = 0;
		ObjectType type = ObjectType.valueOf(s.toString());
		

		for(FileHandle file: files) {
			final DynamicObject obj = saver.Load(file.name(), type);
			Image icon = processPath(obj.getImgPath());
			
			
			String labels = null;
			String tooltip_labels = null;
			
			switch(type) {
			case PLAYER:
				labels = "Name: " + obj.getName();

				
				tooltip_labels = labels + "\nHealth: " + obj.getHp() 
				+ "\nDamage: " + obj.getContactDamage();

				
				break;
			case ENEMY:
				labels = "Name: " + obj.getName();
				
				tooltip_labels = labels + "\nHealth: " + obj.getHp() 
										+ "\nDamage: " + obj.getContactDamage() 
										+ "\nAtk Rate: " + ((Enemy) obj).getAttackTime();				
				break;
			case ITEM:
				labels = "Name: " + obj.getName();
				tooltip_labels = labels + "\nRestore: " + ((Item) obj).getRestoreValue();
				break;
			default:
				break;
			}

				
			Label icon_labels = new Label(labels, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
			
			//Tool-tip (More information)
			icon.addListener(new TextTooltip(tooltip_labels, skin));
			
			newTable.add(icon).size(40, 40);
			newTable.add(icon_labels);
			newTable.row();
				

			icon.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					System.out.println("Selected - " + obj.getName());
					selected_Dyn = obj;
					related.setDynamicSelection(obj);
				}
			});
				
			// Need to format these
			if (i % 2 == 1 && i != 0) newTable.row();
			i++;		
			}	
		return newTable;
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
		int i = 1;
		
		// If editor tab contains other stuff
		// Hard coded for now

		TextButton editButton = null;
		TextButton customButton = null;
		
		if(s == ToolbarSelection.PLAYER || s == ToolbarSelection.ENEMY || s == ToolbarSelection.ITEM) {
			editButton = generateButton("Edit");
			newTable.add(editButton);
			editButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					
					if(selected_Dyn == null) {
						System.out.println("No object selected!");
						return;
					}
					
					System.out.println("Editing - " + selected_Dyn);
					newEdit(selected_Dyn);
		        }
			});

			customButton = generateButton("Custom");
			newTable.add(customButton);
			customButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					updateCustom(s);
		        }
			});
		}
		
		// Custom buttons
		switch(s) {
		case FLOOR:
			TextButton fillButton = generateButton("Fill");
			newTable.add(fillButton);
			fillButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					System.out.println("FILL GRID!");
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
			newTable.add(colField).size(40, 30).pad(PAD);
			
			
			TextureRegionDrawable tr = new TextureRegionDrawable(new TextureRegion
					(new Texture(Gdx.files.internal("EditorScreen/info_icon.png"))));
			
			
			ImageButton ib = new ImageButton(tr);
			
			String description = "Dimension constraints:\n" +
									"Min - " + Integer.toString(MAP_MIN) + " x " + Integer.toString(MAP_MIN) + "\n" +
									"Max - " + Integer.toString(MAP_MAX) + " x " + Integer.toString(MAP_MAX);
			
			ib.addListener(new TextTooltip(description, skin));
			
			newTable.add(ib).size(40);
			
			
			
			
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
					if (rowField.getText().matches("^-?\\d+$") && colField.getText().matches("^-?\\d+$")) {		// Check if number
						int rows = Integer.parseInt(rowField.getText());
						int cols = Integer.parseInt(colField.getText());
						if (rows >= MAP_MIN && rows <= MAP_MAX && cols >= MAP_MIN && cols <= MAP_MAX)
							related.resize(rows, cols);									// Deletes all of the old data, resizes map
					}
					System.out.println(rowField.getText() + ", " + colField.getText());
		        }
			});
			newTable.add(refreshButton);
			newTable.row();
			
			TextButton clearButton = generateButton("Clear");
			newTable.add(clearButton);
			clearButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					System.out.println("Clear grid!");
					related.clearGrid();
		        }
			});
			
			return newTable;
		default:
			break;
		}
		
		
		
		// DEFAULT TABLE CONTINUES
		// Editor tab
		newTable.row();
		
		for(FileHandle file: files) {

			
			// Display Images on the editor tab as Icons
			// No object correspondence
			final String fileName = file.name().split("\\.", 2)[0];
			final String filePath = file.path();
			
			//String labels = fileName;
			
			final ObjectType cur = ObjectType.valueOf(s.toString());

			// Display the sprite (Information)
			final Texture texture = new Texture(file);	
			Image icon = new Image(new TextureRegion(texture));
			icon.addListener(new TextTooltip("Preset: " + fileName, skin));
			newTable.add(icon).size(40, 40).pad(PAD);
			
			switch(cur){
			case PLAYER:
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + fileName);
						
						
						// DEFAULTS FOR ATTACKS
						Attack light = new Attack(Arrays.asList(new Coord(0,1)), 
								5, Arrays.asList(ObjectType.ENEMY), 15 , 10);
						Attack heavy = new Attack(Arrays.asList(new Coord(0,1), new Coord(1,1), new Coord(-1,1)), 
								5, Arrays.asList(ObjectType.ENEMY), 45 , 10);
						
						Player obj = new Player(10, 10, light, heavy, filePath);
						//System.out.print(getActionState());
						selected_Dyn = obj;
						related.setDynamicSelection(obj);
			        }
				});
				break;
			case ENEMY:
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + fileName);
						
						// Right now all attributes initialized as null (Changed through edit)
						Enemy obj = new Enemy(10, 2, 30, new MoveRandom(), filePath);
						// double hp, double damage, int moveRate, MoveBehaviour b, String img_path
						selected_Dyn = obj;
						related.setDynamicSelection(obj);
			        }
				});
				break;
			case ITEM:
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + fileName);
						
						// Right now all attributes initialized as null (Changed through edit)
						Item obj = new Item(filePath);
						if (fileName.equals("win")) obj.setName(fileName);
						selected_Dyn = obj;
						related.setDynamicSelection(obj);
			        }
				});
				break;
			case FLOOR:
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + fileName);
						
						// Right now all attributes initialized as null (Changed through edit)
						GameObject obj = new GameObject(cur, filePath);
						related.setStaticSelection(obj);
			        }
				});
				break;
			case WALL:
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + fileName);
						
						GameObject obj = new GameObject(cur, filePath);
						related.setStaticSelection(obj);
			        }
				});
				break;
			default:
				break;
			}
		
			if (i % 4 == 0) {
				newTable.row();
			}
			i++;
			
		}
		return newTable;
	}
	
	/*
	 * Creates a new table pop up
	 * Displays all attributes
	 */	
	public void newEdit(DynamicObject obj) {
		final DynamicObject object = obj;
		final ObjectType type = object.getType();
				
		Table editTable = new Table();
		Image icon = processPath(obj.getImgPath());
		
		editTable.add(icon);
		editTable.row();
		
		Double hp = obj.getHp();
		Double dmg = obj.getContactDamage();
		String name = obj.getName();
		
		int atk = 0;
		int restore = 0;
		
		// Special attributes for objects
		if(type == ObjectType.ENEMY) {
			atk = ((Enemy) obj).getAttackTime();
		}else if(type == ObjectType.ITEM) {
			restore = ((Item) obj).getRestoreValue();
		}
		
		ArrayList<TextField> fieldList = new ArrayList<TextField>();
		
		final TextField nameField = generateTextField("Name - " + name);
		final TextField hpField = generateTextField("HP - " + Double.toString(hp));
		final TextField dmgField = generateTextField("DMG - " + Double.toString(dmg));
		final TextField atkField = generateTextField("Atk rate - " + Integer.toString(atk));
		final TextField resField = generateTextField("Restore - " + Integer.toString(restore));
		

		switch(type) {
		case PLAYER:
			fieldList.add(nameField);
			fieldList.add(hpField);
			fieldList.add(dmgField);
			break;
			
		case ENEMY:
			fieldList.add(nameField);
			fieldList.add(hpField);
			fieldList.add(dmgField);
			fieldList.add(atkField);
			break;
			
		case ITEM:
			fieldList.add(resField);
			break;
		default:
			break;
		}
		
		
		for(TextField tf : fieldList) {
			editTable.add(tf);
			editTable.row();
		}
		
		TextButton saveButton = generateButton("Save");
		editTable.add(saveButton);
		editTable.row();
		
		
		// If save gets clicked, clone it with new attributes
		// before saving
		saveButton.addListener(new ClickListener(){
			@Override
	        public void clicked(InputEvent event, float x, float y) {

				
					DynamicObject clone = object.clone(); //CLONE
					
					// Sanitation check
					boolean check = true;
					
					if(nameField.getText().isEmpty()) {
						System.out.println("Invalid name field!");
						check = false;
					}
					
					if(!hpField.getText().matches("[0-9]+")) {
						System.out.println("Invalid HP value!");
						check = false;
					}
					
					if(!dmgField.getText().matches("[0-9]+")) {
						System.out.println("Invalid Damage value!");
						check = false;
					}
					
					
					// Custom attributes
					switch(type) {
					case PLAYER:

						break;
					case ENEMY:
						if(!atkField.getText().matches("[0-9]+")) {
							System.out.println("Invalid attack value!");
							check = false;
						}
						break;
					case ITEM:
						if(!resField.getText().matches("[0-9]+")) {
							System.out.println("Invalid restore value!");
							check = false;
						}
						break;
					default:
						break;
					}

					
					if(!check)
						return;
					
					
					String s = nameField.getText().replaceAll(" ","_");
					
					clone.setName(s);
					clone.setHp(Double.valueOf(hpField.getText()));
					clone.setContactDamage(Double.valueOf(dmgField.getText()));
					
					saveObject(clone);

				
	        }
		});
		
		TextButton closeButton = generateButton("Close");
		editTable.add(closeButton);
		editTable.row();
		closeButton.addListener(new ClickListener(){
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				try {
					update(current);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		});
		display(editTable);
	}

	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ INTERNALS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/*
	 * String to image
	 */
	private Image processPath(String path) {
		return new Image(new TextureRegion(new Texture(Gdx.files.internal(path))));
	}
	
	/*
	 * Updates the stage according to toolbar selection
	 */
	public void update(ToolbarSelection s) throws IOException {
		
		// Set current selection for return
		current = s;
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
	 * Updates stage according to edit/custom buttons
	 */
	private void updateCustom(ToolbarSelection s) {
		this.clear();
		try {
			display(generateCustomTable(s));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		if(toSave == null) {
			System.out.println("Map not saved.");
			return;
		}
		
		saver.Save(toSave, s);
		System.out.println("Saved as file: " + s);
	}
	

	
	private void saveObject(DynamicObject obj) {
		try {
			saver.Save(obj, obj.getName());
			System.out.println("Object - " + obj.getName() + " saved!");
		} catch (IOException e) {
			System.out.println("I/O Error. Cannot save object");
			e.printStackTrace();
		}
	}
	
	public void setDependence(State s) {
		this.related = s;
	}
}
