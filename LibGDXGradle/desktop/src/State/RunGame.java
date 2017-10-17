package State;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class RunGame {
	private DynamicGame activeGame;
	private ScheduledExecutorService executor;
	private int stepRate;
	
	
	public RunGame(DynamicGame g, int stepRate) {
		this.activeGame = g;
		this.stepRate = stepRate; // per second
	}
	
	public void run() {	
		System.out.print("Setting up Runnable Threads\n");
		final Runnable stepThread = new Runnable() {
            public void run() { activeGame.step(); }
        };
		executor = new ScheduledThreadPoolExecutor(1);
		
		executor.scheduleAtFixedRate(stepThread, 0, 1000/stepRate, TimeUnit.MILLISECONDS);
	}
	
	public void stop() {
		System.out.print("Shutting down\n");
		executor.shutdown();
	}
}
