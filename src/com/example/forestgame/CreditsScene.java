package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.HorizontalAlign;

public class CreditsScene extends Scene {
    
    private static final float CAPTIONS_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 1 / 6;
    private static final float CAPTIONS_POSITION_UP = MainActivity.TEXTURE_HEIGHT / 11;
    private static final float DEV_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 119 / 1024;
    private static final float DEV_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 10 / 64;
    private static final AlphaModifier BACKGROUND_ALPHA_MODIFIER = new AlphaModifier(0.55f, 0.99f, 0.5f);
    private static final AlphaModifier CREDITS_ALPHA_MODIFIER = new AlphaModifier(0.95f, 0.0f, 1.0f);
    
    
    
    private Sprite background = new Sprite(	0
            				, 0
            				, MainActivity.TEXTURE_WIDTH
            				, MainActivity.TEXTURE_HEIGHT
            				, MainActivity.mainActivity.textureBackground
            				, MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Text captions = new Text(	CAPTIONS_POSITION_LEFT
					, CAPTIONS_POSITION_UP
					, MainActivity.mainActivity.tCaptions
					, "Developers:\n\n\n\n\n\n\n\n"
					+ "Music by:\n\n\n" 
					+ "Special thanks to:"
					, MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Text devNames = new Text(	DEV_POSITION_LEFT
					, DEV_POSITION_UP
					, MainActivity.mainActivity.tDevNames
					, "Buvaylik Sergey\n" +
					  "Cherkasov Alexander\n" + 
					  "Kolesnichenko Pavel\n" +
					  "Kuznetsov Mixail\n" +
					  "Shadrin Sergey\n" +
					  "Sivulskiy Sergey\n\n\n" +
					  "Kirill Real-K\n\n\n" +
					  "Igor & Ivan"
					, MainActivity.mainActivity.getVertexBufferObjectManager());
    
   
    public CreditsScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	attachChild(background);
	background.registerEntityModifier(BACKGROUND_ALPHA_MODIFIER.deepCopy());
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	attachChild(devNames);
	attachChild(captions);
	devNames.setHorizontalAlign(HorizontalAlign.CENTER);
	captions.setHorizontalAlign(HorizontalAlign.CENTER);
	devNames.registerEntityModifier(CREDITS_ALPHA_MODIFIER.deepCopy());
	captions.registerEntityModifier(CREDITS_ALPHA_MODIFIER.deepCopy());
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
	background.registerEntityModifier(BACKGROUND_ALPHA_MODIFIER.deepCopy());
   	devNames.registerEntityModifier(CREDITS_ALPHA_MODIFIER.deepCopy());
   	captions.registerEntityModifier(CREDITS_ALPHA_MODIFIER.deepCopy());
    }
    
    public void hide() {
	
   	background.setAlpha(1.0f);
   	devNames.setAlpha(0.0f);
   	captions.setAlpha(0.0f);
   	setVisible(false);
   	setIgnoreUpdate(true);
    }
}
