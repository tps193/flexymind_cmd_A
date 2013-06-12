package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class GameOverScene extends Scene {
    
    private static final float GAMEOVER_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 17 / 120;
    private static final float GAMEOVER_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 15 / 64;
    private static final float GAMEOVER_WIDTH = MainActivity.TEXTURE_WIDTH * 286 / 400;
    private static final float GAMEOVER_HEIGHT = MainActivity.TEXTURE_HEIGHT * 12 / 128;
    private static final float MAINMENU_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 222 / 1024;
    private static final float MAINMENU_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 26 / 64;
    private static final float MAINMENU_WIDTH = MainActivity.TEXTURE_WIDTH * 225 / 400;
    private static final float MAINMENU_HEIGHT = MainActivity.TEXTURE_HEIGHT * 9 / 128;
    private static final float NEWGAME_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 227 / 1024;
    private static final float NEWGAME_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 33 / 64;
    private static final float NEWGAME_WIDTH = MainActivity.TEXTURE_WIDTH * 225 / 400;
    private static final float NEWGAME_HEIGHT = MainActivity.TEXTURE_HEIGHT * 9 / 128;
    
    private Sprite background = new Sprite( 0
            				, 0
            				, MainActivity.TEXTURE_WIDTH
            				, MainActivity.TEXTURE_HEIGHT
            				, MainActivity.mainActivity.textureBackground
            				, MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Sprite gameover = new Sprite( GAMEOVER_POSITION_LEFT
	     , GAMEOVER_POSITION_UP
	     , GAMEOVER_WIDTH
	     , GAMEOVER_HEIGHT
	     , MainActivity.mainActivity.textureGameOverL
	     , MainActivity.mainActivity.getVertexBufferObjectManager());

    private Sprite mainmenu = new Sprite(	MAINMENU_POSITION_LEFT
		      , MAINMENU_POSITION_UP
		      , MAINMENU_WIDTH
		      , MAINMENU_HEIGHT
		      , MainActivity.mainActivity.textureMainMenu
		      , MainActivity.mainActivity.getVertexBufferObjectManager()) {
    @Override
    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
	    			, float pTouchAreaLocalX
	    			, float pTouchAreaLocalY) {
	
	if (pSceneTouchEvent.isActionDown()) {
	    
	    applyTouchEffects(mainmenu);
	} else if (pSceneTouchEvent.isActionUp()) {
	    
	    if (    (pTouchAreaLocalX > 0) 
	                 && (pTouchAreaLocalX < MAINMENU_WIDTH)
	                 && (pTouchAreaLocalY > 0)
	                 && (pTouchAreaLocalY < MAINMENU_HEIGHT)) {
	    
	    applyUntouchEffects(mainmenu);
	    MainActivity.mainActivity.mClick.play();
	    mainMenuClick();
	    
	    } else {
		applyUntouchEffects(mainmenu);
	    }
	    
	} else if (pSceneTouchEvent.isActionMove()) {
	    if (    !((pTouchAreaLocalX > 0) 
	                 && (pTouchAreaLocalX < MAINMENU_WIDTH)
	                 && (pTouchAreaLocalY > 0)
	                 && (pTouchAreaLocalY < MAINMENU_HEIGHT))) {
		applyUntouchEffects(mainmenu);
	    } else {
		applyTouchEffects(mainmenu);
	    }
	}
	return true;
	}
    };
    
    private void mainMenuClick() {
	MainScene.showMainMenuScene();
    }

    private Sprite newgame = new Sprite(NEWGAME_POSITION_LEFT
		    , NEWGAME_POSITION_UP
		    , NEWGAME_WIDTH
		    , NEWGAME_HEIGHT
		    , MainActivity.mainActivity.textureNewGame
		    , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	@Override
	public boolean onAreaTouched( TouchEvent pSceneTouchEvent
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
		MainActivity.mainActivity.mClick.play();
		newGameClick();
		
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
    
    private void newGameClick() {
	MainScene.getGameScene().getSlotMatrix().reInit();
	MainScene.getGameScene().getPrison().clear();
	MainScene.getGameScene().getRespawn().clear();
	MainScene.getGameScene().getRespawn().generateElement();
	MainScene.showGameScene();
    }
   
    public GameOverScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	attachChild(background);
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	background.registerEntityModifier(MainActivity.PAUSE_ALPHA_MODIFIER.deepCopy());
	attachChild(gameover);
	attachChild(mainmenu);
	attachChild(newgame);
	registerTouchArea(gameover);
	registerTouchArea(mainmenu);
	registerTouchArea(newgame);
	setTouchAreaBindingOnActionDownEnabled(true);
	setTouchAreaBindingOnActionMoveEnabled(true);
	
    }
    
    public void show() {
	MainScene.getGameScene().getScoresTable().saveResult(MainScene.getGameScene().getSlotMatrix().getScore());
	setVisible(true);
	setIgnoreUpdate(false);
	MainActivity.mainActivity.mGameOver.play();
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