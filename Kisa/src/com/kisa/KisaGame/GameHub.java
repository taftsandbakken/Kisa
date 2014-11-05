package com.kisa.KisaGame;

public class GameHub {

	public KisaGame game;
	public World world;
	
	public GameHub(KisaGame game) {
		this.game = game;
		world = new World(this);
	}
	
	/*
	 * Kisa just died
	 */
	public void justDied() {
		game.gameScreen.justDied();
	}
	
	public void restartLevel() {
		world.restartLevel();
		game.gameScreen.restartLevel();
	}
	
	public void victory() {
		game.gameScreen.victory();
	}
}
