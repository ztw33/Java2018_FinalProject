package com.ztw33.javafinal.thing;

import com.ztw33.javafinal.space.Position;

import javafx.scene.image.Image;

public abstract class Thing {
	protected Image image;
	public Image getImage() { return image; }
	
	protected Position position;
}
