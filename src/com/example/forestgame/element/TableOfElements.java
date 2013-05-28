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
	private String helpTexture;

	private ElementInfo( String name
			   , String texture
			   , String helpTexture
			   , int scores
			   , double chance
			   , String nextLevelElement) {

	    this.name = name;
	    this.texture = texture;
	    this.helpTexture = helpTexture;
	    this.scores = scores;
	    this.chance = chance;
	    this.nextLevelElement = nextLevelElement;
	}
    }

    //Table includes info about elements:
    //column 0 - name of current element
    //column 1 - name of texture in AtlasStorage
    //column 2 - scores
    //column 3 - chance
    //column 4 - name of nextLevel Element
    private static final ElementInfo[] ARRAY_OF_ELEMENTS = {

	  new ElementInfo("GRASS",      	"gfx_grass.png",        "help_grass.png",	10,  		0.6, 		"TREE")
	, new ElementInfo("TREE",       	"gfx_tree.png",         "help_tree.png",	50,  		0.2, 		"SQUIRREL")
	, new ElementInfo("SQUIRREL",   	"gfx_squirrel.png",     "help_squirrel.png",	250,  		0.0375,		"NUT")
	  
	, new ElementInfo("FORESTER",		"gfx_forester.png",	"help_forester.png",	100,		0.05,		"HUT") //textures needed
	, new ElementInfo("DROP",		"gfx_drop.png",		"help_drop.png",	1000,		0.04,		"POND") //textures needed
	, new ElementInfo("MAGIC_STICK",	"gfx_magic_stick.png",	"help_magic_stick.png",	0,		0.04,		"POND") //textures needed
	, new ElementInfo("FLYING_SQUIRREL",	"gfx_flying_squirrel.png", "help_flying_squirrel.png",	1000,	0.02,		"SQUIRREL") //textures needed
	  
	, new ElementInfo("NUT",        	"gfx_nut.png",          "help_nut.png",		1000,  		0.009375,	"GOLDEN_NUT")
	, new ElementInfo("GOLDEN_NUT", 	"gfx_golden_nut.png",   "help_golden_nut.png",	5000,  		0.003125,	"CROWN")
	, new ElementInfo("CROWN",      	"gfx_crown.png",       	"help_crown.png",	25000,  	0,		"NUTS_KING")
	, new ElementInfo("NUTS_KING",		"gfx_nuts_king.png",  	"help_nuts_king.png",	100000, 	0,		"NUTS_IMPEROR") //textures needed
	, new ElementInfo("NUTS_IMPEROR",	"gfx_nuts_imperor.png",	"help_nuts_imperor.png", 500000, 	0,		"NUTS_MAGNUM") //textures needed
	, new ElementInfo("NUTS_MAGNUM",	"gfx_nuts_magnum.png",	"help_nuts_magnum.png",	1000000,	0,		"NUTS_MAGNUM") //textures needed
	
	, new ElementInfo("HUT",		"gfx_hut.png",		"help_hut.png",		1000,		0,		"COUNTRY") //textures needed
	, new ElementInfo("COUNTRY",		"gfx_country.png",	"help_country.png",	10000,		0,		"CITY") //textures needed
	, new ElementInfo("CITY",		"gfx_city.png",		"help_city.png",	100000,		0,		"MEGAPOLIS") //textures needed
	, new ElementInfo("MEGAPOLIS",		"gfx_megapolis.png",	"help_megapolis.png",	1000000,	0,		"MEGAPOLIS") //textures needed
	  
	, new ElementInfo("POND",		"gfx_pond.png",		"help_pond.png",	500,		0,		"SWAMP") //textures needed
	, new ElementInfo("SWAMP",		"gfx_swamp.png",	"help_swamp.png",	5000,		0,		"LAKE") //textures needed
	, new ElementInfo("LAKE",		"gfx_lake.png",		"help_lake.png",	50000,		0,		"SEA") //textures needed
	, new ElementInfo("SEA",		"gfx_sea.png",		"help_sea.png",		500000,		0,		"SEA") //textures needed
    };
   
    public static String getTextureName(Element el) {

	for (ElementInfo elInfo : ARRAY_OF_ELEMENTS) {
	    if (el.getName().equals(elInfo.name)) {
		return elInfo.texture;
	    }
	}
	return null;
    }
    
    public static String getHelpTextureName(Element el) {
	for (ElementInfo elInfo : ARRAY_OF_ELEMENTS) {
	    if (el.getName().equals(elInfo.name)) {
		return elInfo.helpTexture;
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
}