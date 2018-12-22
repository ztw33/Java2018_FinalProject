package com.ztw33.javafinal.loginfo;

import com.ztw33.javafinal.thing.creature.CreatureState;

public class CreatureInfo {

	public String name;
	public CreatureState state;
	public double hpPCT;
	public int row;
	public int column;
	
	public CreatureInfo(String name, CreatureState state, double hpPCT, int row, int column) {
		this.name = name;
		this.state = state;
		this.hpPCT = hpPCT;
		this.row = row;
		this.column = column;
	}
	
	public CreatureInfo(String name, int row, int column) {
		this.name = name;
		this.row = row;
		this.column = column;
	}
	
	public void setState(CreatureState state) {
		this.state = state;
	}
	
	public void setHpPCT(double pct) {
		this.hpPCT = pct;
	}
	
	@Override
	public String toString() {
		int temp = state.ordinal();
		String ret = name+" "+temp+" "+hpPCT+" "+row+" "+column+"\n";
		return ret;
	}
}
