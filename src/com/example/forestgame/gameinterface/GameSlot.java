package com.example.forestgame.gameinterface;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;

import com.example.forestgame.GameScene;
import com.example.forestgame.element.Element;

public abstract class GameSlot {

    protected boolean isEmpty;
    protected Element element;
    protected GameScene gameScene;
    
    protected TextureRegion slotTexture;
    protected Sprite slotSprite;
    
    public GameSlot(GameScene scene) {
	
	gameScene = scene;
	element = null;
	isEmpty = true;
    }
    
    public void addElement(Element e) {
	
	element = e;
	isEmpty = false;
	show();
    }
    
    public Element getElement() {
	
	return element;
    }
    
    public boolean isEmpty() {
	
	return isEmpty;
    }
    
    public void clear() {
	
	element = null;
	isEmpty = true;
	show();
    }
    
    protected void gameSlotAttach(int Z_INDEX) {
	
	gameScene.attachChild(slotSprite);
	gameScene.registerTouchArea(slotSprite);
	gameScene.setTouchAreaBindingOnActionDownEnabled(true);
	gameScene.setTouchAreaBindingOnActionMoveEnabled(true);
	    
	slotSprite.setZIndex(Z_INDEX);
	slotSprite.getParent().sortChildren();
    }
    
    protected void gameSlotDetach() {
	
	gameScene.detachChild(slotSprite);
	gameScene.unregisterTouchArea(slotSprite);
	slotSprite = null;
    }
    
    protected abstract void backToGameSlot(Element e);
    
    protected abstract void show();
    
    protected abstract void gameSlotIsActionDown(int row, int column);
    
    protected abstract void gameSlotIsActionUp(int row, int column);
    
    protected abstract void gameSlotIsActionMove(int row, int column, Sprite slotSprite, TouchEvent pSceneTouchEvent);
}
