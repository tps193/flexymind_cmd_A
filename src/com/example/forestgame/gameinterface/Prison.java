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

public class Prison {

    private boolean isEmpty;
    private Element element;
    private GameScene gameScene;
    
    private TextureRegion prisonTexture;
    private Sprite prisonSprite;
    
    private final static float PRISON_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 136 / 625;
    private final static float PRISON_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1381 / 2000;
    private final static float PRISON_WIDTH = MainActivity.TEXTURE_WIDTH * 61 / 250;
    private final static float PRISON_HEIGHT = MainActivity.TEXTURE_HEIGHT * 303 / 2000;
    private static final int PRISON_Z_INDEX = 401;
    
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
    
    private void backToPrison(Element element) {
	
	prisonSprite.setPosition( PRISON_POSITION_LEFT
    		                , PRISON_POSITION_UP);
	
    }
    
    public void show() {
	
	if(!isEmpty) {
	    
	    prisonTexture = MainActivity.mainActivity.storage.getTexture(TableOfElements.getTextureName(element));
	    prisonSprite = new Sprite ( PRISON_POSITION_LEFT
		    		      , PRISON_POSITION_UP
		    		      , PRISON_WIDTH
		    		      , PRISON_HEIGHT
		    		      , prisonTexture
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
			    backToPrison(element);
			}
			    
		    } else if (pSceneTouchEvent.isActionMove()) {
			    
			Log.d("prison", "move");
			    
			float touchPointX = pSceneTouchEvent.getX() - this.getWidth() / 2;
			float touchPointY = pSceneTouchEvent.getY() - this.getHeight() / 2 - (float)(this.getHeight() / gameScene.getOffset());
			this.setPosition(touchPointX, touchPointY);
			      
			gameScene.moveElement(touchPointX, touchPointY);
			column = gameScene.getPutInColum();
			row = gameScene.getPutInRow(); 
			    
		    }
		    return true;
		}
	    };
	    
	    gameScene.attachChild(prisonSprite);
	    gameScene.registerTouchArea(prisonSprite);
	    gameScene.setTouchAreaBindingOnActionDownEnabled(true);
	    gameScene.setTouchAreaBindingOnActionMoveEnabled(true);
	    
	    prisonSprite.setZIndex(PRISON_Z_INDEX);
	    prisonSprite.getParent().sortChildren();
	    
	} else {
	    
	    gameScene.detachChild(prisonSprite);
	    gameScene.unregisterTouchArea(prisonSprite);
	    prisonSprite = null;
	}
    }
}
