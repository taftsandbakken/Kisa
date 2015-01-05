package com.kisa.KisaGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Kisa {
	
	public final static int LEFT = -1;
	public final static int RIGHT = 1;
	static float WIDTH = 100;
	static float HEIGHT = 100;
	private static float MAX_VELOCITY_WALKING = 8f;  //orig: 12f
	private static float MAX_VELOCITY_RUNNING = 12f;
	private float currentMaxVelocity = MAX_VELOCITY_WALKING;
	private static float JUMP_VELOCITY = 40f;  //orig: 40f
	private static float DAMPING = 0.87f;
	private static final float GRAVITY = -2.0f;  //orig: -2.5f
	private static final float CLIFF_Y_LIMIT = -10;
	public enum State{
		IDLE, RUNNING, JUMPING, SLIDING, DEAD
	}
	
	private World world;
	private Texture kisaTexture;
	private Image kisaImage;
	private Animation stand;
	private Animation walk;
	private Animation jump;
//	private Animations animations;
	
	private int dir = RIGHT;
	private State state = State.IDLE;
	private boolean grounded = false;
	private float stateTime = 0;
	private float speed = 200;
	
	public final Vector2 position = new Vector2();
	public final Vector2 velocity = new Vector2();	
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject()
		{
			return new Rectangle();
		}
	};
	
	private TiledMapTileLayer backgroundLayer;
	private TiledMapTileLayer collisionLayer;
	
	public Kisa(World world) {
		this.world = world;
//		animations = new Animations();
//		animations.getCurrent().setPosition(x, y);
		
		// load the kisa frames, split them, and assign them to Animations
		kisaTexture = new Texture(Data.KISA_SPRITE_FILE);
		TextureRegion[][] regions = TextureRegion.split(kisaTexture, kisaTexture.getWidth()/Data.KISA_SPRITE_COL_NUM, kisaTexture.getHeight()/Data.KISA_SPRITE_ROW_NUM);
		stand = new Animation(0, regions[0][0]);
		jump = new Animation(0, regions[0][1]);
		TextureRegion[] walkFrames = {regions[0][0], regions[0][1], regions[0][2], regions[0][3],
				regions[0][4], regions[0][5], regions[0][6], regions[0][7]};
		walk = new Animation(0.15f, walkFrames);
 
		// figure out the width and height of the kisa for collision
		// detection and rendering by converting a kisa frames pixel
		// size into world units (1 unit == 16 pixels)
		WIDTH = 1 / 85f * regions[0][0].getRegionWidth();
		HEIGHT = 1 / 85f * regions[0][0].getRegionHeight();
		
		position.set(16, 4);
	}
	
	public void update(float deltaTime, boolean goLeft, boolean goRight, boolean goRun, boolean goUp) {
		if(checkForDeath())
			return;
		
		stateTime += deltaTime;
		checkInput(goLeft, goRight, goRun, goUp);
		
		updatePosition(deltaTime);
	}
	
	// check input and apply to velocity & state
	public void checkInput(boolean goLeft, boolean goRight, boolean goRun, boolean goUp) {
		currentMaxVelocity = MAX_VELOCITY_WALKING;
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || goRun)
			currentMaxVelocity = MAX_VELOCITY_RUNNING;
		if((Gdx.input.isKeyPressed(Keys.SPACE) || goUp) && grounded) {  //isTouched(0.75f, 1)
			moveJump();
        }
		else if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A) || goLeft) {  //isTouched(0, 0.25f)
			moveLeftOrRight(LEFT);
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D) || goRight) {  //isTouched(0.25f, 0.5f)
			moveLeftOrRight(RIGHT);
        }
	}
	
	public void moveLeftOrRight(int newDir) {
		velocity.x = newDir * currentMaxVelocity;
		if (grounded)
			state = State.RUNNING;
		
		//setX(getX() + (newDir * speed) * Gdx.graphics.getDeltaTime());
//		if(newDir != dir) //this is used to fix the x value when the image flips
//			setX(getX() - (newDir * WIDTH));
        setDir(newDir);
//        getCurrentAnimatedImage().setWidth(Math.abs(getCurrentAnimatedImage().getWidth()) * dir);
//        getCurrentAnimatedImage().setAnimation(animations.getRunAnimation());
	}
	
	public void moveJump() {
		//getCurrentAnimatedImage().setAnimation(animations.getJumpAnimation());
		state = State.JUMPING;
		velocity.y += JUMP_VELOCITY;
		grounded = false;
	}
	
//	private boolean isTouched(float startX, float endX)
//	{
//		// check if any finger is touching the area between startX and endX
//		// startX/endX are given between 0 (left edge of the screen) and 1 (right edge of the screen)
//		for (int i = 0; i < 2; i++)
//		{
//			float x = Gdx.input.getX() / (float) Gdx.graphics.getWidth();
//			if (Gdx.input.isTouched(i) && (x >= startX && x <= endX))
//				return true;
//		}
//		return false;
//	}
	
	private void updatePosition(float deltaTime) {
		if(collisionLayer == null) {
			backgroundLayer = (TiledMapTileLayer) world.getMap().getLayers().get(0);
			collisionLayer = (TiledMapTileLayer) world.getMap().getLayers().get(1);	
		}
		
		// apply gravity if we are falling
		velocity.add(0, GRAVITY);
		
		clampVelocity();
		
		// multiply by delta time so we know how far we go in this frame
		velocity.scl(deltaTime);
		
		// perform collision detection & response, on each axis, separately
		Rectangle kisaRect = rectPool.obtain();
		kisaRect.set(position.x, position.y, WIDTH, HEIGHT);
		//kisaRect.set(position.x * 0.75f, position.y, WIDTH * 0.6f, HEIGHT); //testing different rect size to take out horse
		//kisaRect.set(position.x + (0.25f * WIDTH), position.y, WIDTH * 0.75f, HEIGHT); //testing different rect size to take out horse

		collisionDetectionXAxis(kisaRect);
		collisionDetectionYAxis(kisaRect);
		backgroundCollisionDetectionYAxis(kisaRect);
		backgroundCollisionDetectionXAxis(kisaRect);
		rectPool.free(kisaRect);
 
		// unscale the velocity by the inverse delta time and set 
		// the latest position
		position.add(velocity);
		velocity.scl(1 / deltaTime);
 
		// Apply damping to the velocity on the x-axis so we don't
		// walk infinitely once a key was pressed
		velocity.x *= DAMPING;
	}
	
	private void clampVelocity() {
		// clamp the velocity to the maximum, x-axis only
		if (Math.abs(velocity.x) > currentMaxVelocity)
			velocity.x = Math.signum(velocity.x) * currentMaxVelocity;
		
		// clamp the velocity to 0 if it's < 1, and set the state to standign
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
			if (grounded) {
				state = State.IDLE;
				//getCurrentAnimatedImage().setAnimation(animations.getIdleAnimation());
			}
		}
	}
	
	/* 
	 * If Kisa is moving right, check the tiles to the right of it's
	 * right bounding box edge, otherwise check the ones to the left
	 */ 
	private void collisionDetectionXAxis(Rectangle kisaRect) {
		int startX, startY, endX, endY;
		if (velocity.x > 0)
			startX = endX = (int) (position.x + WIDTH + velocity.x);
		else
			startX = endX = (int) (position.x + velocity.x);
		
		startY = (int) (position.y);
		endY = (int) (position.y + HEIGHT);
		getTiles(startX, startY, endX, endY, world.getTiles(), collisionLayer);
		kisaRect.x += velocity.x;
		for (Rectangle tile : world.getTiles())	{
			if (kisaRect.overlaps(tile)) {
				velocity.x = 0;
				applyCollisionBothAxis(tile);
				break;
			}
		}
		kisaRect.x = position.x;
	}
	
	/* 
	 * If the kisa is moving upwards, check the tiles to the top of it's
	 * top bounding box edge, otherwise check the ones to the bottom
	 */ 
	private void collisionDetectionYAxis(Rectangle kisaRect) {
		int startX, startY, endX, endY;
		if (velocity.y > 0)
			startY = endY = (int) (position.y + HEIGHT + velocity.y);
		else
			startY = endY = (int) (position.y + velocity.y);
		startX = (int) (position.x);
		endX = (int) (position.x + WIDTH);
		getTiles(startX, startY, endX, endY, world.getTiles(), collisionLayer);
		kisaRect.y += velocity.y;
		for (Rectangle tile : world.getTiles()) {
			if (kisaRect.overlaps(tile)) {
				applyCollisionBothAxis(tile);
				// we actually reset the kisa y-position here
				// so it is just below/above the tile we collided with this removes bouncing :)
				if (velocity.y > 0)	{
					position.y = tile.y - HEIGHT;
					applyCollisionYAxis(tile);
				}
				else {
					position.y = tile.y + tile.height;
					grounded = true; // if we hit the ground, mark us as grounded so we can jump
				}
				velocity.y = 0;
				break;
			}
		}
	}
	
	private void applyCollisionYAxis(Rectangle tile) {
		if(checkForTileProperty(collisionLayer, tile, "breakable")) { // we hit a block jumping upwards, let's destroy it!
			collisionLayer.setCell((int)tile.x, (int)tile.y, null);
		}
	}
	
	private void applyCollisionBothAxis(Rectangle tile) {
		if(checkForTileProperty(backgroundLayer, tile, "victory")) { //good job, you win
			world.setVictoryFlag(true);
		}

		if(checkForTileProperty(collisionLayer, tile, "death")) { // oops, you died
			justDied();
		}
	}
	
	/* 
	 * Check for collisions with the background on the Y axis without affecting movement
	 */ 
	private void backgroundCollisionDetectionYAxis(Rectangle kisaRect) {
		int startX, startY, endX, endY;
		if (velocity.y > 0)
			startY = endY = (int) (position.y + HEIGHT + velocity.y);
		else
			startY = endY = (int) (position.y + velocity.y);
		startX = (int) (position.x);
		endX = (int) (position.x + WIDTH);
		getTiles(startX, startY, endX, endY, world.getTiles(), backgroundLayer);
		kisaRect.y += velocity.y;
		for (Rectangle tile : world.getTiles()) {
			if (kisaRect.overlaps(tile)) {
				applyBackgroundCollisionBothAxis(tile);
			}
		}
	}
	
	/* 
	 * Check for collisions with the background on the X axis without affecting movement
	 */ 
	private void backgroundCollisionDetectionXAxis(Rectangle kisaRect) {
		int startX, startY, endX, endY;
		if (velocity.x > 0)
			startX = endX = (int) (position.x + WIDTH + velocity.x);
		else
			startX = endX = (int) (position.x + velocity.x);
		startY = (int) (position.y);
		endY = (int) (position.y + HEIGHT);
		getTiles(startX, startY, endX, endY, world.getTiles(), backgroundLayer);
		kisaRect.x += velocity.x;
		for (Rectangle tile : world.getTiles()) {
			if (kisaRect.overlaps(tile)) {
				applyBackgroundCollisionBothAxis(tile);
			}
		}
	}
	
	private void applyBackgroundCollisionBothAxis(Rectangle tile) {
		if(checkForTileProperty(backgroundLayer, tile, "coin")) { // capture the coin
			backgroundLayer.setCell((int)tile.x, (int)tile.y, null);
			world.addCoin();
		}
	}
	
	private boolean checkForTileProperty(TiledMapTileLayer layer, Rectangle tile, String property) {
		Object prop = getTileProperty(layer, tile, property);
		return prop != null;// && prop.equals("true");
	}
	
	private Object getTileProperty(TiledMapTileLayer layer, Rectangle tile, String property) {
		Cell cell = layer.getCell((int)tile.getX(), (int)tile.getY());
		if(cell == null)
			return null;
		
		
		TiledMapTile tiledTile = cell.getTile();
		return tiledTile.getProperties().get(property);
	}
	
	private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles, TiledMapTileLayer layer) {
		rectPool.freeAll(tiles);
		tiles.clear();
		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
	}
	
	/*
	 * Based on the Kisa state, get the animation frame
	 */
	public TextureRegion getCurrentFrame() {
		TextureRegion frame = null;
		switch(getState()) {
			case RUNNING:
				frame = getWalk().getKeyFrame(getStateTime(), true);
				break;
			case JUMPING:
				frame = getJump().getKeyFrame(getStateTime());
				break;
			default:
				frame = getStand().getKeyFrame(getStateTime());
				break;
		}
		return frame;
	}
	
	// returns true if Kisa perished foolishly or otherwise
	public boolean checkForDeath() {
		if(state == State.DEAD)
			return true;
		
		//check the different ways you can die
		if(position.y < CLIFF_Y_LIMIT) 
			justDied();
		//check against enemies and objects of death
		
		if(state == State.DEAD) {
			justDied();
			return true;
		}
		return false;
	}
	
	/*
	 * Kisa just died
	 */
	public void justDied() {
		state = State.DEAD;
		world.gameHub.justDied();
	}
	
	public Texture getKisaTexture() {
		return kisaTexture;
	}

	public void setKisaTexture(Texture kisaTexture) {
		this.kisaTexture = kisaTexture;
	}
	
	public Image getKisaImage() {
		return kisaImage;
	}

	public void setKisaImage(Image kisaImage) {
		this.kisaImage = kisaImage;
	}
	
	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

//	public AnimatedImage getCurrentAnimatedImage() {
//		return animations.getCurrent();
//	}
//
//	public Animations getAnimations() {
//		return animations;
//	}
//
//	public void setAnimations(Animations animations) {
//		this.animations = animations;
//	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public boolean isGrounded() {
		return grounded;
	}

	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Animation getStand() {
		return stand;
	}

	public void setStand(Animation stand) {
		this.stand = stand;
	}

	public Animation getWalk() {
		return walk;
	}

	public void setWalk(Animation walk) {
		this.walk = walk;
	}

	public Animation getJump() {
		return jump;
	}

	public void setJump(Animation jump) {
		this.jump = jump;
	}

	public Vector2 getVelocity() {
		return velocity;
	}
	
	public boolean isDead() {
		return state == State.DEAD;
	}
}
