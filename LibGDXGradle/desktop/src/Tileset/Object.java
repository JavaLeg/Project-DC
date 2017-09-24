package Tileset;

import java.awt.image.BufferedImage;

public class Object {
	private BufferedImage image;
	private int height;
	private int width;
	
	public Object() {
		this.height = 16;
		this.width = 16;
	}
	
	public Object(int height, int width) {
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
