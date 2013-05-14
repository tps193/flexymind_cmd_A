package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class GameOverScene extends Scene {
    
    private static final float GAMEOVER_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 16 / 120;
    private static final float GAMEOVER_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 4 / 14;
    private static final float MAINMENU_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 258 / 1024;
    private static final float MAINMENU_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 29 / 64;
    private static final float NEWGAME_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 270 / 1024;
    private static final float NEWGAME_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 36 / 64;
    
    private Sprite background = new Sprite( 0
            				, 0
            				, MainActivity.TEXTURE_WIDTH
            				, MainActivity.TEXTURE_HEIGHT
            				, MainActivity.mainActivity.textureBackground
            				, MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Text gameover = new Text(	GAMEOVER_POSITION_LEFT
					, GAMEOVER_POSITION_UP
					, MainActivity.mainActivity.tGameOver
					, "GAME OVER"
					, MainActivity.mainActivity.getVertexBufferObjectManager());

    private Text mainmenu = new Text(	MAINMENU_POSITION_LEFT
					, MAINMENU_POSITION_UP
					, MainActivity.mainActivity.tMainMenu
					, "Main Menu"
					, MainActivity.mainActivity.getVertexBufferObjectManager()) {
    @Override
    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
	    			, float pTouchAreaLocalX
	    			, float pTouchAreaLocalY) {
	
	if (pSceneTouchEvent.isActionDown()) {
	    
	    this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
	} else if (pSceneTouchEvent.isActionUp()) {
	    
	    this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
	    mainMenuClick();
	}
	return true;
	}
    };
    
    private void mainMenuClick() {
	MainScene.showMainMenuScene();
    }

    private Text newgame = new Text(	NEWGAME_POSITION_LEFT
	    				, NEWGAME_POSITION_UP
	    				, MainActivity.mainActivity.tNewGame
	    				, "New Game"
	    				, MainActivity.mainActivity.getVertexBufferObjectManager()) {
	@Override
	public boolean onAreaTouched( TouchEvent pSceneTouchEvent
    			, float pTouchAreaLocalX
    			, float pTouchAreaLocalY) {
	    
	    if (pSceneTouchEvent.isActionDown()) {
		
		this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
	    } else if (pSceneTouchEvent.isActionUp()) {
		
		this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		newGameClick();
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
	//setTouchAreaBindingOnActionDownEnabled(true);
	//setTouchAreaBindingOnActionMoveEnabled(true);
	
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
   	background.registerEntityModifier(MainActivity.PAUSE_ALPHA_MODIFIER.deepCopy());
    }
    
    public void hide() {
	
   	setVisible(false);
   	setIgnoreUpdate(true);
   	background.setAlpha(0.8f);
    }
}