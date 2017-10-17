package Interface;

import java.io.Serializable;
import java.util.ArrayList;

import State.Tile;

public class EditorModel implements Serializable {
	private int row;
	private int col;
	private static final long serialVersionUID = 1125293377219154163L;
	private TileTuple[][] modelPaths;
	
	public EditorModel(int row, int col) {
		this.row = row;
		this.col = col;
		modelPaths = new TileTuple[row][col];
	}
	
	public void setTile(TileTuple t, int row, int col) {
		this.modelPaths[row][col] = t;
	}
	
	public TileTuple[][] getmodelPaths(){
		return modelPaths;
	}
	
	/*
	 * For debugging purposes
	 * 1 - floor
	 * 2 - object
	 */
	public void display(int layer) {
		for(int i = 0; i < row; i++) {
			System.out.print("Row: " + i + " ");
			
			for(int j = 0; j < col; j++) {
				if(layer == 1) {
					System.out.print(modelPaths[i][j].getFloor());
				}else if(layer == 2) {
					System.out.print(modelPaths[i][j].getObject());
				}
				
			}
			System.out.println(" ");
		}
	}
}
