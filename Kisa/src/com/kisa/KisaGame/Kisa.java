package com.kisa.KisaGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Kisa {
	
	private Texture kisaTexture;
	private Image kisaImage;
	
	private int dir = 1; //1 == right, -1 == left
	private float width = 100;
	private float height = 100;
	private float x;
	private float y;
	
	//experimental:
	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	
	public enum State{
		IDLE, WALKING, JUMPING, DEAD
	}
	private float maxJumpHeight = 200;
	private float speed = 200;
	private float jumpSpeed = 100;
	
	
	public Kisa(int x, int y) {
		Texture.setEnforcePotImages(false);
		kisaTexture = new Texture(Gdx.files.internal("data/kisa.png"));
//		kisaTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion kisaRegion = new TextureRegion(kisaTexture, 0, 0, 400, 400);
		kisaImage = new Image(kisaRegion);
		kisaImage.setHeight(height);
		kisaImage.setWidth(width);
		kisaImage.setX(x);
		kisaImage.setY(y);
//		kisaImage.
		
		this.x = x;
		this.y = y;
		
		//experimental:
		this.bounds.height = height;
		this.bounds.width = width;
	}
	
	public void moveLeft(){
		setX(getX() - speed * Gdx.graphics.getDeltaTime());
		if(getDir() == 1)
			setX(getX() + 100);
        setDir(-1);
        kisaImage.setWidth(width * dir);
	}
	
	public void moveRight(){
		setX(getX() + speed * Gdx.graphics.getDeltaTime());
		if(getDir() == -1)
			setX(getX() - 100);
        setDir(1);
        kisaImage.setWidth(width * dir);
	}
	
	public void moveJump(){
//		System.out.println("Jump!");
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

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		kisaImage.setX(x);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		kisaImage.setY(y);
	}
}
