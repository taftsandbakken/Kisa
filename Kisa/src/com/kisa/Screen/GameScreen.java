package com.kisa.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kisa.KisaGame.KisaGame;
import com.kisa.KisaGame.World;

public class GameScreen implements Screen {

	KisaGame game;
	World world;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	BitmapFont font;
	float screenW = Gdx.graphics.getWidth();
	float screenH = Gdx.graphics.getHeight();
	
	Stage stage;
	Label titleLabel;
	TextButton mainMenuButton;
	TextButtonStyle textButtonStyle;
	Skin uiSkin;
	
	private FileHandle UI_SKIN_FILE = Gdx.files.internal("data/uiskin.json");
	
	public GameScreen(KisaGame game, String title) {
		this.game = game;
		world = this.game.world;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		stage = new Stage();
		addActors();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		world.kisa.update(deltaTime);
		
		camera.position.x = world.kisa.position.x;
		camera.update();
		
		world.getRenderer().setView(camera);
		world.getRenderer().render();
 
		// render the kisa
		world.renderKisa(deltaTime);
		
//		stage.act(delta);
//		//batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//        stage.draw();
//        batch.end();
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
		
//		titleLabel = new Label("Kisa Game Screen", uiSkin);
//		titleLabel.setPosition(345, 550);
//		titleLabel.setAlignment(Align.center);
//		titleLabel.setColor(Color.BLACK);
//		titleLabel.setFontScale(2);
		
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		
		mainMenuButton = new TextButton("Main Menu", uiSkin);
		mainMenuButton.setSize(screenW * 0.1f, screenH * 0.05f);
		mainMenuButton.setPosition(screenW * 0.9f, screenH * 0.9f);
		
//		stage.addActor(titleLabel);
		stage.addActor(mainMenuButton);

//		stage.addActor(world.kisa.getCurrentAnimatedImage());
		
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
