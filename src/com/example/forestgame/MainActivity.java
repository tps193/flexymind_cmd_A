package com.example.forestgame;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import android.util.DisplayMetrics;
import android.util.Log;

public class MainActivity extends SimpleBaseGameActivity {

    private static int CAMERA_WIDTH;
    private static int CAMERA_HEIGHT;
    private static Camera camera;
    private static Scene mainScene;
    private TextureRegion textureBackground;
    private TextureRegion textureTitle;
    private TextureRegion texturePlay;
    private TextureRegion textureScores;
    private TextureRegion textureCredits;
    private TextureRegion textureExit;
    private BitmapTextureAtlas backgroundTexture;
    private BitmapTextureAtlas titleTexture;
    private BitmapTextureAtlas playTexture;
    private BitmapTextureAtlas scoresTexture;
    private BitmapTextureAtlas creditsTexture;
    private BitmapTextureAtlas exitTexture;

    @Override
    public EngineOptions onCreateEngineOptions() {
	setScreenResolutionRatio();
	camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	final EngineOptions options = new EngineOptions(true,
		ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(
			CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	return options;
    }

    @Override
    protected void onCreateResources() {
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("main_menu/");

	backgroundTexture = new BitmapTextureAtlas(new TextureManager(), 800,
		1280, TextureOptions.DEFAULT);
	titleTexture = new BitmapTextureAtlas(new TextureManager(), 542, 273,
		TextureOptions.DEFAULT);
	playTexture = new BitmapTextureAtlas(new TextureManager(), 542, 273,
		TextureOptions.DEFAULT);
	scoresTexture = new BitmapTextureAtlas(new TextureManager(), 542, 273,
		TextureOptions.DEFAULT);
	creditsTexture = new BitmapTextureAtlas(new TextureManager(), 542, 273,
		TextureOptions.DEFAULT);
	exitTexture = new BitmapTextureAtlas(new TextureManager(), 542, 273,
		TextureOptions.DEFAULT);

	textureBackground = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.backgroundTexture, this,
			"background.jpg", 0, 0);
	textureTitle = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
		this.titleTexture, this, "main_menu_title.png", 0, 0);
	texturePlay = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
		this.playTexture, this, "menu_play.png", 0, 0);
	textureScores = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
		this.scoresTexture, this, "menu_scores.png", 0, 0);
	textureCredits = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.creditsTexture, this, "menu_credits.png",
			0, 0);
	textureExit = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
		this.exitTexture, this, "menu_exit.png", 0, 0);

	this.mEngine.getTextureManager().loadTexture(this.backgroundTexture);
	this.mEngine.getTextureManager().loadTexture(this.titleTexture);
	this.mEngine.getTextureManager().loadTexture(this.playTexture);
	this.mEngine.getTextureManager().loadTexture(this.scoresTexture);
	this.mEngine.getTextureManager().loadTexture(this.creditsTexture);
	this.mEngine.getTextureManager().loadTexture(this.exitTexture);
    }

    @Override
    protected Scene onCreateScene() {
	mainScene = new Scene();
	Sprite sprite = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT,
		textureBackground, new VertexBufferObjectManager());
	SpriteBackground spriteBackground = new SpriteBackground(sprite);

	Sprite Title = new Sprite(CAMERA_WIDTH / 8, CAMERA_HEIGHT / 16,
		CAMERA_WIDTH * 6 / 8, CAMERA_HEIGHT / 4, textureTitle,
		new VertexBufferObjectManager());

	Sprite ButtonPlay = new Sprite(CAMERA_WIDTH / 4,
		CAMERA_HEIGHT * 47 / 128, CAMERA_WIDTH * 2 / 4,
		CAMERA_HEIGHT * 15 / 128, texturePlay,
		this.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonPlay", "touch");
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonPlay", "no touch");
		}
		return true;
	    }
	};
	Sprite ButtonScores = new Sprite(CAMERA_WIDTH / 4,
		CAMERA_HEIGHT * 64 / 128, CAMERA_WIDTH * 2 / 4,
		CAMERA_HEIGHT * 15 / 128, textureScores,
		this.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonScores", "touch");
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonScores", "no touch");
		}
		return true;
	    }
	};

	Sprite ButtonCredits = new Sprite(CAMERA_WIDTH / 4,
		CAMERA_HEIGHT * 81 / 128, CAMERA_WIDTH * 2 / 4,
		CAMERA_HEIGHT * 15 / 128, textureCredits,
		this.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonCredits", "touch");
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonCredits", "no touch");
		}
		return true;
	    }
	};

	Sprite ButtonExit = new Sprite(CAMERA_WIDTH / 4,
		CAMERA_HEIGHT * 98 / 128, CAMERA_WIDTH * 2 / 4,
		CAMERA_HEIGHT * 15 / 128, textureExit,
		this.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonExit", "touch");
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonExit", "no touch");
		}
		return true;
	    }
	};
	mainScene.setBackground(spriteBackground);
	MainActivity.mainScene.attachChild(Title);
	MainActivity.mainScene.attachChild(ButtonPlay);
	MainActivity.mainScene.attachChild(ButtonScores);
	MainActivity.mainScene.attachChild(ButtonCredits);
	MainActivity.mainScene.attachChild(ButtonExit);
	MainActivity.mainScene.registerTouchArea(ButtonPlay);
	MainActivity.mainScene.registerTouchArea(ButtonScores);
	MainActivity.mainScene.registerTouchArea(ButtonCredits);
	MainActivity.mainScene.registerTouchArea(ButtonExit);

	return mainScene;
    }

    private void setScreenResolutionRatio() {
	DisplayMetrics dm = new DisplayMetrics();
	getWindowManager().getDefaultDisplay().getMetrics(dm);
	CAMERA_HEIGHT = dm.heightPixels;
	CAMERA_WIDTH = dm.widthPixels;

    }
}