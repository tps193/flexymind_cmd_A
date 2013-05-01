package com.example.forestgame;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.util.DisplayMetrics;
import android.view.KeyEvent;

import com.example.forestgame.altasstorage.AtlasStorage;

public class MainActivity extends SimpleBaseGameActivity {

    public static int CAMERA_WIDTH = 800;
    public static int CAMERA_HEIGHT = 1280;
    
    public static Camera camera;  //made public modifier camera so that it can be accessed from GameScene
    
    private boolean gameLoaded = false; // flag of game loading state
    
    public static MainActivity mainActivity;
    
    private static MainScene mainScene;
    public TextureRegion textureBackground;
    public TextureRegion textureTitle;
    public TextureRegion texturePlay;
    public TextureRegion textureScores;
    public TextureRegion textureCredits;
    public TextureRegion textureExit;
    private AtlasStorage storage;

    @Override
    public EngineOptions onCreateEngineOptions() {
	
	mainActivity = this;
	
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

    @Override
    protected Scene onCreateScene() {
	    mainScene = new MainScene();
	    gameLoaded = true;
	    return mainScene;
	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
	
	if(keyCode == KeyEvent.KEYCODE_BACK) 
	{
	  if(!gameLoaded) return true;
	  if(mainScene != null && gameLoaded) 
	  {
	      mainScene.keyPressed(keyCode, event);
	      return true;
	  }
	  return true;
	}
	return super.onKeyDown(keyCode, event);
    }
    
    @Override
    protected void onDestroy() 
    {
	super.onDestroy();
	android.os.Process.killProcess(android.os.Process.myPid());
    }
	
}