package com.example.app;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.util.exception.TMXException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;


public class MainActivity extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
	private GameManager gameManager;
	private BoundCamera mBoundCamera;
	private BitmapTextureAtlas playerTextureAtlas;
	private ITextureRegion playerTextureRegion;
	private TMXTiledMap mTMXTiledMap;
	
	
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
		final Scene scene = new Scene();
		
		//Create tmxLoader with internal listener that can listen for properties of tiles (such as collisions, item pickups etc.)
		try{
			final TMXLoader tmxLoader = new TMXLoader(this.getAssets(), this.mEngine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, this.getVertexBufferObjectManager(), 
					new ITMXTilePropertiesListener() {
						@Override
						public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
							
							
						}
					});
				
			//Get instance of the map	
			this.mTMXTiledMap = tmxLoader.loadFromAsset("tmx/level1.tmx");
			
			
			
		}catch(final TMXException tmxe){
			Debug.e(tmxe);
		}
		//Attach each layer of the map to the scene
		for(TMXLayer tmxLayer : mTMXTiledMap.getTMXLayers()){
			scene.attachChild(tmxLayer);
		}
		
		//Attach each object to the scene
		ArrayList<TMXObjectGroup> objectGroups = new ArrayList<TMXObjectGroup>();
		ArrayList<TMXObject> objects = new ArrayList<TMXObject>();
		for(TMXObjectGroup group : objectGroups){
			objects = group.getTMXObjects();
			for(TMXObject object : objects){
				String type = "";
				if(group.getTMXObjectGroupProperties().size() > 0){
					type = object.getTMXObjectProperties().get(0).getValue();
				}
				
				HashMap<String, String> propertiesMap = new HashMap<String, String>();
				int size = object.getTMXObjectProperties().size();
				for(int i=0; i<size; i++){
					propertiesMap.put(object.getTMXObjectProperties().get(i).getName(), object.getTMXObjectProperties().get(i).getValue());
				}
				
				if(propertiesMap.containsKey("type")){
					type = propertiesMap.get("type");
				}
				EntityHandler.addEntity(this, scene, object.getX(), object.getY(), object.getWidth(), object.getHeight(), type, propertiesMap, this.getVertexBufferObjectManager());
			}
		}
		
		return null;
	}


}
