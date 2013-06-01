package com.example.forestgame;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.view.KeyEvent;

public class MainScene extends Scene {
    
    public enum GameState { MAIN_MENU, GAME_RUNNING, SHOW_SCORES, SHOW_CREDITS, PAUSE, GAME_OVER, SHOW_HINTS };
    public static GameState gameState;
    
    private static MainMenuScene mainMenuScene = new MainMenuScene();
    public static GameScene gameScene = new GameScene();
    private static CreditsScene creditsScene = new CreditsScene();
    private static ScoresScene scoresScene = new ScoresScene();
    public static HelpScene helpScene = new HelpScene();
    
    public MainScene() {
	
	attachChild(mainMenuScene);
	attachChild(gameScene);
	attachChild(scoresScene);
	attachChild(creditsScene);
	attachChild(helpScene);
	MainActivity.camera.setHUD(helpScene.hud);
	showMainMenuScene();
    }
    
    public static GameScene getGameScene() {
	return gameScene;
    }
    
    public static CreditsScene getCreditsScene() {
	return creditsScene;
    }
    
    public static ScoresScene getScoresScene() {
	return scoresScene;
    }
    
    public static MainMenuScene getMainMenuScene() {
	return mainMenuScene;
    }
    
    public static HelpScene getHelpScene() {
	return helpScene;
    }
    
    public static void showMainMenuScene() {
	
	mainMenuScene.show();
	gameScene.hide();
	scoresScene.hide();
	creditsScene.hide();
	gameScene.getPauseScene().hide();
	gameScene.getGameOverScene().hide();
	helpScene.hide();
	gameState = GameState.MAIN_MENU;
    }
    
    public static void showGameScene() {
	
	mainMenuScene.hide();
	gameScene.show();
	scoresScene.hide();
	creditsScene.hide();
	gameScene.getPauseScene().hide();
	gameScene.getGameOverScene().hide();
	helpScene.hide();
	gameState = GameState.GAME_RUNNING;
    }
    
    public static void showCreditsScene() {
	
	mainMenuScene.hide();
	gameScene.hide();
	scoresScene.hide();
	creditsScene.show();
	gameScene.getPauseScene().hide();
	gameScene.getGameOverScene().hide();
	helpScene.hide();
	gameState = GameState.SHOW_CREDITS;
    }
    
    public static void showScoresScene() {
	
	mainMenuScene.hide();
	gameScene.hide();
	scoresScene.show();
	creditsScene.hide();
	gameScene.getPauseScene().hide();
	gameScene.getGameOverScene().hide();
	helpScene.hide();
	gameState = GameState.SHOW_SCORES;
    }
    
    public static void showInGamePause() {
	
	mainMenuScene.hide();
	gameScene.setIgnoreUpdate(false);
	scoresScene.hide();
	creditsScene.hide();
	gameScene.getPauseScene().show();
	gameScene.getGameOverScene().hide();
	helpScene.hide();
	gameState = GameState.PAUSE;
    }
    
    public static void showGameOverScene() {
	
	mainMenuScene.hide();
	gameScene.setIgnoreUpdate(false);
	scoresScene.hide();
	creditsScene.hide();
	gameScene.getGameOverScene().show();
	gameScene.getPauseScene().hide();
	helpScene.hide();
	gameState = GameState.GAME_OVER;
    }
    
    public static void showHelpScene() {
	  
	mainMenuScene.hide();
	gameScene.hide();
	scoresScene.hide();
	creditsScene.hide();
	helpScene.show();
	gameScene.getPauseScene().hide();
	gameScene.getGameOverScene().hide();
	gameState = GameState.SHOW_SCORES;
    }
    
    @Override
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
	
	switch(gameState) {
	
	case MAIN_MENU:
	    mainMenuScene.onSceneTouchEvent(pSceneTouchEvent);
	    break;
	    
	case GAME_RUNNING:
	    gameScene.onSceneTouchEvent(pSceneTouchEvent);
	    break;
	    
	case SHOW_CREDITS:
	    creditsScene.onSceneTouchEvent(pSceneTouchEvent);
	    break;
	    
	case SHOW_SCORES:
	    scoresScene.onSceneTouchEvent(pSceneTouchEvent);
	    break;
	    
	case PAUSE:
	    gameScene.getPauseScene().onSceneTouchEvent(pSceneTouchEvent);
	    break;
	    
	case GAME_OVER:
	    gameScene.getGameOverScene().onSceneTouchEvent(pSceneTouchEvent);
	    break;
	
	case SHOW_HINTS:
	    helpScene.onSceneTouchEvent(pSceneTouchEvent);
	    break;
	}
	return super.onSceneTouchEvent(pSceneTouchEvent);
    }
    
    public void keyPressed(int keyCode, KeyEvent event) {
	
	switch(gameState) {
	
	case MAIN_MENU:
	    MainActivity.mainActivity.onDestroy();
	    break;
	    
	case GAME_RUNNING:
	    showInGamePause();
	    break;
	    
	case SHOW_CREDITS:
	    showMainMenuScene();
	    break;
	    
	case SHOW_SCORES:
	    showMainMenuScene();
	    break;
	    
	case PAUSE:
	    showMainMenuScene();
	    break;
	    
	case GAME_OVER:
	    showMainMenuScene();
	    break;
	
	case SHOW_HINTS:
	    showMainMenuScene();
	    break;
	}
    }
}
