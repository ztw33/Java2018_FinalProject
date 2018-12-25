package com.ztw33.javafinal.thing.creature.good;

import com.ztw33.javafinal.skill.Shoot;
import com.ztw33.javafinal.space.BattleField;
import com.ztw33.javafinal.thing.creature.Creature;
import com.ztw33.javafinal.thing.creature.CreatureState;
import com.ztw33.javafinal.thing.skillthing.Water;

import javafx.scene.image.Image;

public class Brother5 extends CalabashBrother implements Shoot {
	
	public Brother5() {
		image = new Image("bro5.png");
		name = "五娃";
		rank = 5;
		
		fullHP = 100;		
		ATK = 30;
		DEF = 15;
		HP = fullHP;
	}

	@Override
	public void shoot(BattleField field) {
		synchronized (field) {
			if (readyToShoot(position.getRow(), position.getColumn()+1)) {
				field.createSkillThing(new Water(position.getRow(), position.getColumn()+1));
			}
			if (readyToShoot(position.getRow()-1, position.getColumn())) {
				field.createSkillThing(new Water(position.getRow()-1, position.getColumn()));
			}
			if (readyToShoot(position.getRow(), position.getColumn()-1)) {
				field.createSkillThing(new Water(position.getRow(), position.getColumn()-1));
			}
			if (readyToShoot(position.getRow()+1, position.getColumn())) {
				field.createSkillThing(new Water(position.getRow()+1, position.getColumn()));
			}
		}	
	}
	
	private boolean readyToShoot(int row, int column) {
		if (field.existCreature(row, column)) {
			Creature creature = field.getCreature(row, column);
			if (creature instanceof Good || creature.getState() == CreatureState.DEAD) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}
