package com.ztw33.javafinal.loginfo;

public class SkillThingInfo {

	public String name;
	public int x;
	public int y;
	
	public SkillThingInfo(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public SkillThingInfo(String name) {
		this.name = name;
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		String ret = name+" "+x+" "+y+"\n";
		return ret;
		
	}
}
