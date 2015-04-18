package com.kisa.KisaGame;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class World {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private Array<Rectangle> tiles = new Array<Rectangle>();
	private Array<StaticTiledMapTile> coin0Tiles;
	
	public GameHub gameHub;
	public Kisa kisa;
	int level = 1;
	boolean isPaused;
	boolean victoryFlag;
	int coinCount = 0;
	
	ArrayList<Dragon> dragons;
	
	public World(GameHub gameHub) {
		this.gameHub = gameHub;
		kisa = new Kisa(this);
		loadLevel();
		loadAnimations();
		isPaused = false;
	}
	
	public void updateAnimations(float deltaTime, boolean goLeft, boolean goRight, boolean goRun, boolean goUp) {
		kisa.update(deltaTime, goLeft, goRight, goRun, goUp);
		
		for(Dragon dragon : dragons) {
			dragon.update(deltaTime);
		}
	}
	
	public void renderKisa(float deltaTime) {
		TextureRegion frame = kisa.getCurrentFrame();
		
		// draw kisa, depending on the current velocity
		// on the x-axis, draw the kisa facing either right or left
		SpriteBatch batch = (SpriteBatch) renderer.getSpriteBatch();
		batch.begin();
		float x = kisa.getDir() == Kisa.RIGHT ? kisa.position.x : kisa.position.x + Kisa.WIDTH;
		batch.draw(frame, x, kisa.position.y, Kisa.WIDTH * kisa.getDir(), Kisa.HEIGHT);
		
		for(Dragon dragon : dragons) {
			TextureRegion dragonFrame = dragon.getCurrentFrame();
			batch.draw(dragonFrame, dragon.position.x, dragon.position.y, Dragon.WIDTH, Dragon.HEIGHT);
		}
		
		batch.end();
	}
	
	public void loadLevel(){
		victoryFlag = false;
		String currentLevel = "";
		switch (level) {
			case 1: currentLevel = Data.LEVEL_ONE_FILE;
				LevelCreator.createLevelOne(this);
				break;
				
		}
		map = new TmxMapLoader().load(currentLevel);
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
//		renderer.setView(, 0, 0, Data.SCREEN_W * .9, Data.SCREEN_H * .9);
	}
	
	public void restartLevel() {
		kisa = new Kisa(this);
		loadLevel();
		loadAnimations();
	}
	
	public void loadAnimations() {
		//load coin 0
		TiledMapTileSet tileset_coin0 = map.getTileSets().getTileSet("coin0");
		if(tileset_coin0 == null)
			return;
		coin0Tiles = new Array<StaticTiledMapTile>();
        for(TiledMapTile tile:tileset_coin0){
            Object property = tile.getProperties().get("coin0Frame");
            if(property != null) {
            	coin0Tiles.add(new StaticTiledMapTile(tile.getTextureRegion()));
            }
        }
        loadLevel();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for(int x = 0; x < layer.getWidth(); x++){
            for(int y = 0; y < layer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if(cell != null) {
	                Object property = cell.getTile().getProperties().get("coin0Frame");
	                if(property != null){
	                	TiledMapTile tile = new AnimatedTiledMapTile(Data.coinAnimationSpeed, coin0Tiles);
	                	tile.getProperties().put("coin", "true");
	                    cell.setTile(tile);
	                }
                }
            }
        }
        
//        loadLevel(); // I don't know why I had to reload the level after the animations were 
        			 // loaded, but it had to be redone for some wretched reason
	}
	
	public void addCoin() {
		coinCount++;
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
