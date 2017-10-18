package Interface;

import java.io.Serializable;

import Tileset.DynamicObject.DynamicObjectType;

public class ObjectModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4622526088784573868L;
	private double hp;
	private double dmg;
	private String path;
	private String name;
	private DynamicObjectType ID;
	
	public ObjectModel(double hp, double dmg, String path, String name, DynamicObjectType ID) {
		this.hp = hp;
		this.dmg = dmg;
		this.path = path;
		this.name = name;
		this.setID(ID);
	}
	
	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getDmg() {
		return dmg;
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DynamicObjectType getID() {
		return ID;
	}

	public void setID(DynamicObjectType iD) {
		ID = iD;
	}
	
}
