package com.engine.desktop;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class EditorModel {
	private State state;
	private TiledMap m;
	private Tile[][] map;
	private Tile selectedTile;
	private int selected[];
	
	public EditorModel() {
		this.m = new TiledMap();
		this.state = new State();
		this.saveHandle = new SaveSys();
		
	}
	
	public void select(int row, int col) {
		selected[0] = row;
		selected[1] = col;
	}
	
	public void addWall() {
		state.setTerrain(selected, TERRAIN.WALL);
	}
	

	
	public void addItem() {
		state.setTerrain(selected, GAME_OBJ.ITEM);
	}
	
	public void clearTile() {
		state.deleteTile();
	}
	
	
}
