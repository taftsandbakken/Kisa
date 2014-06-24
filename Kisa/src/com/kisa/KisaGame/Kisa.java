package com.kisa.KisaGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Kisa {
	
	private Texture kisaTexture;
	private Image kisaImage;
	
	private int dir = 1; //1 == right, -1 == left
	private float width = 75;
	private float height = 75;
	private float x;
	private float y;
	
	private Animations animations;
	
	//experimental:
	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	
	public enum State{
		IDLE, RUNNING, JUMPING, DEAD
	}
	private float maxJumpHeight = 200;
	private float speed = 200;
	private float jumpSpeed = 100;
	
	
	public Kisa(int x, int y) {
		this.x = x;
		this.y = y;
		
		animations = new Animations();
		animations.getCurrent().setPosition(x, y);
		
		//experimental:
		this.bounds.height = height;
		this.bounds.width = width;
	}
	
	public void move() {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            moveLeft();
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            moveRight();
        }
		//I think Dave said that jumping and sliding was the other functionality of Kisa
//		else if(Gdx.input.isKeyPressed(Keys.SPACE)) {
//            //Not yet implemented
//			moveJump();
//        }
//        else if (Gdx.input.isKeyPressed(Keys.Z)) {
//            moveSlide();
//        }
		else {
			idle();
		}
	}
	
	public void moveLeft() {
        state = State.RUNNING;
		setX(getX() - speed * Gdx.graphics.getDeltaTime());
		if(getDir() == 1)
			setX(getX() + 100);
        setDir(-1);
        getCurrentAnimatedImage().setWidth(Math.abs(getCurrentAnimatedImage().getWidth()) * dir);
        getCurrentAnimatedImage().setAnimation(animations.getRunAnimation());
	}
	
	public void moveRight() {
        state = State.RUNNING;
		setX(getX() + speed * Gdx.graphics.getDeltaTime());
		if(getDir() == -1)
			setX(getX() - 100);
        setDir(1);
        getCurrentAnimatedImage().setWidth(Math.abs(getCurrentAnimatedImage().getWidth()) * dir);
        getCurrentAnimatedImage().setAnimation(animations.getRunAnimation());
	}
	
	public void moveJump() {
		
		state = State.JUMPING;
	}
	
	public void idle() {
		getCurrentAnimatedImage().setAnimation(animations.getIdleAnimation());
		state = State.IDLE;
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
	
//	public Sprite getKisaRunningSprite() {
//		return kisaRunningSprite;
//	}
//
//	public void setKisaRunningSprite(Sprite kisaRunningSprite) {
//		this.kisaRunningSprite = kisaRunningSprite;
//	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		getCurrentAnimatedImage().setX(x);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		kisaImage.setY(y);
	}

	public AnimatedImage getCurrentAnimatedImage() {
		return animations.getCurrent();
	}

	public Animations getAnimations() {
		return animations;
	}

	public void setAnimations(Animations animations) {
		this.animations = animations;
	}
	
	
}
