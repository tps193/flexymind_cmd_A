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

public class GameOverScene extends Scene {
    
    private Sprite sprite = new Sprite( 0
            , 0
            , MainActivity.TEXTURE_WIDTH
            , MainActivity.TEXTURE_HEIGHT
            , MainActivity.mainActivity.textureBackground
            , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Text gameover = new Text(MainActivity.TEXTURE_WIDTH * 16 / 120
		, MainActivity.TEXTURE_HEIGHT * 4 / 14
		, MainActivity.mainActivity.tGameOver
		, "GAME OVER"
		, MainActivity.mainActivity.getVertexBufferObjectManager());

    private Text mainmenu = new Text(MainActivity.TEXTURE_WIDTH * 258 / 1024
		, MainActivity.TEXTURE_HEIGHT * 29 / 64
		, MainActivity.mainActivity.tMainMenu
		, "Main Menu"
		, MainActivity.mainActivity.getVertexBufferObjectManager()) {
    @Override
    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
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

    private Text newgame = new Text(MainActivity.TEXTURE_WIDTH * 270 / 1024
	, MainActivity.TEXTURE_HEIGHT * 36 / 64
	, MainActivity.mainActivity.tNewGame
	, "New Game"
	, MainActivity.mainActivity.getVertexBufferObjectManager()) {
	@Override
	public boolean onAreaTouched( TouchEvent pSceneTouchEvent
    			, float pTouchAreaLocalX
    			, float pTouchAreaLocalY) {
	    if (pSceneTouchEvent.isActionDown()) {
		this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
	    } else if (pSceneTouchEvent.isActionUp()) {
		this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		MainScene.gameScene.slotMatrix.reInit();
		MainScene.gameScene.prison.clear();
		MainScene.gameScene.respawn.clear();
		MainScene.gameScene.respawn.generateElement();
		MainScene.showGameScene();
	    }
	    return true;
	}
    };
   
    public GameOverScene() {
	setBackgroundEnabled(true);
	setBackground(new Background(new Color(0.1f, 0.1f, 0.0f)));
	attachChild(sprite);
	sprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	sprite.registerEntityModifier(new AlphaModifier(0.55f, 0.8f, 0.5f));
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
   	sprite.registerEntityModifier(new AlphaModifier(0.55f, 0.8f, 0.5f));
    }
    
    public void hide() {
   	setVisible(false);
   	setIgnoreUpdate(true);
   	sprite.setAlpha(0.8f);
    }
}