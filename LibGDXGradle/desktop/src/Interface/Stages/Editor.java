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
	private String[] terrain_buttons;
	private String[] object_buttons;
	private String[] creature_buttons;
	private String[] tab_buttons;
	
	/*
	 * Dimensions: 280 x 480
	 */
	public Editor(Viewport v, TextureAtlas atlas, Skin skin) {
		super(v);
		this.atlas = atlas;
		this.skin = skin;
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
		
		String[] temp;
		temp = new String[] {"Wall", "Ground", "Empty", "Fill ground", "Reset", "Exit"};
		
		// Hard-coded for now, can deal with it later when images replace these buttons
		final HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("Wall", 2);
		map.put("Ground", 1);
		map.put("Empty", 0);
		
		Table mainTable = new Table();
		Table titleTable = new Table();
		
        Label HUDlabel = new Label("Editor Mode", 
        		new Label.LabelStyle(new BitmapFont(), Color.CYAN));
        

        titleTable.add(HUDlabel);
		
		for (final String s : temp) {
			TextButton button = generateButton(s);
			mainTable.row();
			mainTable.add(button);	
			button.addListener(new ClickListener(){
				@Override
		        public void clicked(InputEvent event, float x, float y) {
					System.out.println("Clicked: " + s);
		        }
			});
			
		}
		titleTable.setPosition(50, 450, 0);
		mainTable.setPosition(140, 300, 0);
		
		// Add this actor
		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/midwall_background_side.png")))));
		super.addActor(mainTable);
		super.addActor(titleTable);
	}
		
	
	
	/*
	 * Temporary, will use images later
	 */
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
        */
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
	private TextButton generateButton(String s) {
		TextButton button = new TextButton(s, skin);
		return button;
	}
}
