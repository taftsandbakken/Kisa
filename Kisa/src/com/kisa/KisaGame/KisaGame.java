package com.kisa.KisaGame;

import com.badlogic.gdx.Game;
import com.kisa.Screen.*;

public class KisaGame extends Game {

	public MainMenuScreen mainMenuScreen;
	public GameScreen gameScreen;
	
	public GameHub gameHub;
	public boolean showDPad;
	
	public KisaGame(boolean showDPad) {
		this.showDPad = showDPad;
	}
	
	@Override
	public void create() {
		gameHub = new GameHub(this);
		mainMenuScreen = new MainMenuScreen(this, "Main Menu");
		gameScreen = new GameScreen(this, "Kisa", showDPad);
		setScreen(gameScreen);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {	
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
