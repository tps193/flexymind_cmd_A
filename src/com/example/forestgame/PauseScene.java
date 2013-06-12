package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

public class PauseScene extends Scene {
    
    private static final float PAUSE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 31 / 120;
    private static final float PAUSE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 13 / 64;
    private static final float PAUSE_WIDTH = MainActivity.TEXTURE_WIDTH * 190 / 400;
    private static final float PAUSE_HEIGHT = MainActivity.TEXTURE_HEIGHT * 11 / 128;
    private static final float RESUME_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 319 / 1024;
    private static final float RESUME_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 24 / 64;
    private static final float RESUME_WIDTH = MainActivity.TEXTURE_WIDTH * 150 / 400;;
    private static final float RESUME_HEIGHT = MainActivity.TEXTURE_HEIGHT * 9 / 128;
    private static final float NEWGAME_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 227 / 1024;
    private static final float NEWGAME_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 31 / 64;
    private static final float NEWGAME_WIDTH = MainActivity.TEXTURE_WIDTH * 225 / 400;
    private static final float NEWGAME_HEIGHT = MainActivity.TEXTURE_HEIGHT * 9 / 128;
    private static final float MAINMENU_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 222 / 1024;
    private static final float MAINMENU_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 38 / 64; 
    private static final float MAINMENU_WIDTH = MainActivity.TEXTURE_WIDTH * 225 / 400;
    private static final float MAINMENU_HEIGHT = MainActivity.TEXTURE_HEIGHT * 9 / 128;

    private Sprite background = new Sprite( 0
            				   , 0
            				   , MainActivity.TEXTURE_WIDTH
            				   , MainActivity.TEXTURE_HEIGHT
            				   , MainActivity.mainActivity.textureBackground
            				   , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Sprite pause = new Sprite( PAUSE_POSITION_LEFT
				     , PAUSE_POSITION_UP
				     , PAUSE_WIDTH
				     , PAUSE_HEIGHT
				     , MainActivity.mainActivity.texturePauseL
				     , MainActivity.mainActivity.getVertexBufferObjectManager());

    private Sprite resume = new Sprite( RESUME_POSITION_LEFT
				      , RESUME_POSITION_UP
				      , RESUME_WIDTH
				      , RESUME_HEIGHT
				      , MainActivity.mainActivity.textureResume
				      , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent
	    			     , float pTouchAreaLocalX
	    			     , float pTouchAreaLocalY) {
	    
	    if (pSceneTouchEvent.isActionDown()) {
	    
		applyTouchEffects(resume);
		
	    } else if (pSceneTouchEvent.isActionUp()) {
		
		if (    (pTouchAreaLocalX > 0) 
	                 && (pTouchAreaLocalX < RESUME_WIDTH)
	                 && (pTouchAreaLocalY > 0)
	                 && (pTouchAreaLocalY < RESUME_HEIGHT)) {

		    applyUntouchEffects(resume);
		MainActivity.mainActivity.mClick.play();
		MainActivity.mainActivity.saveProgress();
		MainScene.showGameScene();
		
		} else {
			applyUntouchEffects(resume);
		    }
		    
		} else if (pSceneTouchEvent.isActionMove()) {
		    if (    !((pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < RESUME_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < RESUME_HEIGHT))) {
			applyUntouchEffects(resume);
		    } else {
			applyTouchEffects(resume);
		    }
		    
		}
	return true;
	}
    };

    private Sprite newgame = new Sprite(NEWGAME_POSITION_LEFT
	    			    , NEWGAME_POSITION_UP
	    			    , NEWGAME_WIDTH
	    			    , NEWGAME_HEIGHT
	    			    , MainActivity.mainActivity.textureNewGame
	    			    , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent
    				     , float pTouchAreaLocalX
    				     , float pTouchAreaLocalY) {
	    
	    if (pSceneTouchEvent.isActionDown()) {
		
		applyTouchEffects(newgame);
		
	    } else if (pSceneTouchEvent.isActionUp()) {
		
		if (    (pTouchAreaLocalX > 0) 
	                 && (pTouchAreaLocalX < NEWGAME_WIDTH)
	                 && (pTouchAreaLocalY > 0)
	                 && (pTouchAreaLocalY < NEWGAME_HEIGHT)) {
		
		    applyUntouchEffects(newgame);
		if (MainScene.getGameScene().getSlotMatrix().getScore() != 0)
		MainScene.getGameScene().getScoresTable().saveResult(MainScene.getGameScene().getSlotMatrix().getScore());
		MainActivity.mainActivity.mClick.play();
		MainScene.getGameScene().getSlotMatrix().reInit();
		MainScene.getGameScene().getPrison().clear();
		MainScene.getGameScene().getRespawn().clear();
		MainScene.getGameScene().getRespawn().generateElement();
		MainActivity.mainActivity.mClick.play();
		MainActivity.mainActivity.progressNotSaved();
		MainScene.showGameScene();
		
		} else {
			applyUntouchEffects(newgame);
		    }
		    
		} else if (pSceneTouchEvent.isActionMove()) {
		    if (    !((pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < NEWGAME_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < NEWGAME_HEIGHT))) {
			applyUntouchEffects(newgame);
		    } else {
			applyTouchEffects(newgame);
		    }
	    }
	    return true;
	}
    };
    
    private Sprite mainMenu = new Sprite(	MAINMENU_POSITION_LEFT
	    			      , MAINMENU_POSITION_UP
	    			      , MAINMENU_WIDTH
	    			      , MAINMENU_HEIGHT
	    			      , MainActivity.mainActivity.textureMainMenu
	    			      , MainActivity.mainActivity.getVertexBufferObjectManager()) {

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent
				   , float pTouchAreaLocalX
				   , float pTouchAreaLocalY) {

	    if (pSceneTouchEvent.isActionDown()) {

		applyTouchEffects(mainMenu);

	    } else if (pSceneTouchEvent.isActionUp()) {
		
		if (    (pTouchAreaLocalX > 0) 
	                 && (pTouchAreaLocalX < MAINMENU_WIDTH)
	                 && (pTouchAreaLocalY > 0)
	                 && (pTouchAreaLocalY < MAINMENU_HEIGHT)) {

		applyUntouchEffects(mainMenu);
		MainActivity.mainActivity.mClick.play();
		MainActivity.mainActivity.saveProgress();
		MainScene.showMainMenuScene();
		} else {
			applyUntouchEffects(mainMenu);
		    }
		    
		} else if (pSceneTouchEvent.isActionMove()) {
		    if (    !((pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < MAINMENU_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < MAINMENU_HEIGHT))) {
			applyUntouchEffects(mainMenu);
		    } else {
			applyTouchEffects(mainMenu);
		    }
		}
	    return true;
	}
    };
   
    
    private Sprite muteOff = new Sprite( MainMenuScene.MUTE_POSITION_LEFT
		       , MainMenuScene.MUTE_POSITION_UP
		       , MainMenuScene.MUTE_WIDTH
		       , MainMenuScene.MUTE_HEIGHT
		       , MainActivity.mainActivity.textureMuteOff
		       , MainActivity.mainActivity.getVertexBufferObjectManager()) {

	@Override
	public boolean onAreaTouched( TouchEvent pSceneTouchEvent
				, float pTouchAreaLocalX
				, float pTouchAreaLocalY) {

	    if (pSceneTouchEvent.isActionDown()) {

		Log.d("MuteOff", "touch");
		applyTouchEffects(muteOff);

	    } else if (pSceneTouchEvent.isActionUp()) {

		if (    (pTouchAreaLocalX > 0) 
			&& (pTouchAreaLocalX < MainMenuScene.MUTE_WIDTH)
			&& (pTouchAreaLocalY > 0)
			&& (pTouchAreaLocalY < MainMenuScene.MUTE_HEIGHT)) {

		    Log.d("MuteOff", "no touch");
		    applyUntouchEffects(muteOff);
		    muteIconCLick();

		} else {
		    Log.d("MuteOff", "no touch outside");
		    applyUntouchEffects(muteOff);
		}

	    } else if (pSceneTouchEvent.isActionMove()) {
		if (    !((pTouchAreaLocalX > 0) 
			&& (pTouchAreaLocalX < MainMenuScene.MUTE_WIDTH)
			&& (pTouchAreaLocalY > 0)
			&& (pTouchAreaLocalY < MainMenuScene.MUTE_HEIGHT))) {
		    applyUntouchEffects(muteOff);
		} else {
		    applyTouchEffects(muteOff);
		}
	    }
	    return true;
	}
    };


    private Sprite muteOn = new Sprite( MainMenuScene.MUTEON_POSITION_LEFT
	    		       	      , MainMenuScene.MUTEON_POSITION_UP
	    		       	      , MainMenuScene.MUTEON_WIDTH
	    		       	      , MainMenuScene.MUTEON_HEIGHT
	    		       	      , MainActivity.mainActivity.textureMuteOn
	    		       	      , MainActivity.mainActivity.getVertexBufferObjectManager());

    private void muteIconCLick() {	    
	if (!MainActivity.isMute) {
	    MainActivity.mainActivity.muteSounds();
	    muteOn.setVisible(false);
	} else {
	    MainActivity.mainActivity.unmuteSounds();
	    muteOn.setVisible(true);
	}
    }
    
    
    public PauseScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	attachChild(background);
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	background.registerEntityModifier(MainActivity.PAUSE_ALPHA_MODIFIER.deepCopy());
	attachChild(pause);
	attachChild(newgame);
	attachChild(resume);
	attachChild(mainMenu);
	attachChild(muteOff);
	attachChild(muteOn);
	muteOn.setVisible(true);
	registerTouchArea(pause);
	registerTouchArea(newgame);
	registerTouchArea(resume);
	registerTouchArea(mainMenu);
	registerTouchArea(muteOff);
	setTouchAreaBindingOnActionDownEnabled(true);
	setTouchAreaBindingOnActionMoveEnabled(true);
	
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
	if (!MainActivity.isMute) {
	    muteOn.setVisible(true);
	} else {
	    muteOn.setVisible(false);
	}
   	background.registerEntityModifier(MainActivity.PAUSE_ALPHA_MODIFIER.deepCopy());
    }
    
    public void hide() {
	
   	setVisible(false);
   	setIgnoreUpdate(true);
   	background.setAlpha(0.8f);
    }
    
    private void applyTouchEffects(Sprite button) {
	button.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
	button.registerEntityModifier(MainActivity.TOUCH_ALPHA_MODIFIER.deepCopy());
    }
    
    private void applyUntouchEffects(Sprite button) {
	button.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
	button.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());
    }
}