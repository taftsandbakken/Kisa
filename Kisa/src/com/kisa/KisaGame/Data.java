package com.kisa.KisaGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Data {

	public static float SCREEN_W = Gdx.graphics.getWidth();
	public static float SCREEN_H = Gdx.graphics.getHeight();
	
	public static float UI_INPUT_WIDTH = Data.SCREEN_W * 0.12f;

	public static int KISA_SPRITE_ROW_NUM = 1;
	public static int KISA_SPRITE_COL_NUM = 8;
	
	public static FileHandle UI_SKIN_FILE = Gdx.files.internal("data/other/uiskin.json");
	public static String KISA_SPRITE_FILE = "data/sprites/kisaSprite10.png";
	public static String SHIRT_TWO_FILE = "data/titles/Shirt2.png";	
	public static String LEVEL_ONE_FILE = "data/tiles/levelTaft.tmx";
	public static String UI_UP_FILE = "data/ui/arrowUp.png";
	public static String UI_RIGHT_FILE = "data/ui/arrowRight.png";
	public static String UI_LEFT_FILE = "data/ui/arrowLeft.png";
	public static String UI_RUN_FILE = "data/ui/running.png";
	public static String UI_GEAR_FILE = "data/ui/gear.png";
	
	public Data() {}
	
}
