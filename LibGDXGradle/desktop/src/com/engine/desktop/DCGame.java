package com.engine.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Interface.Splash;
import Interface.Screens.MainMenuScreen;
import Interface.Screens.SplashScreen;

public class DCGame extends Game{

	public SpriteBatch batch;
	public BitmapFont font;
	private DCGame game;
	
	public DCGame() {
		game = this;
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new SplashScreen(game));

	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}


