package Interface;

import java.io.Serializable;
import java.util.ArrayList;

import Interface.Stages.Preview;

public class EditorModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1125293377219154163L;
	private int rows;
	private int cols;
	private String[][] terrain_paths;
	private String[][] object_paths;
	
	public EditorModel(int col, int row) {
		rows = row;
		cols = col;
		terrain_paths = new String[col][row];
		object_paths = new String[col][row];
	}
	
	public void convert(ArrayList<PreviewCell> copy) {
		for(int i = 0; i < cols*rows ; i++) {
			int offset = i % 11;
			int y_offset = i/11;
			terrain_paths[y_offset][offset] = copy.get(i).getTerrainPath();
			object_paths[y_offset][offset] = copy.get(i).getObjectPath();
		}
	}
}
