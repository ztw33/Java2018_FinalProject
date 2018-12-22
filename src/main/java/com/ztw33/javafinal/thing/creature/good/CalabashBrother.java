package com.ztw33.javafinal.thing.creature.good;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.thing.creature.CreatureState;

public class CalabashBrother extends Good {

	protected int rank;

	public void getCured(int add) {
		CreatureState preState = state;
		state = CreatureState.CURE;
		HP += add;
		if (HP > fullHP) {
			HP = fullHP;
		}
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		state = preState;
	}
	
	public int getRank() { return rank; }
}
