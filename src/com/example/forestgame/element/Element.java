package com.example.forestgame.element;

public class Element {

    private String name;

    public Element(String name) {

	this.name = name;
    }

    public String getName() {

	return this.name;
    }

    // Conversion several elements into one of higher level.
    public void changeToNextLvl() {

	name = TableOfElements.getNextLvl(name);	
    }
}