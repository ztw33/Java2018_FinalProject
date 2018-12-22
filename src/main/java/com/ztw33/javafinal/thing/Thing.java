package com.ztw33.javafinal.thing;

import com.ztw33.javafinal.space.BattleField;

import javafx.scene.image.Image;

public abstract class Thing {
	protected Image image;
	
	protected static BattleField field;
	
	public static void setField(BattleField field) {
		Thing.field = field;
	}
	
	public Image getImage() { return image; }
	
	protected boolean isKilled = false;
	
	public boolean isKilled() { return isKilled; }
}
