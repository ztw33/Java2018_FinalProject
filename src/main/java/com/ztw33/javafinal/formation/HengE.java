package com.ztw33.javafinal.formation;

import java.util.ArrayList;

import com.ztw33.javafinal.space.BattleField;
import com.ztw33.javafinal.thing.Creature;

public class HengE<T extends Creature> implements CanArrangeCreatures {

	@Override
	public boolean arrangeCreature(BattleField bf, ArrayList<? extends Creature> creatures, int startRow, int startColumn) {
		if ((startRow < 0) || (startRow + creatures.size() > bf.getRow()) || (startColumn < 1)) {
			System.err.println("衡轭阵布置失败");
			return false;
		} else {
			int indexRow = startRow, indexColumn = startColumn;
			int i = 0;
			while (i < creatures.size()) {
				if (!bf.setCreatrue(creatures.get(i), indexRow, indexColumn)) {
					System.err.println("衡轭阵布置失败");
					return false;
				}
				indexRow++;
				if (i % 2 == 0) {
					indexColumn--;
				} else {
					indexColumn++;
				}
				i++;
			}
		}
		return true;
	}

}
