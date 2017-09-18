package com.engine.desktop;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;

/*
 * Object generation
 */
public class ObjectGen {
	
	// Create empty object (no texture)
	public TextureMapObject generateObject() {
		return new TextureMapObject();
	}
	
	// Create textured object
	public TextureMapObject generateObject(TextureRegion textureRegion) {
		return new TextureMapObject();
	}
}
