package com.ztw33.javafinal.thing.skillthing;

import com.ztw33.javafinal.space.Pixel;
import com.ztw33.javafinal.thing.Thing;

public abstract class SkillThing extends Thing implements Runnable {
	
	protected Pixel pixel;
	
	public void kill() {
		isKilled = true;
	}
	
	public int getPixelX() { return pixel.getX(); }
	public int getPixelY() { return pixel.getY(); }
}
