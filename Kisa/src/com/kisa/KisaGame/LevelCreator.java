package com.kisa.KisaGame;

import java.util.ArrayList;

public class LevelCreator {

	public LevelCreator() {
		
	}
	
	public static void createLevelOne(World world) {
		world.dragons = new ArrayList<Dragon>();
		world.dragons.add(new Dragon(18, 4, world));
		world.dragons.add(new Dragon(20, 39, world));
	}
}
