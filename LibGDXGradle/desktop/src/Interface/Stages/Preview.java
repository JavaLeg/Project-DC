package Interface.Stages;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
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

import Interface.Stages.Selections.ToolbarSelection;
import Interface.PreviewCell;

public class Preview extends Stage{
	private int rowActors;
	private int colActors;
	private Stage related;
	
	private boolean has_player = false;			// Is this the appropriate place
	
	private TableTuple tablePos;
	private TextureRegion selected_tr;
	
	private ArrayList<PreviewCell> cellList;
	
	// Should rename it soon, Image Stack can hold more than one "layer" of object objects.
	private ToolbarSelection selectedLayer;
	
	
	/*
	 * Dimensions: 520 x 480
	 */
	public Preview(Viewport v, int viewWidth, int viewHeight, int cellWidth, int cellHeight) {
		super(v);
		this.rowActors = viewWidth/cellWidth - 2;
		this.colActors = viewHeight/cellHeight + 1;
		this.cellList = new ArrayList<PreviewCell>();
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
				cellList.add(cell);
				
				cell.addListener(new ClickListener(){
					@Override
			        public void clicked(InputEvent event, float x, float y) {
						// Allow for only one player per map (multiplayer possibly later)
						if (selectedLayer == ToolbarSelection.PLAYER) {
							if (has_player == true) return;
							has_player = true;
						}
						cell.setTexture(selected_tr, selectedLayer);
			        }
				});
				gridTable.add(cell).size(40, 40);	
			}
			gridTable.row();
		}
		//gridTable.setPosition(tablePos.getX(), tablePos.getY());
		gridTable.top();
		gridTable.setFillParent(true);
		super.addActor(gridTable);
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
	
	public void setSelection(Texture s, ToolbarSelection ts) {

		this.selected_tr = new TextureRegion(s);
		this.selectedLayer = ts;
	}
	
	/*
	 * Fills grid with currently selected texture
	 */
	
	public void fillGrid() {
		
		if(selected_tr == null || selectedLayer != ToolbarSelection.TERRAIN) 
			return;
		
		
		Texture texture = selected_tr.getTexture();
		String path = ((FileTextureData)texture.getTextureData()).getFileHandle().name();
		System.out.println("Fill grid with : " + path);
		
		for(PreviewCell cell : cellList) {
			cell.setTexture(selected_tr, selectedLayer);
		}
	}
	
	public void clearGrid() {		
		has_player = false;
		for(PreviewCell cell : cellList) {
			cell.clear();
		}
	}

	/*
	 * Check the map is valid before saving (e.g, at least one player)
	 * At least one tile, (check creatures on tile, etc. etc.)
	 */
	public boolean checkValidMap() {
		// TODO Auto-generated method stub
		boolean no_err = true;
		
		if (has_player == false) {
			System.out.println("No player present, insert Player to fix");
			no_err = false;
		}
		
		for (PreviewCell cell: cellList) {
			if (cell.isValid() == false) {
				System.out.println("Invalid object on empty cell");
				no_err = false;
			}
		}
		
		
		return no_err;
	}

}
