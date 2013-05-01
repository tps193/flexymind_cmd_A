package com.example.forestgame;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class MainMenuScene extends CameraScene {
    
    Sprite sprite = new Sprite(0, 0, MainActivity.CAMERA_WIDTH, MainActivity.CAMERA_HEIGHT,
	    MainActivity.mainActivity.textureBackground, new VertexBufferObjectManager());

	Sprite Title = new Sprite(MainActivity.CAMERA_WIDTH / 8, MainActivity.CAMERA_HEIGHT / 16,
		MainActivity.CAMERA_WIDTH * 6 / 8, MainActivity.CAMERA_HEIGHT / 4, MainActivity.mainActivity.textureTitle,
		new VertexBufferObjectManager());

	Sprite ButtonPlay = new Sprite(MainActivity.CAMERA_WIDTH / 4,
		MainActivity.CAMERA_HEIGHT * 52 / 128, MainActivity.CAMERA_WIDTH * 2 / 4,
		MainActivity.CAMERA_HEIGHT * 12 / 128, MainActivity.mainActivity.texturePlay,
		MainActivity.mainActivity.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

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
	
	Sprite ButtonScores = new Sprite(MainActivity.CAMERA_WIDTH / 4,
		MainActivity.CAMERA_HEIGHT * 69 / 128, MainActivity.CAMERA_WIDTH * 2 / 4,
		MainActivity.CAMERA_HEIGHT * 12 / 128, MainActivity.mainActivity.textureScores,
		MainActivity.mainActivity.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonScores", "touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 1.0f, 0.5f));
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonScores", "no touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 0.5f, 1.0f));
		}
		return true;
	    }
	};

	Sprite ButtonCredits = new Sprite(MainActivity.CAMERA_WIDTH / 4,
		MainActivity.CAMERA_HEIGHT * 86 / 128, MainActivity.CAMERA_WIDTH * 2 / 4,
		MainActivity.CAMERA_HEIGHT * 12 / 128, MainActivity.mainActivity.textureCredits,
		MainActivity.mainActivity.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonCredits", "touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 1.0f, 0.5f));
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonCredits", "no touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 0.5f, 1.0f));
		}
		return true;
	    }
	};

	Sprite ButtonExit = new Sprite(MainActivity.CAMERA_WIDTH / 4,
		MainActivity.CAMERA_HEIGHT * 103 / 128, MainActivity.CAMERA_WIDTH * 2 / 4,
		MainActivity.CAMERA_HEIGHT * 12 / 128, MainActivity.mainActivity.textureExit,
		MainActivity.mainActivity.getVertexBufferObjectManager()) {
		//this.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonExit", "touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 1.0f, 0.5f));
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonExit", "no touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 0.5f, 1.0f));
		}
		return true;
	    }
	};
    
    public MainMenuScene() {
	super(MainActivity.camera);
	setBackgroundEnabled(true);
	
	attachChild(Title);
	attachChild(ButtonPlay);
	attachChild(ButtonScores);
	attachChild(ButtonCredits);
	attachChild(ButtonExit);
	registerTouchArea(ButtonPlay);
	registerTouchArea(ButtonScores);
	registerTouchArea(ButtonCredits);
	registerTouchArea(ButtonExit);
	setTouchAreaBindingOnActionDownEnabled(true);
	setTouchAreaBindingOnActionMoveEnabled(true);
	
	
    }
    
    public void show() {
	setVisible(true);
	setIgnoreUpdate(false);
    }
    
    public void hide() {
   	setVisible(false);
   	setIgnoreUpdate(true);
       }

}
