package Tileset;

public class Enemy extends GameObject {
	private double hp;
	// Other enemy fields
	
	public Enemy(double hp) {
		super();
		this.setHp(hp);
	}
	
	public Enemy(double hp, int height, int width) {
		super(height, width);
		this.setHp(hp);
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}
	
	/*
	 public void nextMove();
	 */
}
