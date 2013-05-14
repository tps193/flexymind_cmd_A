package com.example.forestgame.gameinterface;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;

import com.example.forestgame.GameScene;
import com.example.forestgame.MainActivity;
import com.example.forestgame.SlotMatrix;
import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;

public class Respawn {

    private boolean isEmpty;
    private Element element;
    private GameScene gameScene;
    
    private TextureRegion respawnTexture;
    private Sprite respawnSprite;
    
    private TextureRegion movingTexture;
    private Sprite movingSprite;
    
    private final static float RESPAWN_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 27 / 50;
    private final static float RESPAWN_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1381 / 2000;
    private final static float RESPAWN_WIDTH = MainActivity.TEXTURE_WIDTH * 61 / 250;
    private final static float RESPAWN_HEIGHT = MainActivity.TEXTURE_HEIGHT * 303 / 2000;
    private static final int RESPAWN_Z_INDEX = 401;
    
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
    
    public void setElement(Element e) {
	
	element = e;
    }
    
    public void clear() {
	
	element = null;
	isEmpty = true;
	show();
    }
    
    //For Animation
    public void backToRespawn(Element e) {
	
	respawnSprite.setPosition(RESPAWN_POSITION_LEFT
				  , RESPAWN_POSITION_UP);
    }
    
    public void show() {
	
	if(!isEmpty) {
	    
	    respawnTexture = MainActivity.mainActivity.storage.getTexture(TableOfElements
			    						  . getTextureName
			    						  (element));
	    
	    respawnSprite = new Sprite ( RESPAWN_POSITION_LEFT
		    			 , RESPAWN_POSITION_UP
		    			 , RESPAWN_WIDTH
		    			 , RESPAWN_HEIGHT
		    			 , respawnTexture
		    			 , MainActivity.mainActivity.getVertexBufferObjectManager()) {
		
		int row = SlotMatrix.getROWS()+2;
		int column = SlotMatrix.getCOLUMNS()+2;
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent
			    		     , float pTouchAreaLocalX
			    		     , float pTouchAreaLocalY) {

		    if (pSceneTouchEvent.isActionDown()) {
			
			row = SlotMatrix.getROWS()+2;
			column = SlotMatrix.getCOLUMNS()+2;
			Log.d("resp", "touch");
			Log.d("resp", Integer.toString(row));
			Log.d("resp", Integer.toString(column));
			    
		    } else if (pSceneTouchEvent.isActionUp()) {
			
			Log.d("resp", "no touch");
			Log.d("resp", Integer.toString(row));
			Log.d("resp", Integer.toString(column));
			    
			if (column == SlotMatrix.getCOLUMNS()+1 && row  == SlotMatrix.getROWS()+1 
				&& gameScene.getPrison().isEmpty()) {
			    
			    Log.d("resp", "newprison");
			    gameScene.getPrison().addElement(element);
			    clear();
			    generateElement();
			    
			} else if (row < SlotMatrix.getROWS() && column < SlotMatrix.getCOLUMNS() 
				&& gameScene.getSlotMatrix().isSlotEmpty(row, column)) {
			    
			    Log.d("resp", "newSlot");
			    gameScene.getSlotMatrix().putToSlot(element, row, column);
			    clear();
			    generateElement();
			    
			} else {
			    
			    Log.d("resp", Integer.toString(row));
			    Log.d("resp", Integer.toString(column));
			    Log.d("resp","nowhere");
			    backToRespawn(element);
			}
			
		    } else if (pSceneTouchEvent.isActionMove()) {
			    
			Log.d("resp", "move");
			    
			float touchPointX = pSceneTouchEvent.getX() - this.getWidth() / 2;
			float touchPointY = pSceneTouchEvent.getY() - this.getHeight() / 2 - (float)(this.getHeight() / gameScene.getOffset());
			this.setPosition(touchPointX, touchPointY);
			      
			gameScene.moveElement(touchPointX, touchPointY);
			column = gameScene.getPutInColum();
			row = gameScene.getPutInRow(); 
			Log.d("resp", Integer.toString(row));
			Log.d("resp", Integer.toString(column));
		    }
		    return true;
		}
	    };
	    
	    gameScene.attachChild(respawnSprite);
	    gameScene.registerTouchArea(respawnSprite);
	    gameScene.setTouchAreaBindingOnActionDownEnabled(true);
	    gameScene.setTouchAreaBindingOnActionMoveEnabled(true);
	    
	    respawnSprite.setZIndex(RESPAWN_Z_INDEX);
	    respawnSprite.getParent().sortChildren();
	    
	} else {
	    
	    gameScene.detachChild(respawnSprite);
	    gameScene.unregisterTouchArea(respawnSprite);
	    respawnSprite = null;
	}
    }
}
