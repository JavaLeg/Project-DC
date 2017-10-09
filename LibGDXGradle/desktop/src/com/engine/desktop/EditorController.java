package com.engine.desktop;

import State.Coordinates;
import State.State;
import Tileset.GameObject;
import Tileset.GameObject.ObjectType;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;

import State.Coordinates;
import State.State;
import State.Tile;

public class EditorController {
	private State state;
	private GameObject obj;
	private SaveSys saveHandle;
	private Coordinates coord;

	
	/*
	 * Constructor initializes a save handle and a state (model) instance
	 */
	public EditorController() throws IOException {

		this.state = new State();
		this.saveHandle = new SaveSys();
		this.coord = new Coordinates();
	}
		
	/*
	 * Any click on the preview screen in EditorScreen should call this function
	 */
	public void select(int row, int col) {
		
		coord.setX(row);
		coord.setY(col);
						
		if(state.getTile(coord).hasObject()) {
			get();
		}else if(obj != null) {
			place();
		}
	}
	
	/*
	 * Quick-place object, according to the coord coordinate
	 */
	private void place() {
		state.setObject(obj, coord);
	}
	
	/*
	 * Retrieve any objects on a tile
	 */
	public GameObject get() {
		return state.getTile(coord).getObject();
	}
	/*
	 * Object selection from editor menu
	 */
	public void selectObj() {
		obj = state.getTile(coord).getObject();
	}
	

	/*
	 * Should clear the tile regardless of what terrain/item
	 */
	public void clearTile() {
		state.deleteTile(coord);
	}
	
	public void saveMap(String filename) throws IOException {
		saveHandle.Save(state, filename);

	}
	
	public void loadMap(String filename) throws IOException, ClassNotFoundException{
		this.state = saveHandle.Load(filename);
	}
	
}
