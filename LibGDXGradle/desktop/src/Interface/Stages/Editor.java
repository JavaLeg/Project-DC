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
import Interface.ObjectModel;
import Interface.Stages.Selections.ToolbarSelection;
import State.State;
import Tileset.DynamicObject;
import Tileset.Enemy;
import Tileset.GameObject;
import Tileset.GameObject.ObjectType;
import Tileset.Player;

/*
 * Stage for the editor UI (Tools on the left of the screen)
 */
public class Editor extends Stage {
	
	private Skin skin;
	private HashMap<ToolbarSelection, Table> tableMap;
	
	private TableTuple titlePos;
	private TableTuple tablePos;

	
	private HashMap<Enum<?>, Class<?>> classMap;
	private HashMap<ToolbarSelection, String[]> customButtonMap;
	
	//private DynamicObject selectedObject;
	private Player selectedPlayerObj;
	private Enemy selectedEnemyObj;
	
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
	
	/*
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
		
	}*/
	
	
	
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
		
		TextButton editButton = null;
		TextButton customButton = null;
		
		switch(s) {
		case PLAYER:
			editButton = generateButton("Edit");
			newTable.add(editButton);
			editButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					
					if(selectedPlayerObj == null) {
						System.out.println("No object selected!");
						return;
					}

					System.out.println("Editing - " + selectedPlayerObj);
					newPlayerEdit(selectedPlayerObj);
		        }
			});
			break;
		case ENEMY:
			editButton = generateButton("Edit");
			newTable.add(editButton);
			editButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					
					if(selectedEnemyObj == null) {
						System.out.println("No object selected!");
						return;
					}

					System.out.println("Editing - " + selectedEnemyObj);
					newEnemyEdit(selectedEnemyObj);
		        }
			});
			break;
		default:
			break;
		}
		
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
		newTable.row();
		
		int i = 0;
		
		switch(s) {
		case PLAYER:
			for(FileHandle file: files) {
				final Player player = saver.LoadPlayer(file.name());
				Image icon = processPath(player.getImgPath());
			
				String labels = "Name: " + player.getName() + "\n" + 
								"Health: " + player.getHp() + "\n" +
								"Damage: " + player.getContactDamage() + "\n";
				
				Label icon_labels = new Label(labels, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
				
				newTable.add(icon).size(40, 40);
				newTable.add(icon_labels).pad(5);
				

				icon.addListener(new ClickListener(){
					@Override
				    public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + player.getName());
						selectedPlayerObj = player;
						related.setPlayerSelection(player);
				    }
				});
				
				// Need to format these
				if (i % 2 == 1 && i != 0) newTable.row();
				i++;		
			}	
			break;
		case ENEMY:
			for(FileHandle file: files) {
				final Enemy enemy = saver.LoadEnemy(file.name());
				Image icon = processPath(enemy.getImgPath());
			
				String labels = "Name: " + enemy.getName() + "\n" + 
								"Health: " + enemy.getHp() + "\n" +
								"Damage: " + enemy.getContactDamage() + "\n";
				
				Label icon_labels = new Label(labels, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
				
				newTable.add(icon).size(40, 40);
				newTable.add(icon_labels).pad(5);
				

				icon.addListener(new ClickListener(){
					@Override
				    public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + enemy.getName());
						selectedEnemyObj = enemy;
						related.setEnemySelection(enemy);
				    }
				});
				
				// Need to format these
				if (i % 2 == 1 && i != 0) newTable.row();
				i++;		
			}	
			break;
		default:
			break;
		}
		
		return newTable;
		
		/*
		 * Load all saved objects into custom table
		 * And revive attributes
		 
		int i = 0;
		for(FileHandle file: files) {
			ObjectModel model = saver.LoadObj(file.name(), quick_path);

			final Texture texture = new Texture(Gdx.files.internal(model.getPath()));
			final ObjectType cur = ObjectType.valueOf(s.toString());
			Image icon = new Image(new TextureRegion(texture));

			final DynamicObject object = new DynamicObject(ObjectType.valueOf(s.toString()), model.getHp(), model.getDmg(), texture);
			object.setName(model.getName());
			
			String labels = "Name: " + object.getName() + "\n" + 
							"Health: " + object.getHp() + "\n" +
							"Damage: " + object.getContactDamage() + "\n";
			


			Label icon_name = new Label(labels, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
			
			newTable.add(icon).size(40, 40);
			newTable.add(icon_name).pad(5);
			

			icon.addListener(new ClickListener(){
				@Override
			    public void clicked(InputEvent event, float x, float y) {
					System.out.println("Selected - " + object.getName());
					selectedObject = object;
					related.setSelection(texture, s, object);
			    }
			});
			
			if (i % 2 == 1 && i != 0) newTable.row();
			
			// Don't let it go over the edge
			if (i > 20 && s == ToolbarSelection.FLOOR) break;
			if (i > 14 && s == ToolbarSelection.ENEMY) break;
			i++;		
		}	
		return newTable;
		*/
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
					
					if(selectedPlayerObj == null) {
						System.out.println("No object selected!");
						return;
					}
					
					System.out.println("Editing - " + selectedPlayerObj);
					newPlayerEdit(selectedPlayerObj);
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
			break;
		case ENEMY:
			editButton = generateButton("Edit");
			newTable.add(editButton);
			editButton.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					
					if(selectedEnemyObj == null) {
						System.out.println("No object selected!");
						return;
					}
					
					System.out.println("Editing - " + selectedEnemyObj);
					newEnemyEdit(selectedEnemyObj);
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
			break;
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
			final String fileName = file.name();
			final String filePath = file.path();
			String labels = fileName;
			
			final ObjectType cur = ObjectType.valueOf(s.toString());

			if(cur == ObjectType.ENEMY || cur == ObjectType.PLAYER)
				labels = "Health: 1\nDamage: 1\nSpeed: 1";

			// Display the sprite (Information)
			final Texture texture = new Texture(file);	
			Image icon = new Image(new TextureRegion(texture));
			Label icon_name = new Label(labels, new Label.LabelStyle(new BitmapFont(), Color.BLACK));
			
			newTable.add(icon).size(40, 40);
			newTable.add(icon_name).pad(5);
			
			switch(cur) {
			case ENEMY:
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + fileName);
						
						// Right now all attributes initialized as null (Changed through edit)
						Enemy enemy = new Enemy(filePath);
						selectedEnemyObj = enemy;
						related.setEnemySelection(enemy);
			        }
				});
				break;
			case PLAYER:
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + fileName);
						
						// Right now all attributes initialised as null (Changed through edit)
						Player player = new Player(filePath);
						selectedPlayerObj = player;
						related.setPlayerSelection(player);
			        }
				});
				break;
			case FLOOR:
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + fileName);
						
						// Right now all attributes initialised as null (Changed through edit)
						GameObject floor = new GameObject(cur, filePath);
						related.setStaticSelection(floor);
			        }
				});
				break;
			case WALL:
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + fileName);
						
						// Right now all attributes initialised as null (Changed through edit)
						GameObject wall = new GameObject(cur, filePath);
						related.setStaticSelection(wall);
			        }
				});
				break;
			case ITEM:
				icon.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						System.out.println("Selected - " + fileName);
						
						// Right now all attributes initialised as null (Changed through edit)
						GameObject item = new GameObject(cur, filePath);
						related.setStaticSelection(item);
			        }
				});
				break;
			default:
				break;
			}
			if (i % 2 == 1 && i != 0) newTable.row();
			
			// Don't let it go over the edge
			if (i > 20 && s == ToolbarSelection.FLOOR) break;
			if (i > 14 && s == ToolbarSelection.ENEMY) break;
			i++;
			
		}
		return newTable;
	}
	
	/*
	 * Creates a new table pop up
	 * Displays all attributes
	 */	
	public void newEnemyEdit(final Enemy obj) {
		Table editTable = new Table();
		Double hp = obj.getHp();
		Double dmg = obj.getContactDamage();
		String name = obj.getName();
		int atk = obj.getAttackRate();
		Image icon = processPath(obj.getImgPath());
		
		editTable.add(icon);
		editTable.row();
		
		// Add more later
		final TextField nameField = generateTextField("Name - " + name);
		final TextField hpField = generateTextField("HP - " + Double.toString(hp));
		final TextField dmgField = generateTextField("DMG - " + Double.toString(dmg));
		final TextField atkField = generateTextField("Atk rate - " + Integer.toString(atk));
		
		editTable.add(nameField);
		editTable.row();
		
		editTable.add(hpField);
		editTable.row();
		
		editTable.add(dmgField);
		editTable.row();
		
		editTable.add(atkField);
		editTable.row();
		
		TextButton saveButton = generateButton("Save");
		editTable.add(saveButton);
		editTable.row();
		
		
		// If save gets clicked, clone it with new attributes
		// before saving
		saveButton.addListener(new ClickListener(){
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				try {
					Enemy clone = obj.clone();
					
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
					
					if(!check)
						return;
					
					
					String s = nameField.getText().replaceAll(" ","_");
					
					clone.setName(s);
					clone.setHp(Double.valueOf(hpField.getText()));
					clone.setContactDamage(Double.valueOf(dmgField.getText()));

					saveEnemy(clone);
				} catch (CloneNotSupportedException e) {
					System.out.println("Not cloneable");
					e.printStackTrace();
				}
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
	
	
	public void newPlayerEdit(final Player obj) {
		Table editTable = new Table();
		Double hp = obj.getHp();
		Double dmg = obj.getContactDamage();
		String name = obj.getName();
		Image icon = processPath(obj.getImgPath());
		
		editTable.add(icon);
		editTable.row();
		
		// Add more later
		final TextField nameField = generateTextField("Name - " + name);
		final TextField hpField = generateTextField("HP - " + Double.toString(hp));
		final TextField dmgField = generateTextField("DMG - " + Double.toString(dmg));
		
		editTable.add(nameField);
		editTable.row();
		
		editTable.add(hpField);
		editTable.row();
		
		editTable.add(dmgField);
		editTable.row();
		
		TextButton saveButton = generateButton("Save");
		editTable.add(saveButton);
		editTable.row();
		
		
		// If save gets clicked, clone it with new attributes
		// before saving
		saveButton.addListener(new ClickListener(){
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				try {
					Player clone = obj.clone();
					
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
					
					if(!check)
						return;
					
					
					String s = nameField.getText().replaceAll(" ","_");
					
					clone.setName(s);
					clone.setHp(Double.valueOf(hpField.getText()));
					clone.setContactDamage(Double.valueOf(dmgField.getText()));

					savePlayer(clone);
				} catch (CloneNotSupportedException e) {
					System.out.println("Not cloneable");
					e.printStackTrace();
				}
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
	
	/*
	 * Saving custom player objects
	 */
	private void savePlayer(Player obj){
		
		try {
			saver.SavePlayer(obj, obj.getName());
			System.out.println("Object - " + obj.getName() + " saved!");
		} catch (IOException e) {
			System.out.println("I/O Error. Cannot save object");
			e.printStackTrace();
		}

	}
	
	/*
	 * Saving custom enemy objects
	 */
	private void saveEnemy(Enemy obj){
		
		try {
			saver.SaveEnemy(obj, obj.getName());
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
