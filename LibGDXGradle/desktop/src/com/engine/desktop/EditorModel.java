package com.engine.desktop;

import java.io.IOException;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class EditorModel {
	private State state;
	private TiledMap m;
	private Tile[][] map;
	private Tile selectedTile;
	private int selected[];
	private SaveSys saveHandle;
	
	public EditorModel() throws IOException {
		this.m = new TiledMap();
		this.state = new State();
		this.saveHandle = new SaveSys();
		
	}
	
	/*
	 * Any click on the preview screen in EditorScreen should call this function
	 */
	public void select(int row, int col) {
		selected[0] = row;
		selected[1] = col;
	}
	
	public void setTerrain(TERRAIN t) {
		
		switch(t) {
		case WALL:
			state.setWall(selected);
			break;
		case TRAP:
			state.setTrap(selected);
			break;
		}


	}
	
	public void setGameObj(GAME_OBJ g) {
		switch(g) {
		
		// Item should be its own thing??
		// Can add an enum later
		case ITEM:
			state.setItem(selected);
			break;
		case ENEMY:
			state.setEnemy(selected);
			break;
		case PLAYER:
			state.setPlayer(selected);
		}
	}

	/*
	 * Should clear the tile regardless of what terrain/item
	 */
	public void clearTile() {
		state.deleteTile(selected);
	}
	
	public void saveMap(String filename) {
		saveHandle.Save(state, filename);
	}
	
	
}
