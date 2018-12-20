package com.ztw33.javafinal.thing.creature.good;

import javafx.scene.image.Image;

public class Brother1 extends CalabashBrother implements Runnable {

	public Brother1() {
		// TODO Auto-generated constructor stub
		image = new Image("bro1.png");
		name = "大娃";
		rank = 1;
	}
}
