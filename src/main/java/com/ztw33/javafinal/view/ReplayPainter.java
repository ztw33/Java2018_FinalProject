package com.ztw33.javafinal.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.loginfo.CreatureInfo;
import com.ztw33.javafinal.loginfo.FrameInfo;
import com.ztw33.javafinal.loginfo.SkillThingInfo;
import com.ztw33.javafinal.thing.creature.CreatureState;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ReplayPainter implements Runnable {

	ArrayList<FrameInfo> frameInfos = new ArrayList<>();
	GraphicsContext gc;
	
	private boolean isKilled = false;
	
	final Image bro1 = new Image("bro1.png");
	final Image bro2 = new Image("bro2.png");
	final Image bro3 = new Image("bro3.png");
	final Image bro4 = new Image("bro4.png");
	final Image bro5 = new Image("bro5.png");
	final Image bro6 = new Image("bro6.png");
	final Image bro7 = new Image("bro7.png");
	final Image grandpa = new Image("grandpa.png");
	final Image minion = new Image("minion.png");
	final Image scorpion = new Image("scorpion.png");
	final Image snake = new Image("snake.png");
	final Image cureBrothers = new Image("cureBrothers.gif");
	final Image cureMonsters = new Image("cureMonsters.gif");
	final Image calabash = new Image("calabash.png");
	final Image fire = new Image("fire.gif");
	final Image water = new Image("water.gif");
	final Image knife = new Image("knife.png");
	final Image dead = new Image("tombstone.png");
	
	int result = 0;
	
	public ReplayPainter(File file, Canvas canvas) {
		gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		try {
			Scanner fin = new Scanner(new BufferedReader(new FileReader(file)));
			int frameNum = fin.nextInt();
			for (int count= 0; count < frameNum; count++) {
				FrameInfo frameInfo = new FrameInfo();
				int creatureInfoNum = fin.nextInt();
				for (int i = 0; i < creatureInfoNum; i++) {
					String name = fin.next();
					CreatureState state = CreatureState.values()[fin.nextInt()];
					double pct = fin.nextDouble();
					int row = fin.nextInt();
					int column = fin.nextInt();
					frameInfo.creatureInfos.add(new CreatureInfo(name, state, pct, row, column));
				}
				int skillThingNum = fin.nextInt();
				for (int i = 0; i < skillThingNum; i++) {
					String name = fin.next();
					int x = fin.nextInt();
					int y = fin.nextInt();
					frameInfo.skillThingInfos.add(new SkillThingInfo(name, x, y));
				}
				frameInfos.add(frameInfo);
			}
			result = fin.nextInt();
			fin.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(frameInfos.size());
	}
	
	@Override
	public void run() {
		for (FrameInfo frameInfo : frameInfos) {
			for (int i = 0; i < frameInfo.creatureInfos.size(); i++) {
				if (isKilled) {
					return;
				}
				
				CreatureInfo creatureInfo = frameInfo.creatureInfos.get(i);
				Image image = null;
				CharSequence ch = "小喽啰";
				boolean goodOrBad = true;
				if (creatureInfo.name.equals("大娃")) {
					image = bro1;
				} else if (creatureInfo.name.equals("二娃")) {
					image = bro2;
				} else if (creatureInfo.name.equals("三娃")) {
					image = bro3;
				} else if (creatureInfo.name.equals("四娃")) {
					image = bro4;
				} else if (creatureInfo.name.equals("五娃")) {
					image = bro5;
				} else if (creatureInfo.name.equals("六娃")) {
					image = bro6;
				} else if (creatureInfo.name.equals("七娃")) {
					image = bro7;
				} else if (creatureInfo.name.contains(ch)) {
					image = minion;
					goodOrBad = false;
				} else if (creatureInfo.name.equals("蝎子精")) {
					image = scorpion;
					goodOrBad = false;
				} else if (creatureInfo.name.equals("蛇精")) {
					image = snake;
					goodOrBad = false;
				} else if (creatureInfo.name.equals("爷爷")) {
					image = grandpa;
				}
				if (creatureInfo.state == CreatureState.DEAD) {
					image = dead;
				}
				gc.drawImage(image, creatureInfo.column*70, creatureInfo.row*52, 60, 60);
				if (creatureInfo.state == CreatureState.CURE) {
					if (goodOrBad) {
						gc.drawImage(cureBrothers, creatureInfo.column*70, creatureInfo.row*52, 60, 60);
					} else {
						gc.drawImage(cureMonsters, creatureInfo.column*70, creatureInfo.row*52, 60, 60);
					}
				}
				if (creatureInfo.state != CreatureState.DEAD) {
		        	gc.setLineWidth(0);
			        gc.setFill(Color.GREEN);
		        	gc.fillRect(creatureInfo.column*70, creatureInfo.row*52-3, 60*creatureInfo.hpPCT, 5);
		        	gc.setFill(Color.RED);
		        	gc.fillRect(creatureInfo.column*70+60*creatureInfo.hpPCT, creatureInfo.row*52-3, 60*(1-creatureInfo.hpPCT), 5);
				}
			}
			
			for (int i = 0; i < frameInfo.skillThingInfos.size(); i++) {
				SkillThingInfo skillThingInfo = frameInfo.skillThingInfos.get(i);
				Image image = null;
				if (skillThingInfo.name.equals("火")) {
					image = fire;
				} else if (skillThingInfo.name.equals("水")) {
					image = water;
				} else if (skillThingInfo.name.equals("宝葫芦")) {
					image = calabash;
				} else if (skillThingInfo.name.equals("砍刀")) {
					image = knife;
				}
				
				if (skillThingInfo.name.equals("宝葫芦")) {
					gc.drawImage(image, skillThingInfo.x, skillThingInfo.y);			
				} else {
					gc.drawImage(image, skillThingInfo.x, skillThingInfo.y, 60, 60);
				}
			}
			
			try {
				TimeUnit.MILLISECONDS.sleep(100);
				gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (result == 0) {
			gc.drawImage(new Image("failed.png"), 200, 100);
		} else {
			gc.drawImage(new Image("victory.png"), 230, 70);
		}
		kill();
	}

	public void kill() {
		isKilled = true;
	}
}
