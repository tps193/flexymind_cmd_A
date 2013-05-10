package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

public class PauseScene extends Scene {
    
    private Sprite sprite = new Sprite( 0
            				, 0
            				, MainActivity.TEXTURE_WIDTH
            				, MainActivity.TEXTURE_HEIGHT
            				, MainActivity.mainActivity.textureBackground
            				, MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Text question = new Text(MainActivity.TEXTURE_WIDTH * 18 / 120
				     , MainActivity.TEXTURE_HEIGHT * 5 / 14
				     , MainActivity.mainActivity.tQuestion
				     , "Really want to exit?"
				     , MainActivity.mainActivity.getVertexBufferObjectManager());

    private Text choiseY = new Text(MainActivity.TEXTURE_WIDTH * 148 / 1024
				    , MainActivity.TEXTURE_HEIGHT * 30 / 64
				    , MainActivity.mainActivity.tChoiseYES
				    , "YES"
				    , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent
	    			     , float pTouchAreaLocalX
	    			     , float pTouchAreaLocalY) {
	    
	    if (pSceneTouchEvent.isActionDown()) {
	    
		this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
		
	    } else if (pSceneTouchEvent.isActionUp()) {
		
		this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		MainScene.showMainMenuScene();	    
	    }
	return true;
	}
    };

    private Text choiseN = new Text(MainActivity.TEXTURE_WIDTH * 685 / 1024
	    			    , MainActivity.TEXTURE_HEIGHT * 30 / 64
	    			    , MainActivity.mainActivity.tChoiseNO
	    			    , "NO"
	    			    , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent
    				     , float pTouchAreaLocalX
    				     , float pTouchAreaLocalY) {
	    
	    if (pSceneTouchEvent.isActionDown()) {
		
		this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
		
	    } else if (pSceneTouchEvent.isActionUp()) {
		
		this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		MainScene.showGameScene();
	    }
	    return true;
	}
    };
   
    public PauseScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(new Color(0.1f, 0.1f, 0.0f)));
	attachChild(sprite);
	sprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	sprite.registerEntityModifier(new AlphaModifier(0.55f, 0.8f, 0.5f));
	attachChild(question);
	attachChild(choiseN);
	attachChild(choiseY);
	registerTouchArea(question);
	registerTouchArea(choiseN);
	registerTouchArea(choiseY);
	//setTouchAreaBindingOnActionDownEnabled(true);
	//setTouchAreaBindingOnActionMoveEnabled(true);
	
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
   	sprite.registerEntityModifier(new AlphaModifier(0.55f, 0.8f, 0.5f));
    }
    
    public void hide() {
	
   	setVisible(false);
   	setIgnoreUpdate(true);
   	sprite.setAlpha(0.8f);
    }
}