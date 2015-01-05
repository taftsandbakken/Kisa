package com.kisa.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kisa.KisaGame.Assets;
import com.kisa.KisaGame.Data;
import com.kisa.KisaGame.KisaGame;

public class MainMenuScreen implements Screen {
	
	KisaGame game;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	BitmapFont font;
	Texture texture;
	float screenW = Gdx.graphics.getWidth();
	float screenH = Gdx.graphics.getHeight();
	
	Stage stage;
	Label titleLabel;
	TextButton newGameButton;
	TextButton quitButton;
	TextButtonStyle textButtonStyle;
	Sprite sprite;
	Skin uiSkin;
	
	public MainMenuScreen(KisaGame game, String title) {
		this.game = game;
		
		camera = new OrthographicCamera(1, screenH / screenW);
//		camera.setToOrtho(false, 800, 600);
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		stage = new Stage();
		addActors();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		batch.setProjectionMatrix(camera.combined);
        batch.begin();
        stage.draw();
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		font.dispose();
        stage.dispose();
        batch.dispose();
	}
	
	public void addActors() {
		uiSkin = new Skin(Data.UI_SKIN_FILE);
		font = new BitmapFont();

		Image background = Assets.getNewImage(Data.KISA_TITLE_PAGE, Data.SCREEN_W * 0.1f, Data.SCREEN_H * 0.05f, Data.SCREEN_W * 0.8f, Data.SCREEN_H * 0.8f, 170, 173);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		
		titleLabel = new Label("Kisa", uiSkin);
		titleLabel.setSize(screenW * 0.3f, screenH * 0.1f);
		titleLabel.setPosition(screenW / 2 - titleLabel.getWidth() / 2, screenH * 0.9f);
		titleLabel.setAlignment(Align.center);
		
		textButtonStyle = new TextButtonStyle();  //skin.get("bigButton", TextButtonStyle.class);
		textButtonStyle.font = font;
		
		newGameButton = new TextButton("New Game", uiSkin);
		newGameButton.setSize(screenW * 0.2f, screenH * 0.1f);
		newGameButton.setPosition(screenW / 3 - newGameButton.getWidth() / 2, screenH * 0.88f);
		//newGameButton.setColor(Color.BLACK);
		
		quitButton = new TextButton("Quit", uiSkin);
		quitButton.setSize(screenW * 0.2f, screenH * 0.1f);
		quitButton.setPosition(screenW / 3 * 2 - quitButton.getWidth() / 2, screenH * 0.88f);
		
		stage.addActor(background);
		//commented because the background pic has a title
		//stage.addActor(titleLabel);
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
			}
		});
	
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//make a confirmation dialog here
				System.out.println("Exiting");
				Gdx.app.exit();
				//game.setScreen(game.gameOverScreen);
			}
		});
	}
}
