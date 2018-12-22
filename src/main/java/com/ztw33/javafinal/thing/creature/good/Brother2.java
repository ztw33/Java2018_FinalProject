package com.ztw33.javafinal.thing.creature.good;

import javafx.scene.image.Image;

public class Brother2 extends CalabashBrother implements Runnable {
	
	public Brother2() {
		image = new Image("bro2.png");
		name = "二娃";
		rank = 2;
		
		fullHP = 150;		
		ATK = 40;
		DEF = 10;
		HP = fullHP;
	}
	
}
