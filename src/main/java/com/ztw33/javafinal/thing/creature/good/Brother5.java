package com.ztw33.javafinal.thing.creature.good;

import com.ztw33.javafinal.skill.Shoot;
import com.ztw33.javafinal.space.BattleField;
import com.ztw33.javafinal.thing.skillthing.Fire;
import com.ztw33.javafinal.thing.skillthing.Water;

import javafx.scene.image.Image;

public class Brother5 extends CalabashBrother implements Shoot {
	
	
	public Brother5() {
		// TODO Auto-generated constructor stub
		image = new Image("bro5.png");
		name = "五娃";
		rank = 5;
	}

	@Override
	public void shoot(BattleField field) {
		synchronized (field) {
			if (!field.existGoodCreature(position.getRow(), position.getColumn()+1)) {
				field.createSkillThing(new Water(position.getRow(), position.getColumn()+1));
			}
			if (!field.existGoodCreature(position.getRow()-1, position.getColumn())) {
				field.createSkillThing(new Water(position.getRow()-1, position.getColumn()));
			}
			if (!field.existGoodCreature(position.getRow(), position.getColumn()-1)) {
				field.createSkillThing(new Water(position.getRow(), position.getColumn()-1));
			}
			if (!field.existGoodCreature(position.getRow()+1, position.getColumn())) {
				field.createSkillThing(new Water(position.getRow()+1, position.getColumn()));
			}
		}

		
	}
}
