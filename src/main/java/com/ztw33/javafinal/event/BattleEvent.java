package com.ztw33.javafinal.event;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.thing.creature.Creature;
import com.ztw33.javafinal.thing.creature.CreatureState;

public class BattleEvent implements Runnable {

	Creature cala;
	Creature mons;
	
	public BattleEvent(Creature cala, Creature mons) {
		this.cala = cala;
		this.mons = mons;
		cala.setState(CreatureState.INBATTLE);
		mons.setState(CreatureState.INBATTLE);
	}
	
	@Override
	public void run() {
		System.out.println(cala.getName()+"和"+mons.getName()+"战斗线程开始");
		while (cala.getHP() > 0 && mons.getHP() > 0 && !cala.isKilled() && !mons.isKilled()) {
			Random random = new Random();
			int temp = random.nextInt(2);
			// 0表示葫芦娃攻击了妖精，1表示妖精攻击了葫芦娃
			if (temp == 0) {
				mons.beAttacked(cala.getATK());
				
			} else {
				cala.beAttacked(mons.getATK());
			}
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (cala.getHP() <= 0) {
			cala.setState(CreatureState.DEAD);
		} else {
			cala.setState(CreatureState.RUNNING);
		}
		if (mons.getHP() <= 0) {
			mons.setState(CreatureState.DEAD);
		} else {
			mons.setState(CreatureState.RUNNING);
		}
		System.out.println(cala.getName()+"和"+mons.getName()+"战斗线程退出");
	}

}
