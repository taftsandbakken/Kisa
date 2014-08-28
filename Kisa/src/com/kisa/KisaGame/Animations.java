package com.kisa.KisaGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animations {

	private Animation runAnimation;
	private Animation idleAnimation;
	private AnimatedImage current;
	private float speed = .125f;
	
	private FileHandle RUNNING_SPRITE_FILE = Gdx.files.internal("data/kisaRunning2.png");
	private FileHandle IDLE_SPRITE_FILE = Gdx.files.internal("data/kisaIdle2.png");
	
	Animations() {
		createAnimations();
	}
	
	public void createAnimations(){
		runAnimation = createAnimation(RUNNING_SPRITE_FILE, 4, 4);
		idleAnimation = createIdleAnimation(IDLE_SPRITE_FILE, 4, 4);
		
		current = new AnimatedImage(idleAnimation);
		current.setSize(Kisa.WIDTH, Kisa.HEIGHT);
	}
	
	//Not the best practice, but I wanted the idle to look better for now
	public Animation createIdleAnimation(FileHandle spriteFile, int colNum, int rowNum) {
		Texture kisaRunningTexture = new Texture(spriteFile);
		TextureRegion[][] tmp = TextureRegion.split(kisaRunningTexture, kisaRunningTexture.getWidth()/colNum, kisaRunningTexture.getHeight()/rowNum);
		TextureRegion[] walkFrames = new TextureRegion[1];
		int index = 0;
//        for (int i = 0; i < rowNum; i++) {
//            for (int j = 0; j < colNum; j++) {
//                walkFrames[index++] = tmp[i][j];
//            }
//        }
		walkFrames[index++] = tmp[0][0];
        Animation animation = new Animation(speed, walkFrames);
        return animation;
	}
	
	public Animation createAnimation(FileHandle spriteFile, int colNum, int rowNum) {
		Texture kisaRunningTexture = new Texture(spriteFile);
		TextureRegion[][] tmp = TextureRegion.split(kisaRunningTexture, kisaRunningTexture.getWidth()/colNum, kisaRunningTexture.getHeight()/rowNum);
		TextureRegion[] walkFrames = new TextureRegion[colNum * rowNum];
		int index = 0;
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
		Kisa.WIDTH = walkFrames[0].getRegionWidth();
		Kisa.HEIGHT = walkFrames[0].getRegionHeight(); //1 / 16f * 
		
        Animation animation = new Animation(speed, walkFrames);
        return animation;
	}

	public AnimatedImage getCurrent() {
		return current;
	}

	public void setCurrent(AnimatedImage current) {
		this.current = current;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Animation getRunAnimation() {
		return runAnimation;
	}

	public void setRunAnimation(Animation runAnimation) {
		this.runAnimation = runAnimation;
	}

	public Animation getIdleAnimation() {
		return idleAnimation;
	}

	public void setIdleAnimation(Animation idleAnimation) {
		this.idleAnimation = idleAnimation;
	}
	
}
