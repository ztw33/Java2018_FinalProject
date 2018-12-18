package com.ztw33.javafinal.thing;

import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;

public class CalabashBrother extends Good implements Runnable {

	int rank;
	
	public CalabashBrother(int rank) {
		this.rank = rank;
		switch (rank) {
		case 1:
			image = new Image("bro1.png");
			name = "大娃";
			break;
		case 2:
			image = new Image("bro2.png");
			name = "二娃";
			break;
		case 3:
			image = new Image("bro3.png");
			name = "三娃";
			break;
		case 4:
			image = new Image("bro4.png");
			name = "四娃";
			break;
		case 5:
			image = new Image("bro5.png");
			name = "五娃";
			break;
		case 6:
			image = new Image("bro6.png");
			name = "六娃";
			break;
		case 7:
			image = new Image("bro7.png");
			name = "七娃";
			break;
		default:
			System.err.println("葫芦娃初始化出错");
			break;
		}
	}
	
	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			synchronized (field) {
				int tempX = position.getX();
				int tempY = position.getY();
				//System.out.println(getName()+"当前位置："+position.getX()+","+position.getY());
				if(!field.setCreatrue(this, position.getX(), position.getY()+1)) {
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
		System.out.println(getName()+"线程退出");
		
	}

}
