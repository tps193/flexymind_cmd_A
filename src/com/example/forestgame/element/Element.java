package com.example.forestgame.element;

public class Element {
	
	private int type;
	
	public Element(int type) {
		super();
		this.type = type;
	}
	
	
	public int getType() {
		return this.type;
	}
	
	// Преобразование нескольких элементов в 1 более высокого уровня.
	public void changeToNextLvl() {
		tableOfElements.getNextLvl(type);
	}
	
}
