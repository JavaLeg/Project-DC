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
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import Interface.Grid.GridCell;
import Interface.ImageID;

public class Preview extends Stage{
	private TextureAtlas atlas;
	private Skin skin;
	private int rowActors;
	private int colActors;
	
	/*
	 * Dimensions: 520 x 480
	 */
	public Preview(Viewport v, int viewWidth, int viewHeight, int cellWidth, int cellHeight) {
		super(v);
		this.rowActors = viewWidth/cellWidth;
		this.colActors = viewHeight/cellHeight;
		//initialise(cellWidth, cellHeight);
	}
	
	private void initialise(int cellWidth, int cellHeight) {
		//super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("EditorScreen/ground3.jpg")))));
		Image[] cellImgs = new Image[rowActors * colActors];
		
		for(int i = 0; i < rowActors; i++) {
			for(int j = 0; j < colActors; j++) {
				GridCell gc = new GridCell(cellWidth, cellHeight);
				super.addActor(gc);
			}
		}
	}
}
