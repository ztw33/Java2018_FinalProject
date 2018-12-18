package com.ztw33.javafinal.thing;

import com.ztw33.javafinal.space.BattleField;
import com.ztw33.javafinal.space.Position;

public abstract class Creature extends Thing implements Runnable{
	protected String name;
	
	protected static BattleField field;
	
	public Creature() {
		position = new Position();
	}
	
	public static void setField(BattleField field) {
		Creature.field = field;
	}
	
	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public String getName() {
		return name;
	}
}
