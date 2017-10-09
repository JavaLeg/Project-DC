package com.engine.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Interface.MainMenuScreen;

public class DCGame extends Game{

	public SpriteBatch batch;
	public BitmapFont font;
	private Game game;
	
	public DCGame() {
		game = this;
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(game));
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}


