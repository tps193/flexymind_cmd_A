package com.example.forestgame;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

public class MainMenuScene extends Scene {
    
    private static final float TITLE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH / 14;
    private static final float TITLE_POSITION_UP = MainActivity.TEXTURE_HEIGHT / 20;
    private static final float TITLE_WIDTH = MainActivity.TEXTURE_WIDTH * 6 / 8;
    private static final float TITLE_HEIGHT = MainActivity.TEXTURE_HEIGHT / 4;
    
    private static final float BUTTON_PLAY_POSITION_LEFT = MainActivity.TEXTURE_WIDTH / 4;
    private static final float BUTTON_PLAY_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 48 / 128;
    private static final float BUTTON_PLAY_WIDTH = MainActivity.TEXTURE_WIDTH * 2 / 4;
    private static final float BUTTON_PLAY_HEIGHT = MainActivity.TEXTURE_HEIGHT * 12 / 128;
    
    private static final float BUTTON_RESUME_POSITION_LEFT =  MainActivity.TEXTURE_WIDTH * 125 / 400;
    private static final float BUTTON_RESUME_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 67 / 128;
    private static final float BUTTON_RESUME_WIDTH = MainActivity.TEXTURE_WIDTH * 150 / 400;
    private static final float BUTTON_RESUME_HEIGHT = MainActivity.TEXTURE_HEIGHT * 9 / 128;
    
    private static final float BUTTON_SCORES_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 125 / 400;
    private static final float BUTTON_SCORE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 79 / 128;
    private static final float BUTTON_SCORES_WIDTH = MainActivity.TEXTURE_WIDTH * 150 / 400;
    private static final float BUTTON_SCORES_HEIGHT = MainActivity.TEXTURE_HEIGHT * 9 / 128;
    
    private static final float BUTTON_CREDITS_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 125 / 400;
    private static final float BUTTON_CREDITS_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 91 / 128;
    private static final float BUTTON_CREDITS_WIDTH = MainActivity.TEXTURE_WIDTH * 150 / 400;
    private static final float BUTTON_CREDITS_HEIGHT = MainActivity.TEXTURE_HEIGHT * 9 / 128;
    
    private static final float BUTTON_EXIT_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 125 / 400;
    private static final float BUTTON_EXIT_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 103 / 128;
    private static final float BUTTON_EXIT_WIDTH = MainActivity.TEXTURE_WIDTH * 150 / 400;
    private static final float BUTTON_EXIT_HEIGHT = MainActivity.TEXTURE_HEIGHT * 9 / 128;
    
    public static final float MUTE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH* 520 / 625;
    public static final float MUTE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1850 / 2000;
    public static final float MUTE_WIDTH = MainActivity.TEXTURE_WIDTH * 36 / 250;
    public static final float MUTE_HEIGHT = MainActivity.TEXTURE_HEIGHT * 100 / 2000;
    
    public static final float MUTEON_POSITION_LEFT = MainActivity.TEXTURE_WIDTH* 568 / 625;
    public static final float MUTEON_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1842 / 2000;
    public static final float MUTEON_WIDTH = MainActivity.TEXTURE_WIDTH * 13 / 250;
    public static final float MUTEON_HEIGHT = MainActivity.TEXTURE_HEIGHT * 116 / 2000;

    
    private Sprite background = new Sprite( 0
	    		          , 0
	    		          , MainActivity.TEXTURE_WIDTH
	    		          , MainActivity.TEXTURE_HEIGHT
	    		          , MainActivity.mainActivity.textureBackground
	    		          , MainActivity.mainActivity.getVertexBufferObjectManager());

    private Sprite title = new Sprite( TITLE_POSITION_LEFT
    				 , TITLE_POSITION_UP
    				 , TITLE_WIDTH
    				 , TITLE_HEIGHT
    				 , MainActivity.mainActivity.textureTitle
    				 , MainActivity.mainActivity.getVertexBufferObjectManager());

    private Sprite buttonPlay = new Sprite( BUTTON_PLAY_POSITION_LEFT
	    			      , BUTTON_PLAY_POSITION_UP
	    			      , BUTTON_PLAY_WIDTH
	    			      , BUTTON_PLAY_HEIGHT
	    			      , MainActivity.mainActivity.texturePlay
	    			      , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
		    			, float pTouchAreaLocalX
		    			, float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.isActionDown()) {
		    
		    Log.d("ButtonPlay", "touch");
		    this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
		    this.registerEntityModifier(MainActivity.TOUCH_ALPHA_MODIFIER.deepCopy());
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("ButtonPlay", "no touch");
		    this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		    this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());
		    buttonPlayPress();
		}
		return true;
	    }
	};

	private void buttonPlayPress() {
	    
	    MainScene.getGameScene().getSlotMatrix().reInit();
	    MainScene.getGameScene().getPrison().clear();
	    MainScene.getGameScene().getRespawn().clear();
	    MainScene.getGameScene().getRespawn().generateElement();
	    MainActivity.mainActivity.mClick.play();
	    MainScene.showGameScene();
	}
	

	Sprite buttonResume = new Sprite( BUTTON_RESUME_POSITION_LEFT
					, BUTTON_RESUME_POSITION_UP
					, BUTTON_RESUME_WIDTH
					, BUTTON_RESUME_HEIGHT
					, MainActivity.mainActivity.textureResume
					, MainActivity.mainActivity.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
		    			, float pTouchAreaLocalX
		    			, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {

	Log.d("ButtonResume", "touch");
	this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
	this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());

		} else if (pSceneTouchEvent.isActionUp()) {

		    Log.d("ButtonResume", "no touch");
		    this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		    this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());
		    MainActivity.mainActivity.mClick.play();
		    try {
			MainScene.gameScene.setSavedGame();
			MainScene.showGameScene();
		    } catch(IOException e) {
			
		    }
		}
		return true;
	    }
	};
	
	private Sprite buttonScores = new Sprite( BUTTON_SCORES_POSITION_LEFT
		        			, BUTTON_SCORE_POSITION_UP
		        			, BUTTON_SCORES_WIDTH
		        			, BUTTON_SCORES_HEIGHT
		        			, MainActivity.mainActivity.textureScores
		        			, MainActivity.mainActivity.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
					, float pTouchAreaLocalX
					, float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.isActionDown()) {
		    
		    Log.d("ButtonScores", "touch");
		    this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
		    this.registerEntityModifier(MainActivity.TOUCH_ALPHA_MODIFIER.deepCopy());
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("ButtonScores", "no touch");
		    this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		    this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());
		    buttonScoresClick();
		}
		return true;
	    }
	};

	private void buttonScoresClick() {
	    
	    MainActivity.mainActivity.mClick.play();
	    MainScene.showScoresScene();
	    //MainScene.gameScene.slotMatrix.loadInit();
	}
	
		
	private Sprite buttonCredits = new Sprite( BUTTON_CREDITS_POSITION_LEFT
				         , BUTTON_CREDITS_POSITION_UP
				         , BUTTON_CREDITS_WIDTH
				         , BUTTON_CREDITS_HEIGHT
				         , MainActivity.mainActivity.textureCredits
				         , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
					, float pTouchAreaLocalX
					, float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.isActionDown()) {
		    
		    Log.d("ButtonCredits", "touch");
		    this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
		    this.registerEntityModifier(MainActivity.TOUCH_ALPHA_MODIFIER.deepCopy());
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("ButtonCredits", "no touch");
		    this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		    this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());
		    buttonCreditsClick();
		}
		return true;
	    }
	};
	
	private void buttonCreditsClick() {

	    MainActivity.mainActivity.mClick.play();
	    MainScene.showCreditsScene();
	}
		    
	private Sprite buttonExit = new Sprite( BUTTON_EXIT_POSITION_LEFT
				      , BUTTON_EXIT_POSITION_UP
				      , BUTTON_EXIT_WIDTH
				      , BUTTON_EXIT_HEIGHT
				      , MainActivity.mainActivity.textureExit
				      , MainActivity.mainActivity.getVertexBufferObjectManager()) {

	    @Override
	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
					, float pTouchAreaLocalX
					, float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.isActionDown()) {
		    
		    Log.d("ButtonExit", "touch");
		    this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
		    this.registerEntityModifier(MainActivity.TOUCH_ALPHA_MODIFIER.deepCopy());
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("ButtonExit", "no touch");
		    this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		    this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());
		    buttonExitCLick();
		}
		return true;
	    }
	};
	
	private void buttonExitCLick() {
	    MainActivity.mainActivity.mClick.play();
	    MainActivity.mainActivity.finish();
	    if (MainActivity.mainActivity.isFinishing() == false) {
		
		android.os.Process.killProcess(android.os.Process.myPid());
	    }
	}
	
	
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
		
		MainActivity.mainActivity.muteSounds();
		muteOn.setVisible(false);
		
	    } else {
		
		MainActivity.mainActivity.unmuteSounds();
		muteOn.setVisible(true);
	    }
	}
	
    
    public MainMenuScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	background.registerEntityModifier(MainActivity.HIDE_ALPHA_MODIFIER.deepCopy());
	attachChild(background);
	attachChild(title);
	attachChild(buttonPlay);
	attachChild(buttonScores);
	attachChild(buttonCredits);
	attachChild(buttonExit);
	attachChild(muteOff);
	attachChild(muteOn);
	attachChild(buttonResume);
	muteOn.setVisible(true);
	registerTouchArea(buttonPlay);
	registerTouchArea(buttonScores);
	registerTouchArea(buttonCredits);
	registerTouchArea(buttonExit);
	registerTouchArea(muteOff);
	registerTouchArea(buttonResume);
	setTouchAreaBindingOnActionDownEnabled(true);
	setTouchAreaBindingOnActionMoveEnabled(true);
	
	MainActivity.mainActivity.mMusic.play();
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
	if (!MainActivity.isMute) {
	    
	    muteOn.setVisible(true);
	    
	} else {
	    
	    muteOn.setVisible(false);
	}
	MainActivity.mainActivity.mGameMusic.pause();
	MainActivity.mainActivity.mMusic.play();
   	background.registerEntityModifier(MainActivity.HIDE_ALPHA_MODIFIER.deepCopy());
    }
    
    public void hide() {
   	setVisible(false);
   	background.setAlpha(0.5f);
   	setIgnoreUpdate(true);
    }
}
