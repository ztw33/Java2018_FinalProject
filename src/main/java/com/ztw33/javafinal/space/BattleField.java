package com.ztw33.javafinal.space;

import com.ztw33.javafinal.thing.Creature;

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
		System.out.println("即将在坐标点("+x+","+y+")放置"+creature.getName());
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
	
	public void guiDisplay(GraphicsContext gc) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature()) {
					Image image = coords[i][j].getCreatrue().getImage();
			        gc.drawImage(image, j*COORDWIDTH, i*COORDHEIGHT, IMAGEWIDTH, IMAGEHEIGHT);
				}
			}
		}
	}
}
