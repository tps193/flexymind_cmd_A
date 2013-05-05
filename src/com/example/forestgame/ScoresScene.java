package com.example.forestgame;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

public class ScoresScene extends Scene {
    
    private Sprite sprite = new Sprite( 0
            , 0
            , MainActivity.TEXTURE_WIDTH
            , MainActivity.TEXTURE_HEIGHT
            , MainActivity.mainActivity.textureBackground
            , MainActivity.mainActivity.getVertexBufferObjectManager());
    
   
    public ScoresScene() {
	setBackgroundEnabled(true);
	setBackground(new Background(Color.BLUE));
	attachChild(sprite);
	sprite.registerEntityModifier(new AlphaModifier(0.55f, 0.5f, 0.8f));
    }
    
    public void show() {
	setVisible(true);
	setIgnoreUpdate(false);
    }
    
    public void hide() {
   	setVisible(false);
   	setIgnoreUpdate(true);
   	sprite.registerEntityModifier(new AlphaModifier(0.55f, 0.5f, 0.8f));
    }
}
