package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class PauseScene extends Scene {
    
    private static final float QUESTION_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 18 / 120;
    private static final float QUESTION_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 5 / 14;
    private static final float CHOISE_YES_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 148 / 1024;
    private static final float CHOISE_YES_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 30 / 64;
    private static final float CHOISE_NO_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 685 / 1024;
    private static final float CHOISE_NO_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 30 / 64;
    

    private Sprite background = new Sprite( 0
            				   , 0
            				   , MainActivity.TEXTURE_WIDTH
            				   , MainActivity.TEXTURE_HEIGHT
            				   , MainActivity.mainActivity.textureBackground
            				   , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Text question = new Text( QUESTION_POSITION_LEFT
				     , QUESTION_POSITION_UP
				     , MainActivity.mainActivity.tQuestion
				     , "Really want to exit?"
				     , MainActivity.mainActivity.getVertexBufferObjectManager());

    private Text choiseYes = new Text( CHOISE_YES_POSITION_LEFT
				      , CHOISE_YES_POSITION_UP
				      , MainActivity.mainActivity.tChoiseYES
				      , "YES"
				      , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent
	    			     , float pTouchAreaLocalX
	    			     , float pTouchAreaLocalY) {
	    
	    if (pSceneTouchEvent.isActionDown()) {
	    
		this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER);
		
	    } else if (pSceneTouchEvent.isActionUp()) {
		
		this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER);
		MainScene.showMainMenuScene();	    
	    }
	return true;
	}
    };

    private Text choiseNo = new Text(CHOISE_NO_POSITION_LEFT
	    			    , CHOISE_NO_POSITION_UP
	    			    , MainActivity.mainActivity.tChoiseNO
	    			    , "NO"
	    			    , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent
    				     , float pTouchAreaLocalX
    				     , float pTouchAreaLocalY) {
	    
	    if (pSceneTouchEvent.isActionDown()) {
		
		this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER);
		
	    } else if (pSceneTouchEvent.isActionUp()) {
		
		this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER);
		MainScene.showGameScene();
	    }
	    return true;
	}
    };
   
    public PauseScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	attachChild(background);
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	background.registerEntityModifier(MainActivity.PAUSE_ALPHA_MODIFIER);
	attachChild(question);
	attachChild(choiseNo);
	attachChild(choiseYes);
	registerTouchArea(question);
	registerTouchArea(choiseNo);
	registerTouchArea(choiseYes);
	//setTouchAreaBindingOnActionDownEnabled(true);
	//setTouchAreaBindingOnActionMoveEnabled(true);
	
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
   	background.registerEntityModifier(MainActivity.PAUSE_ALPHA_MODIFIER);
    }
    
    public void hide() {
	
   	setVisible(false);
   	setIgnoreUpdate(true);
   	background.setAlpha(0.8f);
    }
}