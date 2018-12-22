package com.ztw33.javafinal.thing.creature.bad;

import javafx.scene.image.Image;

public class Minion extends Bad {

	public Minion(int rank) {
		image = new Image("minion.png");
		name = "小喽啰"+rank;
		
		fullHP = 100;		
		ATK = 40;
		DEF = 10;
		HP = fullHP;
	}
}
