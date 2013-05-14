package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

import com.example.forestgame.element.Element;
import com.example.forestgame.gameinterface.Prison;
import com.example.forestgame.gameinterface.Respawn;

public class GameScene extends Scene {
    
    private PauseScene pauseScene = new PauseScene();
    private GameOverScene gameOverScene = new GameOverScene();
    
    private SlotMatrix slotMatrix;
    private Prison prison;
    private Respawn respawn;
    
    private Element movingElement;
    
    private int putInRow;
    private int putInColum;
    
    private static final float CAGE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 136 / 625;
    private static final float CAGE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1381 / 2000;
    private static final float CAGE_WIDTH = MainActivity.TEXTURE_WIDTH * 63 / 250;
    private static final float CAGE_HEIGHT = MainActivity.TEXTURE_HEIGHT * 313 / 2000;
    
    private static final int CAGE_Z_INDEX = 9999;
    private static final int PAUSE_SCENE_Z_INDEX = 10000;
    private static final int GAME_OVER_SCENE_Z_INDEX = 10000;
    
    private static final AlphaModifier BACKGROUND_ALPHA_MODIFIER = new AlphaModifier(0.55f, 1.0f, 0.8f);
    private static final AlphaModifier SLOTS_ALPHA_MODIFIER = new AlphaModifier(0.4f, 0.5f, 1.0f);
    
    
    private static final float PRISON_POSITION_LEFT = CAGE_POSITION_LEFT;
    private static final float PRISON_POSITION_UP = CAGE_POSITION_UP;
    private static final float PRISON_POSITION_RIGHT = PRISON_POSITION_LEFT + CAGE_WIDTH;
    private static final float PRISON_POSITION_BOTTOM = PRISON_POSITION_UP + CAGE_HEIGHT;
    private static double OFFSET_ON_MOVING;
    
    private Sprite background = new Sprite( 0
	                              , 0
	                              , MainActivity.TEXTURE_WIDTH
	                              , MainActivity.TEXTURE_HEIGHT
	                              , MainActivity.mainActivity.textureBackground
	                              , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Sprite slots = new Sprite( 0
	    			     , 0
	    			     , MainActivity.TEXTURE_WIDTH
	    			     , MainActivity.TEXTURE_HEIGHT
	    			     , MainActivity.mainActivity.textureSlots
	    			     , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Sprite cage = new Sprite( CAGE_POSITION_LEFT
	        		    , CAGE_POSITION_UP
	        		    , CAGE_WIDTH
	        		    , CAGE_HEIGHT
	        		    , MainActivity.mainActivity.textureCage
	        		    , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    public GameScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	background.registerEntityModifier(BACKGROUND_ALPHA_MODIFIER);
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	slots.registerEntityModifier(SLOTS_ALPHA_MODIFIER);
	attachChild(background);
	attachChild(slots);
	attachChild(cage);
	
	prison = new Prison(this);
	respawn = new Respawn(this);
	slotMatrix = new SlotMatrix(this);

	attachChild(pauseScene);
	attachChild(gameOverScene);
	pauseScene.hide();
	gameOverScene.hide();
	
	cage.setZIndex(CAGE_Z_INDEX);
	pauseScene.setZIndex(PAUSE_SCENE_Z_INDEX);
	gameOverScene.setZIndex(GAME_OVER_SCENE_Z_INDEX);
	
	//here the test for tablet/phone is needed
	if (MainActivity.mainActivity.hasLargeScreen()) {
	    OFFSET_ON_MOVING = 0.2;
	} else {
	    OFFSET_ON_MOVING = 0.7;
	}
    }
    
    public double getOffsetCoef() {
	return OFFSET_ON_MOVING;
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
	MainActivity.mainActivity.mMusic.pause();
   	background.registerEntityModifier(BACKGROUND_ALPHA_MODIFIER);
   	slots.registerEntityModifier(SLOTS_ALPHA_MODIFIER);
    }
    
    public void hide() {
	
   	setVisible(false);
   	setIgnoreUpdate(true);
   	background.setAlpha(1.0f);
   	slots.setAlpha(0.5f);
    }
    
    public SlotMatrix getSlotMatrix() {
	
	return slotMatrix;
    }
    
    public Prison getPrison() {
	return prison;
    }
    
    public Respawn getRespawn() {
	return respawn;
    }
    
    public PauseScene getPauseScene() {
	return pauseScene;
    }
    
    public GameOverScene getGameOverScene() {
	return gameOverScene;
    }
    
    public Element getMovingElement() {
	return movingElement;
    }
    
    public void moveElement(float touchPointX, float touchPointY) {
	
	 for (int i = 0; i < SlotMatrix.getROWS(); i++) {
	     
	     boolean flg=false;
	     for (int j = 0; j < SlotMatrix.getCOLUMNS(); j++) {
		   
		 flg=true;
		 float slotLeftBorder = SlotMatrix.getSlotPositionLeft(j) - SlotMatrix.getSlotWidth();
		 float slotUpperBorder = SlotMatrix.getSlotPositionUp(i) - SlotMatrix.getSlotHeight();
		 float slotRightBorder = slotLeftBorder + SlotMatrix.getSlotWidth();
		 float slotBottomBorder = slotUpperBorder + SlotMatrix.getSlotHeight();
		 
		 if (slotLeftBorder <= touchPointX && touchPointX <= slotRightBorder && 
		     slotUpperBorder <= touchPointY && touchPointY <= slotBottomBorder) {
			
		     Log.d("slot x ",Integer.toString(j));
		     Log.d("slot y ",Integer.toString(i));
		     putInRow = i;
		     putInColum = j;
		     break;
		 } else if (PRISON_POSITION_LEFT <= touchPointX && touchPointX <= PRISON_POSITION_RIGHT && 
			    PRISON_POSITION_UP <= touchPointY && touchPointY <= PRISON_POSITION_BOTTOM) {
			
		     Log.d("slotPrison x ",Integer.toString(7));
		     Log.d("slotPrison y ",Integer.toString(7));
		     putInRow = SlotMatrix.getROWS()+1;
		     putInColum = SlotMatrix.getCOLUMNS()+1;
		     break;
		 } else {
		     putInRow = SlotMatrix.getROWS()+20;
		     putInColum = SlotMatrix.getCOLUMNS()+20;
		     flg=false;
		 }
		   
	     }
	     if (flg) {
		 
		break;
	     }
	 }
    }
    
    public int getPutInRow() {
	
	return putInRow;
    }  
    
    public int getPutInColum() {
	
	return putInColum;
    } 
   
}
