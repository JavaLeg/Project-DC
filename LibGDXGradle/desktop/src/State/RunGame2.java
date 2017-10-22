package State;

import java.util.concurrent.ScheduledExecutorService;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.TimeUtils;

import Interface.GameInputProcessor;

public class RunGame2 extends ApplicationAdapter {
	private DynamicGame activeGame;
	private GameInputProcessor input;
	private int stepRate;
	private boolean isRunning;
	private double timeOfLastUpdate;
	
	
	//private double currentTime;
    //private double accumulator;
    //private float step = 1.0f / 60.0f;
	
	
	public RunGame2(DynamicGame g, GameInputProcessor i, int stepRate) {
		
		this.activeGame = g;
		this.stepRate = stepRate; // per second
		this.input = i;
		isRunning = false;
	}
	
	
	public void render() {
		super.render();
		if (isRunning) {
			double newTime = TimeUtils.millis() / 1000;
			double diff = newTime - timeOfLastUpdate;
			
			if (diff >= stepRate/1000) {
				timeOfLastUpdate -= stepRate/1000;
				activeGame.step();
				input.step();
			}
			
			/*
			double frameTime = Math.min(newTime - currentTime, 0.25);
	        float deltaTime = (float)frameTime;

	        currentTime = newTime;

	        while (accumulator >= step) {
	            activeGame.step();
	            input.step();
	            accumulator -= step;
	        }
	        */
		}
	}
	
	public void run() {
		timeOfLastUpdate = TimeUtils.millis() / 1000;
		isRunning = true;
	}
	
	public void stop() {
		isRunning = false;
	}
	
	
	
}
