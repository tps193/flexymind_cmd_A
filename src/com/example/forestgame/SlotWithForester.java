package com.example.forestgame;


public class SlotWithForester {
    
    private int column;
    private int row;
    private boolean hasAlreadyMoved;
    
    public SlotWithForester(int row, int column) {
	
	this.row = row;
	this.column = column;
	hasAlreadyMoved = false;
    }
    
    public int getRow() {
	
	return row;
    }
    
    public int getColumn() {
	
	return column;
    }
    
    public boolean getHasAlreadyMoved() {
	
	return hasAlreadyMoved;
    }
    
    public void setHasAlreadyMoved(boolean has) {
	
	hasAlreadyMoved = has;
    }
    
    public void foresterMoveTo(int row, int column) {
	
	this.row = row;
	this.column = column;
	hasAlreadyMoved = true;
    }
}
