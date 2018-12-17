package com.ztw33.javafinal.thing;

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
}
