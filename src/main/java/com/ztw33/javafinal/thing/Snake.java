package com.ztw33.javafinal.thing;

import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;

public class Snake extends Bad {

	public Snake() {
		image = new Image("snake.png");
		name = "蛇精";
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
		System.out.println("蛇精线程退出");
	}
}
