package com.engine.desktop;

import State.Coordinates;
import State.State;
import Tileset.GameObject.ObjectType;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class EditorController {
	private State state;
	private ObjectType obj;
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
		coord.setY(row);
						
		if(!state.isTileEmpty(coord)) {
			getSelection();
		}else if(obj != null) {
			place();
		}
	}
	
	/*
	 * Quick-place object, according to the coord coordinate
	 */
	private void place() {
		state.setObject(coordObj, coord);
	}

	
	/*
	 * Retrieve any objects on a tile
	 */
	public ArrayList<ObjectType> getSelection() {
		return obj = state.getObj(coord);
	}
	
	/*
	 * Object selection from editor menu
	 */
	public void selectObj(ObjectType t) {
		obj = t;
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
