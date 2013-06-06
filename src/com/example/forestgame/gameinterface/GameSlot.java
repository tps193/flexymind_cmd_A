package com.example.forestgame.gameinterface;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationByModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import com.example.forestgame.MainActivity;
import com.example.forestgame.SlotMatrix;
import com.example.forestgame.GameScene;
import com.example.forestgame.element.Element;

public abstract class GameSlot {

    protected boolean isEmpty;
    protected Element element;
    protected GameScene gameScene;
    
    protected TextureRegion slotTexture;
    protected Sprite slotSprite;
    
    protected int row;
    protected int column;
    protected float animationDuration = 0.3f;
    
    protected LoopEntityModifier rotationModifier = new LoopEntityModifier(
	    						new SequenceEntityModifier(
	    							new RotationByModifier(animationDuration/4, 5)
	    							, new RotationByModifier(animationDuration/2, -10)
	    							, new RotationByModifier(animationDuration/4, 5)));
    
    
    protected static float VERTICAL_OFFSET;
    
    public GameSlot(GameScene scene) {
	
	gameScene = scene;
	element = null;
	isEmpty = true;
	if (MainActivity.mainActivity.hasLargeScreen()) {
	    VERTICAL_OFFSET = (float)(SlotMatrix.getSlotHeight() * 0.4);
	} else {
	    VERTICAL_OFFSET = (float)(SlotMatrix.getSlotHeight() * 0.8);
	}
    }
    
    public void addElement(Element e) {
	
	element = e;
	isEmpty = false;
	show();
	
    }
    
    public Element getElement() {
	
	return element;
    }
    
    public boolean isEmpty() {
	
	return isEmpty;
    }
    
    public void clear() {
	
	element = null;
	isEmpty = true;
	show();
    }
    
    protected void gameSlotAttach(int Z_INDEX) {
	
	gameScene.attachChild(slotSprite);
	gameScene.registerTouchArea(slotSprite);
	gameScene.setTouchAreaBindingOnActionDownEnabled(true);
	gameScene.setTouchAreaBindingOnActionMoveEnabled(true);
	    
	slotSprite.setZIndex(Z_INDEX);
	slotSprite.getParent().sortChildren();
    }
    
    protected void gameSlotDetach() {
	
	gameScene.detachChild(slotSprite);
	gameScene.unregisterTouchArea(slotSprite);
	slotSprite = null;
    }
    
    protected abstract void backToGameSlot(Element e);
    
    protected abstract void show();
    
    protected abstract void gameSlotIsActionDown();
    
    protected abstract void gameSlotIsActionUp();
    
    protected abstract void gameSlotIsActionMove(TouchEvent pSceneTouchEvent);
}
