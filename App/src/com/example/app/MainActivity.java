package com.example.app;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;


public class MainActivity extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
	private GameManager gameManager;
	private BoundCamera mBoundCamera;
	private BitmapTextureAtlas playerTextureAtlas;
	private TiledTextureRegion playerTextureRegion;
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.mBoundCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mBoundCamera);
	}

	@Override
	protected void onCreateResources() {
		//page 28
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		playerTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 72, 128, TextureOptions.DEFAULT);
		playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(playerTextureAtlas, this, "Boost.png", 0, 0, 3, 4);
		playerTextureAtlas.load();
		
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		return null;
	}


}
