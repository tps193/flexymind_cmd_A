package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

public class CreditsScene extends Scene {
    
    private Sprite sprite = new Sprite(	0
            				, 0
            				, MainActivity.TEXTURE_WIDTH
            				, MainActivity.TEXTURE_HEIGHT
            				, MainActivity.mainActivity.textureBackground
            				, MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Text captions = new Text(	MainActivity.TEXTURE_WIDTH * 1 / 6
					, MainActivity.TEXTURE_HEIGHT / 7
					, MainActivity.mainActivity.tCaptions
					, "Developers:\n\n\n\n\n\n\n\n\n"
					+ "Special thanks to:"
					, MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Text devNames = new Text(	MainActivity.TEXTURE_WIDTH * 119 / 1024
					, MainActivity.TEXTURE_HEIGHT * 15 / 64
					, MainActivity.mainActivity.tDevNames
					, "Buvaylik Sergey\n" +
					  "Cherkasov Alexander\n" + 
					  "Kolesnichenko Pavel\n" +
					  "Kuznetsov Mixail\n" +
					  "Shadrin Sergey\n" +
					  "Sivulskiy Sergey\n\n\n\n" +
					  "Igor & Ivan"
					, MainActivity.mainActivity.getVertexBufferObjectManager());
    
   
    public CreditsScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(new Color(0.10f, 0.10f, 0.0f)));
	attachChild(sprite);
	sprite.registerEntityModifier(new AlphaModifier(0.55f, 0.99f, 0.5f));
	sprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	attachChild(devNames);
	attachChild(captions);
	devNames.setHorizontalAlign(HorizontalAlign.CENTER);
	captions.setHorizontalAlign(HorizontalAlign.CENTER);
	devNames.registerEntityModifier(new AlphaModifier(0.95f, 0.0f, 1.0f));
	captions.registerEntityModifier(new AlphaModifier(0.95f, 0.0f, 1.0f));
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
	sprite.registerEntityModifier(new AlphaModifier(0.55f, 1.0f, 0.5f));
   	devNames.registerEntityModifier(new AlphaModifier(0.95f, 0.0f, 1.0f));
   	captions.registerEntityModifier(new AlphaModifier(0.95f, 0.0f, 1.0f));
    }
    
    public void hide() {
	
   	sprite.setAlpha(1.0f);
   	devNames.setAlpha(0.0f);
   	captions.setAlpha(0.0f);
   	setVisible(false);
   	setIgnoreUpdate(true);
    }
}
