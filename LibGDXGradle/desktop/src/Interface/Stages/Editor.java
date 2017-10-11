package Interface.Stages;

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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import Interface.Stages.Selections.CreatureSelection;
import Interface.Stages.Selections.TerrainSelection;
import Interface.Stages.Selections.ToolbarSelection;

/*
 * Stage for the editor UI (Tools on the left of the screen)
 */
public class Editor extends Stage{
	
	private TextureAtlas atlas;
	private Skin skin;
	private Table mainTable;
	
	// Terrain will be screen 0, creatures will be screen 1, objects will be screen 2
	private Table terrainTable;
	private Table creatureTable;
	private Table objectTable;
	
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
	
	//private CreatureTable currTable;
	//private TerrainTable currTable;
	
	//private String[] terrain_buttons;
	//private String[] object_buttons;
	//private String[] creature_buttons;
	//private String[] tab_buttons;
	
	/*
	 * Dimensions: 280 x 480
	 */
	public Editor(Viewport v, TextureAtlas atlas, Skin skin) {
		super(v);
		this.atlas = atlas;
		this.skin = skin;
		this.titlePos = new TableTuple(50, 450);
		this.tablePos = new TableTuple(140, 440);
		this.path = "SpriteFamily/";
		initialise();
	}
	
	/*
	private void initialise() {
		
		
		terrain_buttons = new String[] {"Wall", "Ground", "Empty", "Fill ground", "Reset", "Exit"};
		creature_buttons = new String[] {"Player", "Enemy"};
		tab_buttons = new String[] {"Terrain", "Creatures", "Objects"};
		
		// Hard-coded for now, can deal with it later when images replace these buttons
		final HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("Wall", 2);
		map.put("Ground", 1);
		map.put("Empty", 0);
		
		// Creating tables
		mainTable = new Table();
		terrainTable = new Table();
		creatureTable = new Table();
		objectTable = new Table();
		//
		
		// Terrain table creation
		
		TextButton wallButton = generateButton("Wall");
		TextButton groundButton = generateButton("Ground");
		TextButton emptyButton = generateButton("Empty");
		TextButton setGround = generateButton("Set Ground");
		
        terrainTable.row();
        terrainTable.add(wallButton);
        wallButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	//current_click = 2;
            }
        });
        terrainTable.row();
        terrainTable.add(groundButton);
        groundButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	//current_click = 1;
            }
        }); 
        
        terrainTable.row();
        terrainTable.add(emptyButton);
        emptyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //current_click = 0;
            }
        }); 
        
        terrainTable.row();
        terrainTable.add(setGround);
        setGround.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //grid.setValue(1, ground_texture);
                // For James
            }
        }); 
        
        // Terrain table created
		
		swapScreens(1);
		
		
		mainTable.setPosition(140, 300, 0);
		// Add this actor
		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/midwall_background_side.png")))));
		super.addActor(mainTable);
	}
	*/
	private void initialise() {
		
		Table titleTable = new Table();	
        Label HUDlabel = new Label("Editor Mode", 
        		new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        titleTable.add(HUDlabel);
        
        // Hashmap enum to table classes
        //classMap = new HashMap<Enum<?>, Class<?>>();
        //classMap.put(ToolbarSelection.CREATURE, t);
        //classMap.put(ToolbarSelection.TERRAIN, c);

		titleTable.setPosition(titlePos.getX(), titlePos.getY(), 0);
		
		// Add background and title
		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/midwall_background_side.png")))));
		super.addActor(titleTable);
	}
	
	private void display(Table newTable, ToolbarSelection s) {

		
		
		Table newTitle = new Table();
		Label title = null;
				
		for(ToolbarSelection ts: ToolbarSelection.values()) {
			if(s == ts) {
				title = new Label(ts.toString(), 
		        		new Label.LabelStyle(new BitmapFont(), Color.BLACK));
				break;
			}
				
		}
		
		newTitle.add(title);
		newTitle.setPosition(titlePos.getX(), titlePos.getY());
		newTable.setPosition(tablePos.getX(), tablePos.getY());
		newTable.top();

		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/midwall_background_side.png")))));
		super.addActor(newTable);
		super.addActor(newTitle);
	}
	
	/*
	 * Temporary, will use images later
	 */
	/*
	public void swapScreens(int screen) {
		
		mainTable.clear();
		
		Table types = new Table();
		
        TextButton terrain = generateButton("Terrain");
        TextButton creatures = generateButton("Creatures");
        TextButton objects = generateButton("Objects");

        Label HUDlabel = new Label("Editor Mode", 
        		new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        
        mainTable.add(HUDlabel);
        mainTable.row();
        
        types.center();
        types.add(terrain);
        types.add(creatures);
        types.add(objects);
        types.row();
        mainTable.add(types);
        mainTable.row();
        
        switch (screen) {
        case 1:
        	mainTable.add(terrainTable);
        	break;
        case 2:
        	mainTable.add(creatureTable);
        	break;
        case 3:
        	mainTable.add(objectTable);
        	break;
        }
        
        mainTable.row();
        /*
        mainTable.add(reset);
        reset.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	grid.setValue(0, empty_texture);
            	// For James
            }
        }); 
        
        terrain.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	swapScreens(1);
            }
        }); 
        
        creatures.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	swapScreens(2);
            }
        }); 
        
        objects.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	swapScreens(3);
            }
        }); 	
	}
*/

	
	public void update(ToolbarSelection s) {
		if(current == s)
			return;
		
		this.clear();
		
		// Check if table already exists
		// If so, pass that table instead of generating
		switch(s) {
		case TERRAIN:
			if(terrainTable == null)
				terrainTable = generateTable(s);
			display(terrainTable, s);
			break;
		case CREATURE:
			if(creatureTable == null)
				creatureTable = generateTable(s);
			display(creatureTable, s);
			break;
		default:
			break;		
		}

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

		int i = 0;
		
		for(FileHandle file: files) {

			final Texture t = new Texture(file);

			Image icon = new Image(new TextureRegion(t));
			Label icon_name = new Label(file.name().split("\\.", 2)[0], new Label.LabelStyle(new BitmapFont(), Color.BLACK));
			newTable.add(icon).size(40, 40);
			newTable.add(icon_name).pad(10);
			
			icon.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					related.setSelection(t, s);
		        }
			});
			
			if(i % 2 == 0)
				newTable.row();
			
			i++;
		}
		
		
		
		/*
		String[] temp;

		switch(s) {
		case TERRAIN:
			temp = new String[] {"Wall", "Ground", "Empty", "Fill ground"};
			
			for(final TerrainSelection select: TerrainSelection.values()) {
				TextButton button = generateButton(select.toString().toLowerCase());
				newTable.add(button);
				
				button.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						related.setSelection(select);
			        }
				});
				newTable.row();
			}

			break;
		case CREATURE:
			temp = new String[] {"Bat", "Skeleton", "Zombie", "Peon", "Roadman Shaq"};
			
			for(final CreatureSelection select: CreatureSelection.values()) {
				TextButton button = generateButton(select.toString().toLowerCase());
				newTable.add(button);
				
				button.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						related.setSelection(select);
			        }
				});
				newTable.row();
			}
			break;
		default:
			return null;
		}
		*/
		return newTable;
	}
	
	public void setDependence(Stage s) {
		this.related = s;
	}
	
	public void setDependence(Preview s) {
		this.related = s;
	}
}
