package com.kisa.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.kisa.KisaGame.KisaGame;

public class GameScreen implements Screen {

	KisaGame game;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	BitmapFont font;
	
	Stage stage;
	Label titleLabel;
	TextButton mainMenuButton;
	TextButtonStyle textButtonStyle;
	Skin uiSkin;
	
	private FileHandle UI_SKIN_FILE = Gdx.files.internal("data/uiskin.json");
	
	public GameScreen(KisaGame game, String title) {
		this.game = game;
		
//		camera = new OrthographicCamera();
//      camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		stage = new Stage();
		addActors();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.kisa.move();
		
		stage.act(delta);
//		batch.setProjectionMatrix(camera.combined);
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
		uiSkin = new Skin(UI_SKIN_FILE);
		font = new BitmapFont();
//		font.setColor(Color.RED);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		
		titleLabel = new Label("Kisa Game Screen", uiSkin);
		titleLabel.setPosition(345, 550);
		titleLabel.setAlignment(Align.center);
		titleLabel.setColor(Color.BLACK);
		titleLabel.setFontScale(2);
		
		textButtonStyle = new TextButtonStyle();
//		textButtonStyle.up = 
		textButtonStyle.font = font;
		
		mainMenuButton = new TextButton("Main Menu", uiSkin);
		mainMenuButton.setPosition(20, 550);
		mainMenuButton.setHeight(30);
		mainMenuButton.setWidth(95);
		
//		Texture.setEnforcePotImages(false);
		
		stage.addActor(titleLabel);
		stage.addActor(mainMenuButton);

		stage.addActor(game.kisa.getCurrentAnimatedImage());
//		stage.addActor(game.kisa.getAnimations().getRun());
//		stage.addActor(game.kisa.getAnimations().getIdle());
		
		addActionListeners();
	}
	
	public void addActionListeners() {
		mainMenuButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(game.mainMenuScreen);
			}
		});
	}
}
