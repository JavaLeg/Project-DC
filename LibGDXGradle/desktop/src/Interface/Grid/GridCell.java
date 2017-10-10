package Interface.Grid;

public class GridCell {
	private static final gridWidth = 40;
	private static final gridHeight = 40;
	
	public final int xPos;
	public final int yPos;
	
	public GridCell(int x, int y) {
		this.xPos = x;
		this.yPos = y;
	}
}
