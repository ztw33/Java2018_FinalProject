package com.ztw33.javafinal.thing.skillthing;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.space.Pixel;

import javafx.scene.image.Image;

public class Calabash extends SkillThing {

	private int rightBorder;
	
	public Calabash(int row, int column, int rightBorder) {
		image = new Image("calabash.png");
		pixel = new Pixel(column*70, row*52+10);
		this.rightBorder = rightBorder;
	}
	
	@Override
	public void run() {
		 
		while (!isKilled) {
			if (pixel.getX() < rightBorder) {
				pixel.setX(pixel.getX()+10);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				isKilled = true;
			}
		}
	}

}
