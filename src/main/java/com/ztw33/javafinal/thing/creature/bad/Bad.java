package com.ztw33.javafinal.thing.creature.bad;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.thing.creature.Creature;
import com.ztw33.javafinal.thing.creature.CreatureState;

public abstract class Bad extends Creature implements Runnable {
	@Override
	public void run() {
		System.out.println(getName()+"线程开始");
		while(!isKilled) {
			synchronized (field) {
				if(state == CreatureState.RUNNING) {
					// 前方有敌人，触发战斗事件
					if (field.existGoodCreature(position.getX(), position.getY()-1)) {
						// TODO: 触发战斗事件
						Creature cala = field.getCreature(position.getX(), position.getY()-1);
						if (cala.getState() == CreatureState.RUNNING) {
							field.createBattleEvent(this, cala);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(getName()+"线程退出");
	}
}
