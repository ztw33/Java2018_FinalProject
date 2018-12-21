package com.ztw33.javafinal.thing.creature.bad;

import com.ztw33.javafinal.skill.Cure;

import javafx.scene.image.Image;

public class Snake extends Bad implements Cure {

	public Snake() {
		image = new Image("snake.png");
		name = "蛇精";
	}

	@Override
	public void cure() {
		
	}

}
