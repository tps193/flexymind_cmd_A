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

import android.util.Log;

public class PauseScene extends Scene {
    
    private Sprite sprite = new Sprite( 0
            , 0
            , MainActivity.TEXTURE_WIDTH
            , MainActivity.TEXTURE_HEIGHT
            , MainActivity.mainActivity.textureBackground
            , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Text question = new Text(MainActivity.TEXTURE_WIDTH * 18 / 120
		, MainActivity.TEXTURE_HEIGHT * 5 / 14
		, MainActivity.mainActivity.tQuestion
		, "Really want to exit?"
		, MainActivity.mainActivity.getVertexBufferObjectManager());

    private Text choiseY = new Text(MainActivity.TEXTURE_WIDTH * 133 / 1024
		, MainActivity.TEXTURE_HEIGHT * 30 / 64
		, MainActivity.mainActivity.tChoiseYES
		, "YES"
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

    private Text choiseN = new Text(MainActivity.TEXTURE_WIDTH * 685 / 1024
	, MainActivity.TEXTURE_HEIGHT * 30 / 64
	, MainActivity.mainActivity.tChoiseNO
	, "NO"
	, MainActivity.mainActivity.getVertexBufferObjectManager()) {
	@Override
	public boolean onAreaTouched( TouchEvent pSceneTouchEvent
    			, float pTouchAreaLocalX
    			, float pTouchAreaLocalY) {
	    if (pSceneTouchEvent.isActionDown()) {
		this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
	    } else if (pSceneTouchEvent.isActionUp()) {
		this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		MainScene.showGameScene();
	    }
	    return true;
	}
    };
    
    /*private Sprite buttonPlay = new Sprite( MainActivity.TEXTURE_WIDTH / 4
		      			  , MainActivity.TEXTURE_HEIGHT * 52 / 128
		      			  , MainActivity.TEXTURE_WIDTH * 2 / 4
		      			  , MainActivity.TEXTURE_HEIGHT * 12 / 128
		      			  , MainActivity.mainActivity.texturePlay
		      			  , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	@Override
	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
		    			, float pTouchAreaLocalX
		    			, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonPlay", "touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 1.0f, 0.5f));
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonPlay", "no touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 0.5f, 1.0f));
		    MainScene.showGameScene();
		}
		return true;
	    }
    };
    
    private Sprite buttonExit = new Sprite( MainActivity.TEXTURE_WIDTH / 4
	      				  , MainActivity.TEXTURE_HEIGHT * 69 / 128
	      				  , MainActivity.TEXTURE_WIDTH * 2 / 4
	      				  , MainActivity.TEXTURE_HEIGHT * 12 / 128
	      				  , MainActivity.mainActivity.textureExit
	      				  , MainActivity.mainActivity.getVertexBufferObjectManager()) {
	
	@Override
	    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
					, float pTouchAreaLocalX
					, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonExit", "touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 1.0f, 0.5f));
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonExit", "no touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 0.5f, 1.0f));
		    MainScene.showMainMenuScene();
		}
		return true;
	    }
	};*/
    
   
    public PauseScene() {
	setBackgroundEnabled(true);
	setBackground(new Background(new Color(0.1f, 0.1f, 0.0f)));
	attachChild(sprite);
	sprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	sprite.registerEntityModifier(new AlphaModifier(0.55f, 0.8f, 0.5f));
	attachChild(question);
	attachChild(choiseN);
	attachChild(choiseY);
	registerTouchArea(question);
	registerTouchArea(choiseN);
	registerTouchArea(choiseY);
	//attachChild(buttonPlay);
	//attachChild(buttonExit);
	//registerTouchArea(buttonPlay);
	//registerTouchArea(buttonExit);
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

