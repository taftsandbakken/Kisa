package com.kisa.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kisa.KisaGame.KisaGame;

public class GameScreen implements Screen {

	KisaGame game;
	
	public GameScreen(KisaGame game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		if(Gdx.input.justTouched())
			game.setScreen(game.mainMenuScreen);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		System.out.println("show game");
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
