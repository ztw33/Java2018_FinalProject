package com.ztw33.javafinal.thing.skillthing;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.space.Pixel;
import com.ztw33.javafinal.space.Position;
import com.ztw33.javafinal.thing.creature.Creature;

import javafx.scene.image.Image;

public class Fire extends SkillThing {
	
	static int count=0;
	
	static final int hurt = 15;
	
	public Fire(int row, int column) {
		image = new Image("fire.gif");
		pixel = new Pixel(column*70, row*52);
	}

	@Override
	public void run() {
		count++;
		System.out.println(count+"火线程开始");
		int step = 0;
		while (!isKilled && step < 8) {
			if (step < 8) {
				Position coordPosition = pixel.getCoordPosition();
				synchronized (field) {
					if (field.existBadCreature(coordPosition.getRow(), coordPosition.getColumn())) {
						Creature bad = field.getCreature(coordPosition.getRow(), coordPosition.getColumn());
						bad.beAttacked(hurt);
						System.out.println(count+"火对"+bad.getName()+"造成了"+hurt+"点伤害");
						break;
					}
				}
				try {
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pixel.setX(pixel.getX()+20);
				step++;
			} else {
				isKilled = true;
			}
		}
		isKilled = true;
		System.out.println(count+"火线程退出");
	}
}
