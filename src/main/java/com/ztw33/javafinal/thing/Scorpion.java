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
		while(true) {
			synchronized (field) {
				int tempX = position.getX();
				int tempY = position.getY();
				//System.out.println(getName()+"当前位置："+position.getX()+","+position.getY());
				if(!field.setCreatrue(this, position.getX(), position.getY()-1)) {
					//System.out.println("放置失败");
					break;
				} else {
					//System.out.println("放置成功");
					//System.out.println(getName()+"现在位置："+position.getX()+","+position.getY());
					field.clearCreature(tempX, tempY);
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
