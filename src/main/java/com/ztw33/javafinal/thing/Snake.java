package com.ztw33.javafinal.thing;

import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;

public class Snake extends Bad {

	public Snake() {
		image = new Image("snake.png");
		name = "蛇精";
	}
	
	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void run() {
		while(true) {
			synchronized (field) {
				int tempX = position.getX();
				int tempY = position.getY();
				if(!field.setCreatrue(this, position.getX(), position.getY()-1)) {
					break;
				} else {
					field.clearCreature(tempX, tempY);
				}
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
