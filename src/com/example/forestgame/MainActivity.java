package com.example.forestgame;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import android.util.DisplayMetrics;

public class MainActivity extends SimpleBaseGameActivity {

	@Override
	public EngineOptions onCreateEngineOptions() {
		setScreenResolutionRatio();
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		final EngineOptions options = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
														new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		return options;
	}

	@Override
	protected void onCreateResources() {
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("graphics/");
		 backgroundTexture = new BitmapTextureAtlas(new TextureManager(), 512, 1024, TextureOptions.DEFAULT);
	     texture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backgroundTexture, this, "Forest-800x480.jpg", 0, 0);
	     this.mEngine.getTextureManager().loadTexture(this.backgroundTexture);
	}

	@Override
	protected Scene onCreateScene() {
		mainScene = new Scene();
		Sprite sprite = new Sprite(0, 0, texture, new VertexBufferObjectManager());
		SpriteBackground spriteBackground = new SpriteBackground(sprite);
		mainScene.setBackground(spriteBackground);
		return mainScene;
	}
	
	private void setScreenResolutionRatio() {
	    DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	    CAMERA_HEIGHT = dm.heightPixels;
	    CAMERA_WIDTH = dm.widthPixels;
	    
	}
	
	
	
	private static int CAMERA_WIDTH;
	private static int CAMERA_HEIGHT;
	private static Camera camera;
	private static Scene mainScene;
	private TextureRegion texture;
	private BitmapTextureAtlas backgroundTexture;
	
	
	
}