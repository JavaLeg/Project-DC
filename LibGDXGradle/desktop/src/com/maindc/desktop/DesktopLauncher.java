package com.maindc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.engine.desktop.DCGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		// Ensure height to width ratio is 0.6
		
		//config.width = 1920;
		//config.height = 1080;
		config.width = 1000; // for debugging
		config.height = 600;
		config.title = "Project-DC";
		
		new LwjglApplication(new DCGame(), config);
	}
}
