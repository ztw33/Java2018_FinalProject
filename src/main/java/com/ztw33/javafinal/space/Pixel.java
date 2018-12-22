package com.ztw33.javafinal.space;

public class Pixel {
	
	/* 从左往右为x， 从上到下为y */
	private int x;
	private int y;
	
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	
	public Position getCoordPosition() {
		return new Position(y/52, x/70);
	}
}
