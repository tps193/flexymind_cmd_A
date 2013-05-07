package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

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
	setBackground(new Background(new Color(0.10f, 0.10f, 0.0f)));
	attachChild(sprite);
	sprite.registerEntityModifier(new AlphaModifier(0.55f, 1.0f, 0.5f));
	sprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
    }
    
    public void show() {
	setVisible(true);
	setIgnoreUpdate(false);
   	sprite.registerEntityModifier(new AlphaModifier(0.55f, 1.0f, 0.5f));
    }
    
    public void hide() {
   	setVisible(false);
   	setIgnoreUpdate(true);
   	sprite.setAlpha(1.0f);
    }
}
