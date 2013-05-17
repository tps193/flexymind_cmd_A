package com.example.forestgame.gameinterface;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.example.forestgame.GameScene;
import com.example.forestgame.MainActivity;
import com.example.forestgame.SlotMatrix;
import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;

public class Respawn extends GameSlot {
    
    private final static float RESPAWN_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 27 / 50;
    private final static float RESPAWN_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1381 / 2000;
    private final static float RESPAWN_WIDTH = MainActivity.TEXTURE_WIDTH * 61 / 250;
    private final static float RESPAWN_HEIGHT = MainActivity.TEXTURE_HEIGHT * 303 / 2000;
    private static final int RESPAWN_Z_INDEX = 401;
    
    public Respawn(GameScene scene) {
	
	super(scene);
	generateElement();
    }
    
    public void generateElement() {
	
	element = TableOfElements.getRandomElement();
	isEmpty = false;
	show();
    }
    
    //For Animation
    protected void backToGameSlot(Element e) {
	
	slotSprite.setPosition(RESPAWN_POSITION_LEFT
				  , RESPAWN_POSITION_UP);
	
    }
    
    protected void show() {
	
	if(!isEmpty) {
	    
	    slotTexture = MainActivity.mainActivity.storage.getTexture(TableOfElements
			    						  . getTextureName
			    						  (element));
	    
	    
	    
	    slotSprite = new Sprite ( RESPAWN_POSITION_LEFT
		    			 , RESPAWN_POSITION_UP
		    			 , RESPAWN_WIDTH
		    			 , RESPAWN_HEIGHT
		    			 , slotTexture
		    			 , MainActivity.mainActivity.getVertexBufferObjectManager()) {
		
		int row = SlotMatrix.getRespawnPlaceRow();
		int column = SlotMatrix.getRespawnPlaceColumn();
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent
			    		     , float pTouchAreaLocalX
			    		     , float pTouchAreaLocalY) {

		    if (pSceneTouchEvent.isActionDown()) {
			
			gameSlotIsActionDown(row, column);
			    
		    } else if (pSceneTouchEvent.isActionUp()) {
			
			gameSlotIsActionUp(row, column);
			
		    } else if (pSceneTouchEvent.isActionMove()) {
			    
			gameSlotIsActionMove(row, column, slotSprite, pSceneTouchEvent);
			
		    }
		    return true;
		}
	    };
	    
	    gameSlotAttach(RESPAWN_Z_INDEX);
	    
	} else {
	    
	    gameSlotDetach();
	}
    }
    
    protected void gameSlotIsActionDown(int row, int column) {
	
	row = SlotMatrix.getRespawnPlaceRow();
	column = SlotMatrix.getRespawnPlaceColumn();
	Log.d("resp", "touch");
	Log.d("resp", Integer.toString(row));
	Log.d("resp", Integer.toString(column));
    }
    
    protected void gameSlotIsActionUp(int row, int column) {
	
	Log.d("resp", "no touch");
	Log.d("resp", Integer.toString(row));
	Log.d("resp", Integer.toString(column));
	
	if (gameScene.isBacklightOn()) {
	    
	    gameScene.detachChild(gameScene.getBacklight());
	    gameScene.setBacklightOn(false);
	}
	
	if (column == SlotMatrix.getPrisonPlaceColumn() && row  == SlotMatrix.getPrisonPlaceRow() 
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
	    backToGameSlot(element);
	}
    }
    
    protected void gameSlotIsActionMove(int row, int column, Sprite slotSprite, TouchEvent pSceneTouchEvent) {
	
	Log.d("resp", "move");
	
	float spriteLeftBorder = pSceneTouchEvent.getX() - slotSprite.getWidth() / 2;
	float verticalOffset = (float)(slotSprite.getHeight() * gameScene.getOffsetCoef());
	float spriteUpBorder = pSceneTouchEvent.getY() - slotSprite.getHeight() / 2 - verticalOffset;
	
	slotSprite.setPosition(spriteLeftBorder, spriteUpBorder);
	      
	gameScene.moveElement(pSceneTouchEvent.getX(), pSceneTouchEvent.getY() - verticalOffset);
	column = gameScene.getPutInColum();
	row = gameScene.getPutInRow(); 
	
	Log.d("resp", Integer.toString(row));
	Log.d("resp", Integer.toString(column));
    }
}
