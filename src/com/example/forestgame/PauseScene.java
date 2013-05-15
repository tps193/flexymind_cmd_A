package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class PauseScene extends Scene {
    
    private static final float PAUSE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 35 / 120;
    private static final float PAUSE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 13 / 64;
    private static final float RESUME_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 319 / 1024;
    private static final float RESUME_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 24 / 64;
    private static final float NEWGAME_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 260 / 1024;
    private static final float NEWGAME_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 31 / 64;
    private static final float MAINMENU_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 235 / 1024;
    private static final float MAINMENU_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 38 / 64;
    

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
	registerTouchArea(pause);
	registerTouchArea(newgame);
	registerTouchArea(resume);
	registerTouchArea(mainMenu);
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