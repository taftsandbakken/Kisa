package com.kisa.KisaGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private static float MAX_VELOCITY = 10f;
	private static float JUMP_VELOCITY = 40f;
	private static float DAMPING = 0.87f;
	private static final float GRAVITY = -2.5f;
//	private final float maxJumpHeight = 200;
//	private final float jumpSpeed = 100;
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
	
	public Kisa(World world) {
		this.world = world;
//		animations = new Animations();
//		animations.getCurrent().setPosition(x, y);
		
		// load the koala frames, split them, and assign them to Animations
		int colNum = 4, rowNum = 4;
		kisaTexture = new Texture("data/kisaRunning.png");
		TextureRegion[][] regions = TextureRegion.split(kisaTexture, kisaTexture.getWidth()/colNum, kisaTexture.getHeight()/rowNum);
		stand = new Animation(0, regions[0][0]);
		jump = new Animation(0, regions[0][1]);
		TextureRegion[] walkFrames = {regions[0][0], regions[0][1], regions[0][2], regions[0][3], 
		              				regions[1][0], regions[1][1], regions[1][2], regions[1][3], 
		            				regions[2][0], regions[2][1]};
		walk = new Animation(0.15f, walkFrames);
//		walk.setPlayMode(Animation.LOOP_PINGPONG);
 
		// figure out the width and height of the koala for collision
		// detection and rendering by converting a koala frames pixel
		// size into world units (1 unit == 16 pixels)
		WIDTH = 1 / 85f * regions[0][0].getRegionWidth();
		HEIGHT = 1 / 85f * regions[0][0].getRegionHeight();
		
		position.set(20, 20);
	}
	
	public void update(float deltaTime) {
		stateTime += deltaTime;
		
		// check input and apply to velocity & state
		if((Gdx.input.isKeyPressed(Keys.SPACE) || isTouched(0.75f, 1)) && grounded) {
			moveJump();
        }
		else if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A) || isTouched(0, 0.25f)) {
			moveLeftOrRight(LEFT);
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D) || isTouched(0.25f, 0.5f)) {
			moveLeftOrRight(RIGHT);
        }		
//        else if (Gdx.input.isKeyPressed(Keys.Z)) {
//            moveSlide();
//        }
//		else {
//			idle();
//		}
		
		// apply gravity if we are falling
		velocity.add(0, GRAVITY);
		
		// clamp the velocity to the maximum, x-axis only
		if (Math.abs(velocity.x) > MAX_VELOCITY)
			velocity.x = Math.signum(velocity.x) * MAX_VELOCITY;
		
		// clamp the velocity to 0 if it's < 1, and set the state to standign
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
			if (grounded) {
				state = State.IDLE;
//				getCurrentAnimatedImage().setAnimation(animations.getIdleAnimation());
			}
		}
		
		// multiply by delta time so we know how far we go in this frame
		velocity.scl(deltaTime);
		
		// perform collision detection & response, on each axis, separately
		// if Kisa is moving right, check the tiles to the right of it's
		// right bounding box edge, otherwise check the ones to the left
		Rectangle kisaRect = rectPool.obtain();
		kisaRect.set(position.x, position.y, WIDTH, HEIGHT);
//		kisaRect.set(position.x * 0.75f, position.y, WIDTH * 0.6f, HEIGHT);
		
		int startX, startY, endX, endY;
		if (velocity.x > 0)
			startX = endX = (int) (position.x + WIDTH + velocity.x);
		else
			startX = endX = (int) (position.x + velocity.x);
		
		startY = (int) (position.y);
		endY = (int) (position.y + HEIGHT);
		getTiles(startX, startY, endX, endY, world.getTiles());
		kisaRect.x += velocity.x;
		for (Rectangle tile : world.getTiles())	{
			if (kisaRect.overlaps(tile)) {
				velocity.x = 0;
				break;
			}
		}
		kisaRect.x = position.x;
 
		// if the koala is moving upwards, check the tiles to the top of it's
		// top bounding box edge, otherwise check the ones to the bottom
		if (velocity.y > 0)
			startY = endY = (int) (position.y + HEIGHT + velocity.y);
		else
			startY = endY = (int) (position.y + velocity.y);
		startX = (int) (position.x);
		endX = (int) (position.x + WIDTH);
		getTiles(startX, startY, endX, endY, world.getTiles());
		kisaRect.y += velocity.y;
		for (Rectangle tile : world.getTiles()) {
			if (kisaRect.overlaps(tile)) {
				// we actually reset the kisa y-position here
				// so it is just below/above the tile we collided with this removes bouncing :)
				if (velocity.y > 0)	{
					position.y = tile.y - HEIGHT;
					// we hit a block jumping upwards, let's destroy it!
					TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getLayers().get(1);
					layer.setCell((int) tile.x, (int) tile.y, null);
				}
				else {
					position.y = tile.y + tile.height;
					// if we hit the ground, mark us as grounded so we can jump
					grounded = true;
				}
				velocity.y = 0;
				break;
			}
		}
		rectPool.free(kisaRect);
 
		// unscale the velocity by the inverse delta time and set 
		// the latest position
		position.add(velocity);
		velocity.scl(1 / deltaTime);
 
		// Apply damping to the velocity on the x-axis so we don't
		// walk infinitely once a key was pressed
		velocity.x *= DAMPING;
	}
	
	public void moveLeftOrRight(int newDir) {
		velocity.x = newDir * MAX_VELOCITY;
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
	
//	public void idle() {
//		getCurrentAnimatedImage().setAnimation(animations.getIdleAnimation());
//		state = State.IDLE;
//	}
	
//	public void slide() {
//		getCurrentAnimatedImage().setAnimation(animations.getSlideAnimation());
//		state = State.SLIDING;
//	}
	
	private boolean isTouched(float startX, float endX)
	{
		// check if any finge is touch the area between startX and endX
		// startX/endX are given between 0 (left edge of the screen) and 1 (right edge of the screen)
		for (int i = 0; i < 2; i++)
		{
			float x = Gdx.input.getX() / (float) Gdx.graphics.getWidth();
			if (Gdx.input.isTouched(i) && (x >= startX && x <= endX))
				return true;
		}
		return false;
	}
	
	private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) world.getMap().getLayers().get(1);
		rectPool.freeAll(tiles);
		tiles.clear();
		for (int y = startY; y <= endY; y++)
		{
			for (int x = startX; x <= endX; x++)
			{
				Cell cell = layer.getCell(x, y);
				if (cell != null)
				{
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
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
	
	
}
