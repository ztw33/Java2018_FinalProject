package com.ztw33.javafinal.thing.skillthing;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.space.Pixel;

import javafx.scene.image.Image;

public class Water extends SkillThing {

	public Water(int coordX, int coordY) {
		image = new Image("water.gif");
		pixel = new Pixel(coordY*70, coordX*52);
	}
	
	@Override
	public void run() {
		try {
			TimeUnit.MILLISECONDS.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isKilled = true;
	}

}
