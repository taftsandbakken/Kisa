package com.kisa.KisaGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Dragon {

	private static int DRAGON1_SPRITE_ROW_NUM = 4;
	private static int DRAGON1_SPRITE_COL_NUM = 4;
	private static String DRAGON1_SPRITE_FILE = "data/sprites/dragon1.png";
	public static float WIDTH = 200;
	public static float HEIGHT = 100;
	public static float RECHARGEDURATION = 3;
	public enum State{
		IDLE, SHOOTING, RECHARGING
	} //my idea is that we can keep track of what the dragon should do by its state
	
	private World world;
	private Texture dragonTexture;
	private Animation idle;
	private Animation shoot;
//	private Animation walk;
	
	private State state = State.IDLE;
	private float stateTime = 0;
	private float speed = 200;
	private float shootFireballTimer = 0;
	
	public final Vector2 position = new Vector2();
	public final Vector2 velocity = new Vector2();	
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject()
		{
			return new Rectangle();
		}
	};
	
	public Dragon(int x, int y, World world) {
		position.set(x, y);
		this.world = world;
		
		// load the dragon frames, split them, and assign them to Animations
		dragonTexture = new Texture(DRAGON1_SPRITE_FILE);
		TextureRegion[][] regions = TextureRegion.split(dragonTexture, 
				dragonTexture.getWidth() / DRAGON1_SPRITE_COL_NUM, 
				dragonTexture.getHeight() / DRAGON1_SPRITE_ROW_NUM);
		TextureRegion[] idleFrames = {regions[0][0], regions[0][1], regions[0][2], regions[0][3],
				regions[1][0], regions[1][1], regions[1][2], regions[1][3]};
		idle = new Animation(0.15f, idleFrames);
		TextureRegion[] shootingFrames = {regions[2][0], regions[2][1], regions[2][2], regions[2][3],
				regions[3][0], regions[3][1], regions[3][2], regions[3][3]};
		shoot = new Animation(0.15f, shootingFrames);
 
		// figure out the width and height of the kisa for collision
		// detection and rendering by converting a kisa frames pixel
		// size into world units (1 unit == 16 pixels)
		WIDTH = 1 / 35f * regions[0][0].getRegionWidth();
		HEIGHT = 1 / 35f * regions[0][0].getRegionHeight();
	}
	
	public void update(float deltaTime) {
		stateTime += deltaTime;

		if(withinRangeOfKisa()) {
			if(shootFireballTimer < 0) {
				shootFireball();
				shootFireballTimer = RECHARGEDURATION;
			}
			state = State.SHOOTING;
		}
		else {
			state = State.IDLE;
		}
		shootFireballTimer -= deltaTime;
	}
	
	public boolean withinRangeOfKisa() {
		double x1 = world.kisa.getPosition().x;
		double x2 = position.x;
		double y1 = world.kisa.getPosition().y;
		double y2 = position.y;
		double distance = Math.sqrt(((x1-x2)*(x1-x2)) + ((y1-y2)*(y1-y2)));
		return distance < 10 ? true : false;
	}
	
	private void shootFireball() {
		System.out.println("shoo!");
	}
	
	/*
	 * Based on the Kisa state, get the animation frame
	 */
	public TextureRegion getCurrentFrame() {
		TextureRegion frame = null;
		switch(getState()) {
			case SHOOTING:
				frame = getShoot().getKeyFrame(getStateTime(), true);
				break;
			case IDLE:
			case RECHARGING:
			default:
				frame = getIdle().getKeyFrame(getStateTime(), true);
				break;
		}
		return frame;
	}

	public static float getWIDTH() {
		return WIDTH;
	}

	public static void setWIDTH(float wIDTH) {
		WIDTH = wIDTH;
	}

	public static float getHEIGHT() {
		return HEIGHT;
	}

	public static void setHEIGHT(float hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public Texture getDragonTexture() {
		return dragonTexture;
	}

	public void setDragonTexture(Texture dragonTexture) {
		this.dragonTexture = dragonTexture;
	}

	public Animation getIdle() {
		return idle;
	}

	public void setIdle(Animation idle) {
		this.idle = idle;
	}

	public Animation getShoot() {
		return shoot;
	}

	public void setShoot(Animation shoot) {
		this.shoot = shoot;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Pool<Rectangle> getRectPool() {
		return rectPool;
	}

	public void setRectPool(Pool<Rectangle> rectPool) {
		this.rectPool = rectPool;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getVelocity() {
		return velocity;
	}
	
	
}
