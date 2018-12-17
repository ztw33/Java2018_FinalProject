package com.ztw33.javafinal.formation;

import java.util.ArrayList;

import com.ztw33.javafinal.space.BattleField;
import com.ztw33.javafinal.thing.Creature;

public class YanXing<T extends Creature> implements CanArrangeCreatures {

	@Override
	public boolean arrangeCreature(BattleField bf, ArrayList<? extends Creature> creatures, int startRow, int startColumn) {
		int row = bf.getRow(), column = bf.getColumn();
		if ((startRow < 0) || (startRow > row - 1) || (startColumn < 0) || (startColumn > column - 1) 
				|| (startRow + creatures.size() > row)) {
			System.err.println("鹤翼阵布置失败");
			return false;
		} else {
			for (int i = 0; i < creatures.size(); i++) {
				bf.setCreatrue(creatures.get(i), startRow + i, startColumn + i);
			}
		}
		
		return true;
	}

}
