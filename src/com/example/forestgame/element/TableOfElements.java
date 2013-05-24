package com.example.forestgame.element;

import java.util.Random;

public class TableOfElements {
    
    private static final Random randomGenerator = new Random();
    
    private static class ElementInfo {

	private String name;
	private String texture;
	private int scores;
	private double chance;

	private ElementInfo( String name
			   , String texture
			   , int scores
			   , double chance) {

	    this.name = name;
	    this.texture = texture;
	    this.scores = scores;
	    this.chance = chance;
	}
    }

    //Table includes info about elements:
    //column 0 - name of current element
    //column 1 - name of texture in AtlasStorage
    //column 2 - scores
    //column 3 - chance
    private static final ElementInfo[] ARRAY_OF_ELEMENTS = {

	  new ElementInfo("GRASS",      "gfx_grass.png",          10,  0.60)
	, new ElementInfo("TREE",       "gfx_tree.png",           50,  0.25)
	, new ElementInfo("SQUIRREL",   "gfx_squirrel.png",      500,  0.10)
	, new ElementInfo("NUT",        "gfx_nut.png",          3000,  0.045)
	, new ElementInfo("GOLDEN_NUT", "gfx_golden_nut.png",  20000,  0.004)
	, new ElementInfo("CROWN",      "gfx_crown.png",      100000,  0.0009)
	, new ElementInfo("NUTS_KING",  "gfx_nuts_king.png",  500000,  0.0001)
    };
   
    public static String getTextureName(Element el) {

	for (ElementInfo elInfo : ARRAY_OF_ELEMENTS) {
	    if (el.getName().equals(elInfo.name)) {
		return elInfo.texture;
	    }
	}
	return null;
    }
    
    public static int getScores(Element el) {

	for (ElementInfo elInfo : ARRAY_OF_ELEMENTS) {
	    if (el.getName().equals(elInfo.name)) {
		return elInfo.scores;
	    }
	}
	return -1;
    }
    
    public static double getChance(Element el) {

	for (ElementInfo elInfo : ARRAY_OF_ELEMENTS) {
	    if (el.getName().equals(elInfo.name)) {
		return elInfo.chance;
	    }
	}
	return -1;
    }
    
    public static String getNextLvl(Element el) {

	for (int i = 0; i < ARRAY_OF_ELEMENTS.length; ++i) {
	    if (el.getName().equals(ARRAY_OF_ELEMENTS[i].name)) {
		if (i != ARRAY_OF_ELEMENTS.length - 1) {
		    return ARRAY_OF_ELEMENTS[i+1].name;
		}
		else {
		    return ARRAY_OF_ELEMENTS[i].name;
		}
	    }
	}
	return null;
    }
    
    public static Element getRandomElement() {

	double random = randomGenerator.nextDouble();

	if (random < 0.60) {
	    return new Element("GRASS");
	}
	else if (random < 0.85) {
	    return new Element("TREE");
	}
	else if (random < 0.95) {
	    return new Element("SQUIRREL");
	}
	else if (random < 0.995) {
	    return new Element("NUT");
	}
	else if (random < 0.999) {
	    return new Element("GOLDEN_NUT");
	}
	else if (random < 0.9999) {
	    return new Element("CROWN");
	}
	return new Element("NUTS_KING");
    }
}