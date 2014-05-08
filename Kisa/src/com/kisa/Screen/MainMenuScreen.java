package com.kisa.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kisa.KisaGame.KisaGame;

public class MainMenuScreen implements Screen {
	
	KisaGame game;
	
//	OrthographicCamera camera;
//	SpriteBatch batch;
	SpriteBatch batch;
	BitmapFont font;
	
	Stage stage;
	Label titleLabel;
	TextButton newGameButton;
	TextButton quitButton;
	TextButtonStyle textButtonStyle;
	
	public MainMenuScreen(KisaGame game, String title) {
		this.game = game;
		
//		camera = new OrthographicCamera();
//      camera.setToOrtho(false, 800, 600);
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		stage = new Stage();
		addActors();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//		super.render(delta);
		stage.act(delta);
//		batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        font.draw(batch, "Hello World", 400, 300);
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
		Gdx.input.setInputProcessor(stage);
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
		font.dispose();
        stage.dispose();
        batch.dispose();
	}
	
	public void addActors() {
		font = new BitmapFont();
//		font.setColor(Color.RED);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		
		titleLabel = new Label("Kisa", labelStyle);
		titleLabel.setPosition(385, 550);
		titleLabel.setAlignment(Align.center);
		
		textButtonStyle = new TextButtonStyle();
//		textButtonStyle.up = 
		textButtonStyle.font = font;
		
		newGameButton = new TextButton("New Game", textButtonStyle);
		newGameButton.setPosition(350, 400);
		newGameButton.setHeight(50);
		newGameButton.setWidth(100);
		
		quitButton = new TextButton("Quit", textButtonStyle);
		quitButton.setPosition(350, 350);
		quitButton.setHeight(50);
		quitButton.setWidth(100);
		
		stage.addActor(titleLabel);
		stage.addActor(newGameButton);
		stage.addActor(quitButton);
		
		addActionListeners();
	}
	
	public void addActionListeners() {
		newGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("clicked!");
				game.setScreen(game.gameScreen);
//				dispose();
			}
		});
	
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Exiting");
				Gdx.app.exit();
				//game.setScreen(game.gameOverScreen);
			}
		});
	}
}
