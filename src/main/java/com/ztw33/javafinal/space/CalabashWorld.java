package com.ztw33.javafinal.space;

import java.util.ArrayList;
import java.util.Collections;

import com.ztw33.javafinal.formation.ChangShe;
import com.ztw33.javafinal.formation.HeYi;
import com.ztw33.javafinal.formation.HengE;
import com.ztw33.javafinal.formation.YanXing;
import com.ztw33.javafinal.thing.Bad;
import com.ztw33.javafinal.thing.CalabashBrother;
import com.ztw33.javafinal.thing.Good;
import com.ztw33.javafinal.thing.Grandpa;
import com.ztw33.javafinal.thing.Minion;
import com.ztw33.javafinal.thing.Scorpion;
import com.ztw33.javafinal.thing.Snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
enum FORMATION{CHANGSHE, HENGE, HEYI, YANXING};
public class CalabashWorld {
	private static final int BATTLEFIELD_ROW = 11;
	private static final int BATTLEFIELD_COLUMN = 13;
	private static final int MINIONS_NUM = 7;
	
	private BattleField battleField = new BattleField(BATTLEFIELD_ROW, BATTLEFIELD_COLUMN);
	
	private ArrayList<CalabashBrother> brothers = new ArrayList<>();
	private Grandpa grandpa = new Grandpa();
	private Scorpion scorpion = new Scorpion();
	private Snake snake = new Snake();
	private ArrayList<Minion> minions = new ArrayList<>(Collections.nCopies(MINIONS_NUM,new Minion()));
	
	ArrayList<Good> goods;
	ArrayList<Bad> bads = new ArrayList<>();
	
	private int goodsStartRow;
	private int goodsStartColumn;
	/* 0：长蛇阵；1：衡轭阵；2：鹤翼阵；3：雁行阵*/
	private int goodsCrtFmt = 0;
	
	private int badsStartRow;
	private int badsStartColumn;
	private int badsCrtFmt = 2;
	
	private int tempStartRow;
	private int tempStartColumn;
	
	public CalabashWorld() {
		// 初始化葫芦娃
		for (int i = 1; i <= 7; i++) {
			brothers.add(new CalabashBrother(i));
		}
		
		// 葫芦娃阵营
		goods = new ArrayList<>(brothers);
		goods.add(grandpa);
		
		
		// 妖精阵营
		for (int i = 0; i < MINIONS_NUM; i++) {
			bads.add(minions.get(i));
		}
		bads.add(scorpion);
		bads.add(snake);
		
		setBothSidesFormation();
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
	}
	
	public void goodsChangeFormation() {
		battleField.clear();
		
		goodsCrtFmt = (++goodsCrtFmt)%4;
		setBothSidesFormation();

	}

	public void badsChangeFormation() {
		battleField.clear();
		
		badsCrtFmt = (++badsCrtFmt)%4;
		setBothSidesFormation();

	}
	
	private void setBothSidesFormation() {
		/* 0：长蛇阵；1：衡轭阵；2：鹤翼阵；3：雁行阵*/
		if (goodsCrtFmt == 0) {
			int tempStartRow = 2;
			int tempStartColumn = 1;
			ChangShe<Good> formation = new ChangShe<Good>();
			if(!formation.arrangeCreature(battleField, goods, tempStartRow, tempStartColumn)) {
				battleField.clearGoods();
			} else {
				goodsStartRow = tempStartRow;
				goodsStartColumn = tempStartColumn;
			}
				
		} else if (goodsCrtFmt == 1) {
			int tempStartRow = 2;
			int tempStartColumn = 2;
			HengE<Good> formation = new HengE<Good>();
			if(!formation.arrangeCreature(battleField, goods, tempStartRow, tempStartColumn)) {
				battleField.clearGoods();
			} else {
				goodsStartRow = tempStartRow;
				goodsStartColumn = tempStartColumn;
			}
		} else if (goodsCrtFmt == 2) {
			int tempStartRow = 2;
			int tempStartColumn = 4;
			HeYi<Good> formation = new HeYi<Good>();
			if(!formation.arrangeCreature(battleField, goods, tempStartRow, tempStartColumn)) {
				battleField.clearGoods();
			} else {
				goodsStartRow = tempStartRow;
				goodsStartColumn = tempStartColumn;
			}
		} else if (goodsCrtFmt == 3) {
			int tempStartRow = 1;
			int tempStartColumn = 0;
			YanXing<Good> formation = new YanXing<Good>();
			if(!formation.arrangeCreature(battleField, goods, tempStartRow, tempStartColumn)) {
				battleField.clearGoods();
			} else {
				goodsStartRow = tempStartRow;
				goodsStartColumn = tempStartColumn;
			}
		}
		
		if (badsCrtFmt == 0) {
			int tempStartRow = 1;
			int tempStartColumn = 11;
			ChangShe<Bad> formation = new ChangShe<Bad>();
			if(!formation.arrangeCreature(battleField, bads, tempStartRow, tempStartColumn)) {
				battleField.clearBads();
			} else {
				badsStartRow = tempStartRow;
				badsStartColumn = tempStartColumn;
			}
		} else if (badsCrtFmt == 1) {
			int tempStartRow = 1;
			int tempStartColumn = 11;
			HengE<Bad> formation = new HengE<Bad>();
			if(!formation.arrangeCreature(battleField, bads, tempStartRow, tempStartColumn)) {
				battleField.clearBads();
			} else {
				badsStartRow = tempStartRow;
				badsStartColumn = tempStartColumn;
			}
		} else if (badsCrtFmt == 2) {
			int tempStartRow = 1;
			int tempStartColumn = 8;
			HeYi<Bad> formation = new HeYi<Bad>();
			if(!formation.arrangeCreature(battleField, bads, tempStartRow, tempStartColumn)) {
				battleField.clearBads();
			} else {
				badsStartRow = tempStartRow;
				badsStartColumn = tempStartColumn;
			}
		} else if (badsCrtFmt == 3) {
			int tempStartRow = 1;
			int tempStartColumn = 3;
			YanXing<Bad> formation = new YanXing<Bad>();
			if(!formation.arrangeCreature(battleField, bads, tempStartRow, tempStartColumn)) {
				battleField.clearBads();
			} else {
				badsStartRow = tempStartRow;
				badsStartColumn = tempStartColumn;
			}
		}
	}
}
