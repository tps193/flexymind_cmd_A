package com.example.forestgame;


public class SlotWithFlyingSquirrel {

    private Slot slot;
    private int row;
    private int column;
    
    public SlotWithFlyingSquirrel(Slot slot, int row, int column) {
	
	this.slot = slot;
	this.row = row;
	this.column = column;
    }
    
    public Slot getSlot() {
	
	return slot;
    }
    
    public int getRow() {
	
	return row;
    }
    
    public int getColumn() {
	
	return column;
    }
}
