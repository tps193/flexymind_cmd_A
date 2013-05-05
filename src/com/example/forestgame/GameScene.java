package com.example.forestgame;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import com.example.forestgame.element.TableOfElements;

public class GameScene extends Scene {
    
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
	slotMatrix = new SlotMatrix(this);
	
	//Test Slots
	slotMatrix.putToSlot(TableOfElements.getRandomElement(), 1, 1);
	slotMatrix.putToSlot(TableOfElements.getRandomElement(), 2, 2);
	slotMatrix.putToSlot(TableOfElements.getRandomElement(), 3, 3);
	slotMatrix.putToSlot(TableOfElements.getRandomElement(), 3, 4);
	slotMatrix.putToSlot(TableOfElements.getRandomElement(), 3, 5);
	
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
}
