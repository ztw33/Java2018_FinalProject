package com.ztw33.javafinal.thing.creature.good;

import com.ztw33.javafinal.skill.Shoot;
import com.ztw33.javafinal.space.BattleField;
import com.ztw33.javafinal.thing.skillthing.Calabash;

import javafx.scene.image.Image;

public class Brother7 extends CalabashBrother implements Shoot {
	
	public Brother7() {
		image = new Image("bro7.png");
		name = "七娃";
		rank = 7;
		
		fullHP = 150;		
		ATK = 30;
		DEF = 15;
		HP = fullHP;
	}

	@Override
	public void shoot(BattleField field) {
		field.createSkillThing(new Calabash(position.getRow(), position.getColumn()+1, field.getWidth()));
		
	}
}
