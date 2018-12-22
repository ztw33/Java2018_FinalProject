package com.ztw33.javafinal.space;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.formation.ChangShe;
import com.ztw33.javafinal.formation.HeYi;
import com.ztw33.javafinal.formation.HengE;
import com.ztw33.javafinal.formation.YanXing;
import com.ztw33.javafinal.loginfo.CreatureInfo;
import com.ztw33.javafinal.loginfo.FrameInfo;
import com.ztw33.javafinal.loginfo.SkillThingInfo;
import com.ztw33.javafinal.thing.Thing;
import com.ztw33.javafinal.thing.creature.CreatureState;
import com.ztw33.javafinal.thing.creature.bad.Bad;
import com.ztw33.javafinal.thing.creature.bad.Minion;
import com.ztw33.javafinal.thing.creature.bad.Scorpion;
import com.ztw33.javafinal.thing.creature.bad.Snake;
import com.ztw33.javafinal.thing.creature.good.Brother1;
import com.ztw33.javafinal.thing.creature.good.Brother2;
import com.ztw33.javafinal.thing.creature.good.Brother3;
import com.ztw33.javafinal.thing.creature.good.Brother4;
import com.ztw33.javafinal.thing.creature.good.Brother5;
import com.ztw33.javafinal.thing.creature.good.Brother6;
import com.ztw33.javafinal.thing.creature.good.Brother7;
import com.ztw33.javafinal.thing.creature.good.Good;
import com.ztw33.javafinal.thing.creature.good.Grandpa;
import com.ztw33.javafinal.view.GuiPainter;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class CalabashWorld implements Runnable {
	private static final int BATTLEFIELD_ROW = 11;
	private static final int BATTLEFIELD_COLUMN = 13;
	private static final int MINIONS_NUM = 6;
	
	private BattleField battleField = new BattleField(BATTLEFIELD_ROW, BATTLEFIELD_COLUMN);
	
	//private ArrayList<CalabashBrother> brothers = new ArrayList<>();
	
	//private ArrayList<Minion> minions = new ArrayList<>();
	
	ArrayList<Good> goods = new ArrayList<>();
	ArrayList<Bad> bads = new ArrayList<>();
	
	private int goodsStartRow;
	private int goodsStartColumn;
	/* 0：长蛇阵；1：衡轭阵；2：鹤翼阵；3：雁行阵*/
	private int goodsCrtFmt = 0;
	
	private int badsStartRow;
	private int badsStartColumn;
	private int badsCrtFmt = 2;
	
	private Canvas battleFieldCanvas;
	
	private Button saveLogBtn;
	private Button discardBtn;
	
	private GuiPainter guiPainter;
	
	private ExecutorService creatureThreadPool = Executors.newCachedThreadPool(); // 所有生物线程
	private ExecutorService guiThread = Executors.newSingleThreadExecutor(); // GUI绘制是一个线程
	private ExecutorService battleEventThreadPool = Executors.newCachedThreadPool(); // 所有战斗事件
	private ExecutorService skillThingThreadPool = Executors.newCachedThreadPool(); // 所有释放的技能
	
	public CalabashWorld(Canvas battleFieldCanvas, Button saveLogBtn, Button discardBtn) {
		this.battleFieldCanvas = battleFieldCanvas;
		this.saveLogBtn = saveLogBtn;
		this.discardBtn = discardBtn;
		guiPainter = new GuiPainter(battleFieldCanvas, battleField);
		
		// 初始化葫芦娃
		Brother1 brother1 = new Brother1();
		Brother2 brother2 = new Brother2();
		Brother3 brother3 = new Brother3();
		Brother4 brother4 = new Brother4();
		Brother5 brother5 = new Brother5();
		Brother6 brother6 = new Brother6();
		Brother7 brother7 = new Brother7();
		Grandpa grandpa = new Grandpa();
		
		// 葫芦娃阵营
		goods.add(brother1);
		goods.add(brother2);
		goods.add(brother3);
		goods.add(brother4);
		goods.add(brother5);
		goods.add(brother6);
		goods.add(brother7);
		goods.add(grandpa);
		
		// 妖精阵营
		Scorpion scorpion = new Scorpion();
		Snake snake = new Snake();
		for (int i = 0; i < MINIONS_NUM; i++) {
			bads.add(new Minion(i+1));
		}
		bads.add(scorpion);
		bads.add(snake);
		
		setBothSidesFormation();
		guiPainter.drawBattleField();
		
		Thing.setField(battleField);
		battleField.setEventThreadPool(battleEventThreadPool, skillThingThreadPool);
	}

	/*public int getRow() {
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
		
	}*/
	
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
			//textArea.appendText("葫芦娃摆出了长蛇阵\n");
		} else if (goodsCrtFmt == 1) {
			goodsStartRow = 2;
			goodsStartColumn = 2;
			HengE<Good> formation = new HengE<Good>();
			formation.arrangeCreature(battleField, goods, goodsStartRow, goodsStartColumn);
			//textArea.appendText("葫芦娃摆出了衡轭阵\n");
		} else if (goodsCrtFmt == 2) {
			goodsStartRow = 2;
			goodsStartColumn = 4;
			HeYi<Good> formation = new HeYi<Good>();
			formation.arrangeCreature(battleField, goods, goodsStartRow, goodsStartColumn);
			//textArea.appendText("葫芦娃摆出了鹤翼阵\n");
		} else if (goodsCrtFmt == 3) {
			goodsStartRow = 1;
			goodsStartColumn = 0;
			YanXing<Good> formation = new YanXing<Good>();
			formation.arrangeCreature(battleField, goods, goodsStartRow, goodsStartColumn);
			//textArea.appendText("葫芦娃摆出了雁行阵\n");
		}
		
		if (badsCrtFmt == 0) {
			badsStartRow = 1;
			badsStartColumn = 11;
			ChangShe<Bad> formation = new ChangShe<Bad>();
			formation.arrangeCreature(battleField, bads, badsStartRow, badsStartColumn);
			//textArea.appendText("妖精摆出了长蛇阵\n");
		} else if (badsCrtFmt == 1) {
			badsStartRow = 1;
			badsStartColumn = 11;
			HengE<Bad> formation = new HengE<Bad>();
			formation.arrangeCreature(battleField, bads, badsStartRow, badsStartColumn);
			//textArea.appendText("妖精摆出了衡轭阵\n");
		} else if (badsCrtFmt == 2) {
			badsStartRow = 2;
			badsStartColumn = 8;
			HeYi<Bad> formation = new HeYi<Bad>();
			formation.arrangeCreature(battleField, bads, badsStartRow, badsStartColumn);
			//textArea.appendText("妖精摆出了鹤翼阵\n");
		} else if (badsCrtFmt == 3) {
			badsStartRow = 1;
			badsStartColumn = 4;
			YanXing<Bad> formation = new YanXing<Bad>();
			formation.arrangeCreature(battleField, bads, badsStartRow, badsStartColumn);
			//textArea.appendText("妖精摆出了雁行阵\n");
		}
	}

	public void gameRoundStart() {
		
		for (Good good : goods) {
			creatureThreadPool.execute(good);
		}
		
		for (Bad bad : bads) {
			creatureThreadPool.execute(bad);
		}
		creatureThreadPool.shutdown();
		guiThread.execute(guiPainter);
		guiThread.shutdown();

		while (!(allBadsDead()||allGoodsDead())) {}

		
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		guiPainter.kill();
		GraphicsContext gc = battleFieldCanvas.getGraphicsContext2D();
		if (allBadsDead() && !allGoodsDead()) {
			gc.drawImage(new Image("victory.png"), 230, 70);
			saveLogBtn.setVisible(true);
			discardBtn.setVisible(true);
		} else if (allGoodsDead() && !allBadsDead()) {
			gc.drawImage(new Image("failed.png"), 200, 100);
			saveLogBtn.setVisible(true);
			discardBtn.setVisible(true);
		}
		killAllTheThread();
		
		
		//while(!creatureThreadPool.isTerminated()) {}
	}

	@Override
	public void run() {
		System.out.println("葫芦世界线程开始");
		gameRoundStart();
		System.out.println("葫芦世界线程退出");
	}

	public void killAllTheThread() {
		for (Good good : goods) {
			good.kill();
		}
		for (Bad bad : bads) {
			bad.kill();
		}
		guiPainter.kill();
		battleEventThreadPool.shutdown();
		skillThingThreadPool.shutdown();
		/*while(!creatureThreadPool.isTerminated()) {}
		while(!battleEventThreadPool.isTerminated()) {}
		while(!skillThingThreadPool.isTerminated()) {}*/

	}
	
	private boolean allGoodsDead() {
		for (Good good : goods) {
			if (good.getState() != CreatureState.DEAD && !good.isKilled()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean allBadsDead() {
		for (Bad bad : bads) {
			if (bad.getState() != CreatureState.DEAD && !bad.isKilled()) {
				return false;
			}
		}
		return true;
	}
	
	public void saveGameLog(File file) throws Exception {
		BufferedWriter fout = null;
		try {
			fout = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<FrameInfo> frameInfos = guiPainter.getFrameInfos();
		for (int i = 0; i < frameInfos.size(); i++) {
			int creatureInfoNum = frameInfos.get(i).creatureInfos.size();
			fout.write(creatureInfoNum+"\n");
			for (CreatureInfo creatureInfo : frameInfos.get(i).creatureInfos) {
				fout.write(creatureInfo.toString());
			}
			int skillThingInfoNum = frameInfos.get(i).skillThingInfos.size();
			fout.write(skillThingInfoNum+"\n");
			for (SkillThingInfo skillThingInfo : frameInfos.get(i).skillThingInfos) {
				fout.write(skillThingInfo.toString());
			}
		}
		fout.flush();
		fout.close();
	}
}

	
