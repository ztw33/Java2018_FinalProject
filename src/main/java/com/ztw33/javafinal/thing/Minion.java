package com.ztw33.javafinal.thing;

import javafx.scene.image.Image;

public class Minion extends Bad {

	public Minion() {
		image = new Image("minion.png");
		name = "小喽啰";
	}
	
	@Override
	public Image getImage() {
		return image;
	}

}
