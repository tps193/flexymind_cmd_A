package com.example.forestgame;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.view.KeyEvent;

public class MainScene extends Scene {
    
    private static int gameState; // constant to hold the game states
    // the actual states of the game
    private static final int mainMenuState = 0; 
    private static final int gameRunningState = 1;
    
    public static MainMenuScene mainMenuScene = new MainMenuScene();
    public static GameScene gameScene = new GameScene();
    
    public MainScene() {
	attachChild(mainMenuScene);
	attachChild(gameScene);
	showMainMenuScene();
    }
    
    public static void showMainMenuScene() {
	mainMenuScene.show();
	gameScene.hide();
	gameState = mainMenuState;
    }
    
    public static void showGameScene() {
	mainMenuScene.hide();
	gameScene.show();
	gameState = gameRunningState;
    }
    
    @Override
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
	switch(gameState)
	{
	case mainMenuState:
	    mainMenuScene.onSceneTouchEvent(pSceneTouchEvent);
	    break;
	case gameRunningState:
	    gameScene.onSceneTouchEvent(pSceneTouchEvent);
	    break;
	}
	return super.onSceneTouchEvent(pSceneTouchEvent);
    }
    
    public void keyPressed(int keyCode, KeyEvent event) {
	switch(gameState) 
	{
	case mainMenuState:
	  MainActivity.mainActivity.onDestroy();
	  break;
	case gameRunningState:
	    showMainMenuScene();
	    break;
	}
    }
}
