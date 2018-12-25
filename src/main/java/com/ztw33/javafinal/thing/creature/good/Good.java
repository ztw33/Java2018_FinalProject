package com.ztw33.javafinal.thing.creature.good;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.skill.Shoot;
import com.ztw33.javafinal.thing.creature.Creature;
import com.ztw33.javafinal.thing.creature.CreatureState;

public abstract class Good extends Creature implements Runnable {
	
	@Override
	public void run() {
		System.out.println(getName()+"线程开始");
		int step = 0;
		while(!isKilled) {
			synchronized (field) {
				if(state == CreatureState.RUNNING) {
					// 前方有妖精，触发战斗事件
					if (field.existBadCreature(position.getRow(), position.getColumn()+1)) {
						Creature monster = field.getCreature(position.getRow(), position.getColumn()+1);
						if (monster.getState() == CreatureState.RUNNING || monster.getState() == CreatureState.CURE) {
							field.createBattleEvent(this, monster);
						} else {
							setCreatureOnNextPosition(getNextPosition());
							step++;
						}
					} else {
						setCreatureOnNextPosition(getNextPosition());
						step++;
						/* 释放技能 */
						if (this instanceof Shoot && (step+1)%3 == 0) {
							((Shoot)this).shoot(field);
						}
					}
				}
			}
			if (state == CreatureState.DEAD) {
				try {
					TimeUnit.SECONDS.sleep(2);
					synchronized (field) {
						field.clearCreature(position.getRow(), position.getColumn());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isKilled = true;
			}
			try {
				if (((CalabashBrother)this).getRank() == 2) {
					TimeUnit.MILLISECONDS.sleep(300);
				}
				else {
					TimeUnit.SECONDS.sleep(1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(getName()+"线程退出");
	}
}
