package com.example.forestgame.gameinterface;

import org.andengine.entity.sprite.Sprite;
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
    
    public abstract void backToGameSlot(Element e);
    
    public abstract void show();
    
}
