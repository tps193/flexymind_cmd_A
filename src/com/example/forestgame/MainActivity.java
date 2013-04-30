package com.example.forestgame;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import com.example.forestgame.altasstorage.AtlasStorage;

public class MainActivity extends SimpleBaseGameActivity {

    private static int CAMERA_WIDTH = 800;
    private static int CAMERA_HEIGHT = 1280;
    
    public static Camera camera;  //made public modifier camera so that it can be accessed from GameScene
    private static GameScene gameScene = new GameScene(); // init of gameScene
    private static int gameState; // constant to hold the game states
    // the actual states of the game
    private static final int mainMenuState = 0; 
    private static final int gameRunningState = 1;
    
    private boolean gameLoaded = false; // flag of game loading state
    
    private static Scene mainScene;
    private TextureRegion textureBackground;
    private TextureRegion textureTitle;
    private TextureRegion texturePlay;
    private TextureRegion textureScores;
    private TextureRegion textureCredits;
    private TextureRegion textureExit;
    private AtlasStorage storage;

    @Override
    public EngineOptions onCreateEngineOptions() {
	
	DisplayMetrics dm = new DisplayMetrics();
	getWindowManager().getDefaultDisplay().getMetrics(dm);
	
	camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	
	final EngineOptions options = new EngineOptions(true,
		ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	return options;
    }

    @Override
    protected void onCreateResources() {
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("main_menu/");
	
	storage = new AtlasStorage();
	storage.createAtlas(this.getTextureManager(), this, "main_menu/", "main_menu_title.png", "menu_play.png"
		, "menu_play_light.png", "menu_scores.png", "menu_scores_light.png", "menu_credits.png"
		, "menu_credits_light.png", "menu_exit.png", "menu_exit_light.png");
	
	
	textureTitle = storage.getTexture("main_menu_title.png");
	texturePlay = storage.getTexture("menu_play.png");
	textureScores = storage.getTexture("menu_scores.png");
	textureCredits = storage.getTexture("menu_credits.png");
	textureExit = storage.getTexture("menu_exit.png");
	storage.createAtlas(this.getTextureManager(), this, "main_menu/", "background.jpg");
	textureBackground = storage.getTexture("background.jpg");
    }

   
    // showing gameScene and hiding mainScene
    public static void showGameScene() {
	gameScene.show();
	mainScene.setVisible(false);
	mainScene.setIgnoreUpdate(true);
	gameState = gameRunningState;
    }
    // showing mainScene and hiding gameScene
    public static void showMainScene() 
    {
	gameScene.hide();
	mainScene.setVisible(true);
	mainScene.setIgnoreUpdate(false);
	gameState = mainMenuState;
    }
    
    // check boot scene
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
	
	if(keyCode == KeyEvent.KEYCODE_BACK) 
	{
	  if(!gameLoaded) return true;
	  if(mainScene != null && gameLoaded) 
	  {
	      keyPressed(keyCode, event);
	      return true;
	  }
	  return true;
	}
	return super.onKeyDown(keyCode, event);
    }
    
    private void keyPressed(int keyCode, KeyEvent event) {
	switch(gameState) 
	{
	case mainMenuState:
	  this.onDestroy();
	  break;
	case gameRunningState:
	    showMainScene();
	    break;
	}
    }
    
    @Override
    protected void onDestroy() 
    {
	super.onDestroy();
	android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected Scene onCreateScene() {
	this.mEngine.registerUpdateHandler(new FPSLogger());
	mainScene = new Scene();
	gameLoaded = true; //changing flag when scene is loaded
	
	Sprite sprite = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT,
		textureBackground, new VertexBufferObjectManager());

	Sprite Title = new Sprite(CAMERA_WIDTH / 8, CAMERA_HEIGHT / 16,
		CAMERA_WIDTH * 6 / 8, CAMERA_HEIGHT / 4, textureTitle,
		new VertexBufferObjectManager());

	Sprite ButtonPlay = new Sprite(CAMERA_WIDTH / 4,
		CAMERA_HEIGHT * 52 / 128, CAMERA_WIDTH * 2 / 4,
		CAMERA_HEIGHT * 12 / 128, texturePlay,
		this.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonPlay", "touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 1.0f, 0.5f));
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonPlay", "no touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 0.5f, 1.0f));
		    showGameScene();
		}
		return true;
	    }
	    
	};
	
	Sprite ButtonScores = new Sprite(CAMERA_WIDTH / 4,
		CAMERA_HEIGHT * 69 / 128, CAMERA_WIDTH * 2 / 4,
		CAMERA_HEIGHT * 12 / 128, textureScores,
		this.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonScores", "touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 1.0f, 0.5f));
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonScores", "no touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 0.5f, 1.0f));
		}
		return true;
	    }
	};

	Sprite ButtonCredits = new Sprite(CAMERA_WIDTH / 4,
		CAMERA_HEIGHT * 86 / 128, CAMERA_WIDTH * 2 / 4,
		CAMERA_HEIGHT * 12 / 128, textureCredits,
		this.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonCredits", "touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 1.0f, 0.5f));
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonCredits", "no touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 0.5f, 1.0f));
		}
		return true;
	    }
	};

	Sprite ButtonExit = new Sprite(CAMERA_WIDTH / 4,
		CAMERA_HEIGHT * 103 / 128, CAMERA_WIDTH * 2 / 4,
		CAMERA_HEIGHT * 12 / 128, textureExit,
		this.getVertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		    float pTouchAreaLocalX, float pTouchAreaLocalY) {

		if (pSceneTouchEvent.isActionDown()) {
		    Log.d("ButtonExit", "touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 1.0f, 0.95f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 1.0f, 0.5f));
		} else if (pSceneTouchEvent.isActionUp()) {
		    Log.d("ButtonExit", "no touch");
		    this.registerEntityModifier(new ScaleModifier(0.001f, 0.95f, 1.0f));
		    this.registerEntityModifier(new AlphaModifier(0.001f, 0.5f, 1.0f));
		}
		return true;
	    }
	};
	
	
	
	mainScene.setBackgroundEnabled(true);
	mainScene.setBackground(new Background(Color.WHITE));
	MainActivity.mainScene.attachChild(sprite);
	
	MainActivity.mainScene.attachChild(gameScene); //adding of GameScene
	showMainScene(); // hiding the newly created gameScene
	
	sprite.attachChild(Title);
	sprite.attachChild(ButtonPlay);
	sprite.attachChild(ButtonScores);
	sprite.attachChild(ButtonCredits);
	sprite.attachChild(ButtonExit);
	MainActivity.mainScene.registerTouchArea(ButtonPlay);
	MainActivity.mainScene.registerTouchArea(ButtonScores);
	MainActivity.mainScene.registerTouchArea(ButtonCredits);
	MainActivity.mainScene.registerTouchArea(ButtonExit);
	MainActivity.mainScene.setTouchAreaBindingOnActionDownEnabled(true);
	MainActivity.mainScene.setTouchAreaBindingOnActionMoveEnabled(true);

	return mainScene;
    }
	
}