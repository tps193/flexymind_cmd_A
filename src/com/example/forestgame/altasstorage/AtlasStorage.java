package com.example.forestgame.altasstorage;

import java.util.HashMap;
import java.util.Map;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;

public class AtlasStorage {
	
	private Map<String, TextureRegion> textureMap = new HashMap<String, TextureRegion>();
	
	public void createAtlas( TextureManager textureManager
 	           		, Context context
 	           		, String assetBasePath
 	           		, String... textureNames) {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(assetBasePath);
		
		BuildableBitmapTextureAtlas atlas = new BuildableBitmapTextureAtlas( textureManager
			 							   , 1024
			 							   , 2048
			 							   , BitmapTextureFormat.RGBA_8888
			 							   , TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		for(String texture : textureNames) {
			
		    textureMap.put( texture, 
					BitmapTextureAtlasTextureRegionFactory
					.createFromAsset( atlas
							, context
							, texture));
		}
		
		try {
			atlas.build( new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource
				   , BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		
		atlas.load();
	}
	
	public TextureRegion getTexture(String name) {
		
	    return textureMap.get(name);
	}
	
}