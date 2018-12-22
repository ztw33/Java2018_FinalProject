package com.ztw33.javafinal.thing.creature.bad;

import com.ztw33.javafinal.skill.Shoot;
import com.ztw33.javafinal.space.BattleField;
import com.ztw33.javafinal.thing.skillthing.Knife;

import javafx.scene.image.Image;

public class Scorpion extends Bad implements Shoot {
	
	public Scorpion() {
		image = new Image("scorpion.png");
		name = "蝎子精";
		
		fullHP = 150;		
		ATK = 30;
		DEF = 15;
		HP = fullHP;
	}

	@Override
	public void shoot(BattleField field) {
		field.createSkillThing(new Knife(position.getRow(), position.getColumn()-1));
		
	}
}
