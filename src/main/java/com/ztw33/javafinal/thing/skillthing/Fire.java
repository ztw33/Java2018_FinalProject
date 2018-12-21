package com.ztw33.javafinal.thing.skillthing;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.space.Pixel;

import javafx.scene.image.Image;

public class Fire extends SkillThing {
	
	static int count=0;
	
	public Fire(int row, int column) {
		image = new Image("fire.gif");
		pixel = new Pixel(column*70, row*52);
	}

	@Override
	public void run() {
		count++;
		System.out.println(count+"火线程开始");
		int step = 0;
		while (!isKilled && step < 7) {
			if (step < 7) {
				pixel.setX(pixel.getX()+20);
				try {
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				step++;
			} else {
				isKilled = true;
			}
		}
		isKilled = true;
		System.out.println(count+"火线程退出");
	}
}
