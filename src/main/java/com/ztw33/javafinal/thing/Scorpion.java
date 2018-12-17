package com.ztw33.javafinal.thing;

import javafx.scene.image.Image;

public class Scorpion extends Bad {
	
	public Scorpion() {
		image = new Image("scorpion.png");
		name = "蝎子精";
	}
	
	@Override
	public Image getImage() {
		return image;
	}
}
