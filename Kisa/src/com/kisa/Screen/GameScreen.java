package com.kisa.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.kisa.KisaGame.KisaGame;

public class GameScreen implements Screen {

	KisaGame game;
	
	SpriteBatch batch;
	BitmapFont font;
	
	Stage stage;
	Label titleLabel;
	TextButton mainMenuButton;
	TextButtonStyle textButtonStyle;
	
	public GameScreen(KisaGame game, String title) {
		this.game = game;
		
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
		System.out.println("show game");
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
		
		titleLabel = new Label("Kisa Game Starting", labelStyle);
		titleLabel.setPosition(345, 550);
		titleLabel.setAlignment(Align.center);
		
		textButtonStyle = new TextButtonStyle();
//		textButtonStyle.up = 
		textButtonStyle.font = font;
		
		mainMenuButton = new TextButton("Main Menu", textButtonStyle);
		mainMenuButton.setPosition(50, 550);
		mainMenuButton.setHeight(50);
		mainMenuButton.setWidth(100);
		
		stage.addActor(titleLabel);
		stage.addActor(mainMenuButton);
		
		addActionListeners();
	}
	
	public void addActionListeners() {
		mainMenuButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("clicked!");
				game.setScreen(game.mainMenuScreen);
//				dispose();
			}
		});
	}
}
