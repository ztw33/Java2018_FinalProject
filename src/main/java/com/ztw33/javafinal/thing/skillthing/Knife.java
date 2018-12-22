package com.ztw33.javafinal.thing.skillthing;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.space.Pixel;
import com.ztw33.javafinal.space.Position;
import com.ztw33.javafinal.thing.creature.Creature;

import javafx.scene.image.Image;

public class Knife extends SkillThing {

	static final int hurt = 10;
	
	public Knife(int row, int column) {
		image = new Image("knife.png");
		pixel = new Pixel(column*70, row*52+10);
	}
	
	@Override
	public void run() {
		while (!isKilled) {
			if (pixel.getX() > 0) {
				synchronized (field) {
					Position coordPosition = pixel.getCoordPosition();
					if (field.existGoodCreature(coordPosition.getRow(), coordPosition.getColumn())) {
						Creature good = field.getCreature(coordPosition.getRow(), coordPosition.getColumn());
						good.beAttacked(hurt);
						System.out.println("砍刀对"+good.getName()+"造成了"+hurt+"点伤害");
						isKilled = true;
						break;
					}
				}
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pixel.setX(pixel.getX()-10);
			} else {
				isKilled = true;
			}
		}

	}

}
