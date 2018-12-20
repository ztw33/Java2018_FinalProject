package com.ztw33.javafinal.thing;

import java.util.Random;

import com.ztw33.javafinal.space.BattleField;
import com.ztw33.javafinal.space.Position;

public abstract class Creature extends Thing implements Runnable{
	protected String name;
	protected int HP;
	protected int ATK;
	protected int DEF;
	
	protected static BattleField field;
	
	protected boolean isKilled;
	protected boolean inBattle = false;
	
	public Creature() {
		position = new Position();
		isKilled = false;
		
		HP = 100;
		ATK = 30;
		DEF = 5;
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
	
	public void kill() {
		synchronized (field) {
			field.clearCreature(position.getX(), position.getY());
			isKilled = true;
		}
	}
	
	protected Position getNextPosition() {
		// 0:上移；1:下移；2：左移；3：右移：4：原地
		Random random = new Random();
		int next = random.nextInt(5);
		if (position.getY() < field.getColumn()/3) {
			next = 3;
		} else if (position.getY() > field.getColumn()*2/3) {
			next = 2;
		} else if (next == 0 && position.getX() <= 0) {
			next = 1;
		} else if (next == 1 && position.getX() >= field.getRow() - 1) {
			next = 0;
		}
		
		Position nextPos;
		switch (next) {
		case 0:
			nextPos = new Position(position.getX()-1, position.getY());
			break;
		case 1:
			nextPos = new Position(position.getX()+1, position.getY());
			break;
		case 2:
			nextPos = new Position(position.getX(), position.getY()-1);
			break;
		case 3:
			nextPos = new Position(position.getX(), position.getY()+1);
			break;
		case 4:
			nextPos = new Position(position.getX(), position.getY());
			break;
		default:
			nextPos = new Position();
			break;
		}
		return nextPos;
	}
	
	protected void setCreatureOnNextPosition(Position nextPos) {
		int preX = position.getX();
		int preY = position.getY();
		if (field.setCreatrue(this, nextPos.getX(), nextPos.getY())) {
			field.clearCreature(preX, preY);
		}
	}
	
	public int getHP() { return HP; }
	
	public int getATK() { return ATK; }
	
	public int getDEF() { return DEF; }
	
	public void beAttacked(int atk) {
		HP -= (atk - DEF);
		System.out.println(name+"当前HP:"+HP);
	}
	
	public void setInBattle() {
		inBattle = true;
	}
}
