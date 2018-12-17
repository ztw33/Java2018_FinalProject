package com.ztw33.javafinal.formation;

import java.util.ArrayList;

import com.ztw33.javafinal.space.BattleField;
import com.ztw33.javafinal.thing.Creature;

public interface CanArrangeCreatures {
	public boolean arrangeCreature(BattleField bf, ArrayList<? extends Creature> creatures, int startRow, int startColumn);
}
