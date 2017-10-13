package State;

import java.io.Serializable;

public class Coordinates implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x;
	int y;
	
	public Coordinates() {
		this.x = -1;
		this.y = -1;
	}
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	
	@Override
	public Coordinates clone() {
		return new Coordinates(x, y);
	}
	
	
	@Override
	public int hashCode(){
		return 17 * x + 8179 * y;
	}
	
	@Override
	public boolean equals(Object arg) {
		if (arg == null) return false;
		if (arg.getClass() != Coordinates.class) return false;
		Coordinates c = (Coordinates) arg;
		return (this.x == this.y && c.x == c.y);
	}
		

}
