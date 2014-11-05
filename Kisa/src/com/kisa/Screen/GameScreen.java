package com.kisa.Screen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.kisa.KisaGame.Assets;
import com.kisa.KisaGame.Data;
import com.kisa.KisaGame.GameHub;
import com.kisa.KisaGame.Kisa;
import com.kisa.KisaGame.KisaGame;
import com.kisa.KisaGame.World;

public class GameScreen implements Screen {

	GameHub gameHub;
	World world;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	Stage stage;
	Label titleLabel;
	Image mainMenuImage;
	Image leftImage;
	Image rightImage;
	Image runImage;
	Image upImage;
	boolean showDPad;
	
	boolean goLeft = false, goRight = false, goRun = false, goUp = false;
	
	public GameScreen(KisaGame game, String title, boolean showDPad) {
		this.gameHub = game.gameHub;
		world = gameHub.world;
		this.showDPad = showDPad;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();
		
		batch = new SpriteBatch();
		
		stage = new Stage();
		loadScreen();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		if(world.isVictoryFlag())
			gameHub.victory();
		
		if(world.kisa.isDead() && (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)))
			gameHub.restartLevel();			
		
		if(!gamePaused() && !world.kisa.isDead())
			world.kisa.update(deltaTime, goLeft, goRight, goRun, goUp);

		camera.position.x = world.kisa.position.x;
		camera.position.y = world.kisa.position.y; //add this to move camera up and down
		camera.update();
		
		world.getRenderer().setView(camera);
		world.getRenderer().render();
		
		// render the kisa
		world.renderKisa(deltaTime);
		
		stage.act(delta);
		//batch.setProjectionMatrix(camera.combined);
        batch.begin();
        stage.draw();
		//stage.setViewport(width, height, true);
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
        stage.dispose();
        batch.dispose();
	}
	
	public void loadScreen() {
		stage.clear();
		addActors();
	}

	public void addActors() {
//		titleLabel = new Label("Kisa Game Screen", Assets.uiSkin);
//		titleLabel.setPosition(345, 550);
//		titleLabel.setAlignment(Align.center);
//		titleLabel.setColor(Color.BLACK);
//		titleLabel.setFontScale(2);
		
//		mainMenuButton = new TextButton("Main Menu", Assets.uiSkin);
//		mainMenuButton.setSize(Data.SCREEN_W * 0.13f, Data.SCREEN_H * 0.05f);
//		mainMenuButton.setPosition(Data.SCREEN_W * 0.85f, Data.SCREEN_H * 0.93f);
		
		mainMenuImage = Assets.getNewImage(Data.UI_GEAR_FILE, 
				(float)(Data.SCREEN_W - (Data.SCREEN_W * 0.1)), 
				(float)(Data.SCREEN_H - (Data.SCREEN_H * 0.1)), 
				(float)(Data.SCREEN_H * 0.1), 
				(float)(Data.SCREEN_H * 0.1), 
				100, 100);

		if(showDPad) {
			leftImage = Assets.getNewImage(Data.UI_LEFT_FILE, 10, 10, Data.UI_INPUT_WIDTH, Data.UI_INPUT_WIDTH, 400, 400);
			rightImage = Assets.getNewImage(Data.UI_RIGHT_FILE, Data.UI_INPUT_WIDTH + 10 + 10, 10, Data.UI_INPUT_WIDTH, Data.UI_INPUT_WIDTH, 400, 400);
			runImage = Assets.getNewImage(Data.UI_RUN_FILE, Data.SCREEN_W - Data.UI_INPUT_WIDTH - Data.UI_INPUT_WIDTH - 30, 10, Data.UI_INPUT_WIDTH, Data.UI_INPUT_WIDTH, 600, 570);
			upImage = Assets.getNewImage(Data.UI_UP_FILE, Data.SCREEN_W - Data.UI_INPUT_WIDTH - 10, 10, Data.UI_INPUT_WIDTH, Data.UI_INPUT_WIDTH, 400, 400);
			stage.addActor(leftImage);
			stage.addActor(rightImage);
			stage.addActor(runImage);
			stage.addActor(upImage);
		}
		
//		stage.addActor(titleLabel);
		stage.addActor(mainMenuImage);

//		stage.addActor(world.kisa.getCurrentAnimatedImage());
		
		addActionListeners();
	}
	
	public void addActionListeners() {
		mainMenuImage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameHub.game.setScreen(gameHub.game.mainMenuScreen);
			}
		});
		
		if(showDPad) {
			leftImage.addListener(new InputListener() {
				@Override
		        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					goLeft = false;
				}
				
				@Override
		        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					goLeft = true;
					return true;
				}
				
				public void enter(InputEvent event, float x, float y, int pointer, Actor toActor) {
					goLeft = true;
				}
				
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					goLeft = false;
				}
			});
	
			rightImage.addListener(new InputListener() {
				@Override
		        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					goRight = false;
				}
				
				@Override
		        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					goRight = true;
					return true;
				}
				
				public void enter(InputEvent event, float x, float y, int pointer, Actor toActor) {
					goRight = true;
				}
				
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					goRight = false;
				}
			});
			
			runImage.addListener(new InputListener() {
				@Override
		        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					goRun = !goRun;
					return true;
				}
			});
			
			upImage.addListener(new InputListener() {
				@Override
		        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					goUp = false;
				}
				
				@Override
		        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					if(!goUp)
						goUp = true;
					return true;
				}
				
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					goUp = false;
				}
			});
		}
	}
	
	public boolean gamePaused() {
		return world.isPaused() || world.kisa.getState() == Kisa.State.DEAD || world.isVictoryFlag();
	}
	
	public void justDied() {
		String msg = "Oops! Press spacebar to restart";
		if(Gdx.app.getType() == ApplicationType.Android)
			msg = "Oops! Tap to restart";
		Label deadLabel = new Label(msg, Assets.uiSkin);
		deadLabel.setPosition(Data.SCREEN_W / 2 - deadLabel.getWidth() / 2, Data.SCREEN_H - 40);
		deadLabel.setAlignment(Align.center);
		deadLabel.setColor(Color.BLACK);
		deadLabel.setFontScale(2);
		if(showDPad)
			deadLabel.setFontScale(4);
		
		stage.addActor(deadLabel);
	}
	
	public void restartLevel() {
		loadScreen();
	}
	
	public void victory() {
		String msg = "Good Job! you win!!";
		Label victoryLabel = new Label(msg, Assets.uiSkin);
		victoryLabel.setPosition(Data.SCREEN_W / 2 - victoryLabel.getWidth() / 2, Data.SCREEN_H - 40);
		victoryLabel.setAlignment(Align.center);
		victoryLabel.setColor(Color.BLACK);
		victoryLabel.setFontScale(2);
		if(showDPad)
			victoryLabel.setFontScale(4);
		
		stage.addActor(victoryLabel);
	}
}
