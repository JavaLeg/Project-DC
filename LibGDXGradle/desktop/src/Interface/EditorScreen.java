package Interface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class EditorScreen implements Screen {
	
    private SpriteBatch batch;
    private Texture texture;
    private Array <Sprite> sprites;		// The grids
	
    // Dimensions of screen
    private int height;			
    private int width;
    public Vector3 touchPos;			// The click spot
 

	// Show only operates once, after it will render
	@Override
	public void show() {
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		sprites = new Array<Sprite>();
        batch = new SpriteBatch();
        touchPos = new Vector3();
        Texture cur_texture = new Texture("tmp.jpg");
        
        // Create grid system and put into sprites to be drawn
        for (int j = 0; j < height; j = j + 50) {
	        for (int i = 0; i < width; i = i + 50) {
	            Sprite cur_sprite = new Sprite(cur_texture);
	            cur_sprite.setSize(50, 50);
	            cur_sprite.setCenter(i, j);
	            sprites.add(cur_sprite);
	        }
        } 
	}
	
	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        // Draw sprites and listen for inputs.
		for (Sprite cur_sprite : sprites) {
        		cur_sprite.draw(batch);
        }
		if (Gdx.input.isTouched()) {
			// Insert into touchPos to calculate later?
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0); 
			System.out.println("X = " + touchPos.x + ", Y = " + touchPos.y);
		}
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
        batch.dispose();
        texture.dispose();
		// TODO Auto-generated method stub
		
	}

}
