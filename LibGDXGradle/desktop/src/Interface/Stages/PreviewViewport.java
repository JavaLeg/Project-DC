package Interface.Stages;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;

/*
 * Required to subclass viewport as any updated bounds will be overwritten in
 * the parent FitViewport class
 */
public class PreviewViewport extends Viewport{

	private Scaling scaling;

	/** Creates a new viewport using a new {@link OrthographicCamera}. */
	public PreviewViewport (float worldWidth, float worldHeight) {
		this.scaling = Scaling.fit;
		setWorldSize(worldWidth, worldHeight);
		setCamera(new OrthographicCamera());
	}

	public PreviewViewport (float worldWidth, float worldHeight, Camera camera) {
		this.scaling = Scaling.fit;
		setWorldSize(worldWidth, worldHeight);
		setCamera(camera);
	}

	@Override
	public void update (int screenWidth, int screenHeight, boolean centerCamera) {
		Vector2 scaled = scaling.apply(getWorldWidth(), getWorldHeight(), screenWidth, screenHeight);
		int viewportWidth = Math.round(scaled.x);
		int viewportHeight = Math.round(scaled.y);

		// Center.
		// Ratio used is 7/20 for the split coordinate of the screen
		//Double ratio = (double) (7/20);
		
		//setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);
		//setScreenBounds(0, 0, viewportWidth, viewportHeight);
		setScreenBounds((screenWidth*7/40), (screenHeight - viewportHeight) / 2 - 40, viewportWidth, viewportHeight);

		apply(centerCamera);
	}

	public Scaling getScaling () {
		return scaling;
	}

	public void setScaling (Scaling scaling) {
		this.scaling = scaling;
	}
}
