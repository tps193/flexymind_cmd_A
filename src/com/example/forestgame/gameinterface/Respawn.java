package com.example.forestgame.gameinterface;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;

import com.example.forestgame.GameScene;
import com.example.forestgame.MainActivity;
import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;

public class Respawn {

    private boolean isEmpty;
    private Element element;
    private GameScene gameScene;
    
    TextureRegion respawnTexture;
    Sprite respawnSprite;
    
    public Respawn(GameScene scene) {
	
	gameScene = scene;
	generateElement();
    }
    
    public void generateElement() {
	
	element = TableOfElements.getRandomElement();
	isEmpty = false;
	show();
    }
    
    public boolean isEmpty() {
	
	return isEmpty;
    }
    
    public Element getElement() {
	
	return element;
    }
    
    public void clear() {
	
	element = null;
	isEmpty = true;
	show();
    }
    
    public void show() {
	
	if(!isEmpty) {
	    respawnTexture = MainActivity.mainActivity.storage.getTexture(TableOfElements
			    								    . getTextureName
			    								    ( element));
	    respawnSprite = new Sprite ( MainActivity.TEXTURE_WIDTH * 27 / 50
		    			      , MainActivity.TEXTURE_HEIGHT * 1381 / 2000
		    			      , MainActivity.TEXTURE_WIDTH * 61 / 250
		    			      , MainActivity.TEXTURE_HEIGHT * 303 / 2000
		    			      , respawnTexture
		    			      , MainActivity.mainActivity.getVertexBufferObjectManager());
	    gameScene.attachChild(respawnSprite);
	}
	else gameScene.detachChild(respawnSprite);
    }
}
