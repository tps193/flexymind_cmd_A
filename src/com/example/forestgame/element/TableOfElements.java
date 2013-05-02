package com.example.forestgame.element;

import java.util.Random;

public class TableOfElements {
    
    private static class ElementInfo {

	private String name;
	private String texture;
	private int scores;
	private double chance;

	private ElementInfo(String name, String texture, int scores, double chance) {

	    this.name = name;
	    this.texture = texture;
	    this.scores = scores;
	    this.chance = chance;
	}
    }

    //Table includes info about elements:
    //column 0 - name of current element
    //column 1 - name of texture
    //column 2 - scores
    //column 3 - chance
    private static final ElementInfo[] ARRAY_OF_ELEMENTS = {

	new ElementInfo("GRASS", "GRASS_TEXTURE", 10, 0.35)
	, new ElementInfo("TREE", "TREE_TEXTURE", 50, 0.25)
	, new ElementInfo("SQUIRREL", "SQUIRREL_TEXTURE", 500, 0.2)
	, new ElementInfo("NUT", "NUT_TEXTURE", 3000, 0.184)
	, new ElementInfo("GOLDEN_NUT", "GOLDEN_NUT_TEXTURE", 20000, 0.01)
	, new ElementInfo("CROWN", "CROWN_TEXTURE", 100000, 0.005)
	, new ElementInfo("NUTS_KING", "NUTS_KING_TEXTURE", 500000, 0.001)
    };
    
    public static String getTextureName(String name) {

	for (ElementInfo el : ARRAY_OF_ELEMENTS) {

	    if (name == el.name) return el.texture;
	}
	return null;
    }
    
    public static int getScores(String name) {

	for (ElementInfo el : ARRAY_OF_ELEMENTS) {

	    if (name == el.name) return el.scores;
	}
	return -1;
    }
    
    public static double getChance(String name) {

	for (ElementInfo el : ARRAY_OF_ELEMENTS) {

	    if (name == el.name) return el.chance;
	}
	return -1;
    }
    
    public static String getNextLvl(String name) {

	for (int i = 0; i < ARRAY_OF_ELEMENTS.length; ++i) {

	    if (name == ARRAY_OF_ELEMENTS[i].name) {

		if (i != ARRAY_OF_ELEMENTS.length - 1) return ARRAY_OF_ELEMENTS[i+1].name;
		else return ARRAY_OF_ELEMENTS[i].name;
	    }
	}
	return null;
    }
    
    public static Element getRandomElement() {

	Random randomGenerator = new Random();
	double random = randomGenerator.nextDouble();

	if (random < 0.35) return new Element("GRASS");
	else if (random < 0.60) return new Element("TREE");
	else if (random < 0.80) return new Element("SQUIRREL");
	else if (random < 0.984) return new Element("NUT");
	else if (random < 0.994) return new Element("GOLDEN_NUT");
	else if (random < 0.999) return new Element("CROWN");
	return new Element("NUTS_KING");
    }
}