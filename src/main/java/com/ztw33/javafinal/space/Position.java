package com.ztw33.javafinal.space;

public class Position {
	
    private int row;
    private int column;
    
    public Position() {
        row = -1;
        column = -1;
    }

    public Position(int row,int column) {
        this.row = row;
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}