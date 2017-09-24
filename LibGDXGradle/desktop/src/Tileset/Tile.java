package Tileset;

import java.awt.image.BufferedImage;

public class Tile {
	private BufferedImage image;
	private int height;
	private int width;
	
	public Tile() {
		this.height = 16;
		this.width = 16;
	}
	
	public Tile(int height, int width) {
		this.height = height;
		this.width = width;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
