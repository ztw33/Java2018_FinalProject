package com.ztw33.javafinal.thing;

import javafx.scene.image.Image;

public abstract class Thing {
	protected Image image;
	public Image getImage() { return image; }
	
	protected boolean isKilled = false;
	
	public boolean isKilled() { return isKilled; }
}
