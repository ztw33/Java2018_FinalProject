package com.ztw33.javafinal.thing.skillthing;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.space.Pixel;
import com.ztw33.javafinal.space.Position;
import com.ztw33.javafinal.thing.creature.Creature;

import javafx.scene.image.Image;

public class Water extends SkillThing {

	static final int hurt = 10;
	
	public Water(int coordX, int coordY) {
		image = new Image("water.gif");
		name = "水";
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
		Position coordPosition = pixel.getCoordPosition();
		synchronized (field) {
			if (field.existBadCreature(coordPosition.getRow(), coordPosition.getColumn())) {
				Creature bad = field.getCreature(coordPosition.getRow(), coordPosition.getColumn());
				bad.beAttacked(hurt);
				System.out.println("水对"+bad.getName()+"造成了"+hurt+"点伤害");
			}
		}
		isKilled = true;
	}

}
