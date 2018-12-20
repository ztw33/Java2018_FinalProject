package com.ztw33.javafinal.thing;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.space.Position;

import javafx.scene.image.Image;
import javafx.scene.layout.ConstraintsBase;

public class Grandpa extends Good {

	public Grandpa() {
		image = new Image("grandpa.png");
		name = "爷爷";
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
					// 前方有妖精，触发战斗事件
					if (field.existBadCreature(position.getX(), position.getY()+1)) {
						// TODO: 触发战斗事件
						Creature monster = field.getCreature(position.getX(), position.getY()+1);
						field.createBattleEvent(this, monster);
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
		System.out.println("爷爷线程退出");
	}

}
