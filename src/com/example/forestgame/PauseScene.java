package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

public class PauseScene extends Scene {
    
    private static final float PAUSE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 35 / 120;
    private static final float PAUSE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 13 / 64;
    private static final float RESUME_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 319 / 1024;
    private static final float RESUME_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 24 / 64;
    private static final float NEWGAME_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 260 / 1024;
    private static final float NEWGAME_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 31 / 64;
    private static final float MAINMENU_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 235 / 1024;
    private static final float MAINMENU_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 38 / 64;
    
    private static final float MUTE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH* 520 / 625;
    private static final float MUTE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1850 / 2000;
    private static final float MUTE_WIDTH = MainActivity.TEXTURE_WIDTH * 20 / 250;
    private static final float MUTE_HEIGHT = MainActivity.TEXTURE_HEIGHT * 18 / 250;
    
    private static final float MUTEON_POSITION_LEFT = MainActivity.TEXTURE_WIDTH* 575 / 625;
    private static final float MUTEON_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1840 / 2000;
    private static final float MUTEON_WIDTH = MainActivity.TEXTURE_WIDTH * 15 / 250;
    private static final float MUTEON_HEIGHT = MainActivity.TEXTURE_HEIGHT * 160 / 2000;
    

    private Sprite background = new Sprite( 0
            				   , 0
            				   , MainActivity.TEXTURE_WIDTH
            				   , MainActivity.TEXTURE_HEIGHT
            				   , MainActivity.mainActivity.textureBackground
            				   , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Text pause = new Text( PAUSE_POSITION_LEFT
				     , PAUSE_POSITION_UP
				     , MainActivity.mainActivity.tPause
				     , "PAUSE"
				     , MainActivity.mainActivity.getVertexBufferObjectManager());

    private Text resume = new Text( RESUME_POSITION_LEFT
				      , RESUME_POSITION_UP
				      , MainActivity.mainActivity.tResume
				      , "RESUME"
				      , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent
	    			     , float pTouchAreaLocalX
	    			     , float pTouchAreaLocalY) {
	    
	    if (pSceneTouchEvent.isActionDown()) {
	    
		this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
		
	    } else if (pSceneTouchEvent.isActionUp()) {

		this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		MainActivity.mainActivity.mClick.play();	
		MainScene.showGameScene();	
		
	    }
	return true;
	}
    };

    private Text newgame = new Text(NEWGAME_POSITION_LEFT
	    			    , NEWGAME_POSITION_UP
	    			    , MainActivity.mainActivity.tNewGame
	    			    , "NEW GAME"
	    			    , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent
    				     , float pTouchAreaLocalX
    				     , float pTouchAreaLocalY) {
	    
	    if (pSceneTouchEvent.isActionDown()) {
		
		this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
		
	    } else if (pSceneTouchEvent.isActionUp()) {
		
		this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		MainActivity.mainActivity.mClick.play();
		MainScene.getGameScene().getSlotMatrix().reInit();
		MainScene.getGameScene().getPrison().clear();
		MainScene.getGameScene().getRespawn().clear();
		MainScene.getGameScene().getRespawn().generateElement();
		MainActivity.mainActivity.mClick.play();
		MainScene.showGameScene();
	    }
	    return true;
	}
    };
    
    private Text mainMenu = new Text(	MAINMENU_POSITION_LEFT
	    			      , MAINMENU_POSITION_UP
	    			      , MainActivity.mainActivity.tMainMenu
	    			      , "MAIN MENU"
	    			      , MainActivity.mainActivity.getVertexBufferObjectManager()) {

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent
				   , float pTouchAreaLocalX
				   , float pTouchAreaLocalY) {

	    if (pSceneTouchEvent.isActionDown()) {

		this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());

	    } else if (pSceneTouchEvent.isActionUp()) {

		this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		MainActivity.mainActivity.mClick.play();
		MainScene.showMainMenuScene();
	    }
	    return true;
	}
    };
   
    
    private Sprite muteOff = new Sprite( MUTE_POSITION_LEFT
	    			       , MUTE_POSITION_UP
	    			       , MUTE_WIDTH
	    			       , MUTE_HEIGHT
	    			       , MainActivity.mainActivity.textureMuteOff
	    			       , MainActivity.mainActivity.getVertexBufferObjectManager()) {

	@Override
	public boolean onAreaTouched( TouchEvent pSceneTouchEvent
		, float pTouchAreaLocalX
		, float pTouchAreaLocalY) {

	    if (pSceneTouchEvent.isActionDown()) {

		Log.d("MuteOff", "touch");
		this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
		this.registerEntityModifier(MainActivity.TOUCH_ALPHA_MODIFIER.deepCopy());

	    } else if (pSceneTouchEvent.isActionUp()) {

		Log.d("MuteOff", "no touch");
		this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());
		muteIconCLick();
	    }
	    return true;
	}
    };


    private Sprite muteOn = new Sprite( MUTEON_POSITION_LEFT
	    		       	      , MUTEON_POSITION_UP
	    		       	      , MUTEON_WIDTH
	    		       	      , MUTEON_HEIGHT
	    		       	      , MainActivity.mainActivity.textureMuteOn
	    		       	      , MainActivity.mainActivity.getVertexBufferObjectManager());

    private void muteIconCLick() {	    
	if (!MainActivity.isMute) {
	    MainActivity.mainActivity.mClick.play();
	    MainActivity.mainActivity.mMusic.setVolume(0);
	    MainActivity.mainActivity.mClick.setVolume(0);
	    MainActivity.mainActivity.mGameOver.setVolume(0);
	    MainActivity.mainActivity.mGameStart.setVolume(0);
	    MainActivity.mainActivity.mSound.setVolume(0);
	    MainActivity.mainActivity.mStep.setVolume(0);
	    MainActivity.isMute = !MainActivity.isMute;
	    muteOn.setVisible(false);
	} else {
	    MainActivity.mainActivity.mMusic.setVolume(1);
	    MainActivity.mainActivity.mClick.setVolume(1);
	    MainActivity.mainActivity.mGameOver.setVolume(1);
	    MainActivity.mainActivity.mGameStart.setVolume(1);
	    MainActivity.mainActivity.mSound.setVolume(1);
	    MainActivity.mainActivity.mStep.setVolume(1);
	    MainActivity.mainActivity.mClick.play();
	    MainActivity.isMute = !MainActivity.isMute;
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
	//setTouchAreaBindingOnActionDownEnabled(true);
	//setTouchAreaBindingOnActionMoveEnabled(true);
	
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
}