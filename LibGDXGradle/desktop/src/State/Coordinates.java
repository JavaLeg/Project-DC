package State;

public class Coordinates {
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
}
