package com.ztw33.javafinal.thing.creature.bad;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.thing.creature.Creature;
import com.ztw33.javafinal.thing.creature.CreatureState;

import javafx.scene.image.Image;

public class Snake extends Bad {

	public Snake() {
		image = new Image("snake.png");
		name = "蛇精";
	}

}
