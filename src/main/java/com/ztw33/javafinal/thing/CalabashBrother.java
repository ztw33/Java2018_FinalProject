package com.ztw33.javafinal.thing;

import javafx.scene.image.Image;

public class CalabashBrother extends Good {

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

}
