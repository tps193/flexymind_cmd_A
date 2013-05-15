package com.example.forestgame.gameinterface;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.example.forestgame.GameScene;
import com.example.forestgame.MainActivity;
import com.example.forestgame.SlotMatrix;
import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;

public class Prison extends GameSlot {
    
    private final static float PRISON_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 136 / 625;
    private final static float PRISON_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1381 / 2000;
    private final static float PRISON_WIDTH = MainActivity.TEXTURE_WIDTH * 61 / 250;
    private final static float PRISON_HEIGHT = MainActivity.TEXTURE_HEIGHT * 303 / 2000;
    private static final int PRISON_Z_INDEX = 401;
    
    public Prison(GameScene scene) {
	
	super(scene);
    }
    
    public void backToGameSlot(Element element) {
	
	slotSprite.setPosition( PRISON_POSITION_LEFT
    		                , PRISON_POSITION_UP);
	
    }
    
    public void show() {
	
	if(!isEmpty) {
	    
	    slotTexture = MainActivity.mainActivity.storage.getTexture(TableOfElements.getTextureName(element));
	    slotSprite = new Sprite ( PRISON_POSITION_LEFT
		    		      , PRISON_POSITION_UP
		    		      , PRISON_WIDTH
		    		      , PRISON_HEIGHT
		    		      , slotTexture
		    		      , MainActivity.mainActivity.getVertexBufferObjectManager()) {
		
		int row = SlotMatrix.getROWS()+1;
		int column = SlotMatrix.getCOLUMNS()+1;
		@Override
		public boolean onAreaTouched( TouchEvent pSceneTouchEvent
			    			, float pTouchAreaLocalX
			    			, float pTouchAreaLocalY) {
		    if (pSceneTouchEvent.isActionDown()) {
			
			Log.d("prison", "touch");
			row = SlotMatrix.getROWS()+1;
			column = SlotMatrix.getCOLUMNS()+1;
			
		    } else if (pSceneTouchEvent.isActionUp()) {
			    
			Log.d("prison", "no touch");
			    
			if (row < SlotMatrix.getROWS() && column <SlotMatrix.getCOLUMNS() 
				&& gameScene.getSlotMatrix().isSlotEmpty(row, column)) {
				
			    Log.d("prison", "new");
			    gameScene.getSlotMatrix().putToSlot(element, row, column);
			    clear();
			    
			} else {
				
			    Log.d("prison","nowhere");
			    backToGameSlot(element);
			}
			    
		    } else if (pSceneTouchEvent.isActionMove()) {
			    
			Log.d("prison", "move");
			    
			float spriteLeftBorder = pSceneTouchEvent.getX() - this.getWidth() / 2;
			float verticalOffset = (float)(this.getHeight() * gameScene.getOffsetCoef());
			float spriteUpBorder = pSceneTouchEvent.getY() - this.getHeight() / 2 - verticalOffset;
			
			this.setPosition(spriteLeftBorder, spriteUpBorder);
			      
			gameScene.moveElement(pSceneTouchEvent.getX(), pSceneTouchEvent.getY() - verticalOffset);
			column = gameScene.getPutInColum();
			row = gameScene.getPutInRow(); 
			
			Log.d("prison", Integer.toString(row));
			Log.d("prison", Integer.toString(column)); 
			    
		    }
		    return true;
		}
	    };
	    
	    gameScene.attachChild(slotSprite);
	    gameScene.registerTouchArea(slotSprite);
	    gameScene.setTouchAreaBindingOnActionDownEnabled(true);
	    gameScene.setTouchAreaBindingOnActionMoveEnabled(true);
	    
	    slotSprite.setZIndex(PRISON_Z_INDEX);
	    slotSprite.getParent().sortChildren();
	    
	} else {
	    
	    gameScene.detachChild(slotSprite);
	    gameScene.unregisterTouchArea(slotSprite);
	    slotSprite = null;
	}
    }
}
