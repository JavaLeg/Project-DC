package Tileset;

public class PlayerObject extends Object {
	private double hp;
	
	public PlayerObject(double hp) {
		super();
		this.setHp(hp);
	}
	
	public PlayerObject(double hp, int height, int width) {
		super(height, width);
		this.setHp(hp);
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}
}
