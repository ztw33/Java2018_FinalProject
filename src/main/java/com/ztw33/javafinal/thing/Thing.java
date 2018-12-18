package com.ztw33.javafinal.thing;

import com.ztw33.javafinal.space.Position;

import javafx.scene.image.Image;

public abstract class Thing {
	protected Image image;
	abstract public Image getImage();
	
	protected Position position;
}
