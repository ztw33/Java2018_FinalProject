package com.ztw33.javafinal.thing.creature.bad;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.skill.Cure;
import com.ztw33.javafinal.thing.creature.Creature;
import com.ztw33.javafinal.thing.creature.CreatureState;

import javafx.scene.image.Image;

public class Snake extends Bad implements Cure {

	private final int CURE_ADDHP = 15;
	
	public Snake() {
		image = new Image("snake.png");
		name = "蛇精";
		
		fullHP = 200;		
		ATK = 40;
		DEF = 5;
		HP = fullHP;
	}

	@Override
	public void cure() {
		ArrayList<Bad> monsters = field.getRunningMonsters();
		if (monsters.isEmpty()) {
			return;
		}
		state = CreatureState.CURE;
		for (int i = 0; i < monsters.size(); i++) {
			monsters.get(i).getCured(CURE_ADDHP);
		}
		System.out.println("蛇精完成了一次治疗");
	}
	
	@Override
	public void run() {
		System.out.println(getName()+"线程开始");
		int step = 0;
		while(!isKilled) {
			synchronized (field) {
				if (state == CreatureState.RUNNING) {
					// 前方有敌人，触发战斗事件
					if (field.existGoodCreature(position.getRow(), position.getColumn()-1)) {
						Creature good = field.getCreature(position.getRow(), position.getColumn()-1);
						if (good.getState() == CreatureState.RUNNING || good.getState() == CreatureState.CURE) {
							field.createBattleEvent(good, this);
						} else {
							setCreatureOnNextPosition(getNextPosition());
							step++;
						}
					} else {
						setCreatureOnNextPosition(getNextPosition());
						step++;
					}
				}
			}
			switch (state) {
			case DEAD:
				try {
					TimeUnit.SECONDS.sleep(2);
					synchronized (field) {
						field.clearCreature(position.getRow(), position.getColumn());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isKilled = true;
				break;
			case RUNNING:
				// 每走10步触发一次治疗
				if (step%10 == 0) {
					cure();
				}
				break;
			case CURE:
				// 治疗时间持续1秒后蛇精恢复运动状态
				state = CreatureState.RUNNING;
				break;
			default:
				break;
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
