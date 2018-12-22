package com.ztw33.javafinal.thing.skillthing;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.space.Pixel;
import com.ztw33.javafinal.space.Position;
import com.ztw33.javafinal.thing.creature.Creature;

import javafx.scene.image.Image;

public class Calabash extends SkillThing {

	private int rightBorder;
	
	static final int hurt = 25;
	
	public Calabash(int row, int column, int rightBorder) {
		image = new Image("calabash.png");
		pixel = new Pixel(column*70, row*52+10);
		this.rightBorder = rightBorder;
	}
	
	@Override
	public void run() {
		 
		while (!isKilled) {
			if (pixel.getX() < rightBorder) {
				synchronized (field) {
					Position coordPosition = pixel.getCoordPosition();
					if (field.existBadCreature(coordPosition.getRow(), coordPosition.getColumn())) {
						Creature bad = field.getCreature(coordPosition.getRow(), coordPosition.getColumn());
						bad.beAttacked(hurt);
						System.out.println("紫葫芦对"+bad.getName()+"造成了"+hurt+"点伤害");
						isKilled = true;
						break;
					}
				}
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pixel.setX(pixel.getX()+10);
			} else {
				isKilled = true;
			}
		}
	}

}
