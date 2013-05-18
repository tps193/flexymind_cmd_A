package com.example.forestgame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import com.example.forestgame.altasstorage.AtlasStorage;

public class MainActivity extends SimpleBaseGameActivity {

    public static int CAMERA_WIDTH;
    public static int CAMERA_HEIGHT;
    private static float DISPLAY_WIDTH;
    private static float DISPLAY_HEIGHT;
    public final static float TEXTURE_WIDTH = 1250;
    public final static float TEXTURE_HEIGHT = 2000;
    private float RATIO_WIDTH;
    private float RATIO_HEIGHT;
    public float ZM;  //zoom factor
    public static final Color BACKGROUND_COLOR = new Color(0.1f, 0.1f, 0.0f);
    public static final AlphaModifier SHOW_ALPHA_MODIFIER = new AlphaModifier(0.55f, 1.0f, 0.5f);
    public static final AlphaModifier HIDE_ALPHA_MODIFIER = new AlphaModifier(0.55f, 0.5f, 1.0f);
    public static final AlphaModifier TOUCH_ALPHA_MODIFIER = new AlphaModifier(0.001f, 1.0f, 0.5f);
    public static final AlphaModifier UNTOUCH_ALPHA_MODIFIER = new AlphaModifier(0.001f, 0.5f, 1.0f);
    public static final ScaleModifier TOUCH_SCALE_MODIFIER = new ScaleModifier(0.001f, 1.0f, 0.95f);
    public static final ScaleModifier UNTOUCH_SCALE_MODIFIER = new ScaleModifier(0.001f, 0.95f, 1.0f);
    public static final AlphaModifier PAUSE_ALPHA_MODIFIER = new AlphaModifier(0.55f, 0.8f, 0.5f);
    
    
    public static ZoomCamera camera;  //made public modifier camera so that it can be accessed from GameScene
    
    private boolean gameLoaded = false; // flag of game loading state
    
    public static boolean isMute = false;
    
    public static MainActivity mainActivity;
    
    private static MainScene mainScene;
    
    public TextureRegion textureCage;
    
    public TextureRegion textureBackground;
    public TextureRegion textureSlots;
    public TextureRegion textureTitle;
    public TextureRegion texturePlay;
    public TextureRegion textureResume;
    public TextureRegion textureScores;
    public TextureRegion textureCredits;
    public TextureRegion textureExit;
    public TextureRegion texturePauseIcon;
    public TextureRegion textureMuteOff;
    public TextureRegion textureMuteOn;
    public AtlasStorage storage;
    public StrokeFont tDevNames;
    public StrokeFont tCaptions;
    private static ITexture creditsCaps;
    private static ITexture creditsNames;
    private static ITexture scoresAtlas;
    public StrokeFont tPause;
    public StrokeFont tResume;
    private static ITexture pauseLabel;
    private static ITexture pauseResume;
    public StrokeFont tGameOver;
    public StrokeFont tMainMenu;
    public StrokeFont tNewGame;
    public StrokeFont tScores;
    private static ITexture GameOver;
    private static ITexture MainMenu;
    private static ITexture NewGame;
    
    public Music mMusic;
    public Sound mSound;
    public Sound mClick;
    public Sound mGameOver;
    public Sound mGameStart;
    public Sound mStep;
    
    private String[][] namesMatrix;
    private String prisonName;
    private String respawnName;
    

    @Override
    public EngineOptions onCreateEngineOptions() {
	
	mainActivity = this;
	
	DisplayMetrics dm = new DisplayMetrics();
	getWindowManager().getDefaultDisplay().getMetrics(dm);
	
	DISPLAY_WIDTH = dm.widthPixels;
	DISPLAY_HEIGHT = dm.heightPixels;
	
	CAMERA_WIDTH = (int) (DISPLAY_WIDTH);
	CAMERA_HEIGHT = (int) (DISPLAY_HEIGHT);
	
	RATIO_WIDTH = TEXTURE_WIDTH / DISPLAY_WIDTH;
	RATIO_HEIGHT = TEXTURE_HEIGHT / DISPLAY_HEIGHT;
	
	if (RATIO_HEIGHT < RATIO_WIDTH) {
	    ZM = 1 / RATIO_HEIGHT;
	} else if (RATIO_HEIGHT > RATIO_WIDTH) {
	    ZM = 1 / RATIO_WIDTH;
	} else {
	    ZM = 1;
	}
	
	camera = new ZoomCamera( 0
			       , 0
			       , CAMERA_WIDTH
			       , CAMERA_HEIGHT);
	//mHUD = new HUD();
	//camera.setHUD(mHUD);
	camera.setCenter( TEXTURE_WIDTH / 2
			, TEXTURE_HEIGHT / 2);
	
	camera.setZoomFactor(ZM * 1f);
	final EngineOptions options = new EngineOptions( true
						       , ScreenOrientation.PORTRAIT_FIXED
						       , new RatioResolutionPolicy( CAMERA_WIDTH
							                          , CAMERA_HEIGHT)
						       , camera);
	int interval = 2;
        options.getTouchOptions().setTouchEventIntervalMilliseconds(interval);
        options.getAudioOptions().setNeedsMusic(true);
        options.getAudioOptions().setNeedsSound(true);
	return options;
    }

    @Override
    protected void onCreateResources() {
	
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("main_menu/");
	
	storage = new AtlasStorage();
	storage.createAtlas( this.getTextureManager()
			   , this, "main_menu/"
			   , "main_menu_title.png"
			   , "menu_play.png"
			   , "menu_resume.png"
			   , "menu_scores.png"
			   , "menu_credits.png"
			   , "menu_credits_light.png"
			   , "menu_exit.png"
			   , "menu_exit_light.png"
			   , "mute_on.png"
			   , "mute_off.png");

	textureTitle = storage.getTexture("main_menu_title.png");
	texturePlay = storage.getTexture("menu_play.png");
	textureResume = storage.getTexture("menu_resume.png");
	textureScores = storage.getTexture("menu_scores.png");
	textureCredits = storage.getTexture("menu_credits.png");
	textureExit = storage.getTexture("menu_exit.png");
	textureMuteOn = storage.getTexture("mute_on.png");
	textureMuteOff = storage.getTexture("mute_off.png");
	
	storage.createAtlas( this.getTextureManager()
	           	   , this
	           	   , "main_menu/"
	           	   , "background.jpg");
	textureBackground = storage.getTexture("background.jpg");
	
	storage.createAtlas( this.getTextureManager()
		 	   , this
		 	   , "game_scene_gfx/"
		 	   , "gfx_slots.png");
        textureSlots = storage.getTexture("gfx_slots.png");
	
	storage.createAtlas( this.getTextureManager()
			   , this
			   , "game_scene_gfx/"
			   , "gfx_crown.png"
			   , "gfx_golden_nut.png"
			   , "gfx_nut.png"
			   , "gfx_grass.png"
			   , "gfx_tree.png"
			   , "gfx_nuts_king.png"
			   , "gfx_squirrel.png"
			   , "gfx_cage.png"
			   , "gfx_empty.png"
			   , "gfx_pause_icon.png");
	
	textureCage = storage.getTexture("gfx_cage.png");
	texturePauseIcon = storage.getTexture("gfx_pause_icon.png");
	MusicFactory.setAssetBasePath("sounds/");
	
        try {
                mMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "main_menu.mp3");
                mMusic.setLooping(true);
        } catch (final IOException e) {
                Debug.e("Error", e);
        }
        
        SoundFactory.setAssetBasePath("sounds/");
        
        try {
    		mSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "convolution.mp3");
    		mClick = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "click.wav");
    		mGameOver = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "game_over.mp3");
    		mGameStart = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "game_start.mp3");
    		mStep = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "general_step.wav");
        } catch (final IOException e) {
    		Debug.e("Error", e);
        }	
	
	creditsCaps = new BitmapTextureAtlas(	this.getTextureManager()
						, 2048
						, 1024
						, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	
	creditsNames = new BitmapTextureAtlas(	this.getTextureManager()
						, 2048
						, 2048
						, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	
	scoresAtlas = new BitmapTextureAtlas(	this.getTextureManager()
						, 2048
						, 1024
						, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	
	tCaptions = new StrokeFont(this.getFontManager()
				   , creditsCaps
				   , Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
				   , 100
				   , true
				   , new Color(1.0f, 0.6f, 0.0f)
				   , 2
				   , new Color(1.0f, 0.2f, 0.0f));
	
	tScores = new StrokeFont(this.getFontManager()
                		 , scoresAtlas
                		 , Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                		 , 100
                		 , true
                		 , new Color(1.0f, 0.6f, 0.0f)
                		 , 2
                		 , new Color(1.0f, 0.2f, 0.0f));
	
	tDevNames = new StrokeFont(this.getFontManager()
				   , creditsNames
				   , Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
				   , 100
				   , true
				   , new Color(1.0f, 1.0f, 1.0f)
				   , 2
				   , new Color(1.0f, 0.2f, 0.0f));
	
	pauseLabel = new BitmapTextureAtlas(	this.getTextureManager()
						, 2048
						, 256
						, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	
	pauseResume = new BitmapTextureAtlas(	this.getTextureManager()
						, 512
						, 256
						, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	
	tPause = new StrokeFont(this.getFontManager()
				   , pauseLabel
				   , Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
				   , 165
				   , true
				   , new Color(1.0f, 0.6f, 0.0f)
				   , 2
				   , new Color(1.0f, 0.2f, 0.0f));
	
	tResume = new StrokeFont(this.getFontManager()
				    , pauseResume
				    , Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
				    , 120
				    , true
				    , new Color(1.0f, 1.0f, 1.0f)
				    , 2
				    , new Color(1.0f, 0.2f, 0.0f));
	
	GameOver = new BitmapTextureAtlas(this.getTextureManager()
					  , 2048
					  , 256
					  , TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	
	MainMenu = new BitmapTextureAtlas(this.getTextureManager()
					  , 1024
					  , 256
					  , TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	
	NewGame = new BitmapTextureAtlas(this.getTextureManager()
					 , 1024
					 , 256
					 , TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	
	tGameOver = new StrokeFont(this.getFontManager()
				   , GameOver
				   , Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
				   , 165
				   , true
				   , new Color(1.0f, 0.6f, 0.0f)
				   , 2
				   , new Color(1.0f, 0.2f, 0.0f));
	
	tMainMenu = new StrokeFont(this.getFontManager()
				   , MainMenu
				   , Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
				   , 120
				   , true
				   , new Color(1.0f, 1.0f, 1.0f)
				   , 2
				   , new Color(1.0f, 0.2f, 0.0f));
	
	tNewGame = new StrokeFont(this.getFontManager()
				  , NewGame
				  , Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
				  , 120
				  , true
				  , new Color(1.0f, 1.0f, 1.0f)
				  , 2
				  , new Color(1.0f, 0.2f, 0.0f));
	
	tDevNames.load();
	tCaptions.load();
	tPause.load();
	tResume.load();
	tGameOver.load();
	tMainMenu.load();
	tNewGame.load();
    }

    @Override
    protected Scene onCreateScene() {
	
	mainScene = new MainScene();
	gameLoaded = true;
	return mainScene;
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        if (this.isGameLoaded())
            MainActivity.mainActivity.mMusic.pause();
    }

    @Override
    protected synchronized void onResume()
    {
        super.onResume();
        System.gc();
        if (this.isGameLoaded())
            MainActivity.mainActivity.mMusic.play(); 
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	  
	    if (!gameLoaded) {
		
		return true;
	    }
	  
	    if (mainScene != null && gameLoaded) {
	      
		mainScene.keyPressed(keyCode, event);
		return true;
	    }
	return true;
	}
	return super.onKeyDown(keyCode, event);
    }
    
    public void muteSounds() {
	
	MainActivity.mainActivity.mClick.play();
	MainActivity.mainActivity.mMusic.setVolume(0);
	MainActivity.mainActivity.mClick.setVolume(0);
	MainActivity.mainActivity.mGameOver.setVolume(0);
	MainActivity.mainActivity.mGameStart.setVolume(0);
	MainActivity.mainActivity.mSound.setVolume(0);
	MainActivity.mainActivity.mStep.setVolume(0);
	MainActivity.isMute = !MainActivity.isMute;
    }
    
    public void unmuteSounds() {
	
	MainActivity.mainActivity.mClick.play();
	MainActivity.mainActivity.mMusic.setVolume(1);
	MainActivity.mainActivity.mClick.setVolume(1);
	MainActivity.mainActivity.mGameOver.setVolume(1);
	MainActivity.mainActivity.mGameStart.setVolume(1);
	MainActivity.mainActivity.mSound.setVolume(1);
	MainActivity.mainActivity.mStep.setVolume(1);
	MainActivity.isMute = !MainActivity.isMute;
    }
    
    @Override
    protected void onDestroy() {
	
	super.onDestroy();
	android.os.Process.killProcess(android.os.Process.myPid());
    }

    public boolean hasLargeScreen() {
	
	boolean xlarge = ((this.getResources().getConfiguration().screenLayout 
		& Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
	
	boolean large = ((this.getResources().getConfiguration().screenLayout 
		& Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
	
	return (xlarge || large);
    }

    public void saveProgress() {
	
	try {
	    
	    ObjectOutputStream oos = new ObjectOutputStream(openFileOutput("saves", 0));
	    oos.writeObject(MainScene.gameScene.slotMatrix.getNamesForSave());
	    //oos.writeObject(MainScene.gameScene.savePrison());
	    //oos.writeObject(MainScene.gameScene.respawn.getElement().getName());
	    oos.flush();
	    oos.close();
	    Log.d("File out", "write");
	    
	} catch(FileNotFoundException e) {
	    
	   e.printStackTrace();
	   Log.d("File out", "not found");
	} catch(IOException e) {
	    
	    e.printStackTrace();
	    Log.d("File out", "IO exception");
	}
    }

    public void savePrison() {
	
	try {
	    
	    ObjectOutputStream oos = new ObjectOutputStream(openFileOutput("savesprison", 0));
	    //oos.writeObject(MainScene.gameScene.slotMatrix.getNamesForSave());
	    oos.writeObject(MainScene.gameScene.savePrisonName());
	    //oos.writeObject(MainScene.gameScene.respawn.getElement().getName());
	    oos.flush();
	    oos.close();
	    Log.d("File out", "write in prison");
	    
	} catch(FileNotFoundException e) {
	    
	   e.printStackTrace();
	   Log.d("File out", "not found");
	   
	} catch(IOException e) {
	    
	    e.printStackTrace();
	    Log.d("File out", "IO exception");
	}
    }
    
    public void saveRespawn() {
	
	try {
	    
	    ObjectOutputStream oos = new ObjectOutputStream(openFileOutput("savesrespawn", 0));
	    //oos.writeObject(MainScene.gameScene.slotMatrix.getNamesForSave());
	    //oos.writeObject(MainScene.gameScene.prison.getElement().getName());
	    oos.writeObject(MainScene.gameScene.saveRespawnName());
	    oos.flush();
	    oos.close();
	    Log.d("File out", "write in resp " + MainScene.gameScene.respawn.getElement().getName());
	    
	} catch(FileNotFoundException e) {
	    
	   e.printStackTrace();
	   Log.d("File out", "not found");
	   
	} catch(IOException e) {
	    
	    e.printStackTrace();
	    Log.d("File out", "IO exception");
	}
    }

    public String[][] loadProgress() {
	
	try {
	    
	    ObjectInputStream ois = new ObjectInputStream(openFileInput("saves"));
	    namesMatrix = (String[][]) ois.readObject();
	    //String prisonName = (String) ois.readObject();
	    //String respawnName = (String) ois.readObject();
	    Log.d("File in", "read");
	    
	} catch(FileNotFoundException e) {
	    
	    e.printStackTrace();
	    Log.d("File in", "not found");
	    
	} catch(IOException e) {
	    
	    e.printStackTrace();
	    Log.d("File in", "IO exception");
	    
	} catch(ClassNotFoundException e) {
	    e.printStackTrace();
	    Log.d("File in", "ClassNotFoundException");
	}
	
	return namesMatrix;
    }
    
    public String loadPrison() {
	prisonName=null;
	try {
	    
	    ObjectInputStream ois = new ObjectInputStream(openFileInput("savesprison"));
	    //namesMatrix = (String[][]) ois.readObject();
	    prisonName = (String) ois.readObject();
	    //String respawnName = (String) ois.readObject();
	    Log.d("File in", "read");
	    
	} catch(FileNotFoundException e) {
	    
	    e.printStackTrace();
	    Log.d("File in", "not found");
	    
	} catch(IOException e) {
	    
	    e.printStackTrace();
	    Log.d("File in", "IO exception");
	    
	} catch(ClassNotFoundException e) {
	    
	    e.printStackTrace();
	    Log.d("File in", "ClassNotFoundException");
	}
	
	return prisonName;
    }
    
    public String loadRespawn() {
	respawnName=null;
	try {
	    
	    ObjectInputStream ois = new ObjectInputStream(openFileInput("savesrespawn"));
	    //namesMatrix = (String[][]) ois.readObject();
	    //prisonName = (String) ois.readObject();
	    respawnName = (String) ois.readObject();

	    Log.d("File in", "read in resp ");

	} catch(FileNotFoundException e) {
	    
	    e.printStackTrace();
	    Log.d("File in", "not found");
	    
	} catch(IOException e) {
	    
	    e.printStackTrace();
	    Log.d("File in", "IO exception");
	    
	} catch(ClassNotFoundException e) {
	    
	    e.printStackTrace();
	    Log.d("File in", "ClassNotFoundException");
	}
	
	return respawnName;
    }
}