package Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Preview {
	
	private SpriteBatch batch;
    private Array <Sprite> sprites;		// The grids
    
    private Stage stage;
    private Viewport viewport;
    
    // Dimensions of screen
    //private int height;			
    //private int width;
    public Vector3 touchPos;
    
    private static final int WORLD_WIDTH  = 250;
    private static final int WORLD_HEIGHT = 250;
	
	public Preview(SpriteBatch batch) {
		//this.batch = batch;
		
        OrthographicCamera camera = new OrthographicCamera();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        
        viewport = new FitViewport(WORLD_HEIGHT, WORLD_WIDTH, camera);
        viewport.apply();
        stage = new Stage(viewport); 
        
		int height = Gdx.graphics.getHeight();
		int width = Gdx.graphics.getWidth();
		
		sprites = new Array<Sprite>();
        //batch = new SpriteBatch();
        touchPos = new Vector3();
        
        Texture cur_texture = new Texture("tmp.jpg");
        
        // Create grid system and put into sprites to be drawn
        for (int j = 0; j < width; j = j + 25) {
	        for (int i = 0; i < height; i = i + 20) {
	            Sprite cur_sprite = new Sprite(cur_texture);
	            cur_sprite.setSize(25, 20);
	            cur_sprite.setCenter(j, i);
	            sprites.add(cur_sprite);
	        }
        } 
	}
	/*
	public void batchDraw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        
        batch.begin();
        // Draw sprites and listen for inputs.
		for (Sprite cur_sprite : sprites) {
        	cur_sprite.draw(batch);
        }
		
		// X range is 160->635
		// Y range is 0->480
		if (Gdx.input.isTouched()) {
			// Can reset texture using sprite.setTexture(...);
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			int col  = (int) ((touchPos.x - 160) / 25);
			int row = (int) (touchPos.y / 20);
			System.out.println("Row = " + row + ", Col = " + col);
			
			//model.select(row, col);
		}
        batch.end();
	}
	*/
	
	public Stage getStage() {
		return stage;
	}
	
	public Array<Sprite> getSprites(){
		return sprites;
	}
}
