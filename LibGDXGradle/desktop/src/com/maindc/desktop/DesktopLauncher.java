package com.maindc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.engine.desktop.DCGame;
import com.maindc.DC_Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		
	      config.width = 800;
	      config.height = 480;
	      config.title = "Project-DC";
		
	      new LwjglApplication(new DCGame(), config);
	}
}
