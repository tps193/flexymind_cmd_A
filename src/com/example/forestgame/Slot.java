package com.example.forestgame;


import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationByModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;

import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;

class Slot {
    
    private boolean isEmpty;
    private boolean hasSimilarNeighbor; // has only one similar neighbor
    private boolean readyForNextLevel; // has more than 1 similar neighbor
    private Element element;
    private Sprite slotSprite;
    
    private static float rotationDuration = 0.3f;
    private LoopEntityModifier rotationModifier = new LoopEntityModifier(
							new SequenceEntityModifier(
								new RotationByModifier(rotationDuration/4, 5)
								, new RotationByModifier(rotationDuration/2, -10)
								, new RotationByModifier(rotationDuration/4, 5)));
    
    public Slot() {
	
	isEmpty = true;
	hasSimilarNeighbor = false;
	readyForNextLevel = false;
	slotSprite = null;
    }
    
    public void setHasSimilarNeighbor(boolean has) {
	
	hasSimilarNeighbor = has;
    }
    
    public boolean getHasSimilarNeighbor() {
	
	return hasSimilarNeighbor;
    }
    
    public void setReadyForNextLevel(boolean ready) {
	
	readyForNextLevel = ready;
    }
    
    public boolean getReadyForNextLevel() {
	
	return readyForNextLevel;
    }
    
    public void addElement(Element e) {
	
	element = e;
	isEmpty = false;
    }
    /*
    public Element getNextLevelElement() {
	
	element.changeToNextLvl();
	hasSimilarNeighbor = false;
	readyForNextLevel = false;
	return element;
    }
    */
    public boolean isEmpty() {
	
	return isEmpty;
    }
    
    public boolean isSimilarTo(Element e) {
	
	if (e == null) {
	    
	    return false;
	} else if (this.isEmpty) {
	    
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
    
    public void reduceNeighbor() {
	
	if (readyForNextLevel) {
	    
	    readyForNextLevel = false;
	} else {
	    
	    hasSimilarNeighbor = false;
	}
    }
    
    public void addEntityModifier() {
	
	slotSprite.registerEntityModifier(rotationModifier);
    }
    
    public void removeEntityModifier() {
	
	slotSprite.unregisterEntityModifier(rotationModifier);
	//slotSprite.clearEntityModifiers();
    }
}