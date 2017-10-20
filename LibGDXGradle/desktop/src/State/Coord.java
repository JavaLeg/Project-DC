package State;

import java.io.Serializable;

public class Coord implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x;
	int y;
	
	public Coord() {
		this.x = -1;
		this.y = -1;
	}
	
	public Coord(int x, int y) {
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
	public Coord clone()  {
		return new Coord(x,y);
	}
	
	
	@Override
	public int hashCode(){
		return 17 * x + 8179 * y;
	}
	
	@Override
	public boolean equals(Object arg) {
		if (arg == null) return false;
		if (arg.getClass() != Coord.class) return false;
		Coord c = (Coord) arg;
		return (this.x == c.x && this.y == c.y);
	}
	
	@Override 
	public String toString() {
		return "(" + x + "," + y + ")";
	}
		

}
