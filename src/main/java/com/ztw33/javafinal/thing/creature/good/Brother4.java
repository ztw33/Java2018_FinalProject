package com.ztw33.javafinal.thing.creature.good;

import com.ztw33.javafinal.skill.Shoot;
import com.ztw33.javafinal.space.BattleField;
import com.ztw33.javafinal.thing.skillthing.Fire;

import javafx.scene.image.Image;

public class Brother4 extends CalabashBrother implements Shoot {
	
	public Brother4() {
		image = new Image("bro4.png");
		name = "四娃";
		rank = 4;
	}

	@Override
	public void shoot(BattleField field) {
		field.createSkillThing(new Fire(position.getRow(), position.getColumn()+1));
	}
}
