package Interface.Stages;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import Interface.Stages.Selections.NewToolbarSelection;
import Interface.Stages.Selections.ToolbarSelection;


public class Toolbar extends Stage{
	private Skin skin;
	private Editor related;

	private TableTuple toolbarPos;
	private final int PAD = 1;

	/*
	 * Dimensions: 280 x 40
	 */
	public Toolbar(Viewport v, Skin skin) {
		super(v);
		this.skin = skin;
		this.toolbarPos = new TableTuple(v.getScreenWidth()*2/23, v.getScreenHeight()/45);
		
		
		initialise();
	}
	
	private void initialise() {		
		Table mainTable = new Table();
		int pad = 0;
		
		for(final NewToolbarSelection selection: NewToolbarSelection.values()) {
			
			switch(selection) {
			case TERRAIN:
				 final SelectBox<ToolbarSelection> terrainDrop = new SelectBox<ToolbarSelection>(skin);
				 
				 ToolbarSelection[] terrainBlob = {ToolbarSelection.FLOOR, ToolbarSelection.WALL};
				 terrainDrop.setItems(terrainBlob);
				 
				 mainTable.add(terrainDrop).pad(PAD);
				 
				 terrainDrop.addListener(new ChangeListener(){

					@Override
					public void changed(ChangeEvent event, Actor actor) {
						try {
							related.update(terrainDrop.getSelected());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				 });
				break;
			case OBJECTS:
				 final SelectBox<ToolbarSelection> objDrop = new SelectBox<ToolbarSelection>(skin);

				 ToolbarSelection[] objBlob = {ToolbarSelection.PLAYER, ToolbarSelection.ENEMY, ToolbarSelection.ITEM};
				 objDrop.setItems(objBlob);
				 
				 mainTable.add(objDrop).pad(PAD);
				 
				 objDrop.addListener(new ChangeListener(){

					@Override
					public void changed(ChangeEvent event, Actor actor) {
						try {
							related.update(objDrop.getSelected());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				 });
				break;
			case TOOLS:
				
				TextureRegionDrawable tr = new TextureRegionDrawable(new TextureRegion
						(new Texture(Gdx.files.internal("EditorScreen/ToolbarButtons/tools.png"))));
				
				ImageButton ib = new ImageButton(tr);
				mainTable.add(ib).size(40).pad(PAD);;
				ib.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						try {
							related.update(ToolbarSelection.MAP);
						} catch (IOException e) {
							e.printStackTrace();
						}

			        }
				});
				break;
				
			case HOME:
				TextureRegionDrawable trr = new TextureRegionDrawable(new TextureRegion
						(new Texture(Gdx.files.internal("EditorScreen/ToolbarButtons/home.png"))));
				
				ImageButton yb = new ImageButton(trr);
				mainTable.add(yb).size(40).pad(PAD);
				yb.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						System.out.println("EXIT!");
			        }
				});
				break;
			case GROUP:
				TextureRegionDrawable trrr = new TextureRegionDrawable(new TextureRegion
					(new Texture(Gdx.files.internal("EditorScreen/ToolbarButtons/group.png"))));
			
				ImageButton ybb = new ImageButton(trrr);
				mainTable.add(ybb).size(40).pad(PAD);
				ybb.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y) {
						System.out.println("Something should go here!");
					}
					});
				break;
			default:
				break;
			}
		}
		mainTable.setPosition(toolbarPos.getX() + pad, toolbarPos.getY(), 0);
		
		// Add this actor
		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/tb_bg.jpg")))));
		super.addActor(mainTable);
	}
	
	private TextButton generateButton(String s) {
		String newString = " " + s + " ";
		TextButton button = new TextButton(newString, skin);
		return button;
	}
		
	public void setDependence(Editor s) {
		this.related = s;
	}
}