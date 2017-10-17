package Interface;

import java.io.Serializable;

import Tileset.GameObject.ObjectType;

public class TileTuple implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7131326769212761462L;
	private String object;
	private String floor;
	public ObjectType ID;
	
	public TileTuple(String object, String floor, ObjectType cur){
		this.setObject(object);
		this.setFloor(floor);
		this.ID = cur;
	}
	
	/*
	 * Allows reassembly of objects using their types
	 */
	public ObjectType getID() {
		return this.ID;
	}
	
	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}
}
