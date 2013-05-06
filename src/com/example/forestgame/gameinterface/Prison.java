package com.example.forestgame.gameinterface;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;

import com.example.forestgame.GameScene;
import com.example.forestgame.MainActivity;
import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;

public class Prison {

    private boolean isEmpty;
    private Element element;
    private GameScene gameScene;
    
    private TextureRegion prisonTexture;
    private Sprite prisonSprite;
    
    public Prison(GameScene scene) {
	
	gameScene = scene;
	element = null;
	isEmpty = true;
    }
    
    public void addElement(Element e) {
	
	element = e;
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
	    prisonTexture = MainActivity.mainActivity.storage.getTexture(TableOfElements
			    								    . getTextureName
			    								    ( element));
	    prisonSprite = new Sprite ( MainActivity.TEXTURE_WIDTH * 136 / 625
		    			      , MainActivity.TEXTURE_HEIGHT * 1381 / 2000
		    			      , MainActivity.TEXTURE_WIDTH * 61 / 250
		    			      , MainActivity.TEXTURE_HEIGHT * 303 / 2000
		    			      , prisonTexture
		    			      , MainActivity.mainActivity.getVertexBufferObjectManager());
	    gameScene.attachChild(prisonSprite);
	}
	else gameScene.detachChild(prisonSprite);
    }
}
