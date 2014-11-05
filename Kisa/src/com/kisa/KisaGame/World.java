package com.kisa.KisaGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class World {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private Array<Rectangle> tiles = new Array<Rectangle>();
	
	public GameHub gameHub;
	public Kisa kisa;
	int level = 1;
	boolean isPaused;
	boolean victoryFlag;
	
	public World(GameHub gameHub) {
		this.gameHub = gameHub;
		kisa = new Kisa(this);
		loadLevel1();
		isPaused = false;
	}
	
	public void renderKisa(float deltaTime) {
		TextureRegion frame = kisa.getCurrentFrame();
		
		// draw kisa, depending on the current velocity
		// on the x-axis, draw the kisa facing either right or left
		SpriteBatch batch = (SpriteBatch) renderer.getSpriteBatch();
		batch.begin();
		float x = kisa.getDir() == Kisa.RIGHT ? kisa.position.x : kisa.position.x + Kisa.WIDTH;
		batch.draw(frame, x, kisa.position.y, Kisa.WIDTH * kisa.getDir(), Kisa.HEIGHT);
		batch.end();
	}
	
	public void loadLevel1(){
		victoryFlag = false;
		map = new TmxMapLoader().load(Data.LEVEL_ONE_FILE);
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
//		renderer.setView(, 0, 0, Data.SCREEN_W * .9, Data.SCREEN_H * .9);
	}
	
	public void restartLevel() {
		kisa = new Kisa(this);
		loadLevel1();
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}

	public OrthogonalTiledMapRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(OrthogonalTiledMapRenderer renderer) {
		this.renderer = renderer;
	}

	public Array<Rectangle> getTiles() {
		return tiles;
	}

	public void setTiles(Array<Rectangle> tiles) {
		this.tiles = tiles;
	}

	public Kisa getKisa() {
		return kisa;
	}

	public void setKisa(Kisa kisa) {
		this.kisa = kisa;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public boolean isVictoryFlag() {
		return victoryFlag;
	}

	public void setVictoryFlag(boolean victoryFlag) {
		this.victoryFlag = victoryFlag;
	}
	
}
