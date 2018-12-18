package com.ztw33.javafinal.space;

public class Position {
    private int x;
    private int y;
    public Position() {
        x = -1;
        y = -1;
    }

    public Position(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getI() {
        return y/52;
    }

    public int getJ() {
        return x/70;
    }

    public void setI(int i) {
        y = i*52;
    }

    public void setJ(int j) {
        x = j*70;
    }

    @Override
    public String toString() {
        return ""+x+" "+y;
    }
}