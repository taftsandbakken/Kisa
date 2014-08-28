package com.kisa.KisaGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
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
	
	public Kisa kisa;
	int level = 1;
	int mapWidth;
	int mapHeight;
	
	public World() {
		kisa = new Kisa(this);
		map = new TmxMapLoader().load("data/level1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
		
		loadLevel1();
	}
	
	public void renderKisa(float deltaTime) {
		// based on the Kisa state, get the animation frame
		TextureRegion frame = null;
		switch(kisa.getState()) {
			case RUNNING:
				frame = kisa.getWalk().getKeyFrame(kisa.getStateTime(), true);
				break;
			case JUMPING:
				frame = kisa.getJump().getKeyFrame(kisa.getStateTime());
				break;
			default:
				frame = kisa.getStand().getKeyFrame(kisa.getStateTime());
				break;
		}
		
		// draw kisa, depending on the current velocity
		// on the x-axis, draw the kisa facing either right
		// or left
		SpriteBatch batch = (SpriteBatch) renderer.getSpriteBatch();
		batch.begin();
		if (kisa.getDir() == Kisa.RIGHT) {
			batch.draw(frame, kisa.position.x, kisa.position.y, kisa.WIDTH, kisa.HEIGHT);
		}
		else {
			batch.draw(frame, kisa.position.x + kisa.WIDTH, kisa.position.y, -kisa.WIDTH, kisa.HEIGHT);
		}
		batch.end();
	}
	
	public void loadLevel1(){
//		kisa.setX(380);
//		kisa.setY(200);
		
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
	
}
