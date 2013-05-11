package com.example.forestgame.element;

import com.example.forestgame.MainActivity;

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

	name = TableOfElements.getNextLvl(this);
	MainActivity.mainActivity.mSound.play();
    }
}