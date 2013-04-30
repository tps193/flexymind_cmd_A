package com.example.forestgame;

import org.andengine.entity.scene.CameraScene;

public class GameScene extends CameraScene {

    public GameScene() {
	super(MainActivity.camera);
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
