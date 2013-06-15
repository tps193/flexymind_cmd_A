package com.example.forestgame;

import javax.microedition.khronos.opengles.GL10;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.TextureRegion;



public class HelpScene extends Scene {
    
    private static final int HELP_ATLAS_COUNT = 7;
    private static final float HELP_SPRITE_WIDTH = MainActivity.TEXTURE_WIDTH;
    private static final float HELP_SPRITE_HEIGHT = MainActivity.TEXTURE_HEIGHT;
    private int currentPage;
    private float firstTouchPointX;
    private float touchPointX;
       
    
   
    private Sprite helpSprite[]=new Sprite[HELP_ATLAS_COUNT];
    private BuildableBitmapTextureAtlas atlasArray[]=new BuildableBitmapTextureAtlas[HELP_ATLAS_COUNT];

    private TextureRegion helpSpriteArray;
    
   
    private Sprite background = new Sprite( 0
            				, 0
            				, HELP_SPRITE_WIDTH
            				, HELP_SPRITE_HEIGHT
            				, MainActivity.mainActivity.textureBackground
            				, MainActivity.mainActivity.getVertexBufferObjectManager());
    
    
    public HelpScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	attachChild(background);
	background.registerEntityModifier(MainActivity.SHOW_ALPHA_MODIFIER.deepCopy());
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	this.buildHelpSprite();
	
    }
    
    public void show() {
	
	currentPage = 0;
	showSpritesAt(0);
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
	currentPage = 0;
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("help_scene/");
	for(int i = 0; i < HELP_ATLAS_COUNT; i++) {
	    
	    
	    atlasArray[i] = new BuildableBitmapTextureAtlas(MainActivity.mainActivity.getTextureManager()
		   							      , 512
		   							      , 1024
		   							      , BitmapTextureFormat.RGBA_8888
		   							      , TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    
	    helpSpriteArray = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset( atlasArray[i]
				, MainActivity.mainActivity
				, "helpSprite" + i + ".png");
	   
	 
	    helpSprite[i] = new Sprite( (i-currentPage)*HELP_SPRITE_WIDTH
		     			, 0
		     			, HELP_SPRITE_WIDTH
		     			, HELP_SPRITE_HEIGHT
		     			, helpSpriteArray
		     			, MainActivity.mainActivity.getVertexBufferObjectManager()) {
		
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent
	    		     			, float pTouchAreaLocalX
	    		     			, float pTouchAreaLocalY) {

		    if (pSceneTouchEvent.isActionDown()) {
	
			helpSpriteIsActionDown();
	    
		    } else if (pSceneTouchEvent.isActionUp()) {
	
			helpSpriteIsActionUp();
	
		    } else if (pSceneTouchEvent.isActionMove()) {
	    
			helpSpriteIsActionMove(pSceneTouchEvent);
	
		    }
		    return true;
		}
	    };
	    try {
	    	atlasArray[i].build( new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource
	    				, BitmapTextureAtlas>(0, 1, 1));
	 	} catch (TextureAtlasBuilderException e) {
	 	    // TODO Auto-generated catch block
	 	    e.printStackTrace();
	 	}   			    	
	    
	    this.attachChild(helpSprite[i]);
	    this.registerTouchArea(helpSprite[i]);
	
   
	}
    } 
    
    
   
    protected void helpSpriteIsActionMove(TouchEvent pSceneTouchEvent) {
	
	touchPointX = pSceneTouchEvent.getX();
	if (firstTouchPointX < 0) {
	    
	    firstTouchPointX = touchPointX;
	}
	
	float deltaX = touchPointX - firstTouchPointX;
	if ((deltaX < 0) && (currentPage != HELP_ATLAS_COUNT-1) 
		|| ((deltaX > 0) && (currentPage != 0))) {
	    
		showSpritesAt(deltaX);

	}
	
	
    }

    protected void helpSpriteIsActionUp() {
	
	float deltaX = touchPointX - firstTouchPointX;
	if ((deltaX < 0) && (currentPage != HELP_ATLAS_COUNT-1) 
		|| ((deltaX > 0) && (currentPage != 0))) {
	    
	    	if (deltaX > 0) {
		    
		    currentPage--;
		} else {
		    
		    currentPage++;
		}
	}
	showSpritesAt(0);
    }

    protected void helpSpriteIsActionDown() {
	
	firstTouchPointX = -1;
	
    }

    public void loadHelpSprite() {
	
	for(int i = 0; i < HELP_ATLAS_COUNT; i++) {

	    atlasArray[i].load();
	}
    }
    
     public void unloadHelpSprite() {
	
	for(int i = 0; i < HELP_ATLAS_COUNT; i++) {
	    
	    atlasArray[i].unload();
	}
    }
     
    private void showSpritesAt(float touchPX) {
	
	for (int i = 0; i < helpSprite.length; i++) {
	    
	    Sprite sprite = helpSprite[i];
	    sprite.setPosition((i-currentPage)*HELP_SPRITE_WIDTH + touchPX, 0);
	}
    }
}