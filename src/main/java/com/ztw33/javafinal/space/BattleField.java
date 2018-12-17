package com.ztw33.javafinal.space;

import com.ztw33.javafinal.thing.Bad;
import com.ztw33.javafinal.thing.Creature;
import com.ztw33.javafinal.thing.Good;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BattleField {
	private Coord[][] coords;
	private int row;
	private int column;
	
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
		return coords[x][y].setCreature(creature);
	}
/*	
	@Override
	public String toString() {
		String ret = "BattleField\n";
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature()) {
					ret += (coords[i][j].toString() + "\t");
				} else {
					ret += "*\t";
				}
			}
			ret += "\n";
		}		
		return ret;
	}*/
	
	public void clear() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				coords[i][j].clearCreature();
			}
		}
	}
	
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
	}
	
	public void guiDisplay(GraphicsContext gc) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature()) {
					Image image = coords[i][j].getCreatrue().getImage();
			        gc.drawImage(image, j*70, i*52, 60, 60);
				}
			}
		}
	}
}
