package Interface;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

// import Interface.Stages.Selections.CreatureSelection;
// import Interface.Stages.Selections.EditorSelection;
// import Interface.Stages.Selections.TerrainSelection;
import Interface.Stages.Selections.ToolbarSelection;


/*
 * Name change from image stack as it seems more appropriate
 */
public class PreviewCell extends Stack {
	
	//private int status;		// 0 for empty, 1 for ground, 2 for wall, 3, 4, 5, etc.
	//private int column;
	//private int row;
	private Image terrain;
	private Image object;
	private Image empty;
	
	private TextureRegion ter;
	private TextureRegion obj;
	
	private boolean terrain_exists;
	private boolean object_exists;
	
	/*
	 * Initialises to empty
	 */
	public PreviewCell() {
		super();
		Texture cur_texture = new Texture(Gdx.files.internal("EditorScreen/empty_grid.png"));
		//TextureRegion gnd = new TextureRegion(cur_texture, 40, 40);
		// Changed, we can lock grid size. Done in the loop in preview
		TextureRegion gnd = new TextureRegion(cur_texture);
		terrain = new Image(gnd);
		empty = terrain;
		
		terrain_exists = false;
		object_exists = false;
		
		this.add(terrain);
		
		//this.status = status;
		//this.row = row;
		//this.column = column;
	}
	
		
	public void setTexture(TextureRegion i, ToolbarSelection ts) {
		
		/*
		 * Only two cases we need to consider
		 * Terrain: Placed with logical order
		 * Other: Just place it damn man, just do it.
		 */
		if (i == null) return;			// If no current texture, don't do shit
		
		this.clearChildren();
		
		switch(ts) {
		case TERRAIN:
			ter = i;
			terrain_exists = true;
			terrain = new Image(i);
			this.add(terrain);
			
			if (object_exists == true) {
				this.add(object);
			}
			break;
		default:
			obj = i;
			object_exists = true;
			object = new Image(i);
			this.add(terrain);
			this.add(object);
			break;
		}
	}
	
	/*
	 * Clear the cell
	 * Keeps the empty texture 
	 */
	
	@Override
	public void clear() {
		this.clearChildren();
		object_exists = false;
		terrain_exists = false;
		obj = null;
		ter = null;
		terrain = empty;
		this.add(empty);
	}
	
		
	/*
	 * When setting all to ground / empty
	 */
	public void setToTerrain(Texture nxt) {
		this.clearChildren();
		object_exists = false;
		terrain = new Image(nxt);
		this.add(terrain);
	}
	
	/*
	 * Checks if this grid is valid (can't have object on null cell)
	 */
	public boolean isValid() {
		if (object_exists == true && terrain_exists == false) return false;
		return true;
	}
	
	public void clearTerrainVar() {
		terrain = empty;
	}
	
	public String getTerrainPath() {
		if (ter != null) {
			String k = ((FileTextureData)ter.getTexture().getTextureData()).getFileHandle().path();
			return k;
		}
		return null;
	}
	
	public String getObjectPath() {
		if (obj != null) {
			String k = ((FileTextureData)obj.getTexture().getTextureData()).getFileHandle().path();
			return k;
		}
		return null;
	}
}
