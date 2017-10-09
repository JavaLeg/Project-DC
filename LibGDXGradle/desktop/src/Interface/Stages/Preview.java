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
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import Interface.Grid;
import Interface.ImageID;

public class Preview extends Stage{
	private TextureAtlas atlas;
	private Skin skin;
	private Array<ImageID> draw;
	private Grid grid;
	
	public Preview(Viewport v, TextureAtlas atlas, Skin skin) {
		super(v);
		this.atlas = atlas;
		this.skin = skin;
		initialise();
	}
	
	private void initialise() {
		/*
		grid = new Grid(40, 40, 480, 480, "tmp.png");
		draw = grid.getGrid();		
		
		for (final ImageID cur: draw) {
            cur.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        System.out.println("Pos = (" + cur.getRow() + ", " + cur.getColumn() 
                        + "), status = " + cur.getStatus());
						return false;
                }
            });
		}*/
		super.addActor(new Image(new TextureRegion(new Texture(Gdx.files.internal("ground2.jpg")))));
		//super.addActor(grid);
	}
}
