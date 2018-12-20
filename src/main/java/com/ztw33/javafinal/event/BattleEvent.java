package com.ztw33.javafinal.event;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.thing.creature.Creature;
import com.ztw33.javafinal.thing.creature.CreatureState;

public class BattleEvent implements Runnable {

	Creature cala;
	Creature mons;
	
	public BattleEvent(Creature cala, Creature mons) {
		// TODO Auto-generated constructor stub
		this.cala = cala;
		this.mons = mons;
		cala.setState(CreatureState.INBATTLE);
		mons.setState(CreatureState.INBATTLE);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(cala.getName()+"和"+mons.getName()+"战斗线程开始");
		while (cala.getHP() > 0 && mons.getHP() > 0 && !cala.isKilled() && !mons.isKilled()) {
			Random random = new Random();
			int temp = random.nextInt(2);
			if (temp == 0) {
				//System.out.println(cala.getName()+"攻击了"+mons.getName());
				mons.beAttacked(cala.getATK());
				
			} else {
				//System.out.println(mons.getName()+"攻击了"+cala.getName());
				cala.beAttacked(mons.getATK());
				
			}
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (cala.getHP() <= 0) {
			cala.setState(CreatureState.DEAD);
			mons.setState(CreatureState.RUNNING);
		}
		if (mons.getHP() <= 0) {
			mons.setState(CreatureState.DEAD);
			cala.setState(CreatureState.RUNNING);
		}
		System.out.println(cala.getName()+"和"+mons.getName()+"战斗线程退出");
	}

}
