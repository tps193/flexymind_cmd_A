package com.example.forestgame;

import java.io.IOException;
import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

import android.util.Log;

import com.example.forestgame.element.Element;
import com.example.forestgame.element.TableOfElements;

public class SlotMatrix {
    
    private Slot[][] matrix;
    private static final int ROWS = 6;
    private static final int COLUMNS = 6;
    private int lastEditedSlotRow;
    private int lastEditedSlotColumn;
    private static int NUMBER_OF_ElEMENTS_ON_START = 18;
    private int score;
    private int filledSlots;
    private GameScene gameScene;
    
    private final static float SLOT_WIDTH = MainActivity.TEXTURE_WIDTH/8;
    private final static float SLOT_HEIGHT = MainActivity.TEXTURE_HEIGHT/13;
    private final static int FIRST_SLOT_POSITION_LEFT = 96;
    private final static int FIRST_SLOT_POSITION_UP = 218;
    private final static float BORDER_WIDTH = 24;
    private final static float BORDER_HEIGHT = 26; 
    
    ParallelEntityModifier entityModifier;
    float animationDuration = 0.2f;
    float fromAlpha = 1;
    float toAlpha = 0;
    IEaseFunction easeFunction = EaseLinear.getInstance();
    TimerHandler spriteTimerHandler;
    
    public SlotMatrix(GameScene scene) {
	
	gameScene = scene;
	matrix = new Slot[ROWS][COLUMNS];
	init();
	viewSlots();
    }
    
    public void putToSlot( Element element
	    		 , int row
	    		 , int col) {
	
	if (isSlotEmpty(row, col)) {
	    
	    addToSlot(element, row, col);
	    lastEditedSlotRow = row;
	    lastEditedSlotColumn = col;
	    MainActivity.mainActivity.mStep.play();
	    update();
	}
    }
    
    // has to be used always before using addToSLot(..)
    public boolean isSlotEmpty( int row
	    		      , int col) {
	
	return matrix[row][col].isEmpty();
    }
    
    private void update() {
	
	checkSimilarElements();
	gameScene.setScores(getScore());
	viewSlots();
	
	filledSlots = 0;
	for (int i = 0; i < ROWS; i++) {
	    
	    for (int j = 0; j < COLUMNS; j++) {
		
		if (!isSlotEmpty(i, j)) {
		    
		    filledSlots++;
		}
	    }
	}
	if (filledSlots == ROWS*COLUMNS) {
	    
	    Log.d("GAME", "OVER");
	    MainScene.showGameOverScene();
	}
    }
    
    public void init() {
	
	for (int i = 0; i < ROWS; i++) {
	    
	    for (int j = 0; j < COLUMNS; j++) {
		
		matrix[i][j] = new Slot();
	    }
	}
	Random random = new Random();
	for (int i = 0; i < NUMBER_OF_ElEMENTS_ON_START; i++) {
	    
	    int row = (int) (random.nextDouble() * ROWS);
	    int col = (int) (random.nextDouble() * COLUMNS);
	    if (isSlotEmpty(row, col)) {
		
		addToSlot(TableOfElements.getRandomElement(), row, col); //Not putToSlot(..) 
								     //because of the update() method
	    } else {
		i--;
	    }
	}
	score=0;
	gameScene.setScores(score);
    }
    
    public int getScore() {
	
	return score;
    }
    
    public static float getSlotPositionLeft(int col) {
	return FIRST_SLOT_POSITION_LEFT + col * (SLOT_WIDTH + BORDER_WIDTH);
    }
    
    public static float getSlotPositionUp(int row) {
	return FIRST_SLOT_POSITION_UP + row * (SLOT_HEIGHT + BORDER_HEIGHT);
    }
    
    public static float getSlotWidth() {
	return SLOT_WIDTH;
    }
    
    public static float getSlotHeight() {
	return SLOT_HEIGHT;
    }
    
    
    public void reInit() {
        
	for (int i = 0; i < ROWS; i++) {
	    
            for (int j = 0; j < COLUMNS; j++) {
        	
        	clearSlot(i, j);
            }
        }
        init();
        viewSlots();
    }
    
    // собирает имена
    public String[][] getNamesForSave() {
	
	String[][] namesMatrix = new String[ROWS][COLUMNS];
	
	for(int i = 0; i < ROWS; i++) {
	    for(int j = 0; j < COLUMNS; j++) {
		if(matrix[i][j].isEmpty()) namesMatrix[i][j] = null;
		else namesMatrix[i][j] = matrix[i][j].getElement().getName();
	    }
	}
	
	return namesMatrix;
    }
    
    //инитит загруженную игру
    public void loadInit() throws IOException {
	
	for (int i = 0; i < ROWS; i++) {
	    
            for (int j = 0; j < COLUMNS; j++) {
        	
        	clearSlot(i, j);
            }
        }
	setMatrix(MainActivity.mainActivity.loadProgress());
	viewSlots();
	//update();
    }
    
    
    //устанавливает загруженную матрицу
    private void setMatrix(String[][] namesMatrix) {
	
        for(int i = 0; i < ROWS; i++) {
            
            for(int j = 0; j < COLUMNS; j++) {
        	
        	if(namesMatrix[i][j] == null) matrix[i][j] = new Slot();
        	
        	else { matrix[i][j] = new Slot();
        	    addToSlot(new Element(namesMatrix[i][j]), i, j);
        	}
            }
        } 
    }

    // method for visualizing textures on GameScene
    private void viewSlots() {
	
	for (int i = 0; i < ROWS; i++) {
	    
	    for (int j = 0; j < COLUMNS; j++) {

		Slot slot = matrix[i][j];
		gameScene.detachChild(slot.getSprite());
                if (!slot.isEmpty()) {
                    
                   TextureRegion slotTexture = MainActivity.mainActivity.storage.getTexture(   TableOfElements
                                                                                                . getTextureName
                                                                                                ( slot.getElement()));

                   slot.setSprite(new Sprite (getSlotPositionLeft(j)
                                           , getSlotPositionUp(i)
                                           , SLOT_WIDTH
                                           , SLOT_HEIGHT
                                           , slotTexture
                                           , MainActivity.mainActivity.getVertexBufferObjectManager()));

                   gameScene.attachChild(slot.getSprite());
		   slot.getSprite().setZIndex(300);
		   slot.getSprite().getParent().sortChildren();
                }
	    }
	}
    }
    
      
    private void clearSlot(int row, int col) {
	
	 Slot s = matrix[row][col];
	 gameScene.detachChild(s.getSprite());
	 matrix[row][col] = new Slot();
    }
    
    // putting Element into Slot and changing flags if needed
    private void addToSlot(Element element, int row, int col) {
	
	matrix[row][col].addElement(element);
	if (row > 0) {
	    
	    analyzeNeighbor(row, col, row-1, col);
	}
	if (row < ROWS-1) {
	    
	    analyzeNeighbor(row, col, row+1, col);
	}
	if (col > 0) {
	    
	    analyzeNeighbor(row, col, row, col-1);
	}
	if (col < COLUMNS-1) {
	    
	    analyzeNeighbor(row, col, row, col+1);
	}
    }
    
    // setting hasSimilarNeighbor and readyForNextLevel flags
    // flag readyForNextLevel doesn't have to be set for every Slot in chain, only for the last edited
    private void analyzeNeighbor( int thisRow
	    			, int thisCol
	    			, int neighborRow
	    			, int neightborCol) {
	
	Slot s = matrix[thisRow][thisCol];
	Slot s1 = matrix[neighborRow][neightborCol];
	if (s1.isSimilarTo(s.getElement())) {
	    
	    if (s.getHasSimilarNeighbor()) {
		
		  s1.setReadyForNextLevel(true);
		  s.setReadyForNextLevel(true);
	    }
	    s.setHasSimilarNeighbor(true);
	    
	    if (s1.getHasSimilarNeighbor()) {
		
		s1.setReadyForNextLevel(true);
		s.setReadyForNextLevel(true);
	    } else {
		
		s1.setHasSimilarNeighbor(true);
	    }
	}
    }
    
    // checking if the last added element is the third (or more) and has to get next level 
    private void checkSimilarElements() {
	
	int curentRow = lastEditedSlotRow;
	int curentCol = lastEditedSlotColumn;
	
	if (matrix[curentRow][curentCol].getReadyForNextLevel()) {
	    
	    Slot slot = matrix[curentRow][curentCol];
	    Element element = slot.getElement();
	    clearSlot(curentRow, curentCol);
	    gameScene.detachChild(matrix[curentRow][curentCol].getSprite());
	    
	    if (curentRow > 0) {
		
		if (matrix[curentRow-1][curentCol].isSimilarTo(element)) {
		    
		    collectSimilarElements(curentRow, curentCol, curentRow-1, curentCol, element);
		}
	    }
	    if (curentRow < ROWS-1) {
		
		if (matrix[curentRow+1][curentCol].isSimilarTo(element)) {
		    
		    collectSimilarElements(curentRow, curentCol, curentRow+1, curentCol, element);
		}
	    }
	    if (curentCol > 0) {
		
		if (matrix[curentRow][curentCol-1].isSimilarTo(element)) {
		    
		    collectSimilarElements(curentRow, curentCol, curentRow, curentCol-1, element);
		}
	    }
	    if (curentCol < COLUMNS-1) {
		
		if (matrix[curentRow][curentCol+1].isSimilarTo(element)) {
		    
		    collectSimilarElements(curentRow, curentCol, curentRow, curentCol+1, element);
		}
	    }
	    element.changeToNextLvl();
	    addToSlot(element, curentRow, curentCol);
	    update();
	}
    }
    
    //recoursively collecting the chain of similar elements, removing them from field
    private void collectSimilarElements( int toRow
	    			       , int toCol
	    			       , int fromRow
	    			       , int fromCol
	    			       , Element element) {
	
	graphicalMoving( toRow
		       , toCol
		       , fromRow
		       , fromCol);
	
	score = score + matrix[fromRow][fromCol].getScore();
 	clearSlot(fromRow, fromCol);
 	
	if (fromRow > 0) {
	    
	    if (matrix[fromRow-1][fromCol].isSimilarTo(element)) {
		
		collectSimilarElements( toRow
				      , toCol
				      , fromRow-1
				      , fromCol
				      , element);
	    }
	}
	if (fromRow < ROWS-1) {
	    
	    if (matrix[fromRow+1][fromCol].isSimilarTo(element)) {
		
		collectSimilarElements( toRow
				      , toCol
				      , fromRow+1
				      , fromCol
				      , element);
	    }
	}
	if (fromCol > 0) {
	    
	    if (matrix[fromRow][fromCol-1].isSimilarTo(element)) {
		
		collectSimilarElements( toRow
			              , toCol
			              , fromRow
			              , fromCol-1
			              , element);
	    }
	}
	if (fromCol < COLUMNS-1) {
	    
	    if (matrix[fromRow][fromCol+1].isSimilarTo(element)) {
		
		collectSimilarElements( toRow
				      , toCol
				      , fromRow
				      , fromCol+1
				      , element);
	    }
	}
    }
    
    private void graphicalMoving(final int toRow, final int toCol, final int fromRow, final int fromCol) {
	
	entityModifier = new ParallelEntityModifier(new AlphaModifier(animationDuration
			    					    , fromAlpha
			    					    , toAlpha
			    					    , easeFunction)

						  , new MoveModifier(animationDuration
							  	   , getSlotPositionLeft(fromRow)
							  	   , getSlotPositionUp(fromCol)
							  	   , getSlotPositionLeft(toRow)
							  	   , getSlotPositionUp(toCol)
							  	   , easeFunction));
		
	matrix[fromRow][fromCol].getSprite().registerEntityModifier(entityModifier);

	MainActivity.mainActivity.getEngine().registerUpdateHandler(
		spriteTimerHandler = new TimerHandler(animationDuration
						    , false
						    , new ITimerCallback() {
		    
		    					@Override
							public void onTimePassed(TimerHandler pTimerHandler) {
							// TODO Auto-generated method stub
							}
						    }));
    }    
    
    public static int getROWS() {
	
	return ROWS;
    }
    
    public static int getCOLUMNS() {
	
	return COLUMNS;
    }
   
    public static int getPrisonPlaceRow() {
	
	return ROWS + 1;
    }
    
    
    public static int getPrisonPlaceColumn() {
	
	return COLUMNS + 1;
    }
    
    public static int getRespawnPlaceRow() {
	
	return ROWS + 2;
    }
    
    
    public static int getRespawnPlaceColumn() {
	
	return COLUMNS + 2;
    }
    
    public static int getMilkPointRow() {
	
	return ROWS + 20;
    }

    public static int getMilkPointColumn() {
	
	return COLUMNS + 20;
    }
}