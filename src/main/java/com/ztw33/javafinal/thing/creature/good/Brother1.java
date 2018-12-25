package com.ztw33.javafinal.thing.creature.good;

import javafx.scene.image.Image;

public class Brother1 extends CalabashBrother {

	public Brother1() {
		image = new Image("bro1.png");
		name = "大娃";
		rank = 1;
		
		fullHP = 250;		
		ATK = 60;
		DEF = 10;
		HP = fullHP;
	}
}
