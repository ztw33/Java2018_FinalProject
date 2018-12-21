package com.ztw33.javafinal.thing.creature.good;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.skill.Cure;
import com.ztw33.javafinal.thing.creature.Creature;
import com.ztw33.javafinal.thing.creature.CreatureState;

import javafx.scene.image.Image;

public class Grandpa extends Good implements Cure {

	private final int CURE_ADDHP = 10;
	
	public Grandpa() {
		image = new Image("grandpa.png");
		name = "爷爷";
	}

	@Override
	public void cure() {
		ArrayList<CalabashBrother> brothers = field.getRunningBrothers();
		if (brothers.isEmpty()) {
			return;
		}
		state = CreatureState.CURE;
		for (int i = 0; i < brothers.size(); i++) {
			brothers.get(i).getCured(CURE_ADDHP);
		}
		System.out.println("爷爷完成了一次治疗");
	}

	@Override
	public void run() {
		System.out.println(getName()+"线程开始");
		int step = 0;
		while(!isKilled) {
			synchronized (field) {
				if (state != CreatureState.DEAD) {
					// 前方有妖精，触发战斗事件
					if (field.existBadCreature(position.getRow(), position.getColumn()+1)) {
						Creature monster = field.getCreature(position.getRow(), position.getColumn()+1);
						if (monster.getState() == CreatureState.RUNNING) {
							field.createBattleEvent(this, monster);
						} else {
							setCreatureOnNextPosition(getNextPosition());
							step++;
						}
					} else {
						setCreatureOnNextPosition(getNextPosition());
						step++;
					}
				} else if (state == CreatureState.CURE) { // 治疗时间持续1秒
					state = CreatureState.RUNNING;
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
			} else if (state == CreatureState.CURE){
				// 治疗时间持续1秒后爷爷恢复运动状态
				state = CreatureState.RUNNING;
			} else {
				// 每走10步触发一次治疗
				if (step%10 == 0) {
					cure();
				}
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
