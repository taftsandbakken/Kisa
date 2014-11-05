package com.kisa.KisaGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

	public static Skin uiSkin = new Skin(Data.UI_SKIN_FILE);
	public static BitmapFont font = new BitmapFont();
	
	public Assets() {}
	
	public static Image getNewImage(String fileName, float x, float y, float w, float h, float pixelW, float pixelH) {
		Texture imageTexture = new Texture(Gdx.files.internal(fileName));
		imageTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(imageTexture, 0, 0, (int) pixelW, (int) pixelH);
		Sprite sprite = new Sprite(region);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(w / 2 - sprite.getWidth() / 2, h / 2 - sprite.getHeight() / 2);
		
		Image image = new Image(sprite);
		image.setSize(w, h);
		image.setPosition(x, y);
		return image;
	}	
}
