package com.example.forestgame;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

public class CreditsScene extends Scene {
    
    private Sprite sprite = new Sprite( 0
            , 0
            , MainActivity.TEXTURE_WIDTH
            , MainActivity.TEXTURE_HEIGHT
            , MainActivity.mainActivity.textureBackground
            , new VertexBufferObjectManager());
    
    private Text textStroke = new Text(MainActivity.TEXTURE_WIDTH / 8
		, MainActivity.TEXTURE_HEIGHT / 5
		, MainActivity.mainActivity.mStrokeFont
		, "Developers:\n" +
		  "Buvaylik Sergey\n" +
		  "Cherkasov Alexander\n" + 
		  "Kolesnichenko Pavel\n" +
		  "Kuznetsov Mixail\n" +
		  "Shadrin Sergey\n" +
		  "Sivulskiy Sergey\n\n" +
		  "Special thanks to:\n" +
		  "Igor & Ivan"
		, MainActivity.mainActivity.getVertexBufferObjectManager());
    
   
    public CreditsScene() {
	setBackgroundEnabled(true);
	setBackground(new Background(Color.BLUE));
	attachChild(sprite);
	sprite.registerEntityModifier(new AlphaModifier(0.55f, 0.5f, 0.8f));
	attachChild(textStroke);
	textStroke.setHorizontalAlign(HorizontalAlign.CENTER);
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
