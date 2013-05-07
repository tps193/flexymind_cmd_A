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
    private static final int pauseState = 4;
    
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
	gameScene.hide();
	scoresScene.hide();
	creditsScene.hide();
	gameScene.pauseScene.hide();
	mainMenuScene.show();
	gameState = mainMenuState;
    }
    
    public static void showGameScene() {
	mainMenuScene.hide();
	gameScene.show();
	scoresScene.hide();
	creditsScene.hide();
	gameScene.pauseScene.hide();
	gameState = gameRunningState;
    }
    
    public static void showCreditsScene() {
	mainMenuScene.hide();
	gameScene.hide();
	scoresScene.hide();
	creditsScene.show();
	gameScene.pauseScene.hide();
	gameState = creditsShowState;
    }
    
    public static void showScoresScene() {
	mainMenuScene.hide();
	gameScene.hide();
	scoresScene.show();
	creditsScene.hide();
	gameScene.pauseScene.hide();
	gameState = creditsShowState;
    }
    
    public static void showInGamePause() {
	mainMenuScene.hide();
	gameScene.setIgnoreUpdate(false);
	scoresScene.hide();
	creditsScene.hide();
	gameScene.pauseScene.show();
	gameState = pauseState;
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
	case pauseState:
	    gameScene.pauseScene.onSceneTouchEvent(pSceneTouchEvent);
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
	  showInGamePause();
	  break;
	case creditsShowState:
	  showMainMenuScene();
	  break;
	case scoresShowState:
	  showMainMenuScene();
	  break;
	case pauseState:
	  showMainMenuScene();
	  break;
	}
    }
}
