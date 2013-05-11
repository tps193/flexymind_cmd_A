package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import android.util.Log;

import com.example.forestgame.element.Element;
import com.example.forestgame.gameinterface.Prison;
import com.example.forestgame.gameinterface.Respawn;

public class GameScene extends Scene {
    
    public PauseScene pauseScene = new PauseScene();
    public GameOverScene gameOverScene = new GameOverScene();
    
    public SlotMatrix slotMatrix;
    public Prison prison;
    public Respawn respawn;
    
    public Element movingElement;
    
    private int putInRow;
    private int putInColum;
    
    private Sprite sprite = new Sprite( 0
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
    
    private Sprite cage = new Sprite( MainActivity.TEXTURE_WIDTH * 136 / 625
	        		    , MainActivity.TEXTURE_HEIGHT * 1381 / 2000
	        		    , MainActivity.TEXTURE_WIDTH * 63 / 250
	        		    , MainActivity.TEXTURE_HEIGHT * 313 / 2000
	        		    , MainActivity.mainActivity.textureCage
	        		    , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    public GameScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(new Color(0.1f, 0.1f, 0.0f)));
	sprite.registerEntityModifier(new AlphaModifier(0.55f, 1.0f, 0.8f));
	sprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	slots.registerEntityModifier(new AlphaModifier(0.4f, 0.5f, 1.0f));
	attachChild(sprite);
	attachChild(slots);
	attachChild(cage);
	
	prison = new Prison(this);
	respawn = new Respawn(this);
	slotMatrix = new SlotMatrix(this);

	attachChild(pauseScene);
	attachChild(gameOverScene);
	pauseScene.hide();
	gameOverScene.hide();
	
	cage.setZIndex(9999);
	pauseScene.setZIndex(10000);
	gameOverScene.setZIndex(10000);
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
	MainActivity.mainActivity.mMusic.pause();
   	sprite.registerEntityModifier(new AlphaModifier(0.55f, 1.0f, 0.8f));
   	slots.registerEntityModifier(new AlphaModifier(0.4f, 0.5f, 1.0f));
    }
    
    public void hide() {
	
   	setVisible(false);
   	setIgnoreUpdate(true);
   	sprite.setAlpha(1.0f);
   	slots.setAlpha(0.5f);
    }
    
    public SlotMatrix getSlotMatrix() {
	
	return slotMatrix;
    }
    
    public void moveElement(float touchX, float touchY) {
	
	 for (int i = 0; i < SlotMatrix.getROWS(); i++) {
	     
	     boolean flg=false;
	     for (int j = 0; j < SlotMatrix.getCOLUMNS(); j++) {
		   
		 flg=true;
		 float slotX1 = 96 + (int) (i * (MainActivity.TEXTURE_WIDTH/8 + 24))
			   	- MainActivity.TEXTURE_WIDTH/8;
		    
		 float slotY1 = 218 + (int) (j * (MainActivity.TEXTURE_HEIGHT/13 + 26)) 
			    	- MainActivity.TEXTURE_HEIGHT/13;
		    
		 float slotX2 = 96 + (int) (i * (MainActivity.TEXTURE_WIDTH/8 + 24));
		 float slotY2 = 218 + (int) (j * (MainActivity.TEXTURE_HEIGHT/13 + 26));
		 float prisonX1 = MainActivity.TEXTURE_WIDTH * 70 / 625;
		 float prisonY1 = MainActivity.TEXTURE_HEIGHT * 1250 / 2000;
		 float prisonX2 = MainActivity.TEXTURE_WIDTH * 70 / 625 +  MainActivity.TEXTURE_WIDTH * 61 / 250;
		 float prisonY2 = MainActivity.TEXTURE_HEIGHT * 1250 / 2000 +  MainActivity.TEXTURE_HEIGHT * 303 / 2000;
		    
		 if (slotX1 < touchX && touchX < slotX2 && slotY1 < touchY && touchY < slotY2) {
			
		     Log.d("slot x ",Integer.toString(j));
		     Log.d("slot y ",Integer.toString(i));
		     putInRow = i;
		     putInColum = j;
		     break;
		 } else if (prisonX1 < touchX && touchX < prisonX2 && prisonY1 < touchY && touchY < prisonY2) {
			
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
