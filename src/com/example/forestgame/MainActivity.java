package com.example.forestgame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.microedition.khronos.opengles.GL10;

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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
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
    public static float DISPLAY_WIDTH;
    public static float DISPLAY_HEIGHT;
    public final static float TEXTURE_WIDTH = 1250;
    public final static float TEXTURE_HEIGHT = 2000;
    private float RATIO_WIDTH;
    private float RATIO_HEIGHT;
    public float ZM;  //zoom factor
    public static final Color BACKGROUND_COLOR = new Color(0.1f, 0.1f, 0.0f);
    public static final AlphaModifier SHOW_ALPHA_MODIFIER = new AlphaModifier(0.55f, 1.0f, 0.5f);
    public static final AlphaModifier HIDE_ALPHA_MODIFIER = new AlphaModifier(0.55f, 0.5f, 1.0f);
    public static final AlphaModifier TOUCH_ALPHA_MODIFIER = new AlphaModifier(0.001f, 1.0f, 0.6f);
    public static final AlphaModifier UNTOUCH_ALPHA_MODIFIER = new AlphaModifier(0.001f, 0.6f, 1.0f);
    public static final ScaleModifier TOUCH_SCALE_MODIFIER = new ScaleModifier(0.001f, 1.0f, 0.95f);
    public static final ScaleModifier UNTOUCH_SCALE_MODIFIER = new ScaleModifier(0.001f, 0.95f, 1.0f);
    public static final AlphaModifier PAUSE_ALPHA_MODIFIER = new AlphaModifier(0.55f, 0.8f, 0.5f);
    
    
    public static ZoomCamera camera;  //made public modifier camera so that it can be accessed from GameScene
    
    private boolean gameLoaded = false; // flag of game loading state
    private boolean gameSaved = false; // flag of game saving
    
    public static boolean isMute = false;
    
    public static MainActivity mainActivity;
    
    private static MainScene mainScene;
    
    public TextureRegion textureCage;
    
    public TextureRegion textureBackground;
    public TextureRegion textureSlots;
    public TextureRegion textureTitle;
    public TextureRegion texturePlay;
    public TextureRegion textureResume;
    public TextureRegion texturePauseL;
    public TextureRegion textureGameOverL;
    public TextureRegion textureNewGame;
    public TextureRegion textureMainMenu;
    public TextureRegion textureScores;
    public TextureRegion textureCredits;
    public TextureRegion textureExit;
    public TextureRegion texturePauseIcon;
    public TextureRegion textureMuteOff;
    public TextureRegion textureMuteOn;
    public TextureRegion textureHelp;
    public TextureRegion textureArrow;
    public TextureRegion textureArrowLeft;
    public TextureRegion textureArrowRight;
    public TextureRegion textureArrowUp;
    public TextureRegion textureArrowDown;
    public AtlasStorage storage;
    public StrokeFont tDevNames;
    public StrokeFont tScoresSceneCaptions;
    public StrokeFont tScoresSceneScores;
    public StrokeFont tCaptions;
    private static ITexture creditsCaps;
    private static ITexture creditsNames;
    private static ITexture scoresAtlas;
    public static ITexture scoresToastAtlas;
    public StrokeFont tScores;
    private BitmapTextureAtlas scoresSceneCaps;
    private BitmapTextureAtlas scoresSceneScores;
    
    private TextureRegion regionStatic;
    private Sprite spriteStatic;
    private BitmapTextureAtlas textureAnim;
    private TiledTextureRegion regionAnim;
    private AnimatedSprite  spriteAnim;
     
    private static int   TILED_SPRITE_COLUMNS  = 2;
    private static int   TILED_SPRITE_ROWS  = 5;
    
    public Music mMusic;
    public Music mGameMusic;
    public Sound mSound;
    public Sound mClick;
    public Sound mGameOver;
    public Sound mGameStart;
    public Sound mStep;
    public Sound mDrop;
    public Sound mMagic;
    
    private static Scene preLoadScene;
    
    private Sprite preLoadBackground; /*= new Sprite( 0
	          , 0
	          , MainActivity.TEXTURE_WIDTH
	          , MainActivity.TEXTURE_HEIGHT
	          , MainActivity.mainActivity.textureBackground
	          , MainActivity.mainActivity.getVertexBufferObjectManager());*/
    

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
	
	storage = new AtlasStorage();
	storage.createAtlas( MainActivity.mainActivity.getTextureManager()
        	   , MainActivity.mainActivity
        	   , "main_menu/"
        	   , "background.png");
	textureBackground = storage.getTexture("background.png");
	
	storage.createAtlas( MainActivity.mainActivity.getTextureManager()
     	   , MainActivity.mainActivity
     	   , "gfx_loading/"
     	   , "gfx_static.png");
	regionStatic = storage.getTexture("gfx_static.png");
	
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx_loading/");
	textureAnim = new BitmapTextureAtlas(MainActivity.mainActivity.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	regionAnim = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAnim, MainActivity.mainActivity,"gfx_anim.png", 0, 0, TILED_SPRITE_COLUMNS, TILED_SPRITE_ROWS);
	mEngine.getTextureManager().loadTexture(textureAnim);
	
	spriteAnim = new AnimatedSprite(840, 890, regionAnim, MainActivity.mainActivity.getVertexBufferObjectManager());
		
	preLoadBackground = new Sprite( 0
	          , 0
	          , MainActivity.TEXTURE_WIDTH
	          , MainActivity.TEXTURE_HEIGHT
	          , MainActivity.mainActivity.textureBackground
	          , MainActivity.mainActivity.getVertexBufferObjectManager());
	
	spriteStatic = new Sprite( MainActivity.TEXTURE_WIDTH / 4
	          , MainActivity.TEXTURE_HEIGHT * 48 / 128
	          , MainActivity.TEXTURE_WIDTH * 64 / 128
	          , MainActivity.TEXTURE_HEIGHT * 20 / 128
	          , MainActivity.mainActivity.regionStatic
	          , MainActivity.mainActivity.getVertexBufferObjectManager());
	
	preLoadBackground.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	preLoadBackground.setAlpha(0.5f);
    }

    @Override
    protected Scene onCreateScene() {
	this.mEngine.registerUpdateHandler(new FPSLogger());
	preLoadScene = new Scene();
	//preLoadScene.setIgnoreUpdate(true);
	/*preLoadScene.setBackgroundEnabled(true);
	preLoadScene.setBackground(new Background(Color.GREEN));*/
	preLoadScene.attachChild(preLoadBackground);
	preLoadScene.attachChild(spriteStatic);
	
	spriteAnim.setWidth(94);
	spriteAnim.setHeight(44);
	spriteAnim.animate(150, true);
	preLoadScene.attachChild(spriteAnim);
	
	
	gameLoaded = false;
	
	final IAsyncCallback callback = new IAsyncCallback() {
	    
            @Override
            public void workToDo() {

        	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("main_menu/");
        	
        	//storage = new AtlasStorage();
        	storage.createAtlas( MainActivity.mainActivity.getTextureManager()
        			   , MainActivity.mainActivity, "main_menu/"
        			   , "main_menu_title.png"
        			   , "menu_play.png"
        			   , "menu_resume.png"
        			   , "menu_scores.png"
        			   , "menu_credits.png"
        			   , "menu_credits_light.png"
        			   , "menu_exit.png"
        			   , "menu_exit_light.png"
        			   , "mute_on.png"
        			   , "mute_off.png"
        			   , "gfx_help_icon.png"
        			   , "arrow_left.png"
        			   , "arrow_right.png"
        			   , "arrow_up.png"
        			   , "arrow_down.png"
        			   , "game_over_label.png"
        			   , "pause_label.png"
        			   , "pause_new_game.png"
        			   , "pause_main_menu.png");
		   

        	textureTitle = storage.getTexture("main_menu_title.png");
        	texturePlay = storage.getTexture("menu_play.png");
        	textureResume = storage.getTexture("menu_resume.png");
        	textureScores = storage.getTexture("menu_scores.png");
        	textureCredits = storage.getTexture("menu_credits.png");
        	textureExit = storage.getTexture("menu_exit.png");
        	textureMuteOn = storage.getTexture("mute_on.png");
        	textureMuteOff = storage.getTexture("mute_off.png");
        	textureHelp = storage.getTexture("gfx_help_icon.png");
        	textureArrowLeft = storage.getTexture("arrow_left.png");
        	textureArrowRight = storage.getTexture("arrow_right.png");
        	textureArrowUp = storage.getTexture("arrow_up.png");
        	textureArrowDown = storage.getTexture("arrow_down.png");
        	texturePauseL = storage.getTexture("pause_label.png");
        	textureGameOverL = storage.getTexture("game_over_label.png");
        	textureMainMenu = storage.getTexture("pause_main_menu.png");
        	textureNewGame = storage.getTexture("pause_new_game.png");
        	
        	/*storage.createAtlas( MainActivity.mainActivity.getTextureManager()
        	           	   , MainActivity.mainActivity
        	           	   , "main_menu/"
        	           	   , "background.png");
        	textureBackground = storage.getTexture("background.png");*/
        	
        	storage.createAtlas( MainActivity.mainActivity.getTextureManager()
        		 	   , MainActivity.mainActivity
        		 	   , "game_scene_gfx/"
        		 	   , "gfx_slots.png");
                textureSlots = storage.getTexture("gfx_slots.png");
        	
        	storage.createAtlas( MainActivity.mainActivity.getTextureManager()
			   , MainActivity.mainActivity
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
			   , "gfx_pause_icon.png"        			 
			, "gfx_forester.png"
			, "gfx_drop.png"
			, "gfx_flying_squirrel.png"
			, "gfx_magic_stick.png"
			, "gfx_nuts_imperor.png"
			, "gfx_nuts_magnum.png"
			, "gfx_hint_arrow_X3.png"
			, "gfx_shadow.png"
			, "gfx_questionCrown.png"
			, "gfx_hint_arrow.png"
			, "gfx_hut.png"
			, "gfx_country.png"
			, "gfx_city.png"
			, "gfx_megapolis.png"
			, "gfx_pond.png"
			, "gfx_swamp.png"
			, "gfx_lake.png"
			, "gfx_sea.png"
			, "gfx_question.png"
			, "gfx_2_questions.png");        	
        	
        	textureArrow = storage.getTexture("gfx_hint_arrow.png");
        	
        	textureCage = storage.getTexture("gfx_cage.png");
        	texturePauseIcon = storage.getTexture("gfx_pause_icon.png");
        	MusicFactory.setAssetBasePath("sounds/");
        	
                try {
                        mMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), MainActivity.mainActivity, "main_menu.ogg");
                        mGameMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), MainActivity.mainActivity, "game_background.ogg");
                        mMusic.setLooping(true);
                        mGameMusic.setLooping(true);
                } catch (final IOException e) {
                        Debug.e("Error", e);
                }
                
                SoundFactory.setAssetBasePath("sounds/");
                
                try {
            		mSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.mainActivity, "convolution.ogg");
            		mClick = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.mainActivity, "click.ogg");
            		mGameOver = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.mainActivity, "game_over.ogg");
            		mGameStart = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.mainActivity, "game_start.ogg");
            		mStep = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.mainActivity, "general_step.ogg");
            		mDrop = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.mainActivity, "drop.ogg");
            		mMagic = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), MainActivity.mainActivity, "magic.ogg");
                } catch (final IOException e) {
            		Debug.e("Error", e);
                }	
        	
                FontFactory.setAssetBasePath("font/");
                creditsCaps = new BitmapTextureAtlas(	MainActivity.mainActivity.getTextureManager()
        						, 2048
        						, 1024
        						, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        	
        	scoresSceneCaps = new BitmapTextureAtlas(MainActivity.mainActivity.getTextureManager()
        						, 2048
        						, 1024
        						, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        	
        	scoresSceneScores = new BitmapTextureAtlas(MainActivity.mainActivity.getTextureManager()
        						, 2048
        						, 1024
        						, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        		
        	creditsNames = new BitmapTextureAtlas(	MainActivity.mainActivity.getTextureManager()
        						, 2048
        						, 2048
        						, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        	
        	scoresAtlas = new BitmapTextureAtlas(	MainActivity.mainActivity.getTextureManager()
        						, 1024
        						, 256
        						, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        	
        	scoresToastAtlas = new BitmapTextureAtlas(MainActivity.mainActivity.getTextureManager()
			, 256
			, 256
			, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        	
        	tCaptions = new StrokeFont(MainActivity.mainActivity.getFontManager()
        				   , creditsCaps
        				   , Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        				   , 100
        				   , true
        				   , new Color(1.0f, 0.6f, 0.0f)
        				   , 2
        				   , new Color(1.0f, 0.2f, 0.0f));
        	
        	tScoresSceneCaptions = new StrokeFont	(MainActivity.mainActivity.getFontManager()
        						, scoresSceneCaps
        						, Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        						, 150
        						, true
        						, new Color(1.0f, 0.6f, 0.0f)
        						, 2
        						, new Color(1.0f, 0.2f, 0.0f));
        	
        	tScoresSceneScores = new StrokeFont(	MainActivity.mainActivity.getFontManager()
        						, scoresSceneScores
        						, Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        						, 120
        						, true
        						, new Color(1.0f, 1.0f, 1.0f)
        						, 2
        						, new Color(1.0f, 0.2f, 0.0f));

        	tScores = FontFactory.createStrokeFromAsset(MainActivity.mainActivity.getFontManager()
        		,scoresAtlas 
        		, MainActivity.mainActivity.getAssets()
        		, "showg.ttf"
        		, (float)100
        		, true
        		, Color.YELLOW_ARGB_PACKED_INT
        		, 2
        		, Color.BLACK_ARGB_PACKED_INT);
        	
        	tDevNames = new StrokeFont(MainActivity.mainActivity.getFontManager()
        				   , creditsNames
        				   , Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        				   , 100
        				   , true
        				   , new Color(1.0f, 1.0f, 1.0f)
        				   , 2
        				   , new Color(1.0f, 0.2f, 0.0f));
        	
      
        	
        	tScoresSceneCaptions.load();
        	tScoresSceneScores.load();
        	tDevNames.load();
        	tCaptions.load();
        	tScores.load();
 
            }
 
            @Override
            public void onComplete() {
                gameLoaded();
            }
        };
        
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AsyncTaskLoader().execute(callback);
            }
            });
	
	/*mainScene = new MainScene();
	gameLoaded = true;*/
	
	return preLoadScene;
    }
    
    protected void gameLoaded() {
	
	gameLoaded = true;
	mainScene = new MainScene();
	mEngine.setScene(mainScene);
	
	preLoadScene.detachChild(preLoadBackground);
	preLoadScene = null;
	//������� �������� ����������
	//MainActivity.mainActivity.getTextureManager().unloadTexture();
	
	MainScene.showMainMenuScene();
	
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        if (this.isGameLoaded()) {
            
            MainActivity.mainActivity.mMusic.pause();
            MainActivity.mainActivity.mGameStart.pause();
            MainActivity.mainActivity.mGameMusic.pause();
            saveProgress();
        }
    }

    @Override
    protected synchronized void onResume()
    {
        super.onResume();
        System.gc();
        if (this.isGameLoaded()) {
            switch(MainScene.gameState) {
            	case MAIN_MENU:
            	    MainActivity.mainActivity.mMusic.play();
            	    break;
            	    
            	case GAME_RUNNING: 
                    MainActivity.mainActivity.mGameStart.play();
                    MainActivity.mainActivity.mGameMusic.play();
            	    break;
            	    
            	case SHOW_CREDITS:
            	    MainActivity.mainActivity.mMusic.play();
        	    break;
        	    
        	case SHOW_SCORES:
        	    MainActivity.mainActivity.mMusic.play();
        	    break;
        	    
        	case PAUSE:
        	    MainActivity.mainActivity.mGameMusic.play();
        	    break;
        	    
        	case GAME_OVER:
        	    MainActivity.mainActivity.mGameMusic.play();
        	    break;
            }
            progressNotSaved();
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	  
	    if (!gameLoaded) {
		
		return true;
	    }
	  
	    if (mainScene != null && gameLoaded) {
	      
		mainScene.keyPressed(keyCode, event);
		MainActivity.mainActivity.mClick.play();
		return true;
	    }
	return true;
	}
	return super.onKeyDown(keyCode, event);
    }
    
    public void muteSounds() {
	
	MainActivity.mainActivity.mMusic.setVolume(0);
	MainActivity.mainActivity.mGameMusic.setVolume(0);
	MainActivity.mainActivity.mClick.setVolume(0);
	MainActivity.mainActivity.mGameOver.setVolume(0);
	MainActivity.mainActivity.mGameStart.setVolume(0);
	MainActivity.mainActivity.mSound.setVolume(0);
	MainActivity.mainActivity.mStep.setVolume(0);
	MainActivity.mainActivity.mDrop.setVolume(0);
	MainActivity.mainActivity.mMagic.setVolume(0);
	MainActivity.isMute = !MainActivity.isMute;
    }
    
    public void unmuteSounds() {
	
	MainActivity.mainActivity.mClick.play();
	MainActivity.mainActivity.mMusic.setVolume(1);
	MainActivity.mainActivity.mGameMusic.setVolume(1);
	MainActivity.mainActivity.mClick.setVolume(1);
	MainActivity.mainActivity.mGameOver.setVolume(1);
	MainActivity.mainActivity.mGameStart.setVolume(1);
	MainActivity.mainActivity.mSound.setVolume(1);
	MainActivity.mainActivity.mStep.setVolume(1);
	MainActivity.mainActivity.mDrop.setVolume(1);
	MainActivity.mainActivity.mMagic.setVolume(1);
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
    
	if (!gameSaved) {
	try {
	    
	    ObjectOutputStream oos = new ObjectOutputStream(openFileOutput("saves", 0));
	    Object[] obj = new Object[3];
	    obj[0]=MainScene.gameScene.getSlotMatrix().getNamesForSave();
	    obj[1]=MainScene.gameScene.getNamesSubmatrix();
	    obj[2]=Integer.valueOf(MainScene.gameScene.getSlotMatrix().getScore());
	    
	//    oos.writeObject(MainScene.gameScene.slotMatrix.getNamesForSave());
	 //   oos.writeObject(MainScene.gameScene.getNamesSubmatrix());
	    oos.writeObject(obj);
	    oos.flush();
	    oos.close();
	    Log.d("File out", "write");
	    gameSaved = true;
	//    for(String str: MainScene.gameScene.getNamesSubmatrix())
	//	Log.d("File out", str);
	    
	} catch(FileNotFoundException e) {
	    
	   e.printStackTrace();
	   Log.d("File out", "not found");
	} catch(IOException e) {
	    
	    e.printStackTrace();
	    Log.d("File out", "IO exception");
	}
	}
    }

public void progressNotSaved() {
    
    gameSaved = false;
}

public Object[] load() throws IOException {
	Object[] obj=null;
	try {
	    
	    ObjectInputStream ois = new ObjectInputStream(openFileInput("saves"));
	    obj = (Object[])ois.readObject();
	} catch(ClassNotFoundException e) {
	    e.printStackTrace();
	    Log.d("File in", "ClassNotFoundException");
	}
	
	return obj;
    }
}