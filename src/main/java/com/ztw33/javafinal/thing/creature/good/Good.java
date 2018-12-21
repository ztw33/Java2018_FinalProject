package com.ztw33.javafinal.thing.creature.good;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.thing.creature.Creature;
import com.ztw33.javafinal.thing.creature.CreatureState;

public abstract class Good extends Creature implements Runnable {
	
	@Override
	public void run() {
		System.out.println(getName()+"线程开始");
		while(!isKilled) {
			synchronized (field) {
				if(state == CreatureState.RUNNING) {
					// 前方有妖精，触发战斗事件
					if (field.existBadCreature(position.getX(), position.getY()+1)) {
						// TODO: 触发战斗事件
						Creature monster = field.getCreature(position.getX(), position.getY()+1);
						if (monster.getState() == CreatureState.RUNNING) {
							field.createBattleEvent(this, monster);
						} else {
							setCreatureOnNextPosition(getNextPosition());
						}
					} else {
						setCreatureOnNextPosition(getNextPosition());
					}
				}
			}
			if (state == CreatureState.DEAD) {
				try {
					TimeUnit.SECONDS.sleep(2);
					synchronized (field) {
						field.clearCreature(position.getX(), position.getY());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isKilled = true;
			}
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(getName()+"线程退出");
	}
}