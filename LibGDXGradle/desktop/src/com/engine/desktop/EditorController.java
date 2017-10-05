package com.engine.desktop;

import java.io.IOException;

import com.badlogic.gdx.maps.tiled.TiledMap;

import State.Coordinates;
import State.State;
import State.Tile;

public class EditorController {
	private State state;
	private Tile[][] map;

	private Coordinates selected;
	private Object selectedObj;
	
	private SaveSys saveHandle;
	
	public EditorController() throws IOException {
		this.m = new TiledMap();
		this.state = new State();
		this.saveHandle = new SaveSys();
		
	}
	
	/*
	 * Any click on the preview screen in EditorScreen should call this function
	 */
	public void select(int row, int col) {

		selected.setX(row);
		selected.setY(col);
		
		//if(!state.isTileEmpty(selected)) COMPILE
		//	getSelection();
	}
	
	/*
	 * After selecting a tile, if not empty
	 * The gameObj (w/e it is that resides on the tile) should be selected
	 */
	public void getSelection() {
		//selectedObj = state.getObject(selected); COMPILE
	}
	
	public void setTerrain(TERRAIN t) {
		
		switch(t) {
		case WALL:
			//state.setWall(selected); COMPILE
			break;
		case TRAP:
			//state.setTrap(selected); COMPILE
			break;
		}


	}
	
	public void setGameObj(GAME_OBJ g) {
		switch(g) {
		
		// Item should be its own thing??
		// Can add an enum later
		case ITEM:
			//state.setItem(selected); COMPILE
			break;
		case ENEMY:
			//state.setEnemy(selected); COMPILE
			break;
		case PLAYER:
			state.setPlayer(selected);
		}
	}
	

	/*
	 * Should clear the tile regardless of what terrain/item
	 */
	public void clearTile() {
		//state.deleteTile(selected); COMPILE
	}
	
	public void saveMap(String filename) {
		//saveHandle.Save(state, filename); COMPILE
	}
	
	
}
