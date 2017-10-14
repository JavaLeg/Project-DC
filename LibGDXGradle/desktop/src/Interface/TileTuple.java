package Interface;

import java.io.Serializable;

public class TileTuple implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7131326769212761462L;
	private String object;
	private String floor;
	
	public TileTuple(String object, String floor){
		this.setObject(object);
		this.setFloor(floor);
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
