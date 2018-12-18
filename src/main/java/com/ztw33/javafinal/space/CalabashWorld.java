package com.ztw33.javafinal.space;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ztw33.javafinal.formation.ChangShe;
import com.ztw33.javafinal.formation.HeYi;
import com.ztw33.javafinal.formation.HengE;
import com.ztw33.javafinal.formation.YanXing;
import com.ztw33.javafinal.thing.Bad;
import com.ztw33.javafinal.thing.CalabashBrother;
import com.ztw33.javafinal.thing.Creature;
import com.ztw33.javafinal.thing.Good;
import com.ztw33.javafinal.thing.Grandpa;
import com.ztw33.javafinal.thing.Minion;
import com.ztw33.javafinal.thing.Scorpion;
import com.ztw33.javafinal.thing.Snake;
import com.ztw33.javafinal.view.GuiPainter;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CalabashWorld {
	private static final int BATTLEFIELD_ROW = 11;
	private static final int BATTLEFIELD_COLUMN = 13;
	private static final int MINIONS_NUM = 6;
	
	private BattleField battleField = new BattleField(BATTLEFIELD_ROW, BATTLEFIELD_COLUMN);
	
	private ArrayList<CalabashBrother> brothers = new ArrayList<>();
	private Grandpa grandpa = new Grandpa();
	private Scorpion scorpion = new Scorpion();
	private Snake snake = new Snake();
	private ArrayList<Minion> minions = new ArrayList<>();
	
	ArrayList<Good> goods;
	ArrayList<Bad> bads = new ArrayList<>();
	
	private int goodsStartRow;
	private int goodsStartColumn;
	/* 0：长蛇阵；1：衡轭阵；2：鹤翼阵；3：雁行阵*/
	private int goodsCrtFmt = 0;
	
	private int badsStartRow;
	private int badsStartColumn;
	private int badsCrtFmt = 2;
	
	private Canvas battleFieldCanvas;
	private GuiPainter guiPainter;
	
	private ExecutorService creatureThreadPool = Executors.newCachedThreadPool();
	
	public CalabashWorld(Canvas battleFieldCanvas) {
		this.battleFieldCanvas = battleFieldCanvas;
		guiPainter = new GuiPainter(battleFieldCanvas, battleField);
		// 初始化葫芦娃
		for (int i = 1; i <= 7; i++) {
			brothers.add(new CalabashBrother(i));
		}
		
		// 葫芦娃阵营
		goods = new ArrayList<>(brothers);
		goods.add(grandpa);
		
		
		// 妖精阵营
		for (int i = 0; i < MINIONS_NUM; i++) {
			minions.add(new Minion());
		}
		for (int i = 0; i < MINIONS_NUM; i++) {
			bads.add(minions.get(i));
		}
		bads.add(scorpion);
		bads.add(snake);
		
		setBothSidesFormation();
		guiPainter.drawBattleField();
		
		Creature.setField(battleField);
	}

	public int getRow() {
		return BATTLEFIELD_ROW;
	}
	
	public int getColumn() {
		return BATTLEFIELD_COLUMN;
	}
	
	public Image getScorpionImage() {
		return scorpion.getImage();
	}
	
	public void displayBattleField(GraphicsContext gc) {
		battleField.guiDisplay(gc);
		for (Good good : goods) {
			System.out.println(good.getName()+":"+good.getPosition());
		}
		for (Bad bad : bads) {
			System.out.println(bad.getName()+":"+bad.getPosition());
		}
		
	}
	
	public void goodsChangeFormation() {
		battleField.clearAll();
		
		goodsCrtFmt = (++goodsCrtFmt)%4;
		setBothSidesFormation();
		guiPainter.drawBattleField();
	}

	public void badsChangeFormation() {
		battleField.clearAll();
		
		badsCrtFmt = (++badsCrtFmt)%4;
		setBothSidesFormation();
		guiPainter.drawBattleField();
	}
	
	private void setBothSidesFormation() {
		/* 0：长蛇阵；1：衡轭阵；2：鹤翼阵；3：雁行阵*/
		if (goodsCrtFmt == 0) {
			goodsStartRow = 2;
			goodsStartColumn = 1;
			ChangShe<Good> formation = new ChangShe<Good>();
			formation.arrangeCreature(battleField, goods, goodsStartRow, goodsStartColumn);
		} else if (goodsCrtFmt == 1) {
			goodsStartRow = 2;
			goodsStartColumn = 2;
			HengE<Good> formation = new HengE<Good>();
			formation.arrangeCreature(battleField, goods, goodsStartRow, goodsStartColumn);
		} else if (goodsCrtFmt == 2) {
			goodsStartRow = 2;
			goodsStartColumn = 4;
			HeYi<Good> formation = new HeYi<Good>();
			formation.arrangeCreature(battleField, goods, goodsStartRow, goodsStartColumn);
		} else if (goodsCrtFmt == 3) {
			goodsStartRow = 1;
			goodsStartColumn = 0;
			YanXing<Good> formation = new YanXing<Good>();
			formation.arrangeCreature(battleField, goods, goodsStartRow, goodsStartColumn);
		}
		
		if (badsCrtFmt == 0) {
			badsStartRow = 1;
			badsStartColumn = 11;
			ChangShe<Bad> formation = new ChangShe<Bad>();
			formation.arrangeCreature(battleField, bads, badsStartRow, badsStartColumn);
		} else if (badsCrtFmt == 1) {
			badsStartRow = 1;
			badsStartColumn = 11;
			HengE<Bad> formation = new HengE<Bad>();
			formation.arrangeCreature(battleField, bads, badsStartRow, badsStartColumn);
		} else if (badsCrtFmt == 2) {
			badsStartRow = 2;
			badsStartColumn = 8;
			HeYi<Bad> formation = new HeYi<Bad>();
			formation.arrangeCreature(battleField, bads, badsStartRow, badsStartColumn);
		} else if (badsCrtFmt == 3) {
			badsStartRow = 1;
			badsStartColumn = 4;
			YanXing<Bad> formation = new YanXing<Bad>();
			formation.arrangeCreature(battleField, bads, badsStartRow, badsStartColumn);
		}
	}

	public void gameRoundStart() {
		
	/*	for (Good good : goods) {
			creatureThreadPool.execute(good);
		}
		
		for (Bad bad : bads) {
			creatureThreadPool.execute(bad);
		}*/
		creatureThreadPool.execute(grandpa);
		//ExecutorService 
		Thread thread = new Thread(guiPainter);
		thread.start();
		creatureThreadPool.shutdown();
		while(!creatureThreadPool.isTerminated()) {
			
		}
		guiPainter.kill();
	}

}

	
