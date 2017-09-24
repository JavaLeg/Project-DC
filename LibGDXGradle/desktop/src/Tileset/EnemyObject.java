package Tileset;

public class EnemyObject extends Tile {
	// TODO: add more enemy specific fields here
	private double hp;
	
	public EnemyObject(double hp) {
		super();
		this.setHp(hp);
	}
	
	public EnemyObject(double hp, int height, int width) {
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
