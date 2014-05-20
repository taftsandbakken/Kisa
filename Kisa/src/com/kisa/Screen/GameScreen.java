package com.kisa.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.kisa.KisaGame.KisaGame;

public class GameScreen implements Screen, InputProcessor {

	KisaGame game;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	BitmapFont font;
	
	Stage stage;
	Label titleLabel;
	TextButton mainMenuButton;
	TextButtonStyle textButtonStyle;
	
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
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            game.kisa.moveLeft();
		}
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            game.kisa.moveRight();
        }
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            //Not yet implemented
        	game.kisa.moveJump();
        }
        //I think Dave said that sliding was the other functionality of Kisa
//        if (Gdx.input.isKeyPressed(Keys.Z)) {
//            game.kisa.moveSlide();
//        }
        
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
		font = new BitmapFont();
//		font.setColor(Color.RED);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = font;
		
		titleLabel = new Label("Kisa Game Screen", labelStyle);
		titleLabel.setPosition(345, 550);
		titleLabel.setAlignment(Align.center);
		
		textButtonStyle = new TextButtonStyle();
//		textButtonStyle.up = 
		textButtonStyle.font = font;
		
		mainMenuButton = new TextButton("Main Menu", textButtonStyle);
		mainMenuButton.setPosition(50, 550);
		mainMenuButton.setHeight(50);
		mainMenuButton.setWidth(100);
		
		Texture.setEnforcePotImages(false);
		
		stage.addActor(titleLabel);
		stage.addActor(mainMenuButton);
		stage.addActor(game.kisa.getKisaImage());
		
		addActionListeners();
	}
	
	public void addActionListeners() {
		mainMenuButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("clicked!");
				game.setScreen(game.mainMenuScreen);
			}
		});
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
