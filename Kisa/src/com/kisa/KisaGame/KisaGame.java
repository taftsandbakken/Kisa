package com.kisa.KisaGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Game;
import com.kisa.Screen.*;

public class KisaGame extends Game {
//	private OrthographicCamera camera;
//	private SpriteBatch batch;
//	private BitmapFont font;
//	private Texture texture;
//	private Sprite sprite;
	
	public MainMenuScreen mainMenuScreen;
	public GameScreen gameScreen;
	
	public Kisa kisa;
	
	
	@Override
	public void create() {
//		float w = Gdx.graphics.getWidth();
//		float h = Gdx.graphics.getHeight();
//		
//		camera = new OrthographicCamera(1, h/w);
//		batch = new SpriteBatch();
//		font = new BitmapFont();
//		font.setColor(Color.RED);
//		
//		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
//		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		
//		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
//		
//		sprite = new Sprite(region);
//		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
//		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
//		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		kisa = new Kisa(380, 200);
		
		mainMenuScreen = new MainMenuScreen(this, "Main Menu");
		gameScreen = new GameScreen(this, "Kisa");
		
		
		setScreen(gameScreen); //mainMenuScreen
	}

	@Override
	public void dispose() {
//		batch.dispose();
//		font.dispose();
//		texture.dispose();
	}

	@Override
	public void render() {	
		super.render();
//		Gdx.gl.glClearColor(1, 1, 1, 1);
//		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//		
//		batch.setProjectionMatrix(camera.combined);
//		batch.begin();
//		font.draw(batch, "Hello World", 400, 300);
//		sprite.draw(batch);
//		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
