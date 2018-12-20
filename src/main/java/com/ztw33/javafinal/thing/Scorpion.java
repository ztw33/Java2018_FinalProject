package com.ztw33.javafinal.thing;

import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;

public class Scorpion extends Bad {
	
	public Scorpion() {
		image = new Image("scorpion.png");
		name = "蝎子精";
	}
	
	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void run() {
		while(!isKilled) {
			synchronized (field) {
				if(!inBattle) {
					// 前方有敌人，触发战斗事件
					if (field.existGoodCreature(position.getX(), position.getY()-1)) {
						// TODO: 触发战斗事件
						Creature cala = field.getCreature(position.getX(), position.getY()-1);
						field.createBattleEvent(cala, this);
					} else {
						setCreatureOnNextPosition(getNextPosition());
					}
				}
			}
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("蝎子精线程退出");
	}
}
