package com.ztw33.javafinal.space;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

import com.ztw33.javafinal.event.BattleEvent;
import com.ztw33.javafinal.thing.creature.Creature;
import com.ztw33.javafinal.thing.creature.CreatureState;
import com.ztw33.javafinal.thing.creature.bad.Bad;
import com.ztw33.javafinal.thing.creature.good.CalabashBrother;
import com.ztw33.javafinal.thing.creature.good.Good;
import com.ztw33.javafinal.thing.skillthing.Calabash;
import com.ztw33.javafinal.thing.skillthing.Fire;
import com.ztw33.javafinal.thing.skillthing.SkillThing;
import com.ztw33.javafinal.thing.skillthing.Water;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BattleField {
	private Coord[][] coords;
	private int row;
	private int column;
	
	private static final int COORDWIDTH = 70;
	private static final int COORDHEIGHT = 52;
	private static final int IMAGEWIDTH = 60;
	private static final int IMAGEHEIGHT = 60;
	
	private ExecutorService battleEventThreadPool;
	private ExecutorService skillThingThreadPool;
	
	private ArrayList<SkillThing> skillThings = new ArrayList<>();
	
	private Image cureImage = new Image("cure.gif");
	
	public BattleField(int r, int c) {
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
	
	public int getWidth() {
		return COORDWIDTH*column;
	}
	
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
		
		/* 绘制生物 */
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature()) {
					Creature creature = coords[i][j].getCreatrue();
					Image image = creature.getImage();
			        gc.drawImage(image, j*COORDWIDTH, i*COORDHEIGHT, IMAGEWIDTH, IMAGEHEIGHT);
			        
			        if (creature.getState() == CreatureState.CURE) {
			        	gc.drawImage(cureImage, j*COORDWIDTH, i*COORDHEIGHT, IMAGEWIDTH, IMAGEHEIGHT);
			        }
			        /* 绘制血量条 */
			        if (creature.getState() != CreatureState.DEAD) {
			        	gc.setLineWidth(5);
				        double pct = creature.getHPPCT();
				        if (pct != 0) {
				        	gc.setStroke(Color.GREEN);
				        	gc.strokeLine(j*COORDWIDTH, i*COORDHEIGHT-3, j*COORDWIDTH+IMAGEWIDTH*pct, i*COORDHEIGHT-3);
				        }
				        
				        if(pct != 1) {
				        	gc.setStroke(Color.RED);
				        	gc.strokeLine(j*COORDWIDTH+IMAGEWIDTH*pct, i*COORDHEIGHT-3, j*COORDWIDTH+IMAGEWIDTH, i*COORDHEIGHT-3);
				        }
			        }
			     }
			}
		}
		
		/* 绘制技能特效 */
		synchronized (skillThings) {
			Iterator<SkillThing> iter = skillThings.iterator();
	        while (iter.hasNext()) {
	            SkillThing item = iter.next();
	            if (item.isKilled()) {
	                iter.remove();
	            }
	        }
		}
		
		
		/*for (int i = 0; i < skillThings.size(); i++) {
			if (skillThings.get(i).isKilled()) {
				skillThings.remove(i);
			}
		}错误的删除方式！*/
		for (SkillThing skillThing : skillThings) {
			if (skillThing instanceof Calabash) {
				gc.drawImage(skillThing.getImage(), skillThing.getPixelX(), skillThing.getPixelY());
			} else {
				gc.drawImage(skillThing.getImage(), skillThing.getPixelX(), skillThing.getPixelY(), IMAGEWIDTH, IMAGEHEIGHT);
			}
		}
		
	}
	
	public Creature getCreature(int x, int y) {
		return coords[x][y].getCreatrue();
	}
	
	public void setEventThreadPool(ExecutorService battleEventThreadPool, ExecutorService skillThingThreadPool) {
		this.battleEventThreadPool = battleEventThreadPool;
		this.skillThingThreadPool = skillThingThreadPool;
	}
	
	public void createBattleEvent(Creature cala, Creature mons) {
		battleEventThreadPool.execute(new BattleEvent(cala, mons));
	}

	public ArrayList<CalabashBrother> getRunningBrothers() {
		ArrayList<CalabashBrother> brothers = new ArrayList<>();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (coords[i][j].existCreature()) {
					Creature creature = coords[i][j].getCreatrue();
					if (creature instanceof CalabashBrother && creature.getState() == CreatureState.RUNNING) {
						brothers.add((CalabashBrother) creature);
					}
				}
			}
		}
		return brothers;
	}

	public void createSkillThing(SkillThing skillThing) {
		synchronized (skillThings) {
			skillThings.add(skillThing);
			skillThingThreadPool.execute(skillThing);
		}

	}
}
