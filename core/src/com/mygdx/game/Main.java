package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Main extends Game {
	SpriteBatch batch;

	Skin skin;
	Main game;
	static MyManager manager;
	public Main(MyManager manager)
	{
		this.game = this;
		Main.manager=manager;
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		//this.setScreen(new MenuScreen(this));
		skin = new Skin (Gdx.files.internal("skin/glassy-ui.json"));
		Gdx.graphics.setTitle("Kocie Opowieści");
		this.setScreen(new LoadingScreen(this));
	}

	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
