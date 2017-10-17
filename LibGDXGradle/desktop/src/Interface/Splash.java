package Interface;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.engine.desktop.DCGame;

import Interface.Screens.MainMenuScreen;
import externals.GifDecoder;

public class Splash implements Screen{

	private SpriteBatch batch;
	private Animation<TextureRegion> animation;
	private AssetManager assetManager;
	private float elapsed;
	private float startTime;
	private DCGame g;
	
	public Splash(DCGame g) {
		this.g = g;
		assetManager = new AssetManager();
	}
	
	@Override
	public void show() {
		startTime = TimeUtils.millis();
		batch = new SpriteBatch();
		animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, 
				Gdx.files.internal("Splash/splash.gif").read());
		
	}

	@Override
	public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsed), 20.0f, 20.0f);        
        batch.end();
        
        //System.out.println(TimeUtils.timeSinceMillis((long) startTime));
        
        if(TimeUtils.timeSinceMillis((long) startTime) > 5000){
            g.setScreen(new MainMenuScreen(g));
        }
        
        if(Gdx.input.justTouched())
        	g.setScreen(new MainMenuScreen(g));
        

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
		// TODO Auto-generated method stub
		
	}

}
