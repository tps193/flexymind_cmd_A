package com.example.forestgame;

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
    private static final float BUTTON_PLAY_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 52 / 128;
    private static final float BUTTON_PLAY_WIDTH = MainActivity.TEXTURE_WIDTH / 2;
    private static final float BUTTON_PLAY_HEIGHT = MainActivity.TEXTURE_HEIGHT * 12 / 128;
    
    private static final float BUTTON_SCORES_POSITION_LEFT = MainActivity.TEXTURE_WIDTH / 4;
    private static final float BUTTON_SCORE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 69 / 128;
    private static final float BUTTON_SCORES_WIDTH = MainActivity.TEXTURE_WIDTH / 2;
    private static final float BUTTON_SCORES_HEIGHT = MainActivity.TEXTURE_HEIGHT * 12 / 128;
    
    private static final float BUTTON_CREDITS_POSITION_LEFT = MainActivity.TEXTURE_WIDTH / 4;
    private static final float BUTTON_CREDITS_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 86 / 128;
    private static final float BUTTON_CREDITS_WIDTH = MainActivity.TEXTURE_WIDTH / 2;
    private static final float BUTTON_CREDITS_HEIGHT = MainActivity.TEXTURE_HEIGHT * 12 / 128;
    
    private static final float BUTTON_EXIT_POSITION_LEFT = MainActivity.TEXTURE_WIDTH / 4;
    private static final float BUTTON_EXIT_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 103 / 128;
    private static final float BUTTON_EXIT_WIDTH = MainActivity.TEXTURE_WIDTH / 2;
    private static final float BUTTON_EXIT_HEIGHT = MainActivity.TEXTURE_HEIGHT * 12 / 128;

    
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
		    this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER);
		    this.registerEntityModifier(MainActivity.SHOW_ALPHA_MODIFIER);
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("ButtonPlay", "no touch");
		    this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER);
		    this.registerEntityModifier(MainActivity.HIDE_ALPHA_MODIFIER);
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
		    this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER);
		    this.registerEntityModifier(MainActivity.SHOW_ALPHA_MODIFIER);
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("ButtonScores", "no touch");
		    this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER);
		    this.registerEntityModifier(MainActivity.HIDE_ALPHA_MODIFIER);
		    buttonScoresClick();
		}
		return true;
	    }
	};

	private void buttonScoresClick() {
	    MainActivity.mainActivity.mClick.play();
	    MainScene.showScoresScene();
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
		    this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER);
		    this.registerEntityModifier(MainActivity.SHOW_ALPHA_MODIFIER);
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("ButtonCredits", "no touch");
		    this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER);
		    this.registerEntityModifier(MainActivity.HIDE_ALPHA_MODIFIER);
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
		    this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER);
		    this.registerEntityModifier(MainActivity.SHOW_ALPHA_MODIFIER);
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    Log.d("ButtonExit", "no touch");
		    this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER);
		    this.registerEntityModifier(MainActivity.HIDE_ALPHA_MODIFIER);
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
	
	
    
    public MainMenuScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	background.registerEntityModifier(MainActivity.HIDE_ALPHA_MODIFIER);
	attachChild(background);
	attachChild(title);
	attachChild(buttonPlay);
	attachChild(buttonScores);
	attachChild(buttonCredits);
	attachChild(buttonExit);
	registerTouchArea(buttonPlay);
	registerTouchArea(buttonScores);
	registerTouchArea(buttonCredits);
	registerTouchArea(buttonExit);
	setTouchAreaBindingOnActionDownEnabled(true);
	setTouchAreaBindingOnActionMoveEnabled(true);	
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
	MainActivity.mainActivity.mMusic.play();
   	background.registerEntityModifier(MainActivity.HIDE_ALPHA_MODIFIER);	
    }
    
    public void hide() {
	
   	setVisible(false);
   	background.setAlpha(0.5f);
   	setIgnoreUpdate(true);
    }
}
