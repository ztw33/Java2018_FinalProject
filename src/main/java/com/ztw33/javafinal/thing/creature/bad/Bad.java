package com.ztw33.javafinal.thing.creature.bad;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.skill.Shoot;
import com.ztw33.javafinal.thing.creature.Creature;
import com.ztw33.javafinal.thing.creature.CreatureState;

public abstract class Bad extends Creature implements Runnable {
	@Override
	public void run() {
		System.out.println(getName()+"线程开始");
		int step = 0;
		while(!isKilled) {
			synchronized (field) {
				if(state == CreatureState.RUNNING) {
					// 前方有敌人，触发战斗事件
					if (field.existGoodCreature(position.getRow(), position.getColumn()-1)) {
						// TODO: 触发战斗事件
						Creature cala = field.getCreature(position.getRow(), position.getColumn()-1);
						if (cala.getState() == CreatureState.RUNNING || cala.getState() == CreatureState.CURE) {
							field.createBattleEvent(this, cala);
						} else {
							setCreatureOnNextPosition(getNextPosition());
							step++;
						}
					} else {
						setCreatureOnNextPosition(getNextPosition());
						step++;
						/* 释放技能(每走3步释放一次技能) */
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
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(getName()+"线程退出");
	}

	public void getCured(int add) {
		CreatureState preState = state;
		state = CreatureState.CURE;
		HP += add;
		if (HP > fullHP) {
			HP = fullHP;
		}
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		state = preState;
	}
}
