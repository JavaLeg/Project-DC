package Interface.Stages;
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
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import Interface.Grid.GridCell;
import Interface.ImageID;

public class Preview extends Stage{
	private int rowActors;
	private int colActors;
	private Stage related;
	private TableTuple tablePos;
	
	
	/*
	 * Dimensions: 520 x 480
	 */
	public Preview(Viewport v, int viewWidth, int viewHeight, int cellWidth, int cellHeight) {
		super(v);
		this.rowActors = viewWidth/cellWidth;
		this.colActors = viewHeight/cellHeight;
		this.tablePos = new TableTuple(50, 50);
		initialise(cellWidth, cellHeight);
	}
	
	private void initialise(int cellWidth, int cellHeight) {
		//super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/ground3.jpg")))));
		GridCell[] cells = new GridCell[rowActors * colActors];
		//Table gridTable = new Table();
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
				System.out.println("x: " + i + " y: " + j);
				GridCell gc = new GridCell(cellWidth, cellHeight);
				//gridTable.add(gc);
				super.addActor(gc);
			}
			//gridTable.row();
		}
		//gridTable.setPosition(tablePos.getX(), tablePos.getY());
		//super.addActor(gridTable);
	}
	
	public void setDependence(Stage s) {
		this.related = s;
	}
	
	public void setDependence(Editor s) {
		this.related = s;
	}
}
