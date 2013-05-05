package com.example.forestgame;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.view.KeyEvent;

public class MainScene extends Scene {
    
    private static int gameState; // constant to hold the game states
    				  // the actual states of the game
    private static final int mainMenuState = 0; 
    private static final int gameRunningState = 1;
    private static final int scoresShowState = 2;
    private static final int creditsShowState = 3;
    
    public static MainMenuScene mainMenuScene = new MainMenuScene();
    public static GameScene gameScene = new GameScene();
    public static CreditsScene creditsScene = new CreditsScene();
    public static ScoresScene scoresScene = new ScoresScene();
    
    public MainScene() {
	attachChild(mainMenuScene);
	attachChild(gameScene);
	attachChild(scoresScene);
	attachChild(creditsScene);
	showMainMenuScene();
    }
    
    public static void showMainMenuScene() {
	mainMenuScene.show();
	gameScene.hide();
	scoresScene.hide();
	creditsScene.hide();
	gameState = mainMenuState;
    }
    
    public static void showGameScene() {
	mainMenuScene.hide();
	gameScene.show();
	scoresScene.hide();
	creditsScene.hide();
	gameState = gameRunningState;
    }
    
    public static void showCreditsScene() {
	mainMenuScene.hide();
	gameScene.hide();
	scoresScene.hide();
	creditsScene.show();
	gameState = creditsShowState;
    }
    
    public static void showScoresScene() {
	mainMenuScene.hide();
	gameScene.hide();
	scoresScene.show();
	creditsScene.hide();
	gameState = creditsShowState;
    }
    
    @Override
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
	switch(gameState){
	case mainMenuState:
	  mainMenuScene.onSceneTouchEvent(pSceneTouchEvent);
	  break;
	case gameRunningState:
	  gameScene.onSceneTouchEvent(pSceneTouchEvent);
	  break;
	case creditsShowState:
	  creditsScene.onSceneTouchEvent(pSceneTouchEvent);
	  break;
	case scoresShowState:
	  scoresScene.onSceneTouchEvent(pSceneTouchEvent);
	  break;
	}
	return super.onSceneTouchEvent(pSceneTouchEvent);
    }
    
    public void keyPressed(int keyCode, KeyEvent event) {
	switch(gameState) {
	case mainMenuState:
	  MainActivity.mainActivity.onDestroy();
	  break;
	case gameRunningState:
	  showMainMenuScene();
	  break;
	case creditsShowState:
	    showMainMenuScene();
	  break;
	case scoresShowState:
	    showMainMenuScene();
	  break;
	}
    }
}
