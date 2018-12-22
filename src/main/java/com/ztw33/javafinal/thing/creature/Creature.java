package com.ztw33.javafinal.thing.creature;

import java.util.Random;

import com.ztw33.javafinal.space.Position;
import com.ztw33.javafinal.thing.Thing;

import javafx.scene.image.Image;

public abstract class Creature extends Thing implements Runnable{
	
	private static final Image deadImage = new Image("tombstone.png");
	
	protected String name;
	protected Position position;
	
	protected int fullHP;
	protected int HP;
	protected int ATK;
	protected int DEF;
	
	protected CreatureState state = CreatureState.RUNNING;
	
	public Creature() {
		position = new Position();
	}
	
	public double getHPPCT() {
		return (double)HP/(double)fullHP;
	}
	
	
	public void setPosition(int row, int column) {
		position.setRow(row);
		position.setColumn(column);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public String getName() {
		return name;
	}
	
	public void kill() {
		synchronized (field) {
			field.clearCreature(position.getRow(), position.getColumn());
			isKilled = true;
		}
	}
	
	protected Position getNextPosition() {
		// 0:上移；1:下移；2：左移；3：右移：4：原地
		Random random = new Random();
		int next = random.nextInt(5);
		if (position.getColumn() < field.getColumn()/3) {
			next = 3;
		} else if (position.getColumn() > field.getColumn()*2/3) {
			next = 2;
		} else if (position.getRow() < field.getRow()/2) {
			next = 1;
		} else if (position.getRow() > field.getRow()*3/4) {
			next = 0;
		} else if (next == 0 && position.getRow() <= 0) {
			next = 1;
		} else if (next == 1 && position.getRow() >= field.getRow() - 1) {
			next = 0;
		}
		
		Position nextPos;
		switch (next) {
		case 0:
			nextPos = new Position(position.getRow()-1, position.getColumn());
			break;
		case 1:
			nextPos = new Position(position.getRow()+1, position.getColumn());
			break;
		case 2:
			nextPos = new Position(position.getRow(), position.getColumn()-1);
			break;
		case 3:
			nextPos = new Position(position.getRow(), position.getColumn()+1);
			break;
		case 4:
			nextPos = new Position(position.getRow(), position.getColumn());
			break;
		default:
			nextPos = new Position();
			break;
		}
		return nextPos;
	}
	
	protected void setCreatureOnNextPosition(Position nextPos) {
		int preX = position.getRow();
		int preY = position.getColumn();
		if (field.setCreatrue(this, nextPos.getRow(), nextPos.getColumn())) {
			field.clearCreature(preX, preY);
		}
	}
	
	public int getHP() { return HP; }
	
	public int getATK() { return ATK; }
	
	public int getDEF() { return DEF; }
	
	public void beAttacked(int atk) {
		HP -= (atk - DEF);
		//System.out.println(name+"当前HP:"+HP);
	}
	
	public CreatureState getState() { return state; }
	
	public void setState(CreatureState state) {
		this.state = state;
		if (state == CreatureState.DEAD) {
			image = deadImage;
		}
	}
}
