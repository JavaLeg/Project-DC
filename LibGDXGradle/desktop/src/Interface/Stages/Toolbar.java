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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;


import Interface.Screens.EditorScreen;
import Interface.Stages.Selections.NewToolbarSelection;
import Interface.Stages.Selections.ToolbarSelection;


public class Toolbar extends Stage{
	private Skin skin;
	private Editor related;

	private TableTuple toolbarPos;
	private final int PAD = 0;
	
	private EditorScreen es;

	/*
	 * Dimensions: 280 x 40
	 */
	public Toolbar(Viewport v, Skin skin, EditorScreen es) {
		super(v);
		this.skin = skin;

		System.out.println("Viewport width: " + v.getScreenWidth());
		System.out.println("Viewport height: " + v.getScreenHeight());
		this.toolbarPos = new TableTuple(v.getScreenWidth()*3/7, v.getScreenHeight()/46);
		this.es = es;		
		initialise();
	}
	
	private void initialise() {		
		Table mainTable = new Table();
		int pad = 0;
		
		 final SelectBox<ToolbarSelection> listDrop = new SelectBox<ToolbarSelection>(skin);
		 ToolbarSelection[] terrainBlob = {ToolbarSelection.MAP, ToolbarSelection.FLOOR, ToolbarSelection.WALL, ToolbarSelection.ITEM};
		 TextureRegionDrawable tr = null;
		 ImageButton ib = null;
		
		for(final NewToolbarSelection selection: NewToolbarSelection.values()) {
			
			switch(selection) {
			case PLAYER:
				tr = new TextureRegionDrawable(new TextureRegion
						(new Texture(Gdx.files.internal("EditorScreen/ToolbarButtons/player.png"))));
				
				ib = new ImageButton(tr);
				mainTable.add(ib).size(40).pad(PAD);;
				ib.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						try {
							related.update(ToolbarSelection.PLAYER);
							listDrop.setSelected(ToolbarSelection.MAP);
						} catch (IOException e) {
							e.printStackTrace();
						}

			        }
				});
				break;
			case ENEMY:
				tr = new TextureRegionDrawable(new TextureRegion
						(new Texture(Gdx.files.internal("EditorScreen/ToolbarButtons/enemy.png"))));
				
				ib = new ImageButton(tr);
				mainTable.add(ib).size(40).pad(PAD);;
				ib.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						try {
							related.update(ToolbarSelection.ENEMY);
							listDrop.setSelected(ToolbarSelection.MAP);
						} catch (IOException e) {
							e.printStackTrace();
						}

			        }
				});
				break;
			case DROP:
				 listDrop.setItems(terrainBlob);
				 mainTable.add(listDrop).pad(PAD);
				 
				 listDrop.addListener(new ChangeListener(){

					@Override
					public void changed(ChangeEvent event, Actor actor) {
						try {
							
							ToolbarSelection filler = listDrop.getSelected();
							
							if(filler != ToolbarSelection.MAP)
								related.update(listDrop.getSelected());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				 });
				break;
			case TOOLS:
				
				tr = new TextureRegionDrawable(new TextureRegion
						(new Texture(Gdx.files.internal("EditorScreen/ToolbarButtons/tools.png"))));
				
				ib = new ImageButton(tr);
				mainTable.add(ib).size(40).pad(PAD);;
				ib.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						try {
							related.update(ToolbarSelection.TOOLS);
							listDrop.setSelected(ToolbarSelection.MAP);
						} catch (IOException e) {
							e.printStackTrace();
						}

			        }
				});
				break;
				
			case HOME:
				tr = new TextureRegionDrawable(new TextureRegion
						(new Texture(Gdx.files.internal("EditorScreen/ToolbarButtons/home.png"))));
				
				ib = new ImageButton(tr);
				mainTable.add(ib).size(40).pad(PAD);
				ib.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						es.exit();
			        }
				});
				break;
			case WAYPOINT:
				tr = new TextureRegionDrawable(new TextureRegion
					(new Texture(Gdx.files.internal("EditorScreen/ToolbarButtons/waypoint.png"))));
			
				ib = new ImageButton(tr);
				mainTable.add(ib).size(40).pad(PAD);
				ib.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y) {
						try {
							related.update(ToolbarSelection.WAYPOINT);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
			
	public void setDependence(Editor s) {
		this.related = s;
	}
}

