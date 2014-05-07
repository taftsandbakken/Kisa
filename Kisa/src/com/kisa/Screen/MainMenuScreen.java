package com.kisa.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kisa.KisaGame.KisaGame;

public class MainMenuScreen implements Screen {
	
	KisaGame game;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	Stage stage;
	TextButton newGameButton;
	TextButton quitButton;
	TextButtonStyle textButtonStyle;
	BitmapFont font;
	
	public MainMenuScreen(KisaGame game) {
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600); 
		this.game = game;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		addButtons();
	}

	@Override
	public void render(float delta) {
		stage.act();
		batch.setProjectionMatrix(camera.combined);
        batch.begin();
        stage.draw();
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		System.out.println("show menu");
		
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
		batch.dispose();
		font.dispose();
        stage.dispose();
	}
	
	public void addButtons() {
		textButtonStyle = new TextButtonStyle();
		font = new BitmapFont();
		textButtonStyle.font = font;
		
		newGameButton = new TextButton("New Game", textButtonStyle);
		newGameButton.setPosition(300, 300);
		newGameButton.setHeight(50);
		newGameButton.setWidth(100);
		
		quitButton = new TextButton("Quit", textButtonStyle);
		quitButton.setPosition(300, 500);
		quitButton.setHeight(50);
		quitButton.setWidth(100);
		
		stage.addActor(newGameButton);
		stage.addActor(quitButton);
		
		addActionListeners();
	}
	
	public void addActionListeners() {
		newGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(game.gameScreen);
			}
		});
	
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//game.setScreen(game.gameOverScreen);
			}
		});
	}
}
