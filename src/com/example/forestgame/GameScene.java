package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

public class GameScene extends Scene {
    
    public PauseScene pauseScene = new PauseScene();
    
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
	setBackground(new Background(new Color(0.1f, 0.1f, 0.0f)));
	sprite.registerEntityModifier(new AlphaModifier(0.55f, 1.0f, 0.8f));
	sprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	slots.registerEntityModifier(new AlphaModifier(0.4f, 0.5f, 1.0f));
	attachChild(sprite);
	attachChild(slots);
	
	slotMatrix = new SlotMatrix(this);
	
	// XXX: Test Slots
	// XXX: slotMatrix.putToSlot(TableOfElements.getRandomElement(), 1, 1);
	
	attachChild(pauseScene);
	pauseScene.hide();
    }
    
    public void show() {
	setVisible(true);
	setIgnoreUpdate(false);
   	sprite.registerEntityModifier(new AlphaModifier(0.55f, 1.0f, 0.8f));
   	slots.registerEntityModifier(new AlphaModifier(0.4f, 0.5f, 1.0f));
    }
    
    public void hide() {
   	setVisible(false);
   	setIgnoreUpdate(true);
   	sprite.setAlpha(1.0f);
   	slots.setAlpha(0.5f);
    }
}
