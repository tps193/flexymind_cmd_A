package com.example.forestgame;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.example.forestgame.element.Element;
import com.example.forestgame.gameinterface.Prison;
import com.example.forestgame.gameinterface.Respawn;

public class GameScene extends Scene {
    
    private PauseScene pauseScene = new PauseScene();
    private GameOverScene gameOverScene = new GameOverScene();
    private ScoresTable scoresTable = new ScoresTable();
    
    
    private SlotMatrix slotMatrix;
    private Prison prison;
    private Respawn respawn;
    private Sprite backlight;
    
    private Element movingElement;
    
    private int putInRow;
    private int putInColumn;
    
    private static final float CAGE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 136 / 625;
    private static final float CAGE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 1381 / 2000;
    private static final float CAGE_WIDTH = MainActivity.TEXTURE_WIDTH * 63 / 250;
    private static final float CAGE_HEIGHT = MainActivity.TEXTURE_HEIGHT * 313 / 2000;
    
    private static final float PAUSE_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 516 / 625;
    private static final float PAUSE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 17 / 2000;
    private static final float PAUSE_WIDTH = MainActivity.TEXTURE_WIDTH * 30 / 250;
    private static final float PAUSE_HEIGHT = MainActivity.TEXTURE_HEIGHT * 18 / 250;
    
    private static final float SCORES_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 4 / 60;
    private static final float SCORES_POSITION_UP = MainActivity.TEXTURE_HEIGHT *3 / 200;
    
    private static final int CAGE_Z_INDEX = 9999;
    private static final int PAUSE_SCENE_Z_INDEX = 10000;
    private static final int GAME_OVER_SCENE_Z_INDEX = 10000;
    
    private static final AlphaModifier BACKGROUND_ALPHA_MODIFIER = new AlphaModifier(0.55f, 1.0f, 0.8f);
    private static final AlphaModifier SLOTS_ALPHA_MODIFIER = new AlphaModifier(0.4f, 0.5f, 1.0f);
    
    
    private static final float PRISON_POSITION_LEFT = CAGE_POSITION_LEFT;
    private static final float PRISON_POSITION_UP = CAGE_POSITION_UP;
    private static final float PRISON_POSITION_RIGHT = PRISON_POSITION_LEFT + CAGE_WIDTH;
    private static final float PRISON_POSITION_BOTTOM = PRISON_POSITION_UP + CAGE_HEIGHT;
    private static final float BACKLIGHT_ALPHA = 0.7f;
    private static final float BACKLIGHT_ALPHA_FULL = 0.4f;
    private static int SUBMATRIX_LENGTH = 2;
    
    private float BORDER_WIDTH = SlotMatrix.getSlotPositionLeft(1) - SlotMatrix.getSlotPositionLeft(0) - SlotMatrix.getSlotWidth();
    private float BORDER_HEIGHT = SlotMatrix.getSlotPositionUp(1) - SlotMatrix.getSlotPositionUp(0) - SlotMatrix.getSlotHeight();
    
    private Text scoresText;
    
    private Sprite background = new Sprite( 0
	                              , 0
	                              , MainActivity.TEXTURE_WIDTH
	                              , MainActivity.TEXTURE_HEIGHT
	                              , MainActivity.mainActivity.textureBackground
	                              , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Sprite slots = new Sprite( 0
	    			     , 0
	    			     , MainActivity.TEXTURE_WIDTH
	    			     , MainActivity.TEXTURE_HEIGHT
	    			     , MainActivity.mainActivity.textureSlots
	    			     , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Sprite cage = new Sprite( CAGE_POSITION_LEFT
	        		    , CAGE_POSITION_UP
	        		    , CAGE_WIDTH
	        		    , CAGE_HEIGHT
	        		    , MainActivity.mainActivity.textureCage
	        		    , MainActivity.mainActivity.getVertexBufferObjectManager());
    
    private Sprite pauseIcon = new Sprite( PAUSE_POSITION_LEFT
	    				 , PAUSE_POSITION_UP
	    				 , PAUSE_WIDTH
	    				 , PAUSE_HEIGHT
	    				 , MainActivity.mainActivity.texturePauseIcon
	    				 , MainActivity.mainActivity.getVertexBufferObjectManager()) {
    @Override
    public boolean onAreaTouched( TouchEvent pSceneTouchEvent
	    			, float pTouchAreaLocalX
	    			, float pTouchAreaLocalY) {
	
	if (pSceneTouchEvent.isActionDown()) {
	    
	    this.registerEntityModifier(MainActivity.TOUCH_ALPHA_MODIFIER);
	    this.setAlpha(0.5f);
	} else if (pSceneTouchEvent.isActionUp()) {
	    
	    this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER);
	    MainActivity.mainActivity.mClick.play();
	    MainScene.showInGamePause();
	    this.setAlpha(1.0f);
	}
	return true;
	}
    };
    
    
    private Sprite muteOff = new Sprite( MainMenuScene.MUTE_POSITION_LEFT
	    			       , MainMenuScene.MUTE_POSITION_UP
	    			       , MainMenuScene.MUTE_WIDTH
	    			       , MainMenuScene.MUTE_HEIGHT
	    			       , MainActivity.mainActivity.textureMuteOff
	    			       , MainActivity.mainActivity.getVertexBufferObjectManager()) {

	@Override
	public boolean onAreaTouched( TouchEvent pSceneTouchEvent
				    , float pTouchAreaLocalX
				    , float pTouchAreaLocalY) {

	    if (pSceneTouchEvent.isActionDown()) {

		Log.d("MuteOff", "touch");
		this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
		this.registerEntityModifier(MainActivity.TOUCH_ALPHA_MODIFIER.deepCopy());

	    } else if (pSceneTouchEvent.isActionUp()) {

		Log.d("MuteOff", "no touch");
		this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());
		muteIconCLick();
	    }
	    return true;
	}
    };


    private Sprite muteOn = new Sprite( MainMenuScene.MUTEON_POSITION_LEFT
	    			      , MainMenuScene.MUTEON_POSITION_UP
	    			      , MainMenuScene.MUTEON_WIDTH
	    			      , MainMenuScene.MUTEON_HEIGHT
	    			      , MainActivity.mainActivity.textureMuteOn
	    			      , MainActivity.mainActivity.getVertexBufferObjectManager());

    private void muteIconCLick() {	    
	if (!MainActivity.isMute) {
	    MainActivity.mainActivity.muteSounds();
	    muteOn.setVisible(false);
	} else {
	    MainActivity.mainActivity.unmuteSounds();
	    muteOn.setVisible(true);
	}
    }
    
    

    public GameScene() {
	
	setBackgroundEnabled(true);
	setBackground(new Background(MainActivity.BACKGROUND_COLOR));
	background.registerEntityModifier(BACKGROUND_ALPHA_MODIFIER.deepCopy());
	background.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_COLOR);
	slots.registerEntityModifier(SLOTS_ALPHA_MODIFIER.deepCopy());
	attachChild(background);
	attachChild(slots);
	attachChild(cage);
	attachChild(pauseIcon);
	attachChild(muteOff);
	attachChild(muteOn);
	muteOn.setVisible(true);
	registerTouchArea(pauseIcon);
	registerTouchArea(muteOff);
	
	
	slotMatrix = new SlotMatrix(this);
	prison = new Prison(this);
	respawn = new Respawn(this);

	attachChild(pauseScene);
	attachChild(gameOverScene);
	pauseScene.hide();
	gameOverScene.hide();

   	MainActivity.mainActivity.mGameMusic.play();
	
	cage.setZIndex(CAGE_Z_INDEX);
	pauseScene.setZIndex(PAUSE_SCENE_Z_INDEX);
	gameOverScene.setZIndex(GAME_OVER_SCENE_Z_INDEX);
	
    }
    
    public void show() {
	setVisible(true);
	setIgnoreUpdate(false);
	MainActivity.mainActivity.mMusic.pause();
   	MainActivity.mainActivity.mGameMusic.play();
	MainActivity.mainActivity.mGameStart.play();
	if (!MainActivity.isMute) {
	    muteOn.setVisible(true);
	} else {
	    muteOn.setVisible(false);
	}
   	background.registerEntityModifier(BACKGROUND_ALPHA_MODIFIER.deepCopy());
   	slots.registerEntityModifier(SLOTS_ALPHA_MODIFIER.deepCopy());
    }
    
    public void hide() {
   	setVisible(false);
   	setIgnoreUpdate(true);
   	background.setAlpha(1.0f);
   	slots.setAlpha(0.5f);
    }
    
    public SlotMatrix getSlotMatrix() {
	
	return slotMatrix;
    }
    
    public Prison getPrison() {
	return prison;
    }
    
    public Respawn getRespawn() {
	return respawn;
    }
    
    public PauseScene getPauseScene() {
	return pauseScene;
    }
    
    public GameOverScene getGameOverScene() {
	return gameOverScene;
    }
    
    public ScoresTable getScoresTable() {
        return scoresTable;
    }



    public Element getMovingElement() {
	return movingElement;
    }

    public void backLight(float touchPointX, float touchPointY, boolean elementIsMagicStick) {

	int backlightRow = (int) ((touchPointY - SlotMatrix.getSlotPositionUp(0)) / (SlotMatrix.getSlotHeight() + BORDER_HEIGHT));
	int backlightColumn = (int) ((touchPointX - SlotMatrix.getSlotPositionLeft(0)) / (SlotMatrix.getSlotWidth() + BORDER_WIDTH));
	if (elementIsMagicStick) {
	    fullSlotBacklight(backlightRow, backlightColumn);
	} else {
	    emptySlotBacklight(backlightRow, backlightColumn);
	}
    }
    
    public void emptySlotBacklight(int i, int j) {
	
	detachChild(backlight);
	
	if (i < SlotMatrix.getROWS() && j < SlotMatrix.getCOLUMNS() && slotMatrix.isSlotEmpty(i, j)) {
	    
	    backlight = new Sprite(SlotMatrix.getSlotPositionLeft(j) - MainActivity.TEXTURE_WIDTH / 800
                	    	 , SlotMatrix.getSlotPositionUp(i)
                	    	 , SlotMatrix.getSlotWidth() + MainActivity.TEXTURE_WIDTH / 210
                	    	 , SlotMatrix.getSlotHeight() + MainActivity.TEXTURE_HEIGHT / 220
                	    	 , MainActivity.mainActivity.storage.getTexture("gfx_empty.png")
                	    	 , MainActivity.mainActivity.getVertexBufferObjectManager());
     
            backlight.setAlpha(BACKLIGHT_ALPHA);
	    attachChild(backlight);
	    backlight.getParent().sortChildren();
	}
    }

    public void fullSlotBacklight(int i, int j) {
	
	detachChild(backlight);
	
	if (i < SlotMatrix.getROWS() && j < SlotMatrix.getCOLUMNS() && !slotMatrix.isSlotEmpty(i, j)) {
	    
	    Log.d("fores",slotMatrix.getElement(i, j));
	    if (slotMatrix.getElement(i, j).equals("HUT") || slotMatrix.getElement(i, j).equals("COUNTRY") || 
		    slotMatrix.getElement(i, j).equals("CITY") || slotMatrix.getElement(i, j).equals("MEGAPOLIS") ||
		    slotMatrix.getElement(i, j).equals("FORESTER") || slotMatrix.getElement(i, j).equals("POND") ||
		    slotMatrix.getElement(i, j).equals("SWAMP") || slotMatrix.getElement(i, j).equals("LAKE") ||
		    slotMatrix.getElement(i, j).equals("SEA") || slotMatrix.getElement(i, j).equals("FLYING_SQUIRREL")){
		Log.d("fores",slotMatrix.getElement(i, j));
	    
	       backlight = new Sprite(SlotMatrix.getSlotPositionLeft(j) - MainActivity.TEXTURE_WIDTH / 800
            	    	 , SlotMatrix.getSlotPositionUp(i)
            	    	 , SlotMatrix.getSlotWidth() + MainActivity.TEXTURE_WIDTH / 210
            	    	 , SlotMatrix.getSlotHeight() + MainActivity.TEXTURE_HEIGHT / 220
            	    	 , MainActivity.mainActivity.storage.getTexture("gfx_empty.png")
            	    	 , MainActivity.mainActivity.getVertexBufferObjectManager());
	    
	    
        backlight.setAlpha(BACKLIGHT_ALPHA_FULL);
	attachChild(backlight);
	backlight.getParent().sortChildren();
	    }
	    
	}
}
    
    
    public void moveElement(float touchPointX, float touchPointY) {

	if ((touchPointX >= SlotMatrix.getSlotPositionLeft(0)) && 
		(touchPointX <= SlotMatrix.getSlotPositionLeft(SlotMatrix.getCOLUMNS()) + SlotMatrix.getSlotWidth()) &&
	    	(touchPointY >= SlotMatrix.getSlotPositionUp(0) &&
	    	(touchPointY <= SlotMatrix.getSlotPositionUp(SlotMatrix.getROWS()) + SlotMatrix.getSlotHeight()))) {

	    putInRow = (int) ((touchPointY - SlotMatrix.getSlotPositionUp(0)) / (SlotMatrix.getSlotHeight() + BORDER_HEIGHT));
	    putInColumn = (int) ((touchPointX - SlotMatrix.getSlotPositionLeft(0)) / (SlotMatrix.getSlotWidth() + BORDER_WIDTH));
	    Log.d("slot x ",Integer.toString(putInColumn));
	    Log.d("slot y ",Integer.toString(putInRow));


	} else if (PRISON_POSITION_LEFT <= touchPointX && touchPointX <= PRISON_POSITION_RIGHT && 
		    PRISON_POSITION_UP <= touchPointY && touchPointY <= PRISON_POSITION_BOTTOM) {

	     Log.d("slotPrison x ",Integer.toString(7));
	     Log.d("slotPrison y ",Integer.toString(7));
	     putInRow = SlotMatrix.getPrisonPlaceRow();
	     putInColumn = SlotMatrix.getPrisonPlaceRow();
	     detachChild(backlight);

	} else {

	    putInRow = SlotMatrix.getMilkPointRow();
	    putInColumn = SlotMatrix.getMilkPointColumn();
	    detachChild(backlight);
	}
    }
    
    public int getPutInRow() {
	
	return putInRow;
    }  
    
    public int getPutInColumn() {
	
	return putInColumn;
    } 
    
public void setScores(int scores) {
	
	Log.d("scores",""+scores);
	detachChild(scoresText);
	scoresText=null;
	
	scoresText= new Text(SCORES_POSITION_LEFT
			   , SCORES_POSITION_UP
			   , MainActivity.mainActivity.tScores
			   , "Scores: "+Integer.toString(scores)
			   , MainActivity.mainActivity.getVertexBufferObjectManager());
	
	attachChild(scoresText);
    }

    public Sprite getBacklight() {
	
        return backlight;
    }

    public String savePrisonName() {
	
	if (prison.isEmpty()) return null;
	
	return prison.getElement().getName();
    }
    
    public String saveRespawnName() {
	
	if (respawn.isEmpty()) return null;
	
	return respawn.getElement().getName();
    }
    
    public String[] getNamesSubmatrix() {
	String[] subNames = new String[SUBMATRIX_LENGTH];
	
	if (prison.isEmpty()) subNames[0] = null;
	else
	subNames[0] = prison.getElement().getName();
	
	if (respawn.isEmpty()) subNames[1] = null; 
	else
	subNames[1] = respawn.getElement().getName();
	
	return subNames;
    }
    
    public void setSavedGame() throws IOException {
	
	/*String newPrison = MainActivity.mainActivity.loadPrisonAndRespawn()[0];
	String newRespawn = MainActivity.mainActivity.loadPrisonAndRespawn()[1];
	
	slotMatrix.loadInit();
	setScores(MainActivity.mainActivity.loadScores());
	prison.clear();
	respawn.clear();
	if(newPrison!=null)prison.addElement(new Element(newPrison));
	if(newRespawn!=null)respawn.addElement(new Element(newRespawn));*/
	Object[] loadedObj = MainActivity.mainActivity.load();
	int scores = Integer.valueOf(loadedObj[2].toString());
	String[] loadedSubMatrix = (String[]) loadedObj[1];
	String loadedPrison = loadedSubMatrix[0];
	String loadedRepawn = loadedSubMatrix[1];
	//String loadedPrison = (String)MainActivity.mainActivity.load()[1][0];
	
	slotMatrix.loadInit(loadedObj[0],scores);
	prison.clear();
	respawn.clear();
	if(loadedPrison!=null)prison.addElement(new Element(loadedPrison));
	if(loadedRepawn!=null)respawn.addElement(new Element(loadedRepawn));
	setScores(scores);
    }
}
