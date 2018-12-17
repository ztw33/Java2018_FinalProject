package com.ztw33.javafinal.thing;

import javafx.scene.image.Image;

public class Grandpa extends Good {

	public Grandpa() {
		image = new Image("grandpa.png");
		name = "爷爷";
	}
	
	@Override
	public Image getImage() {
		return image;
	}

}
