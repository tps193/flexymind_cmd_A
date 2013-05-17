package com.example.forestgame;


import org.andengine.entity.sprite.Sprite;

import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;

class Slot {
    
    private boolean isEmpty;
    boolean hasSimilarNeighbor; // Not private, has only one similar neighbor
    boolean readyForNextLevel; // Not private, has more than 1 similar neighbor
    private Element element;
    private Sprite slotSprite;
    
    public Slot() {
	
	isEmpty = true;
	hasSimilarNeighbor = false;
	readyForNextLevel = false;
	slotSprite = null;
    }
    
    public void addElement(Element e) {
	
	element = e;
	isEmpty = false;
    }
    
    public Element getNextLevelElement() {
	
	element.changeToNextLvl();
	hasSimilarNeighbor = false;
	readyForNextLevel = false;
	return element;
    }
    
    public boolean isEmpty() {
	
	return isEmpty;
    }
    
    public boolean isSimilarTo(Element e) {
	
	if (this.isEmpty) {
	    return false; 
	    
	} else {
	    
	    return (this.element.getName().equals(e.getName()));
	}
    }
    
    public Element getElement() {
	
	return element;
    }
    
    public int getScore() {
	
	return TableOfElements.getScores(element);
    }
    
    public void setSprite(Sprite sprite) {

	slotSprite = sprite;
    }
    
    public Sprite getSprite() {

	return slotSprite;
    }    
}