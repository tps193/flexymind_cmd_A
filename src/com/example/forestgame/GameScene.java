package com.example.forestgame;

import java.io.IOException;
import java.util.LinkedList;
import javax.microedition.khronos.opengles.GL10;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import android.util.Log;
import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;
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
    private static final float PAUSE_POSITION_UP = MainActivity.TEXTURE_HEIGHT * 22 / 2000;
    private static final float PAUSE_WIDTH = MainActivity.TEXTURE_WIDTH * 30 / 250;
    private static final float PAUSE_HEIGHT = MainActivity.TEXTURE_HEIGHT * 18 / 250;
    
    private static final float SCORES_POSITION_LEFT = MainActivity.TEXTURE_WIDTH * 4 / 60;
    private static final float SCORES_POSITION_UP = MainActivity.TEXTURE_HEIGHT *6 / 200;
    
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
    private static int SUBMATRIX_LENGTH = 2;
    
    private float BORDER_WIDTH = SlotMatrix.getSlotPositionLeft(1) - SlotMatrix.getSlotPositionLeft(0) - SlotMatrix.getSlotWidth();
    private float BORDER_HEIGHT = SlotMatrix.getSlotPositionUp(1) - SlotMatrix.getSlotPositionUp(0) - SlotMatrix.getSlotHeight();
    
    private Text scoresText;
    
    private Sprite helpPart1;
    private Sprite helpPart2;
    private Sprite helpPart3;
    private Sprite helpPart4;
    private boolean helpIsShown;
    private String helpTextureName1;
    private String helpTextureName2;
    private String helpTextureName3;
    private String helpTextureName4;
    
    public static String helpTextureX3 = "gfx_hint_arrow_X3.png";
    public static String helpTextureShadow = "gfx_shadow.png";
    public static String helpTextureQuestionWithCrown = "gfx_questionCrown.png";
    public static String helpTextureQuestion = "gfx_question.png";
    public static String helpTextureTwoQuestions = "gfx_2_questions.png";
    public static String helpTextureArrow = "gfx_hint_arrow.png";
    
    
    private int backlightRow;
    private int backlightColumn;
    private LinkedList<Slot> slotsForCombo = new LinkedList<Slot>();
    private Element bestElementForDropAdd;
    
    
    
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
	    
	    if (    (pTouchAreaLocalX > 0) 
	                 && (pTouchAreaLocalX < PAUSE_WIDTH)
	                 && (pTouchAreaLocalY > 0)
	                 && (pTouchAreaLocalY < PAUSE_HEIGHT)) {
	    
	    this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER);
	    this.setAlpha(1.0f);
	    MainActivity.mainActivity.mClick.play();
	    MainScene.showInGamePause();
	    
	    
	    } else {
		this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER);
		    this.setAlpha(1.0f);
	    }
	    
	} else if (pSceneTouchEvent.isActionMove()) {
	    if (    !((pTouchAreaLocalX > 0) 
	                 && (pTouchAreaLocalX < PAUSE_WIDTH)
	                 && (pTouchAreaLocalY > 0)
	                 && (pTouchAreaLocalY < PAUSE_HEIGHT))) {
		this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER);
		    this.setAlpha(1.0f);
	    } else {
		this.registerEntityModifier(MainActivity.TOUCH_ALPHA_MODIFIER);
		    this.setAlpha(0.5f);
	    }
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
		
		if (    (pTouchAreaLocalX > 0) 
	                 && (pTouchAreaLocalX < MainMenuScene.MUTE_WIDTH)
	                 && (pTouchAreaLocalY > 0)
	                 && (pTouchAreaLocalY < MainMenuScene.MUTE_HEIGHT)) {

		Log.d("MuteOff", "no touch");
		this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
		this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());
		muteIconCLick();
		
		} else {
			Log.d("MuteOff", "no touch outside");
			this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
			this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());
		    }
		    
		} else if (pSceneTouchEvent.isActionMove()) {
		    if (    !((pTouchAreaLocalX > 0) 
		                 && (pTouchAreaLocalX < MainMenuScene.MUTE_WIDTH)
		                 && (pTouchAreaLocalY > 0)
		                 && (pTouchAreaLocalY < MainMenuScene.MUTE_HEIGHT))) {
			this.registerEntityModifier(MainActivity.UNTOUCH_SCALE_MODIFIER.deepCopy());
			this.registerEntityModifier(MainActivity.UNTOUCH_ALPHA_MODIFIER.deepCopy());
		    } else {
			this.registerEntityModifier(MainActivity.TOUCH_SCALE_MODIFIER.deepCopy());
			this.registerEntityModifier(MainActivity.TOUCH_ALPHA_MODIFIER.deepCopy());
		    }
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
	    MainActivity.mainActivity.saveSettings();
	} else {
	    MainActivity.mainActivity.unmuteSounds();
	    muteOn.setVisible(true);
	    MainActivity.mainActivity.saveSettings();
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
	helpIsShown = false;
	
	
	slotMatrix = new SlotMatrix(this);
	prison = new Prison(this);
	respawn = new Respawn(this);

	attachChild(pauseScene);
	pauseScene.setZIndex(10000);
	sortChildren();
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

    public void backLight(float touchPointX, float touchPointY, String elementName) {

	
	int newBacklightRow = (int) ((touchPointY - SlotMatrix.getSlotPositionUp(0)) / (SlotMatrix.getSlotHeight() + BORDER_HEIGHT));
	int newBacklightColumn = (int) ((touchPointX - SlotMatrix.getSlotPositionLeft(0)) / (SlotMatrix.getSlotWidth() + BORDER_WIDTH));
	if ((backlightRow != newBacklightRow) || (backlightColumn != newBacklightColumn)) {
	    
	    for (Slot slot : slotsForCombo) {
		
		slot.removeEntityModifier();
	    }
	    slotsForCombo.clear();
	    detachChild(backlight);
	    backlightRow = newBacklightRow;
	    backlightColumn = newBacklightColumn;
	    if (elementName.equals("MAGIC_STICK")) {
		
		fullSlotBacklight(backlightRow, backlightColumn);
	    } else {
		
		emptySlotBacklight(backlightRow, backlightColumn, elementName);
	    }
	}
    }
    
    
    
    private void emptySlotBacklight(int i, int j, String elementName) {
	
	if (i < SlotMatrix.getROWS() && j < SlotMatrix.getCOLUMNS() && i >= 0 && j >=0 && slotMatrix.isSlotEmpty(i, j)) {
	    
	    backlight = new Sprite(SlotMatrix.getSlotPositionLeft(j) - MainActivity.TEXTURE_WIDTH / 800
                	    	 , SlotMatrix.getSlotPositionUp(i)
                	    	 , SlotMatrix.getSlotWidth() + MainActivity.TEXTURE_WIDTH / 210
                	    	 , SlotMatrix.getSlotHeight() + MainActivity.TEXTURE_HEIGHT / 220
                	    	 , MainActivity.mainActivity.storage.getTexture("gfx_empty.png")
                	    	 , MainActivity.mainActivity.getVertexBufferObjectManager());
     
            backlight.setAlpha(BACKLIGHT_ALPHA);
	    attachChild(backlight);
	    backlight.getParent().sortChildren();
	    if ((elementName.equals("FORESTER")) || (elementName.equals("FLYING_SQUIRREL"))) {
		
		return;
	    } else if (!elementName.equals("DROP")) {
		    
		outlineNeighborsForCombo(i, j, elementName);
	    } else {
		    
		outlineNeightborsForDropAdd(i, j);
	    }
	}
    }

    private void fullSlotBacklight(int i, int j) {
	
	if (i < SlotMatrix.getROWS() && j < SlotMatrix.getCOLUMNS() && i >= 0 && j >=0 && !slotMatrix.isSlotEmpty(i, j)) {
	    
	    Log.d("fores",slotMatrix.getElement(i, j));
	    backlight = new Sprite(SlotMatrix.getSlotPositionLeft(j) - MainActivity.TEXTURE_WIDTH / 800
            	    	 , SlotMatrix.getSlotPositionUp(i)
            	    	 , SlotMatrix.getSlotWidth() + MainActivity.TEXTURE_WIDTH / 210
            	    	 , SlotMatrix.getSlotHeight() + MainActivity.TEXTURE_HEIGHT / 220
            	    	 , MainActivity.mainActivity.storage.getTexture("gfx_empty.png")
            	    	 , MainActivity.mainActivity.getVertexBufferObjectManager());
	    backlight.setAlpha(BACKLIGHT_ALPHA);
	    attachChild(backlight);
	    backlight.setZIndex(700);
	    backlight.getParent().sortChildren();
	    
	    if (slotMatrix.getElement(i, j).equals("FORESTER")) {
		    
		outlineNeighborsForCombo(i, j, "HUT");
	    } else if (slotMatrix.getElement(i, j).equals("FLYING_SQUIRREL")) {
		    
		outlineNeighborsForCombo(i, j, "SQUIRREL");
	    }
	    slotsForCombo.add(slotMatrix.getSlot(i, j));
	    slotMatrix.getSlot(i, j).addEntityModifier();
	    
	}
    }
    
    private void outlineNeighborsForCombo(int row, int column, String elementName) {
	
	int forCombo = 0;
	LinkedList<Slot> list = new LinkedList<Slot>();
	if (row != 0) {
	    if (!slotMatrix.isSlotEmpty(row-1, column)) {
		Slot slot = slotMatrix.getSlot(row-1, column);
		if (slot.getElement().getName().equals(elementName)) {
		    forCombo++;
		    if (slot.getHasSimilarNeighbor()) {
			forCombo++;
			tryToFindMoreNeighbors(list, row-2, column, elementName);
			tryToFindMoreNeighbors(list, row-1, column-1, elementName);
			tryToFindMoreNeighbors(list, row-1, column+1, elementName);
		    }
		    list.add(slot);
		}
	    }
	}
	if (row != SlotMatrix.getROWS()-1) {
	    if (!slotMatrix.isSlotEmpty(row+1, column)) {
		Slot slot = slotMatrix.getSlot(row+1, column);
		if (slot.getElement().getName().equals(elementName)) {
		    forCombo++;
		    if (slot.getHasSimilarNeighbor()) {
			forCombo++;
			tryToFindMoreNeighbors(list, row+2, column, elementName);
			tryToFindMoreNeighbors(list, row+1, column-1, elementName);
			tryToFindMoreNeighbors(list, row+1, column+1, elementName);
		    }
		    list.add(slot);
		}
	    }
	}
	if (column != 0) {
	    if (!slotMatrix.isSlotEmpty(row, column-1)) {
		Slot slot = slotMatrix.getSlot(row, column-1);
		if (slot.getElement().getName().equals(elementName)) {
		    forCombo++;
		    if (slot.getHasSimilarNeighbor()) {
			forCombo++;
			tryToFindMoreNeighbors(list, row, column-2, elementName);
			tryToFindMoreNeighbors(list, row-1, column-1, elementName);
			tryToFindMoreNeighbors(list, row+1, column-1, elementName);
		    }
		    list.add(slot);
		}
	    }
	}
	if (column != SlotMatrix.getCOLUMNS()-1) {
	    if (!slotMatrix.isSlotEmpty(row, column+1)) {
		Slot slot = slotMatrix.getSlot(row, column+1);
		if (slot.getElement().getName().equals(elementName)) {
		    forCombo++;
		    if (slot.getHasSimilarNeighbor()) {
			forCombo++;
			tryToFindMoreNeighbors(list, row, column+2, elementName);
			tryToFindMoreNeighbors(list, row-1, column+1, elementName);
			tryToFindMoreNeighbors(list, row+1, column+1, elementName);
		    }
		    list.add(slot);
		}
	    }
	}
	if (forCombo >= 2) {
	    
	    for (Slot slot : list) {
		
		slot.addEntityModifier();
		slotsForCombo.add(slot);
	    }
	    outlineNeighborsForCombo(row, column, TableOfElements.getNextLvl(new Element(elementName)));
	    
	}
	
    }
    
    private void tryToFindMoreNeighbors(LinkedList<Slot> list, int row, int col, String elementName) {
	
	if ((row >= 0) && (row < SlotMatrix.getROWS()) && (col >=0) && (col < SlotMatrix.getCOLUMNS())) {
	    if (!slotMatrix.isSlotEmpty(row, col)) {
		if (slotMatrix.getElement(row, col).equals(elementName)) {
		    if (!list.contains(slotMatrix.getSlot(row, col))) {
			list.add(slotMatrix.getSlot(row, col));
		    }
		}
	    }
	}
    }
    
    private void outlineNeightborsForDropAdd(int row, int column) {
	
	LinkedList<Slot> slots = new LinkedList<Slot>();
	if (row != 0) {
	    if (!slotMatrix.isSlotEmpty(row-1, column)) {
		if (!slotMatrix.getElement(row-1, column).equals("FORESTER")  
		&& (!slotMatrix.getElement(row-1, column).equals("FLYING_SQUIRREL"))) {
		    
		    slots.add(slotMatrix.getSlot(row-1, column)); 
		}
	    }
	}
	if (row != SlotMatrix.getROWS()-1) {
	    if (!slotMatrix.isSlotEmpty(row+1, column)) {
		if (!slotMatrix.getElement(row+1, column).equals("FORESTER")  
		&& (!slotMatrix.getElement(row+1, column).equals("FLYING_SQUIRREL"))) {
			    
		    slots.add(slotMatrix.getSlot(row+1, column)); 
		}
	    }
	}
	if (column != 0) {
	    if (!slotMatrix.isSlotEmpty(row, column-1)) {
		if (!slotMatrix.getElement(row, column-1).equals("FORESTER")  
		&& (!slotMatrix.getElement(row, column-1).equals("FLYING_SQUIRREL"))) {
			    
		    slots.add(slotMatrix.getSlot(row, column-1)); 
		}
	    }
	}
	if (column != SlotMatrix.getCOLUMNS()-1) {
	    if (!slotMatrix.isSlotEmpty(row, column+1)) {
		if (!slotMatrix.getElement(row, column+1).equals("FORESTER")  
		&& (!slotMatrix.getElement(row, column+1).equals("FLYING_SQUIRREL"))) {
			    
		    slots.add(slotMatrix.getSlot(row, column+1)); 
		}
	    }
	}
	slotMatrix.filterSlotsLinkedList(slots);
	if ( !slots.isEmpty() ) {
	    
	    bestElementForDropAdd = slotMatrix.bestElementToAdd(slots.getFirst().getElement(), slots);
	    outlineNeighborsForCombo(row, column, bestElementForDropAdd.getName());
	} else {
	    
	    bestElementForDropAdd = new Element("POND");
	    outlineNeighborsForCombo(row, column, "POND");
	}
    }
    
    public Element getBestElementForDropAdd() {
	
	return bestElementForDropAdd;
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
	scoresText.setZIndex(999);
	sortChildren();
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
    
    public void attachHelpForElement(Element element) {
	
	if (helpIsShown) {
	    
	    detachHelpForElement();
	}
	if (element.getName().equals("FORESTER")) {
	    
	    makeHelpForForester();
	} else if (element.getName().equals("DROP")) {
	    
	    makeHelpForDrop();
	} else if (element.getName().equals("FLYING_SQUIRREL")) {
	    
	    makeHelpForFlyingSquirrel();
	} else if (element.getName().equals("MAGIC_STICK")) {
	    
	    makeHelpForMagicStick();
	} else {   
	    
	    helpTextureName1 = TableOfElements.getTextureName(element);
	    helpTextureName2 = helpTextureX3;
	    helpTextureName3 = helpTextureArrow;
	    helpTextureName4 = TableOfElements.getNextLevelTextureName(element);
	}    
	attachHelpSprites();
	
	
    }
    
    public void detachHelpForElement() {
	
	if (helpIsShown) {
	    
	    detachChild(helpPart1);
	    detachChild(helpPart2);
	    detachChild(helpPart3);
	    detachChild(helpPart4);
	    
	}
	helpIsShown = false;
	
    }
    
    private void makeHelpForDrop() {
	
	helpTextureName1 = TableOfElements.getTextureName(new Element("DROP"));
	helpTextureName2 = helpTextureTwoQuestions;
	helpTextureName3 = helpTextureArrow;
	helpTextureName4 = helpTextureQuestionWithCrown;
    }
    
    private void makeHelpForFlyingSquirrel() {
	
	helpTextureName1 = TableOfElements.getTextureName(new Element("MAGIC_STICK"));
	helpTextureName2 = TableOfElements.getTextureName(new Element("FLYING_SQUIRREL"));
	helpTextureName3 = helpTextureArrow;
	helpTextureName4 = TableOfElements.getTextureName(new Element("SQUIRREL"));
    }
    
    private void makeHelpForForester() {
	
	helpTextureName1 = TableOfElements.getTextureName(new Element("MAGIC_STICK"));
	helpTextureName2 = TableOfElements.getTextureName(new Element("FORESTER"));
	helpTextureName3 = helpTextureArrow;
	helpTextureName4 = TableOfElements.getTextureName(new Element("HUT"));
    }
    private void makeHelpForMagicStick() {
	
	helpTextureName1 = TableOfElements.getTextureName(new Element("MAGIC_STICK"));
	helpTextureName2 = helpTextureQuestion;
	helpTextureName3 = helpTextureArrow;
	helpTextureName4 = helpTextureShadow;
    }
    
    private void attachHelpSprites() {
	
	helpPart1 = new Sprite( MainActivity.TEXTURE_WIDTH*190/2000 
		    , MainActivity.TEXTURE_HEIGHT*1780/2000
		    , MainActivity.TEXTURE_WIDTH/8
		    , MainActivity.TEXTURE_HEIGHT/13
		    , MainActivity.mainActivity.storage.getTexture(helpTextureName1)
		    , MainActivity.mainActivity.getVertexBufferObjectManager());
	
	helpPart2 = new Sprite( MainActivity.TEXTURE_WIDTH*490/2000 
		    , MainActivity.TEXTURE_HEIGHT*1780/2000
		    , MainActivity.TEXTURE_WIDTH/8
		    , MainActivity.TEXTURE_HEIGHT/13
		    , MainActivity.mainActivity.storage.getTexture(helpTextureName2)
		    , MainActivity.mainActivity.getVertexBufferObjectManager());
	
	helpPart3 = new Sprite( MainActivity.TEXTURE_WIDTH*790/2000 
		    , MainActivity.TEXTURE_HEIGHT*1780/2000
		    , MainActivity.TEXTURE_WIDTH/8
		    , MainActivity.TEXTURE_HEIGHT/13
		    , MainActivity.mainActivity.storage.getTexture(helpTextureName3)
		    , MainActivity.mainActivity.getVertexBufferObjectManager());

	helpPart4 = new Sprite( MainActivity.TEXTURE_WIDTH*1090/2000 
		    , MainActivity.TEXTURE_HEIGHT*1780/2000
		    , MainActivity.TEXTURE_WIDTH/8
		    , MainActivity.TEXTURE_HEIGHT/13
		    , MainActivity.mainActivity.storage.getTexture(helpTextureName4)
		    , MainActivity.mainActivity.getVertexBufferObjectManager());
	
	attachChild(helpPart1);
	attachChild(helpPart2);
	attachChild(helpPart3);
	attachChild(helpPart4);
	
	helpIsShown = true;
    }
}
