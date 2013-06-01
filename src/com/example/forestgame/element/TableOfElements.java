package com.example.forestgame.element;

import java.util.Random;

public class TableOfElements {
    
    private static final Random randomGenerator = new Random();
    private static int avaliableRandomElements = 4;
    
    private static class ElementInfo {

	private String name;
	private String texture;
	private int scores;
	private double chance;
	private String nextLevelElement;
	private String nextLevelTexture;

	private ElementInfo( String name
			   , String texture
			   , String nextLevelTexture
			   , int scores
			   , double chance
			   , String nextLevelElement) {

	    this.name = name;
	    this.texture = texture;
	    this.nextLevelTexture = nextLevelTexture;
	    this.scores = scores;
	    this.chance = chance;
	    this.nextLevelElement = nextLevelElement;
	}
    }

    //Table includes info about elements:
    //column 0 - name of current element
    //column 1 - name of texture in AtlasStorage
    //column 2 - name of help texture in AtlasStorage
    //column 3 - scores
    //column 4 - chance
    //column 5 - name of nextLevel Element
    private static final ElementInfo[] ARRAY_OF_ELEMENTS = {

	  new ElementInfo("GRASS",      	"gfx_grass.png",        "gfx_tree.png",		10,  		0.6, 		"TREE")
	, new ElementInfo("TREE",       	"gfx_tree.png",         "gfx_squirrel.png",	50,  		0.2, 		"SQUIRREL")
	, new ElementInfo("SQUIRREL",   	"gfx_squirrel.png",     "gfx_nut.png",		250,  		0.0375,		"NUT")
	  
	, new ElementInfo("FORESTER",		"gfx_forester.png",	"gfx_hut.png",		100,		0.05,		"HUT") //textures needed
	, new ElementInfo("DROP",		"gfx_drop.png",		"gfx_drop.png",		1000,		0.04,		"POND") //textures needed
	, new ElementInfo("MAGIC_STICK",	"gfx_magic_stick.png",	"gfx_magic_stick.png",	0,		0.04,		"POND") //textures needed
	, new ElementInfo("FLYING_SQUIRREL",	"gfx_flying_squirrel.png", "gfx_squirrel.png",	1000,		0.02,		"SQUIRREL") //textures needed
	  
	, new ElementInfo("NUT",        	"gfx_nut.png",          "gfx_golden_nut.png",	1000,  		0.009375,	"GOLDEN_NUT")
	, new ElementInfo("GOLDEN_NUT", 	"gfx_golden_nut.png",   "gfx_crown.png",	5000,  		0.003125,	"CROWN")
	, new ElementInfo("CROWN",      	"gfx_crown.png",       	"gfx_nuts_king.png",	25000,  	0,		"NUTS_KING")
	, new ElementInfo("NUTS_KING",		"gfx_nuts_king.png",  	"gfx_nuts_imperor.png",	100000, 	0,		"NUTS_IMPEROR") //textures needed
	, new ElementInfo("NUTS_IMPEROR",	"gfx_nuts_imperor.png",	"gfx_nuts_magnum.png", 	500000, 	0,		"NUTS_MAGNUM") //textures needed
	, new ElementInfo("NUTS_MAGNUM",	"gfx_nuts_magnum.png",	"gfx_nuts_magnum.png",	1000000,	0,		"NUTS_MAGNUM") //textures needed
	
	, new ElementInfo("HUT",		"gfx_hut.png",		"gfx_country.png",	1000,		0,		"COUNTRY") //textures needed
	, new ElementInfo("COUNTRY",		"gfx_country.png",	"gfx_city.png",		10000,		0,		"CITY") //textures needed
	, new ElementInfo("CITY",		"gfx_city.png",		"gfx_megapolis.png",	100000,		0,		"MEGAPOLIS") //textures needed
	, new ElementInfo("MEGAPOLIS",		"gfx_megapolis.png",	"gfx_megapolis.png",	1000000,	0,		"MEGAPOLIS") //textures needed
	  
	, new ElementInfo("POND",		"gfx_pond.png",		"gfx_swamp.png",	500,		0,		"SWAMP") //textures needed
	, new ElementInfo("SWAMP",		"gfx_swamp.png",	"gfx_lake.png",		5000,		0,		"LAKE") //textures needed
	, new ElementInfo("LAKE",		"gfx_lake.png",		"gfx_sea.png",		50000,		0,		"SEA") //textures needed
	, new ElementInfo("SEA",		"gfx_sea.png",		"gfx_sea.png",		500000,		0,		"SEA") //textures needed
    };
   
    public static String getTextureName(Element el) {

	for (ElementInfo elInfo : ARRAY_OF_ELEMENTS) {
	    if (el.getName().equals(elInfo.name)) {
		return elInfo.texture;
	    }
	}
	return null;
    }
    
    public static String getNextLevelTextureName(Element el) {
	for (ElementInfo elInfo : ARRAY_OF_ELEMENTS) {
	    if (el.getName().equals(elInfo.name)) {
		return elInfo.nextLevelTexture;
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
		return ARRAY_OF_ELEMENTS[i].nextLevelElement;
	    }
	}
	return null;
	
    }
    
    public static Element getRandomElement() {

	double random = randomGenerator.nextDouble();
	double current = 0;
	for (int i = 0; i < avaliableRandomElements; i++) {
	    current = current + ARRAY_OF_ELEMENTS[i].chance;
	    if (random < current) {
		return new Element(ARRAY_OF_ELEMENTS[i].name);
	    }
	}
	return new Element("GRASS");
    }
    
    public static void renewAvaliableRandomElements(int score) {
	if (score > 10000) {
	    avaliableRandomElements = 9;
	} else if (score > 2000) {
	    avaliableRandomElements = 6;
	}
    }
    
    public static String getElementOfLvl(String firstElName, int lvl) {
	
	String name = firstElName;
	Element temp = new Element(firstElName);
	for(int i = 0; i < lvl; i++) {
	    
	    temp = new Element(getNextLvl(temp));
	    name = temp.getName();
	}
	return name;
    }
}