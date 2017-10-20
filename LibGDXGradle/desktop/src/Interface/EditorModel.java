package Interface;

import java.io.Serializable;
import java.util.ArrayList;

import State.Tile;
import Tileset.Enemy;
import Tileset.Item;
import Tileset.Player;

public class EditorModel implements Serializable {
	private static final long serialVersionUID = 1125293377219154163L;
	private TileTuple[][] encodedTable;
	
	public EditorModel(int row, int col) {
		this.encodedTable = new TileTuple[row][col];
		
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				this.encodedTable[i][j] = new TileTuple();
			}
		}
	}
	
	
	public TileTuple[][] getEncodedTable(){
		return encodedTable;
	}
	

	public void display() {
		for(int i = 0; i < 10; i++) {
			System.out.print("Row: " + i + " ");
			
			for(int j = 0; j < 10; j++) {
				System.out.print(encodedTable[i][j].getDynamic());
			}
			System.out.println(" ");
		}
	}
}
