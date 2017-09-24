package com.engine.desktop;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class EditorModel {
	private State s;
	private TiledMap m;
	
	public EditorModel() {
		m = new TiledMap();
		s = new State();
	}
	
	
}
