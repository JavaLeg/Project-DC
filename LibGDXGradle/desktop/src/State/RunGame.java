package State;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Interface.GameInputProcessor;


public class RunGame {
	private DynamicGame activeGame;
	private GameInputProcessor input;
	private ScheduledExecutorService executor;
	private int stepRate;
	private boolean isRunning;
	
	
	public RunGame(DynamicGame g, GameInputProcessor i, int stepRate) {
		this.activeGame = g;
		this.stepRate = stepRate; // per second
		this.input = i;
		isRunning = false;
	}
	
	public void run() {	
		System.out.print("Setting up Runnable Threads\n");
		final Runnable stepThread = new Runnable() {
            public void run() { 
            	 try {
            		 activeGame.step(); 
            		 input.step();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
            }
        };
        
		executor = new ScheduledThreadPoolExecutor(1);
		
		executor.scheduleAtFixedRate(stepThread, 1000/stepRate, 1000/stepRate, TimeUnit.MILLISECONDS);
		
		isRunning = true;
		input.setEnabled(true);
	}
	
	public void stop() {
		System.out.print("Shutting down\n");
		executor.shutdown();
		isRunning = false;
		input.setEnabled(false);
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
