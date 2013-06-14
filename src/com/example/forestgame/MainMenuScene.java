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
    private static final float BUTTON_PLAY_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 51 / 128;
    private static final float BUTTON_PLAY_WIDTH = MainActivity.TEXTURE_WIDTH * 2 / 4;
    private static final float BUTTON_PLAY_HEIGHT = MainActivity.TEXTURE_HEIGHT * 17 / 256;
    
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
    
    public static final float MUTE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH* 500 / 625;
    public static final float MUTE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1796 / 2000;
    public static final float MUTE_WIDTH = MainActivity.TEXTURE_WIDTH * 35 / 250;
    public static final float MUTE_HEIGHT = MainActivity.TEXTURE_HEIGHT * 149 / 2000;
    
    public static final float MUTEON_POSITION_LEFT = MainActivity.TEXTURE_WIDTH* 548 / 625;
    public static final float MUTEON_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1800 / 2000;
    public static final float MUTEON_WIDTH = MainActivity.TEXTURE_WIDTH * 15 / 250;
    public static final float MUTEON_HEIGHT = MainActivity.TEXTURE_HEIGHT * 123 / 2000;
    
    public static final float HELP_POSITION_LEFT = MainActivity.TEXTURE_WIDTH* 40 / 625;
    public static final float HELP_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1797 / 2000;
    public static final float HELP_WIDTH = MainActivity.TEXTURE_WIDTH * 30 / 250;
    public static final float HELP_HEIGHT = MainActivity.TEXTURE_HEIGHT * 160 / 2000;

    
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
		    applyTouchEffects(buttonPlay);
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    if (    (pTouchAreaLocalX > 0) 
	                 && (pTouchAreaLocalX < BUTTON_PLAY_WIDTH)
	                 && (pTouchAreaLocalY > 0)
	                 && (pTouchAreaLocalY < BUTTON_PLAY_HEIGHT)) {
		    
			Log.d("ButtonPlay", "no touch");
			applyUntouchEffects(buttonPlay);
			buttonPlayPress();
		    } else {
			Log.d("ButtonPlay", "no touch outside");
			applyUntouchEffects(buttonPlay);
		    }
		    
		} else if (pSceneTouchEvent.isActionMove()) {
		    if (    !((pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < BUTTON_PLAY_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < BUTTON_PLAY_HEIGHT))) {
			applyUntouchEffects(buttonPlay);
		    } else {
			applyTouchEffects(buttonPlay);
		    }
		}
		
		return true;
	    }
	};
	
	private Sprite helpIcon = new Sprite( HELP_POSITION_LEFT
					, HELP_POSITION_UP
					, HELP_WIDTH
					, HELP_HEIGHT
					, MainActivity.mainActivity.textureHelp
					, MainActivity.mainActivity.getVertexBufferObjectManager()) {
		
		  @Override
		  public boolean onAreaTouched( TouchEvent pSceneTouchEvent
		            , float pTouchAreaLocalX
		            , float pTouchAreaLocalY) {
		
		        if (pSceneTouchEvent.isActionDown()) {
		
		            Log.d("Help", "touch");
		            applyTouchEffects(helpIcon);
		
		        } else if (pSceneTouchEvent.isActionUp()) {
		            
		            if (    (pTouchAreaLocalX > 0) 
			                 && (pTouchAreaLocalX < HELP_WIDTH)
			                 && (pTouchAreaLocalY > 0)
			                 && (pTouchAreaLocalY < HELP_HEIGHT)) {
		
		            Log.d("Help", "no touch");
		            applyUntouchEffects(helpIcon);
		            helpIconCLick();
		            
		            } else {
				Log.d("Help", "no touch outside");
				applyUntouchEffects(helpIcon);
			    }
			    
			} else if (pSceneTouchEvent.isActionMove()) {
			    if (    !((pTouchAreaLocalX > 0) 
			                 && (pTouchAreaLocalX < HELP_WIDTH)
			                 && (pTouchAreaLocalY > 0)
			                 && (pTouchAreaLocalY < HELP_HEIGHT))) {
				applyUntouchEffects(helpIcon);
			    } else {
				applyTouchEffects(helpIcon);
			    }
		        }
		    return true;
		    }
		  };
		  
	private void helpIconCLick() {
	
	    MainActivity.mainActivity.mClick.play();
	    MainScene.showHelpScene();
	      
	}

	private void buttonPlayPress() {
	    MainActivity.mainActivity.mClick.play();
	    if (MainScene.getGameScene().getSlotMatrix().getScore() != 0)
	    MainScene.getGameScene().getScoresTable().saveResult(MainScene.getGameScene().getSlotMatrix().getScore());
	    MainScene.getGameScene().getSlotMatrix().reInit();
	    MainScene.getGameScene().getPrison().clear();
	    MainScene.getGameScene().getRespawn().clear();
	    MainScene.getGameScene().getRespawn().generateElement();
	    MainActivity.mainActivity.progressNotSaved();
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
		    applyTouchEffects(buttonResume);

		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    if (    (pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < BUTTON_RESUME_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < BUTTON_RESUME_HEIGHT)) {
		    
		    MainActivity.mainActivity.mClick.play();
		    Log.d("ButtonResume", "no touch");
		    applyUntouchEffects(buttonResume);
		    try {
			MainScene.gameScene.setSavedGame();
			MainScene.showGameScene();
		    } catch(IOException e) {
			
		    }
		    
		 } else {
			Log.d("ButtonResume", "no touch outside");
			applyUntouchEffects(buttonResume);
		    }
		    
		} else if (pSceneTouchEvent.isActionMove()) {
		    if (    !((pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < BUTTON_RESUME_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < BUTTON_RESUME_HEIGHT))) {
			applyUntouchEffects(buttonResume);
		    } else {
			applyTouchEffects(buttonResume);
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
		    applyTouchEffects(buttonScores);
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    if (    (pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < BUTTON_SCORES_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < BUTTON_SCORES_HEIGHT)) {
		    
		    Log.d("ButtonScores", "no touch");
		    applyUntouchEffects(buttonScores);
		    buttonScoresClick();
		    
		    } else {
			Log.d("ButtonScores", "no touch outside");
			applyUntouchEffects(buttonScores);
		    }
		    
		} else if (pSceneTouchEvent.isActionMove()) {
		    if (    !((pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < BUTTON_SCORES_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < BUTTON_SCORES_HEIGHT))) {
			applyUntouchEffects(buttonScores);
		    } else {
			applyTouchEffects(buttonScores);
		    }
		    
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
		    applyTouchEffects(buttonCredits);
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    if (    (pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < BUTTON_CREDITS_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < BUTTON_CREDITS_HEIGHT)) {
		    
		    Log.d("ButtonCredits", "no touch");
		    applyUntouchEffects(buttonCredits);
		    buttonCreditsClick();
		    
		} else {
			Log.d("ButtonCredits", "no touch outside");
			applyUntouchEffects(buttonCredits);
		    }
		    
		} else if (pSceneTouchEvent.isActionMove()) {
		    if (    !((pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < BUTTON_CREDITS_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < BUTTON_CREDITS_HEIGHT))) {
			applyUntouchEffects(buttonCredits);
		    } else {
			applyTouchEffects(buttonCredits);
		    }
		    
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
		    applyTouchEffects(buttonExit);
		    
		} else if (pSceneTouchEvent.isActionMove()) {
		    if (    !((pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < BUTTON_EXIT_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < BUTTON_EXIT_HEIGHT))) {
			applyUntouchEffects(buttonExit);
		    } else {
			applyTouchEffects(buttonExit);
		    }
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    if (    (pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < BUTTON_EXIT_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < BUTTON_EXIT_HEIGHT)) {
		    
		    Log.d("ButtonExit", "no touch");
		    applyUntouchEffects(buttonExit);
		    buttonExitCLick();
		    
		    } else {
			Log.d("ButtonExit", "no touch outside");
			applyUntouchEffects(buttonExit);
		    }
		    
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
		    applyTouchEffects(muteOff);
		    
		} else if (pSceneTouchEvent.isActionMove()) {
		    if (    !((pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < MUTE_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < MUTE_HEIGHT))) {
			applyUntouchEffects(muteOff);
		    } else {
			applyTouchEffects(muteOff);
			
		    }
		    
		} else if (pSceneTouchEvent.isActionUp()) {
		    
		    if (    (pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < MUTE_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < MUTE_HEIGHT)) {

		    Log.d("MuteOff", "no touch");
		    applyUntouchEffects(muteOff);		    
		    muteIconCLick();
		    
		} else {
			Log.d("MuteOff", "no touch outside");
			applyUntouchEffects(muteOff);
		    }	

		    
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
		MainActivity.mainActivity.saveSettings();
	    } else {
		
		MainActivity.mainActivity.unmuteSounds();
		muteOn.setVisible(true);
		MainActivity.mainActivity.saveSettings();
	    }
	}
	
	/*private void updateUntouchEffect(float tt, final Sprite sp) {

	    registerUpdateHandler(new TimerHandler(tt, false, new ITimerCallback() {
		    @Override
		    public void onTimePassed(TimerHandler pTimerHandler) {
			applyUntouchEffects(sp);		
		    }}));
	}*/
	
    
    public MainMenuScene() {
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	background.registerEntityModifier(MainActivity.HIDE_ALPHA_MODIFIER.deepCopy());
	attachChild(background);
	attachChild(title);
	attachChild(buttonPlay);
	attachChild(buttonResume);
	attachChild(buttonScores);
	attachChild(buttonCredits);
	attachChild(buttonExit);
	attachChild(muteOff);
	attachChild(muteOn);
	attachChild(helpIcon);
	muteOn.setVisible(true);
	registerTouchArea(buttonPlay);
	registerTouchArea(buttonResume);
	registerTouchArea(buttonScores);
	registerTouchArea(buttonCredits);
	registerTouchArea(buttonExit);
	registerTouchArea(muteOff);
	registerTouchArea(helpIcon);
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
   	
   	applyUntouchEffects(buttonPlay);
   	applyUntouchEffects(buttonResume);
   	applyUntouchEffects(buttonScores);
   	applyUntouchEffects(buttonCredits);
   	applyUntouchEffects(buttonExit);
   	
    }
    
    public void hide() {
   	setVisible(false);
   	background.setAlpha(0.5f);
   	setIgnoreUpdate(true);
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
