package com.example.forestgame;

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
    public Prison prison;
    public Respawn respawn;
    
    public Element movingElement;
    
    private int putInRow;
    private int putInColum;
    
    private SlotMatrix slotMatrix;
    
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
    public GameScene() {
	setBackgroundEnabled(true);
	setBackground(new Background(Color.BLUE));
	sprite.registerEntityModifier(new AlphaModifier(0.55f, 0.5f, 0.8f));
	slots.registerEntityModifier(new AlphaModifier(0.4f, 0.5f, 1.0f));
	attachChild(sprite);
	attachChild(slots);
	
	prison = new Prison(this);
	respawn = new Respawn(this);
	slotMatrix = new SlotMatrix(this);
	
	// XXX: slotMatrix.putToSlot(TableOfElements.getRandomElement(), 1, 1);
	// XXX: slotMatrix.putToSlot(TableOfElements.getRandomElement(), 2, 2);
	// XXX: slotMatrix.putToSlot(TableOfElements.getRandomElement(), 3, 3);
	// XXX: slotMatrix.putToSlot(TableOfElements.getRandomElement(), 3, 4);
	// XXX: slotMatrix.putToSlot(TableOfElements.getRandomElement(), 3, 5);
	
	attachChild(pauseScene);
	pauseScene.hide();
    }
    
    public void show() {
	setVisible(true);
	setIgnoreUpdate(false);
    }
    
    public void hide() {
   	setVisible(false);
   	setIgnoreUpdate(true);
   	sprite.registerEntityModifier(new AlphaModifier(0.55f, 0.5f, 0.8f));
   	slots.registerEntityModifier(new AlphaModifier(0.4f, 0.5f, 1.0f));
    }
    
    public SlotMatrix getSlotMatrix()
    {
	return slotMatrix;
    }
    
    public void moveElement(float touchX, float touchY)
    {
	
	 for (int i = 0; i < 6; i++){
		for (int j = 0; j < 6; j++){
		    float slotX1 = 96 + (int) (i * (MainActivity.TEXTURE_WIDTH/8 + 24)) - MainActivity.TEXTURE_WIDTH/8;
		    float slotY1 = 218 + (int) (j * (MainActivity.TEXTURE_HEIGHT/13 + 26)) - MainActivity.TEXTURE_HEIGHT/13;
		    float slotX2 = 96 + (int) (i * (MainActivity.TEXTURE_WIDTH/8 + 24));
		    float slotY2 = 218 + (int) (j * (MainActivity.TEXTURE_HEIGHT/13 + 26));
		    float prisonX1 = MainActivity.TEXTURE_WIDTH * 70 / 625;
		    float prisonY1 = MainActivity.TEXTURE_HEIGHT * 1250 / 2000;
		    float prisonX2 = MainActivity.TEXTURE_WIDTH * 70 / 625 +  MainActivity.TEXTURE_WIDTH * 61 / 250;
		    float prisonY2 = MainActivity.TEXTURE_HEIGHT * 1250 / 2000 +  MainActivity.TEXTURE_HEIGHT * 303 / 2000;
		    if (slotX1 < touchX && touchX < slotX2 && slotY1 < touchY && touchY < slotY2){
			Log.d("slot x ",Integer.toString(j));
			Log.d("slot y ",Integer.toString(i));
			putInRow = i;
			putInColum = j;
		    } else if (prisonX1 < touchX && touchX < prisonX2 && prisonY1 < touchY && touchY < prisonY2) {
			Log.d("slotPrison x ",Integer.toString(7));
			Log.d("slotPrison y ",Integer.toString(7));
			putInRow = 7;
			putInColum = 7;
		    } else if (putInRow == 0 && putInColum == 0 ){
			putInRow = 8;
			putInColum = 8;
		    }
		}
	    }
    }
    
    public int getPutInRow()
    {
	return putInRow;
    }  
    
    public int getPutInColum()
    {
	return putInColum;
    } 
   
}
