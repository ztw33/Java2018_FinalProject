package com.ztw33.javafinal.thing.creature.good;

import javafx.scene.image.Image;

public class Brother3 extends CalabashBrother {
	public Brother3() {
		// TODO Auto-generated constructor stub
		image = new Image("bro3.png");
		name = "三娃";
		rank = 3;
		
		fullHP = 200;		
		ATK = 40;
		DEF = 20;
		HP = fullHP;
	}
}
