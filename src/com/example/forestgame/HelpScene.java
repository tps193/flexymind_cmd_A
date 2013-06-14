package com.example.forestgame;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;

import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;

public class HelpScene extends Scene {
    
    private static final float ARROW_LEFT_POSITION_LEFT = MainActivity.DISPLAY_WIDTH * 10 / 2000;
    private static final float ARROW_LEFT_POSITION_UP = MainActivity.DISPLAY_HEIGHT * 900 / 2000;
    private static final float ARROW_LEFT_WIDTH = MainActivity.DISPLAY_WIDTH * 100 / 2000;
    private static final float ARROW_LEFT_HEIGHT = MainActivity.DISPLAY_HEIGHT * 200 / 2000;
    
    private static final float ARROW_RIGHT_POSITION_LEFT = MainActivity.DISPLAY_WIDTH * 1890 / 2000;
    private static final float ARROW_RIGHT_POSITION_UP = MainActivity.DISPLAY_HEIGHT * 900 / 2000;
    private static final float ARROW_RIGHT_WIDTH = MainActivity.DISPLAY_WIDTH * 100 / 2000;
    private static final float ARROW_RIGHT_HEIGHT = MainActivity.DISPLAY_HEIGHT * 200 / 2000;
    
    private static final float ARROW_UP_POSITION_LEFT = MainActivity.DISPLAY_WIDTH * 900 / 2000;
    private static final float ARROW_UP_POSITION_UP = MainActivity.DISPLAY_HEIGHT * 10 / 2000;
    private static final float ARROW_UP_WIDTH = MainActivity.DISPLAY_WIDTH * 400 / 2000;
    private static final float ARROW_UP_HEIGHT = MainActivity.DISPLAY_HEIGHT * 50 / 2000;
    
    private static final float ARROW_DOWN_POSITION_LEFT = MainActivity.DISPLAY_WIDTH * 900 / 2000;
    private static final float ARROW_DOWN_POSITION_UP = MainActivity.DISPLAY_HEIGHT * 1940 / 2000;
    private static final float ARROW_DOWN_WIDTH = MainActivity.DISPLAY_WIDTH * 400 / 2000;
    private static final float ARROW_DOWN_HEIGHT = MainActivity.DISPLAY_HEIGHT * 50 / 2000;

    private static final float RECTANGLE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 200 / 2000;
    private static final float RECTANGLE_POSITION_UP = MainActivity.TEXTURE_WIDTH * 200 / 2000;
    private static final float RECTANGLE_WIDTH = MainActivity.TEXTURE_WIDTH * 1600 / 2000;
    private static final float RECTANGLE_HEIGHT = MainActivity.TEXTURE_WIDTH * 500 / 2000;
    
    private static final float RECTANGLES_INTERVAL = RECTANGLE_HEIGHT + MainActivity.TEXTURE_WIDTH * 20 / 2000;
    
    private static final float ARROW_POSITION_LEFT = RECTANGLE_POSITION_LEFT + MainActivity.TEXTURE_HEIGHT * 526 /2000;
    private static final float ARROW_POSITION_UP = RECTANGLE_POSITION_UP + MainActivity.TEXTURE_HEIGHT * 100 /2000;
    private static final float ARROW_WIDTH = MainActivity.TEXTURE_WIDTH * 256 / 2000;
    private static final float ARROW_HEIGHT = MainActivity.TEXTURE_HEIGHT * 100 / 2000;
    
    private static final float ELEMENT_MARGIN_LEFT = MainActivity.TEXTURE_WIDTH * 80 / 2000;
    private static final float ELEMENT_MARGIN_TOP = MainActivity.TEXTURE_HEIGHT * 50 / 2000;
    
    private static final float ELEMENT_WIDTH = MainActivity.TEXTURE_WIDTH * 325 / 2000;
    private static final float ELEMENT_HEIGHT = MainActivity.TEXTURE_HEIGHT * 200 / 2000;    
    
    private static final float SECOND_ELEMENT_DELTA_X = MainActivity.TEXTURE_WIDTH * 200 / 2000;
    private static final float SECOND_ELEMENT_DELTA_Y = MainActivity.TEXTURE_WIDTH * 100 / 2000;
    
    private static final float THIRD_ELEMENT_DELTA_X = MainActivity.TEXTURE_WIDTH * 200 / 2000;
    private static final float THIRD_ELEMENT_DELTA_Y = MainActivity.TEXTURE_WIDTH * (-200) / 2000;
    
    private static final float FIRST_ELEMENT_LEFT = RECTANGLE_POSITION_LEFT + ELEMENT_MARGIN_LEFT;
    private static final float FIRST_ELEMENT_UP = RECTANGLE_POSITION_UP + ELEMENT_MARGIN_TOP;
    
    private static final float SECOND_ELEMENT_LEFT = FIRST_ELEMENT_LEFT + SECOND_ELEMENT_DELTA_X;
    private static final float SECOND_ELEMENT_UP = FIRST_ELEMENT_UP + SECOND_ELEMENT_DELTA_Y;
    
    private static final float THIRD_ELEMENT_LEFT = SECOND_ELEMENT_LEFT + THIRD_ELEMENT_DELTA_X;
    private static final float THIRD_ELEMENT_UP = SECOND_ELEMENT_UP + THIRD_ELEMENT_DELTA_Y;
    
    private static final float NEXT_ELEMENT_LEFT = RECTANGLE_POSITION_LEFT + MainActivity.TEXTURE_HEIGHT * 716 /2000;
    private static final float NEXT_ELEMENT_UP = RECTANGLE_POSITION_UP + MainActivity.TEXTURE_HEIGHT * 50 /2000;
    
    
    /////////////
    private static final float ARROW_DELTA_LEFT = MainActivity.TEXTURE_HEIGHT * 526 /2000;
    private static final float ARROW_DELTA_UP = MainActivity.TEXTURE_HEIGHT * 100 /2000;
    
    private static final float SECOND_DELTA_LEFT = ELEMENT_MARGIN_LEFT + SECOND_ELEMENT_DELTA_X;
    private static final float SECOND_DELTA_UP = ELEMENT_MARGIN_TOP + SECOND_ELEMENT_DELTA_Y;
    
    private static final float THIRD_DELTA_LEFT = SECOND_DELTA_LEFT + THIRD_ELEMENT_DELTA_X;
    private static final float THIRD_DELTA_UP = SECOND_DELTA_UP + THIRD_ELEMENT_DELTA_Y;
    
    private static final float NEXT_DELTA_LEFT = MainActivity.TEXTURE_HEIGHT * 716 /2000;
    private static final float NEXT_DELTA_UP = MainActivity.TEXTURE_HEIGHT * 50 /2000;
    ////////////
    private static final int CARD_ON_LIST = 5;
    private static final int LIST_COUNT[] = {1, 0, 0, 0};
    private static final int ROOT_COUNT = 3;
    private static final int SPECIAL_ROOT = 3;
    private int listNumber = 0;
    private int rootNumber = 0;
    private Element el;
    private static final String FIRST_ELEMENT_OF_ROOT[] = { "GRASS"
    							  , "HUT"
    							  , "POND"
    };
    
    ////////////
    
    private Sprite helpSprite;
    
    private Sprite background = new Sprite( 0
            				, 0
            				, MainActivity.TEXTURE_WIDTH
            				, MainActivity.TEXTURE_HEIGHT
            				, MainActivity.mainActivity.textureBackground
            				, MainActivity.mainActivity.getVertexBufferObjectManager());
    
    public HelpScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	attachChild(background);
	background.registerEntityModifier(MainActivity.SHOW_ALPHA_MODIFIER.deepCopy());
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
    }
    
    public void show() {
	
	setVisible(true);
	setIgnoreUpdate(false);
	setVisible(true);
	setIgnoreUpdate(false);	
	background.registerEntityModifier(MainActivity.SHOW_ALPHA_MODIFIER.deepCopy());
    }
    
    public void hide() {
	
	setVisible(false);
	setIgnoreUpdate(true);
   	setVisible(false);
   	setIgnoreUpdate(true);
   	background.setAlpha(1.0f);
    }
    
    private void buildHelpSprite() {
	
	for(int i = 0; i < HELP_ATLAS_COUNT; i++) {
	    
	    /*BuildableBitmapTextureAtlas */atlasArray[i] = new BuildableBitmapTextureAtlas(MainActivity.mainActivity.getTextureManager()
		   							      , 1024
		   							      , 2048
		   							      , BitmapTextureFormat.RGBA_8888
		   							      , TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    
	    helpSpriteArray[i] = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset( atlas
				, MainActivity.mainActivity
				, "helpSpritePage" + i);
	    
	    atlasArray[i].build();
	}
    }
    
    private void loadHelpSprite() {
	
	for(int i = 0; i < HELP_ATLAS_COUNT; i++) {
	    
	    atlasArray[i].load();
	}
    }
    
    private void unloadHelpSprite() {
	
	for(int i = 0; i < HELP_ATLAS_COUNT; i++) {
	    
	    atlasArray[i].unload();
	}
    }
}