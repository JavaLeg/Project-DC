package Interface.Stages;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;


import Interface.Stages.Selections.CreatureSelection;

import Interface.Stages.Selections.TerrainSelection;
import Interface.PreviewCell;

public class Preview extends Stage{
	private int rowActors;
	private int colActors;
	private Stage related;
	private TableTuple tablePos;
	
	private TerrainSelection t_select;
	private CreatureSelection cr_select;
	
	
	/*
	 * Dimensions: 520 x 480
	 */
	public Preview(Viewport v, int viewWidth, int viewHeight, int cellWidth, int cellHeight) {
		super(v);
		this.rowActors = viewWidth/cellWidth - 2;
		this.colActors = viewHeight/cellHeight + 1;
		initialise(cellWidth, cellHeight);
		
		// HashMap <EditorSelection, Image>
		// map(selection) passed to the preview cell
	}
	
	private void initialise(int cellWidth, int cellHeight) {
		Table gridTable = new Table();
		//ImageStack[] cells = new ImageStack[rowActors * colActors];
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
				System.out.println("x: " + i + " y: " + j);
								
				final PreviewCell cell = new PreviewCell();
				cell.setSize(40, 40);
				cell.setWidth(40);
				cell.setHeight(40);
				cell.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						if(t_select != null) {
							cell.placeTerrain(new Texture(Gdx.files.internal("SpriteFamily/Terrain/sprite_020.png")));
						}else if(cr_select != null) {
							cell.placeCreature(new Texture(Gdx.files.internal("SpriteFamily/Creature/sprite_107.png")));
						}
			        }
				});

				gridTable.add(cell);	
			}
			gridTable.row();
		}
		//gridTable.setPosition(tablePos.getX(), tablePos.getY());
		gridTable.top();
		gridTable.setFillParent(true);
		super.addActor(gridTable);
		System.out.println("hlo");
	}

	/*
	private ImageStack ImageStack(TextureRegion textureRegion) {
		// TODO Auto-generated method stub
		return null;
	}
	*/

	public void setDependence(Stage s) {
		this.related = s;
	}
	
	public void setDependence(Editor s) {
		this.related = s;
	}
	
	public void setSelection(TerrainSelection s) {
		this.t_select = s;
		this.cr_select = null;
	}
	
	public void setSelection(CreatureSelection s) {
		this.cr_select = s;
		this.t_select = null;
	}
}
