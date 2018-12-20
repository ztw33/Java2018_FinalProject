package com.ztw33.javafinal.space;

import java.util.concurrent.ExecutorService;

import com.ztw33.javafinal.event.BattleEvent;
import com.ztw33.javafinal.thing.Bad;
import com.ztw33.javafinal.thing.Creature;
import com.ztw33.javafinal.thing.Good;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BattleField {
	private Coord[][] coords;
	private int row;
	private int column;
	
	private static final int COORDWIDTH = 70;
	private static final int COORDHEIGHT = 52;
	private static final int IMAGEWIDTH = 60;
	private static final int IMAGEHEIGHT = 60;
	
	private ExecutorService battleEventThreadPool;
	
	public BattleField(int r, int c) {
		// TODO Auto-generated constructor stub
		row = r;
		column = c;
		coords = new Coord[r][c];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				coords[i][j] = new Coord();
			}
		}
		
	}
	
	public int getRow() { return row; }
	public int getColumn() { return column; }
	
	public boolean setCreatrue(Creature creature, int x, int y) {
		//System.out.println("即将在坐标点("+x+","+y+")放置"+creature.getName());
		if (x < 0 || x >= row || y < 0 || y >= column)
			return false;
		if (coords[x][y].setCreature(creature)) {
			creature.setPosition(x, y);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		String ret = "BattleField\n";
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature()) {
					ret += (coords[i][j].getCreatrue().getName() + "\t");
				} else {
					ret += "*\t";
				}
			}
			ret += "\n";
		}		
		return ret;
	}
	
	public void clearAll() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				coords[i][j].clearCreature();
			}
		}
	}
	
	public void clearCreature(int x, int y) {
		coords[x][y].clearCreature();
	}
/*	
	public void clearGoods() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature() && coords[i][j].getCreatrue() instanceof Good) {
					coords[i][j].clearCreature();
				}
			}
		}
	}
	
	public void clearBads() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature() && coords[i][j].getCreatrue() instanceof Bad) {
					coords[i][j].clearCreature();
				}
			}
		}
	}*/
	
	public boolean existCreature(int x, int y) {
		if (x < 0 || x >= row || y < 0 || y >= column) {
			System.err.println("error at BattleField.existCreature");
			return true;
		}
		else {
			return coords[x][y].existCreature();
		}
	}
	
	public boolean existGoodCreature(int x, int y) {
		if (x < 0 || x >= row || y < 0 || y >= column) {
			return false;
		} else {
			if (coords[x][y].existCreature() && coords[x][y].getCreatrue() instanceof Good) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean existBadCreature(int x, int y) {
		if (x < 0 || x >= row || y < 0 || y >= column) {
			return false;
		} else {
			if (coords[x][y].existCreature() && coords[x][y].getCreatrue() instanceof Bad) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public void guiDisplay(GraphicsContext gc) {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature()) {
					Image image = coords[i][j].getCreatrue().getImage();
			        gc.drawImage(image, j*COORDWIDTH, i*COORDHEIGHT, IMAGEWIDTH, IMAGEHEIGHT);
				}
			}
		}
	}
	
	public Creature getCreature(int x, int y) {
		return coords[x][y].getCreatrue();
	}
	
	public void setBattleEventThreadPool(ExecutorService pool) {
		battleEventThreadPool = pool;
	}
	
	public void createBattleEvent(Creature cala, Creature mons) {
		battleEventThreadPool.execute(new BattleEvent(cala, mons));
	}
}
